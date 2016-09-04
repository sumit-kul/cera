package support.community.lucene.search;

import java.io.StringReader;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.HitCollector;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.NullFragmenter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.springframework.util.Assert;

import support.community.database.IndexedScrollerPage;
import support.community.database.IndexedScrollerPageImpl;
import support.community.database.QueryPaginator;
import support.community.lucene.index.EntityHandler;
import support.community.lucene.index.EntityIndex;

import com.era.community.blog.dao.BlogEntry;
import com.era.community.communities.dao.Community;
import com.era.community.events.dao.Event;
import com.era.community.forum.dao.ForumTopic;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.User;
import com.era.community.wiki.dao.WikiEntry;

public class EntityOnTheFlySearchScroller implements QueryPaginator
{   
	private int m_pageSize = 20; 

	private Vector<SortField> m_scrollKeys = new Vector<SortField>(2);

	private EntityIndex m_index;

	private IndexSearcher m_searcher;   

	private Query m_decoratedQuery;
	private String m_userQuery;

	private int m_maxSummarySize = 400;
	private User currentUser;

	Log logger = LogFactory.getLog(getClass());


	EntityOnTheFlySearchScroller(EntityIndex index, IndexSearcher searcher, String userQuery, Query decoratedQuery) throws Exception
	{
		m_index = index;
		m_searcher = searcher;
		m_userQuery = userQuery;
		m_decoratedQuery = decoratedQuery;
	}

	public IndexedScrollerPage readPage(int pageNumber) throws Exception
	{
		QuotedExactQueryParser q2 =  new QuotedExactQueryParser("ff", m_index.getAnalyzer());
		q2.parse(m_userQuery);

		Assert.isTrue(pageNumber>0, "Invalid page number ["+pageNumber+"]. Must be > 0.");

		logger.debug("Rewriting query:"+m_decoratedQuery.toString());

		m_decoratedQuery = m_decoratedQuery.rewrite(m_searcher.getIndexReader());

		logger.debug("Executing query:"+m_decoratedQuery.toString());


		Hits hits = m_searcher.search(m_decoratedQuery, getSort());

		IndexedScrollerPageImpl data = new IndexedScrollerPageImpl();

		int rowCount = hits.length();
		int pageCount = rowCount==0 ? 0 : ((int)((rowCount-1)/m_pageSize))+1;

		if (pageNumber>pageCount&&pageCount>0) pageNumber = pageCount; 

		int start = (pageNumber-1)*m_pageSize;
		int end = start + m_pageSize - 1;

		 for (int n=start; n<=end&&n<rowCount;  n++) {

			 Document doc = hits.doc(n);
			 EntitySearchHit hit = new EntitySearchHit();
			 hit.setHitNumber(n-start);

			 Object entity = mapHitToEntity(doc);
			 if (entity==null) continue; //entity has been deleted from database but not from index

			 //hit.setEntity(entity); entity will not work in pagination with json...
			 if (entity instanceof Community) {
				 Community community = (Community)entity;
				 hit.setMemberCount(community.getMemberCount());
				 hit.setCreatorId(community.getCreatorId());
				 hit.setCreatorFullName(community.getCreator().getFullName());
			 }
			 hit.setScore(hits.score(n));

			 EntityHandler handler = m_index.getEntityHandlerForClass(entity.getClass());
			 hit.setTitle(handler.getTitle(entity));
			 hit.setDate(handler.getDate(entity));
			 hit.setModified(handler.getModified(entity));
			 hit.setLink(handler.getLink(entity, m_userQuery, m_index));
			 hit.setTitle2(handler.getTitle2(entity, m_userQuery, m_index));
			 hit.setTitle3(handler.getTitle3(entity, m_userQuery, m_index));

			 /* Community related fields for community search only */
			 Community com = null;
			 User user = null;
			 String typeNamePath = doc.getField(EntityIndex.ENTITY_TYPE_FIELD).stringValue();
			 String typeName = typeNamePath.substring(typeNamePath.lastIndexOf('/')+1);
			 hit.setEntityTypeName(typeName);
			 if ("PublicCommunity".equals(typeName) || "ProtectedCommunity".equals(typeName)) {
				 com = (Community)entity;
				 hit.setIscommunity("true");
				 user = com.getCreator();
				 hit.setLogoPresent(Boolean.toString(com.isLogoPresent()));
				 hit.setDateCommunityCreation(com.getCreated());
			 } else {
				 if ("BlogEntry".equals(typeName)) {
					 BlogEntry bi = (BlogEntry)entity;
					 com = bi.getCommunityBlog().getCommunity();
					 user = bi.getPoster();
				 } else if ("Document".equals(typeName)){
					 com.era.community.doclib.dao.Document document = (com.era.community.doclib.dao.Document)entity;
					 com = document.getLibrary().getCommunity();
					 user = document.getPoster();
				 } else if ("WikiEntry".equals(typeName)){
					 WikiEntry we = (WikiEntry)entity;
					 com = we.getWiki().getCommunity();
					 user = we.getPoster();
				 } else if ("Event".equals(typeName)){
					 Event ev = (Event)entity;
					 com = ev.getEventCalendar().getCommunity();
					 user = ev.getPoster();  
				 } else if ("ForumTopic".equals(typeName)){
					 ForumTopic ft = (ForumTopic)entity;
					 com = ft.getForum().getCommunity();
					 user = ft.getAuthor();
				 } else if ("User".equals(typeName)){
					 user = (User)entity;
					 hit.setIsUser("true");
					 hit.setConnectionCount(getConnectionCountInfo(user));
				 }
			 }

			 if (com != null) {
				 hit.setCommunityName(com.getName());
				 hit.setCommunityId(com.getId());
				 hit.setIsprivateCommunity(String.valueOf(com.isPrivate()));
			 }
			 if (user != null) {
				 hit.setUserId(String.valueOf(user.getId()));
				 hit.setUserName(user.getFirstName() + " " + user.getLastName());
				 if (user.isPhotoPresent()) hit.setIsPhotoPresent("true");
			 }
			 data.add(hit);
		 }

		 data.setRowCount(rowCount);
		 data.setPageCount(pageCount);
		 data.setCurrentPageNumber(pageNumber);
		 data.setScrollerPageSize(m_pageSize);

		 return data;
	}
	
	public boolean isMember(Community community, User currentUser) throws Exception
	{
		if (currentUser == null ) return false;
		return community.isMember(currentUser);
	}

	public boolean isMembershipRequested(Community community, User currentUser) throws Exception
	{
		if (currentUser == null ) return false;
		return community.isMemberShipRequestPending(currentUser);
	}
	
	private String getConnectionCountInfo(User user) throws Exception
	{
		String returnString = "";
		if (user.getConnectionCount() > 0) {
			returnString = "<a href='pers/connectionList.do?id="+user.getId()+"' class='connname' >"+user.getConnectionCount()+" Connection";
		} else {
			returnString = "0 Connection";
		}
		if (user.getConnectionCount() > 1) {
			returnString += "s </a>";
		} else {
			returnString += "</a>";
		}
		return returnString;
	}
	
	private String getConnectionInfo(User user, User current, Contact contact) throws Exception
	{
		String returnString = "";
		if (current == null) // No action for User
			return "";
		if (current.getId() == user.getId()) // No action for User
			return "";
		if (contact != null) {
			if (contact.getOwningUserId() == current.getId()) {
				if (contact.getStatus() == 0 || contact.getStatus() == 2 || contact.getStatus() == 3) {
					// connection request sent
					returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+user.getId()+"&currId="+current.getId()+"&userName="+user.getFullName()+"'>Request Sent</span>";
				} else if (contact.getStatus() == 1) {
					// Already connected
					returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+user.getId()+"&currId="+current.getId()+"&userName="+user.getFullName()+"'>Connected</span>";
				} else if (contact.getStatus() == 4) {
					// user has spammed you and you have cancelled the request...
					returnString = "<a class='button normalTip' onClick='addConnectionInner("+user.getId()+", \""+user.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Add "+user.getFullName()+" to my connections' >Add to my connections</a>";
				}
			} else {
				if (contact.getStatus() == 0) {
					returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+user.getId()+"&currId="+current.getId()+"&userName="+user.getFullName()+"'>Request Received</span>";
				} else if (contact.getStatus() == 1) {
					// Already connected
					returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+user.getId()+"&currId="+current.getId()+"&userName="+user.getFullName()+"'>Connected</span>";
				} else if (contact.getStatus() == 3 || contact.getStatus() == 4) {
					// Spammed case
					returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+user.getId()+"&currId="+current.getId()+"&userName="+user.getFullName()+"'>Spammed</span>";
				}
			}
		} else { 
			// Add to my connections
			returnString = "<a class='button normalTip' onClick='addConnectionInner("+user.getId()+", \""+user.getFullName()+"\");' href='javascript:void(0);'" +
			"title='Add "+user.getFullName()+" to my connections'>Add to my connections</a>";
		}
		return returnString;
	}

	protected String getSummary(EntityHandler handler, Object entity) throws Exception
	{
		/*
		 * If the description has hits then use it.
		 */
		String description = handler.getDescription(entity);
		String summary = createSummary(EntityIndex.DESCRIPTION_FIELD, description, m_decoratedQuery);
		if (summary.trim().length()>0) return summary;

		/*
		 * If the entity content has hits use it but skip this if the title already has hits because reading the
		 * content is typically quite expensive. 
		 */
		if (!titleIsHit(handler.getTitle(entity))) {
			String content = handler.getContent(entity, m_index.getBinaryDataHandler(), true);
			summary = createSummary(EntityIndex.CONTENT_FIELD, content, m_decoratedQuery);
			if (summary.trim().length()>0) return summary;
		}

		/*
		 * Return the entity description (truncated if necessary).
		 */
		if (description!=null&&description.trim().length()>0)
			return truncate(description, "...");

		return "";
	}

	protected String createSummary(String field, String text, Query query) throws Exception
	{
		if (text==null||text.trim().length()==0) return "";

		if (text.length()>m_maxSummarySize)
			return extract(field, text, query);
		else
			return highlight(field, text, query);
	}    

	protected String highlight(String field, String text, Query query) throws Exception
	{
		Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter(), new QueryScorer(query));
		highlighter.setTextFragmenter(new NullFragmenter());

		TokenStream tokenStream = m_index.getAnalyzer().tokenStream(field, new StringReader(text));
		String summary = highlighter.getBestFragments(tokenStream, text, 1, "");

		return summary;
	}

	protected String extract(String field, String text, Query query) throws Exception
	{
		SimpleFragmenter fragmenter = new SimpleFragmenter((int)(m_maxSummarySize/3));
		Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter(), new QueryScorer(query));
		highlighter.setTextFragmenter(fragmenter);

		TokenStream tokenStream = m_index.getAnalyzer().tokenStream(field, new StringReader(text));

		String[] frags = highlighter.getBestFragments(tokenStream, text, 3);
		if (frags.length==0) 
			return "";
		else if (frags.length==1) {
			fragmenter.setFragmentSize(m_maxSummarySize);
			tokenStream = m_index.getAnalyzer().tokenStream(field, new StringReader(text));
			return highlighter.getBestFragments(tokenStream, text, 1, "");
		}

		return StringUtils.join(frags, "....");
	}

	private boolean titleIsHit(String title) throws Exception
	{
		String s = extract(EntityIndex.TITLE_FIELD, title, m_decoratedQuery);
		return s.trim().length()>0;
	}

	protected final String truncate(String text, String ellipsis)
	{
		return text.subSequence(0, Math.min(text.length(),m_maxSummarySize))+
		(text.length()>m_maxSummarySize?ellipsis:"");

	}

	protected Object mapHitToEntity(Document doc) throws Exception
	{
		String typeNamePath = doc.getField(EntityIndex.ENTITY_TYPE_FIELD).stringValue();
		logger.debug("Mapping hit of type ["+typeNamePath+"]");
		return m_index.getEntity(doc);
	}

	public int readRowCount() throws Exception
	{
		Hits hits = m_searcher.search(m_decoratedQuery);
		return hits.length();
	}

	/**
	 * Iterate over all the rows returned by the query for this scroller calling a handler
	 * for each row in turn.
	 */
	public void doForAllRows(final EntitySearchScrollerCallback callback) throws Exception
	{
		HitCollector coll = new ScrollerHitCollector(m_searcher, callback);
		m_searcher.search(m_decoratedQuery, coll);
	}

	protected static class ScrollerHitCollector extends HitCollector
	{
		private EntitySearchScrollerCallback callback;
		private IndexSearcher searcher;

		public ScrollerHitCollector(IndexSearcher searcher, EntitySearchScrollerCallback callback)
		{
			this.callback = callback;
			this.searcher = searcher;
		}

		public void collect(int rowNum, float score)
		{
			try {
				Document doc = searcher.doc(rowNum);
				callback.handleRow(doc, score, rowNum);
			}
			catch (Exception e) {
				throw new RuntimeException(e.toString());
			}
		}

	}

	public void addScrollKey(String sName, String sDir, String sType) throws Exception
	{
		int iType;
		if (sType.equals(QueryPaginator.TYPE_TEXT))
			iType = SortField.STRING;        
		else if (sType.equals(QueryPaginator.TYPE_INTEGER))
			iType = SortField.INT;
		else if (sType.equals(QueryPaginator.TYPE_DATE))
			iType = SortField.STRING;
		else
			throw new Exception("Invalid type ["+sType+"]");        
		m_scrollKeys.add(new SortField(sName, iType, sDir.equals(QueryPaginator.DIRECTION_DESCENDING)));
	}

	private Sort getSort() throws Exception
	{
		if (m_scrollKeys.isEmpty()) return null;
		SortField[] fields = new SortField[m_scrollKeys.size()];
		for (int n=0; n<fields.length; n++) {
			fields[n] = (SortField)m_scrollKeys.get(n);
		}
		return new Sort(fields);
	}    

	public int getPageSize()
	{
		return m_pageSize;
	}

	public void setPageSize(int pageSize)
	{
		m_pageSize = pageSize;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}