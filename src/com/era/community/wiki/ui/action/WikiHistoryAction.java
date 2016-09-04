package com.era.community.wiki.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.util.StringHelper;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.Taggable;
import com.era.community.communities.dao.Community;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryFinder;
import com.era.community.wiki.ui.dto.WikiEntryDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/cid/[cec]/wiki/wikiHistory.do" 
 */
public class WikiHistoryAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected WikiEntryFinder wikiEntryFinder; 
	protected UserFinder userFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected TagFinder tagFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		
		if (context.getCurrentUser() == null) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do");
		}

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}
		WikiEntry entry = null;
		try {
			entry = wikiEntryFinder.getLatestWikiEntryForEntryId(cmd.getEntryId());
		} catch (ElementNotFoundException e) {
			return new ModelAndView("/pageNotFound");
		}


		if (entry != null) {
			QueryScroller scroller = entry.listHistoryByEditDate(true);
			
			if (pageNum != null) {
				HttpServletResponse resp = contextManager.getContext().getResponse();
				JSONObject json = null;
				if (pNumber > 0) {
					scroller.setBeanClass(RowBean.class, this);
					if (cmd.getPageSize() == 10) {
						cmd.setPageSize(50);
					}
					scroller.setPageSize(cmd.getPageSize());
					IndexedScrollerPage page = scroller.readPage(pNumber);
					json = page.toJsonString(pNumber);
					json.put("userSysAdmin", Boolean.toString(context.isUserSysAdmin()));
				} else {
					json = new JSONObject();
					json.put("pageNumber", pNumber);
					JSONArray jData = new JSONArray();
					json.put("aData", jData);
				}
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
				return null;
			} else {
				cmd.setSearchType("Wiki");
				cmd.setQueryText(entry.getTitle());

				cmd.copyPropertiesFrom(entry);

				cmd.setPopularTags( entry.getPopularTags() );
				cmd.setWikiEntryFinder(wikiEntryFinder);
				User user = userFinder.getUserEntity( entry.getPosterId() );
				cmd.setLastEditUserName( user.getFirstName() + " "  + user.getLastName() );
				cmd.setPhotoPresent(user.isPhotoPresent());
				cmd.setNextSequenceNumber( wikiEntryFinder.getNextSequenceNumberForEntry(entry) );
				cmd.setDatePosted(entry.getDatePosted());

				int versionCount = wikiEntryFinder.getWikiEntryVersionCount(entry.getEntryId());
				cmd.setVersionCount(versionCount);
				
				WikiEntry firstVersion = null;
				if (versionCount == 1) {
					firstVersion = wikiEntryFinder.getLatestWikiEntryForEntryId(entry.getEntryId());
				} else {
					firstVersion = wikiEntryFinder.getWikiEntryForEntryIdAndSequence(entry.getEntryId(), 1);
				}
				
				cmd.setMember(isMember(context.getCurrentCommunity()));
				cmd.setCreatorId(firstVersion.getPosterId());
				cmd.setCreationTime(firstVersion.getCreated().toString());
				User creator = userFinder.getUserEntity( firstVersion.getPosterId() );
				cmd.setCreatorName(creator.getFullName());
				cmd.setCreatorPhotoPresent(creator.isPhotoPresent());
				cmd.setEntryId(entry.getEntryId());
				List contrb = wikiEntryFinder.getContributors(entry.getEntryId());
				if (contrb != null && contrb.size() > 0) {
					cmd.setContributors(contrb);
				}

				cmd.setVisitCount(entry.getVisitors());
				cmd.setLastVisitTime(entry.getLastVisitorsTime());
				
				scroller.setPageSize(cmd.getPageSize());
				cmd.setPageCount(scroller.readPageCount());
				cmd.setRowCount(scroller.readRowCount());
				return new ModelAndView("/wiki/wikiHistory");
			}
		} else {
			return new ModelAndView("/pageNotFound");
		}
	}

	public boolean isMember(Community community) throws Exception
	{
		if (contextManager.getContext().getCurrentUser() == null ) return false;
		if (contextManager.getContext().isUserAuthenticated() == false) return false;
		int currentUser = contextManager.getContext().getCurrentUser().getId();
		return community.isMember(currentUser);
	}
	
	public class Command extends WikiEntryDto implements CommandBean, Taggable
	{        
		private String lastEditUserName;
		private int nextSequenceNumber;
		private String tags;
		private TreeMap popularTags;
		private List wikiHistoryEntries = new ArrayList();
		private int likeCount;
		private int visitCount;
		private String lastVisitTime;
		private boolean alreadyLike;
		private boolean likeAllowed;
		private boolean photoPresent;

		private int creatorId;
		private String creatorName;
		private String creationTime;
		private boolean creatorPhotoPresent;
		private int versionCount;
		private boolean member = false;

		private List contributors = new ArrayList();

		public String getTaggedKeywords(){
			String taggedKeywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(this.getId(), 0, 20, "WikiEntry");
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='"+contextManager.getContext().getCurrentCommunityUrl()+"/search/searchByTagInCommunity.do?filterTag="+tag+"' class='euInfoSelect normalTip' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += ", ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

		public String getDatePostOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("dd MMM, yy");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				Date date = formatter.parse(getDatePosted());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return getDatePosted();
			}
		}

		public String getDatePostOnHover() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			try {
				Date date = formatter.parse(getDatePosted());
				return fmt2.format(date);
			} catch (ParseException e) {
				return getDatePosted();
			}
		}

		public String getCreatedOnHover() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			try {
				Date date = formatter.parse(getCreationTime());
				return fmt2.format(date);
			} catch (ParseException e) {
				return getCreationTime();
			}
		}

		public String getCreatedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("dd MMM, yy");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				Date date = formatter.parse(getCreationTime());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return getCreationTime();
			}
		}

		public String getTags() {
			return tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public boolean isCurrentUserSubscribed() throws Exception
		{
			try {
				if (contextManager.getContext().getCurrentUser() == null)
					return false;
				subscriptionFinder.getWikiEntrySubscriptionForUser(getEntryId(), contextManager.getContext().getCurrentUser().getId());
				return true;
			}
			catch (ElementNotFoundException x) {
				return false;
			}
		}

		public boolean isRollbackPossible() throws Exception
		{
			return wikiEntryFinder.getPreviousWikiEntryForEntryId(getEntryId()) !=null;
		}

		public String getLastEditUserName()
		{
			return lastEditUserName;
		}

		public void setLastEditUserName(String lastEditUserName)
		{
			this.lastEditUserName = lastEditUserName;
		}                 

		public String getDisplayBody()
		{
			/* Replace newline with <br/> Format urls*/
			return  StringHelper.formatForDisplay(getBody());
		}

		public boolean isOlderVersionAvailable() {
			if ( getEntrySequence() > 1  ) {
				return true;
			}
			else {
				return false;     
			}            
		}

		public int getOlderSequence() {
			if ( isOlderVersionAvailable() ) {
				return getEntrySequence() - 1;
			}
			else {
				return 0;    
			}            
		}

		public int getNewerSequence() {
			if ( isNewerVersionAvailable() ) {
				return getEntrySequence() + 1;
			}
			else {
				return 0;    
			}            
		}

		public boolean isNewerVersionAvailable() {
			if ( getEntrySequence() != Integer.MAX_VALUE  && 
					getNextSequenceNumber() != (getEntrySequence() + 1) ) {
				return true;
			}
			else {
				return false;     
			}
		}

		public int getNextSequenceNumber()
		{
			return nextSequenceNumber;
		}

		public void setNextSequenceNumber(int nextSequenceNumber)
		{
			this.nextSequenceNumber = nextSequenceNumber;
		}

		public TreeMap getPopularTags()
		{
			return popularTags;
		}

		public void setPopularTags(TreeMap popularTags)
		{
			this.popularTags = popularTags;
		}


		public List getWikiHistoryEntries()
		{
			return wikiHistoryEntries;
		}

		public void setWikiHistoryEntries(List wikiHistoryEntries)
		{
			this.wikiHistoryEntries = wikiHistoryEntries;
		}

		public boolean isFirstWikiEntry() throws Exception
		{

			WikiEntry wikiEntry = null;
			try {
				wikiEntry =  wikiEntryFinder.getFirstWikiEntry(this.getWikiId());
				if(wikiEntry.getEntryId() == this.getEntryId()){
					return true;
				}
				return false;
			}
			catch (ElementNotFoundException x) {
				return false;
			}
		}

		public int getLikeCount() {
			return likeCount;
		}

		public void setLikeCount(int likeCount) {
			this.likeCount = likeCount;
		}

		public int getVisitCount() {
			return visitCount;
		}

		public void setVisitCount(int visitCount) {
			this.visitCount = visitCount;
		}

		public String getLastVisitTime() {
			return lastVisitTime;
		}

		public void setLastVisitTime(Date lastVisitorsTime) throws Exception {
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			Date date = lastVisitorsTime != null ? lastVisitorsTime : fmter.parse(getLastVisitTime());
			if (fmter.format(date).equals(sToday)) {
				lastVisitTime =  "Today " + fmt.format(date);
			}
			lastVisitTime =  fmt2.format(date);
		}

		public boolean isAlreadyLike() {
			return alreadyLike;
		}

		public void setAlreadyLike(boolean alreadyLike) {
			this.alreadyLike = alreadyLike;
		}

		public boolean isLikeAllowed() {
			return likeAllowed;
		}

		public void setLikeAllowed(boolean likeAllowed) {
			this.likeAllowed = likeAllowed;
		}

		public boolean isPhotoPresent() {
			return photoPresent;
		}

		public void setPhotoPresent(boolean photoPresent) {
			this.photoPresent = photoPresent;
		}

		public String getCreatorName() {
			return creatorName;
		}

		public void setCreatorName(String creatorName) {
			this.creatorName = creatorName;
		}

		public String getCreationTime() {
			return creationTime;
		}

		public void setCreationTime(String creationTime) {
			this.creationTime = creationTime;
		}

		public boolean isCreatorPhotoPresent() {
			return creatorPhotoPresent;
		}

		public void setCreatorPhotoPresent(boolean creatorPhotoPresent) {
			this.creatorPhotoPresent = creatorPhotoPresent;
		}

		public int getCreatorId() {
			return creatorId;
		}

		public void setCreatorId(int creatorId) {
			this.creatorId = creatorId;
		}

		public int getVersionCount() {
			return versionCount;
		}

		public void setVersionCount(int versionCount) {
			this.versionCount = versionCount;
		}

		public List getContributors() {
			return contributors;
		}

		public void setContributors(List contributors) {
			this.contributors = contributors;
		}

		public boolean isMember() {
			return member;
		}

		public void setMember(boolean member) {
			this.member = member;
		}
	} 


	public static class RowBean extends WikiEntryDto 
	{  
		private int resultSetIndex;        
		private String editedBy;
		private boolean editedByPhotoPresent;
		private String sequence;

		public String getDatePostOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				Date date = formatter.parse(getDatePosted());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (Exception e) {
				return getDatePosted();
			}
		}

		public int getResultSetIndex()
		{
			return resultSetIndex; 
		}

		public void setResultSetIndex(int resultSetIndex) 
		{
			this.resultSetIndex = resultSetIndex;
		}

		public boolean isEvenRow()
		{
			return resultSetIndex%2==0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex%2==1;
		}

		public String getEditedBy()
		{
			return editedBy;
		}
		public void setEditedBy(String editedBy)
		{
			this.editedBy = editedBy; 
		}

		public boolean isEditedByPhotoPresent() {
			return editedByPhotoPresent;
		}

		public void setEditedByPhotoPresent(boolean editedByPhotoPresent) {
			this.editedByPhotoPresent = editedByPhotoPresent;
		}

		public String getSequence() {
			if (this.getEntrySequence() == Integer.MAX_VALUE) {
				return "Latest";
			} else {
				return Integer.toString(this.getEntrySequence());
			}
		}

		public void setSequence(String sequence) {
			this.sequence = sequence;
		}

	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder)
	{
		this.wikiEntryFinder = wikiEntryFinder; 
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}
}