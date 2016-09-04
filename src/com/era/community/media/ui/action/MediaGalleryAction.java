package com.era.community.media.ui.action;

import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.media.dao.Photo;
import com.era.community.media.dao.PhotoFinder;
import com.era.community.media.dao.generated.PhotoEntity;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/mediaGallery.do" 
 *  @spring.bean name="/pers/mediaGallery.ajx"   
 *  
 */
public class MediaGalleryAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private UserFinder userFinder;
	protected PhotoFinder photoFinder;
	private ContactFinder contactFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data; 
		User currUser = context.getCurrentUser();
		User usr = null;

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		try {
			usr = userFinder.getUserEntity(cmd.getId());
		} catch (ElementNotFoundException e) {
			return new ModelAndView("/pageNotFound");
		}

		if (!usr.isValidated()) {
			return new ModelAndView("/pageNotFound");
		} else {
			cmd.setQueryText(usr.getFullName());
			cmd.setSearchType("People");
		}

		QueryScroller scroller = photoFinder.getIndexedPhotosForUser(usr.getId());
		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(cmd.getPageSize());
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = page.toJsonString(pNumber);
				json.put("userSysAdmin", Boolean.toString(context.isUserSysAdmin()));
				cmd.setPage(cmd.getPage() + 1);
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
			scroller.setBeanClass(RowBean.class, this);
			cmd.setScrollerPage(scroller.readPage(cmd.getPage()));
			cmd.setPage(cmd.getPage() + 1);
			cmd.setReturnString(getConnectionInfo(currUser, usr));
			cmd.copyPropertiesFrom(usr);
			cmd.setUser(usr);
		}
		return new ModelAndView("pers/mediaGallery");
	}

	public class RowBean extends PhotoEntity implements EntityWrapper
	{
		private int resultSetIndex;
		private int level = 1;

	}

	public String getConnectionInfo(User currentuser, User profileUser) throws Exception
	{
		String returnString = "";
		User current=contextManager.getContext().getCurrentUser();
		if (currentuser == null) // No action for User
			return "";
		if (current.getId() == profileUser.getId()) // No action for User
			return "";
		try {
			Contact contact = contactFinder.getContact(current.getId(), profileUser.getId());
			if (contact.getOwningUserId() == current.getId()) {
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
					returnString = returnString + "<a class='btnmain normalTip' onclick='sendMessage("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
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
					returnString = returnString + "<a class='btnmain normalTip' onclick='sendMessage("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
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
		} catch (ElementNotFoundException e) { 
			// Add to my connections
			returnString = "<a class='btnmain normalTip' onclick='addConnection("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
			"title='Add "+profileUser.getFullName()+" to my connections'>Add to my connections</a>";
		}
		return returnString;
	} 

	public class Command extends UserDto implements CommandBean
	{
		private User user;
		private List photoItems;
		private String returnString = "";

		private int page = 1;
		private int pageSize = 10;
		private int rowCount = 0;
		private int pageCount = 0;
		private IndexedScrollerPage scrollerPage;

		public boolean isContactionAllowed() throws Exception
		{
			User current=contextManager.getContext().getCurrentUser();

			if (current == null) return false;

			if (current.getId()==this.getId()) return false;

			return true;
		}

		public User getUser()
		{
			return user;
		}

		public void setUser(User user)
		{
			this.user = user;
		}

		public List getPhotoItems() {
			return photoItems;
		}

		public String getItems() {
			String jsonString = "";
			try {
				JSONArray jData = new JSONArray();
				for (Iterator iterator = this.getPhotoItems().iterator(); iterator.hasNext();) {
					Photo photo = (Photo) iterator.next();
					JSONObject name = new JSONObject();
					name.put("src", "${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+photo.getId());
					name.put("w", 600);
					name.put("h", 400);
					jData.add(name);
				}
				jsonString = jData.serialize();
			} catch (Exception e) {
			}
			return jsonString;
		}

		public void setPhotoItems(List photoItems) {
			this.photoItems = photoItems;
		}

		public String getReturnString() {
			return returnString;
		}

		public void setReturnString(String returnString) {
			this.returnString = returnString;
		}

		public int getPage() {
			return page;
		}

		public void setPage(int page) {
			this.page = page;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public int getRowCount() {
			return rowCount;
		}

		public void setRowCount(int rowCount) {
			this.rowCount = rowCount;
		}

		public int getPageCount() {
			return pageCount;
		}

		public void setPageCount(int pageCount) {
			this.pageCount = pageCount;
		}

		public IndexedScrollerPage getScrollerPage() {
			return scrollerPage;
		}

		public void setScrollerPage(IndexedScrollerPage scrollerPage) {
			this.scrollerPage = scrollerPage;
		}
	}

	public CommunityEraContextManager getContextManager() {
		return contextManager;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public PhotoFinder getPhotoFinder() {
		return photoFinder;
	}

	public void setPhotoFinder(PhotoFinder photoFinder) {
		this.photoFinder = photoFinder;
	}

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}
}