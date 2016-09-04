package com.era.community.blog.ui.action;

import java.io.Writer;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.CommunityBlogFinder;
import com.era.community.blog.dao.PersonalBlogFinder;
import com.era.community.blog.ui.dto.BlogAuthorDto;
import com.era.community.blog.ui.dto.BlogEntryDto;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * View a user's personal blog
 * 
 * @spring.bean name="/blog/personalBlog.do" 
 */
public class PersonalBlogAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager;
	private PersonalBlogFinder personalBlogFinder;
	private CommunityBlogFinder communityBlogFinder;
	private UserFinder userFinder;
	protected TagFinder tagFinder; 
	private ContactFinder contactFinder;
	protected CommunityFinder communityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data;

		User current = context.getCurrentUser();

		User owner = null;
		if (cmd.getId() > 0) {
			try {
				owner = userFinder.getUserEntity(cmd.getId());
			} catch (ElementNotFoundException e) {
				return new ModelAndView("/pageNotFound");
			}
		} else if (current != null) {
			owner = current;
		} else {
			return new ModelAndView("/pageNotFound");
		}
		cmd.setOwner(owner);

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		if (context.getRequest().getParameter("sortByOption") != null 
				&& !"".equals(context.getRequest().getParameter("sortByOption"))) {
			cmd.setSortByOption(context.getRequest().getParameter("sortByOption"));
		} else {
			cmd.setSortByOption("Most Recent");
		}

		if (context.getRequest().getParameter("fTagList") != null 
				&& !"".equals(context.getRequest().getParameter("fTagList"))) {
			cmd.setFilterTagList(context.getRequest().getParameter("fTagList"));
		}

		if (context.getRequest().getParameter("filterTag") != null 
				&& !"".equals(context.getRequest().getParameter("filterTag"))) {
			cmd.addFilterTagList(context.getRequest().getParameter("filterTag"));
		}

		if (context.getRequest().getParameter("rmFilterTag") != null 
				&& !"".equals(context.getRequest().getParameter("rmFilterTag"))) {
			cmd.removeFilterTagList(context.getRequest().getParameter("rmFilterTag"));
		}

		if (context.getRequest().getParameter("toggleList") != null 
				&& !"".equals(context.getRequest().getParameter("toggleList"))
				&& "true".equals(context.getRequest().getParameter("toggleList"))) {
			cmd.setToggleList("true");
		}

		QueryScroller scroller = personalBlogFinder.getBlogsForBlogAuther(owner, current);

		if ("Title".equalsIgnoreCase(cmd.getSortByOption())) {
			scroller.addScrollKey("STEMP.blogName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else {
			scroller.addScrollKey("STEMP.created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		scroller.setBeanClass(RowBean.class, this);
		scroller.setPageSize(cmd.getPageSize());
		//pagination
		cmd.setPageCount(scroller.readPageCount());

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(cmd.getPageSize());
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = toJsonStringForAllBlogs(pNumber, cmd.getFilterTagList(), "", cmd.getSortByOption(), cmd.getToggleList(), page);
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
			scroller.setPageSize(cmd.getPageSize());
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());

			Contact contact = null;
			try {
				if (current != null) {
					contact = contactFinder.getContact(current.getId(), owner.getId());
					cmd.setReturnString(getConnectionInfo(current, owner, contact));
				}
			} catch (ElementNotFoundException e) { 
				String returnString = "<a class='btnmain normalTip' onclick='addConnection("+owner.getId()+", \""+owner.getFullName()+"\");' href='javascript:void(0);'" +
				"title='Add "+owner.getFullName()+" to my connections'>Add to my connections</a>";
				cmd.setReturnString(returnString);
			}
			cmd.setSearchType("People");
			cmd.setQueryText(owner.getFullName());
			return new ModelAndView("blog/personalBlog");
		}
	}

	public JSONObject toJsonStringForAllBlogs(int pageNumber, String filterTag, String commOption, String sortOption, String toggleList, IndexedScrollerPage page) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("rowCount", page.getRowCount());
		json.put("pageCount", page.getPageCount());
		json.put("pageSize", page.getScrollerPageSize());
		json.put("pageNumber", page.getCurrentPageNumber());

		JSONArray data = new JSONArray();
		for (Object o : page) {
			JSONObject row = new JSONObject();
			for (Method m : o.getClass().getMethods()) {
				String name = m.getName(); 
				if (!(name.startsWith("get") || name.startsWith("is"))) continue;
				if (name.equals("getClass")) continue;
				if ("getLatestEntries".equalsIgnoreCase(name)) {
					JSONArray entryData = new JSONArray();
					List<BlogEntryDto> entries = (List<BlogEntryDto>)m.invoke(o, new Object[] {});
					for (BlogEntryDto entry : entries) {
						JSONObject entryName = new JSONObject();
						for (Method im : entry.getClass().getMethods()) {
							String iName = im.getName(); 
							if (!iName.startsWith("get")) continue;
							if (iName.equals("getClass")) continue;
							entryName.put(iName.substring(3,4).toLowerCase()+iName.substring(4), im.invoke(entry, new Object[] {}));
						}
						entryData.add(entryName);
					}
					row.put("entryData", entryData);
				} else {
					if (name.startsWith("get")) {
						if (name.equals("getTaggedKeywords")) {
							Object[] obj = new Object[] {pageNumber, filterTag, commOption, sortOption, toggleList};
							row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, obj));  
						} else {
							row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, new Object[] {}));
						}
					}
					if (name.startsWith("is"))
						row.put(name.substring(2,3).toLowerCase()+name.substring(3), m.invoke(o, new Object[] {}));
				}
			}            
			data.add(row);
		}
		json.put("aData", data);
		return json;
	}

	public class RowBean implements EntityWrapper
	{
		private int blogId;
		private String blogName;
		private String  blogType;
		private String description;
		private String created;
		private String ownerName;
		private int ownerId;
		private String photoPresent;
		private int commId;
		private int commentCount;
		private int likeCount;
		private String communityName;

		public String getAuthors(){
			String authors = "";
			try {
				int authorCount = 0;
				List authorList = new ArrayList();
				if (getCommId() > 0) {
					authorCount = communityBlogFinder.getAuthorCountCommunityBlog(getBlogId());
					authorList = communityBlogFinder.getAuthorsForCommunityBlog(getBlogId(), 10);
				} else {
					authorCount = personalBlogFinder.getAuthorCountPersonalBlog(getBlogId());
					authorList = personalBlogFinder.getAuthorsForPersonalBlog(getBlogId(), 10);
				}

				if (authorCount > 10) {
					authorCount = authorCount - 10;
				} else {
					authorCount = 0;
				}

				for (Iterator iterator = authorList.iterator(); iterator.hasNext();) {
					BlogAuthorDto at = (BlogAuthorDto) iterator.next();
					String name = at.getFirstName() + " " + at.getLastName();
					authors += "<a href='pers/connectionResult.do?id="+at.getAuthorId()+"&backlink=ref' onmouseover='tip(this, &#39;"+name+"&#39;)'>"+name+"</a>";
					if (iterator.hasNext())authors += " , ";
				}

				if (authorCount > 0) {
					authors += " , and " + authorCount + " other(s)";
				}
			} catch (Exception e) {
				return authors;
			}
			return authors;
		}

		public String getDisplayBlogName(){
			String name = getBlogName();
			if(name.length() > 90)
				name = name.substring(0, 90).concat("...");
			return name;
		}

		public String getCreatedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				Date date = formatter.parse(getCreated());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return getCreated();
			}
		}

		public String getTaggedKeywords(int pageNumber, String filterTag, String commOption, String sortOption, String toggleList){
			String taggedKeywords = "";
			try {
				List tags = null;
				if (commId > 0) {
					tags = tagFinder.getTagsForParentTypeByPopularity(this.getBlogId(), 0, 20, "CommunityBlog");
				} else {
					tags = tagFinder.getTagsForParentTypeByPopularity(this.getBlogId(), 0, 20, "PersonalBlog");
				}
				String filterTagString = "";
				String sortOptionString = "";

				if (filterTag != null && !"".equals(filterTag))
					filterTagString = "&fTagList="+filterTag;

				if (sortOption != null && !"".equals(sortOption))
					sortOptionString = "&sortByOption="+sortOption;

				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='blog/allBlogs.do?filterTag="+tag+filterTagString+sortOptionString+"&toggleList="+toggleList+" ' class='normalTip euInfoSelect' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += " , ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

		public boolean isMember() throws Exception
		{
			try {
				Community comm = communityFinder.getCommunityForId(this.getCommId());
				if (comm != null && comm.isPrivate()) {
					if (contextManager.getContext().getCurrentUser() == null ) return false;
					if (contextManager.getContext().isUserAuthenticated() == false) return false;
					if (this.getCommId() == 0) return false;
					int currentUser = contextManager.getContext().getCurrentUser().getId();
					return comm.isMember(currentUser);
				} else {
					return true;
				}
			} catch (ElementNotFoundException ex) {
				return true;
			}

		}

		public String getDisplayBody()
		{
			if ( (this.getDescription()==null) || (this.getDescription().length()==0)) return "";

			String sBody = this.getDescription();

			sBody = sBody.replaceAll("<p>","");
			sBody = sBody.replaceAll("</p>"," ");
			sBody = sBody.replaceAll("   ", "");

			if (sBody.contains("<")) {
				sBody = sBody.substring(0, sBody.indexOf("<"));
				if(sBody.length() >= 500)sBody.substring(0, 500);
				sBody.concat("...");
			} else if (sBody.length() >= 500) {
				sBody = sBody.substring(0, 500).concat("...");
			}
			return sBody;
		}

		public List<BlogEntryDto> getLatestEntries() {
			String type = this.getBlogType();
			List<BlogEntryDto> list = new ArrayList<BlogEntryDto>();

			try {
				if ("PersonalBlog".equalsIgnoreCase(type)) {
					list = personalBlogFinder.getEntrisForPersonalBlog(this.getBlogId(), 10);
				} else if ("CommunityBlog".equalsIgnoreCase(type)) {
					list = communityBlogFinder.getEntrisForCommunityBlog(this.getBlogId(), 10);
				}
			} catch (Exception e) {
			}
			return list;
		}

		public String getCreated() {
			return created;
		}

		public void setCreated(java.util.Date created) {
			this.created = created.toString();
		}

		public String getPhotoPresent() {
			return photoPresent;
		}

		public void setPhotoPresent(String photoPresent) {
			this.photoPresent = photoPresent;
		}

		public int getBlogId() {
			return blogId;
		}

		public void setBlogId(int blogId) {
			this.blogId = blogId;
		}

		public String getBlogName() {
			return blogName;
		}

		public void setBlogName(String blogName) {
			this.blogName = blogName;
		}

		public String getBlogType() {
			return blogType;
		}

		public void setBlogType(String blogType) {
			this.blogType = blogType;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getOwnerName() {
			return ownerName;
		}

		public void setOwnerName(String ownerName) {
			this.ownerName = ownerName;
		}

		public int getCommentCount() {
			return commentCount;
		}

		public void setCommentCount(Long commentCount) {
			this.commentCount = commentCount.intValue();
		}

		public int getLikeCount() {
			return likeCount;
		}

		public void setLikeCount(Long likeCount) {
			this.likeCount = likeCount.intValue();
		}

		public int getOwnerId() {
			return ownerId;
		}

		public void setOwnerId(int ownerId) {
			this.ownerId = ownerId;
		}

		public String getCommunityName() {
			return communityName;
		}

		public void setCommunityName(String communityName) {
			this.communityName = communityName;
		}

		public int getCommId() {
			return commId;
		}

		public void setCommId(Long commId) {
			this.commId = commId.intValue();
		}
	}

	public String getConnectionInfo(User currentuser, User profileUser, Contact contact) throws Exception
	{
		String returnString = "";
		if (contact.getOwningUserId() == currentuser.getId()) {
			if (contact.getStatus() == 0 || contact.getStatus() == 2 || contact.getStatus() == 3) {
				// connection request sent
				returnString = "<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\")' href='javascript:void(0);'" +
				"title='Cancel connection request sent to "+profileUser.getFullName()+"' >Cancel Request</a>";
			} else if (contact.getStatus() == 1) {
				// Already connected
				returnString = "<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
				"title='Remove "+profileUser.getFullName()+" from you connections' >Disconnect</a>";
				if (contact.getFollowContact() == 1) { //pass 0 for Owner and 1 for Contact
					returnString = returnString + "<a class='btnmain normalTip' onclick='stopFollowing("+contact.getId()+", "+1+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Stop Following'>Stop Following</a>";
				} else {
					returnString = returnString + "<a class='btnmain normalTip' onclick='startFollowing("+contact.getId()+", "+1+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Start Following'>Follow</a>";
				}
				returnString = returnString + "<a class='btnmain normalTip' onclick='sendMessageFromInfo("+profileUser.getId()+", \""+profileUser.getFullName()+"\", \""+profileUser.isPhotoPresent()+"\");' href='javascript:void(0);'" +
				"title='Send message to "+profileUser.getFullName()+"'>Send Message</a>";
			} else if (contact.getStatus() == 4) {
				// user has spammed you and you have cancelled the request...
				returnString = "<a class='btnmain normalTip' onclick='addConnection("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
				"title='Add "+profileUser.getFullName()+" to my connections'>Add to my connections</a>";
			}
		} else {
			if (contact.getStatus() == 0) {
				returnString = "<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+4+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
				"title='Confirm connection request from "+profileUser.getFullName()+"'>Confirm Request</a>" +
				//"<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+5+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
				//"title='Hide this request. ("+this.getFullName()+" won't know)' class='search_btn right' style='font-size:12px;'>Not Now</a>";
				"<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\")' href='javascript:void(0);' " +
				"title='Delete connection request from "+profileUser.getFullName()+"'>Delete</a>";
			} else if (contact.getStatus() == 1) {
				// Already connected
				returnString = "<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
				"title='Remove "+profileUser.getFullName()+" from you connections' style='float:right;'>Disconnect</a>";
				if (contact.getFollowOwner() == 1) { //pass 0 for Owner and 1 for Contact
					returnString = returnString + "<a class='btnmain normalTip' onclick='stopFollowing("+contact.getId()+", "+0+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Stop Following'>Stop Following</a>";
				} else {
					returnString = returnString + "<a class='btnmain normalTip' onclick='startFollowing("+contact.getId()+", "+0+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Start Following'>Follow</a>";
				}
				returnString = returnString + "<a class='btnmain normalTip' onclick='sendMessageFromInfo("+profileUser.getId()+", \""+profileUser.getFullName()+"\", \""+profileUser.isPhotoPresent()+"\");' href='javascript:void(0);'" +
				"title='Send message to "+profileUser.getFullName()+"'>Send Message</a>";
			} /*if (contact.getStatus() == 2) {
    				// Not now case
    				returnString = "<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+4+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Confirm connection request from "+this.getFullName()+"' class='search_btn right' style='font-size:12px;'>Confirm Request</a>" +
    				"<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+1+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Delete connection request from "+this.getFullName()+"' class='search_btn right' style='font-size:12px;'>Delete</a>" +
    				"<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+2+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Mark spam to "+this.getFullName()+". You won't get connection request from "+this.getFullName()+" anymore.' class='search_btn right' style='font-size:12px;'>Mark Spamm</a>";
    			}*/ else if (contact.getStatus() == 3 || contact.getStatus() == 4) {
    				// Spammed case
    				returnString = "<span>Spammed</span><a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+3+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Undo spam to "+profileUser.getFullName()+"'>Undo</a>";
    			}
		}
		return returnString;
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{       
		private User owner;
		private int id;
		private String sortByOption;
		private String filterTagList = "";
		private String returnString = "";

		public List getSortByOptionOptions() throws Exception
		{
			List sortByOptionList = new ArrayList();
			sortByOptionList.add("Most Recent");
			sortByOptionList.add("Title");
			return sortByOptionList;
		}

		/**
		 * @param filterTagList the filterTagList to add as comma separated 
		 */
		public void addFilterTagList(String filterTag) {
			if (filterTag != null && !"".equals(filterTag)) {
				if (this.filterTagList == null || this.filterTagList.equals("")) {
					this.filterTagList = filterTag;
				} else {
					StringTokenizer st = new StringTokenizer(getFilterTagList(), ",");
					boolean isAdditionAllowed = true;
					while (st.hasMoreTokens()) {
						String tag = st.nextToken().trim().toLowerCase();
						if (tag.equalsIgnoreCase(filterTag)) {
							isAdditionAllowed = false;
							break;
						}
					}
					if (isAdditionAllowed && filterTag != null && !"".equals(filterTag))
						this.filterTagList += ","+filterTag;
				}
			}
		}

		/**
		 * @param filterTagList the filterTagList to remove from the filterTag list 
		 */
		public void removeFilterTagList(String rmfilterTag) {
			String newFilterTagList = "";
			if (getFilterTagList() != null && !"".equals(getFilterTagList())) {
				StringTokenizer st = new StringTokenizer(getFilterTagList(), ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					if (rmfilterTag != null && !rmfilterTag.equalsIgnoreCase(tag)) {
						newFilterTagList += tag;
						if (st.hasMoreTokens()) newFilterTagList += ",";
					}
				}
				this.filterTagList = newFilterTagList;
			}
		}

		public String getDisplayedFilterTag() {
			String returnHtmlTags = "";
			String filterList = getFilterTagList();
			if (getFilterTagList() != null && !"".equals(filterList)) {
				StringTokenizer st = new StringTokenizer(filterList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					returnHtmlTags += "<a href='blog/allBlogs.do?fTagList="+filterList+"&rmFilterTag="+tag+"&communityOption="+"&sortByOption="+this.getSortByOption()+"&toggleList="+this.getToggleList()+"' class='normalTip euInfoRmTag' style='display: inline;' title='Remove &#39;"+tag+"&#39; filter'>"+tag+" <span style='color: rgb(137, 143, 156); font-size: 12px;' title='Remove "+tag+" filter' >X</span></a>";
				}
			}
			return returnHtmlTags;
		}

		public boolean isContactionAllowed() throws Exception
		{
			User current=contextManager.getContext().getCurrentUser();

			if (current == null) return false;

			if (current.getId()==this.getId()) return false;

			return true;
		}

		public User getOwner() {
			return owner;
		}

		public void setOwner(User owner) {
			this.owner = owner;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getSortByOption() {
			return sortByOption;
		}

		public void setSortByOption(String sortByOption) {
			this.sortByOption = sortByOption;
		}

		public String getFilterTagList() {
			return filterTagList;
		}

		public void setFilterTagList(String filterTagList) {
			this.filterTagList = filterTagList;
		}

		public String getReturnString() {
			return returnString;
		}

		public void setReturnString(String returnString) {
			this.returnString = returnString;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}

	public void setPersonalBlogFinder(PersonalBlogFinder personalBlogFinder) {
		this.personalBlogFinder = personalBlogFinder;
	}

	public void setCommunityBlogFinder(CommunityBlogFinder communityBlogFinder) {
		this.communityBlogFinder = communityBlogFinder;
	}
}