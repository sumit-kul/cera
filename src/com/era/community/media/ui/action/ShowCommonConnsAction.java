package com.era.community.media.ui.action;

import java.io.Writer;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.media.ui.dto.AlbumDto;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.ui.dto.UserDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/showCommonConns.ajx" 
 */
public class ShowCommonConnsAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private ContactFinder contactFinder;
	private CommunityFinder communityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 1;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		try {
			if (cmd.getOwnerId() > 0) {
				QueryScroller scroller = contactFinder.listCommonConnections(cmd.getOwnerId(), currentUser.getId());
				HttpServletResponse resp = contextManager.getContext().getResponse();
				JSONObject json = null;

				scroller.setBeanClass(RowBean.class, this);
				//scroller.setPageSize(cmd.getPageSize());
				scroller.setPageSize(2);
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = page.toJsonString(pNumber);
				json.put("userSysAdmin", Boolean.toString(context.isUserSysAdmin()));
				json.put("pageCountcommConn", scroller.readPageCount());
				cmd.setPage(cmd.getPage() + 1);
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private JSONObject toJsonString(List<AlbumDto> albums) throws Exception
	{
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		for (AlbumDto album : albums) {
			JSONObject row = new JSONObject();
			for (Method m : album.getClass().getMethods()) {
				String name = m.getName(); 
				if (name.startsWith("get")) {
					if (name.equals("getClass")) continue;
					row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(album, new Object[] {}));
				}            
			}
			data.add(row);
		}
		json.put("aData", data);
		return json;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int ownerId;
		private String type;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getOwnerId() {
			return ownerId;
		}

		public void setOwnerId(int ownerId) {
			this.ownerId = ownerId;
		}
	}

	public class RowBean extends UserDto
	{              
		private int resultSetIndex;
		private String DateConnection;

		/*public String getConnectionDate() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy");
			try {
				Date date = formatter.parse(getDateConnection());
				return fmt2.format(date);

			} catch (ParseException e) {
				return getDateConnection();
			}
		}
		public String getDateConnection() {
			return DateConnection;
		}
		public void setDateConnection(Date dateConnection) {
			DateConnection = dateConnection.toString();
		}*/

		public boolean isEvenRow()
		{
			return resultSetIndex%2==0;
		}
		public boolean isOddRow()
		{
			return resultSetIndex%2==1;
		}              

		public final int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public final void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}

		public int getCommunityCount() throws Exception
		{
			User current = contextManager.getContext().getCurrentUser();
			int commCount = communityFinder.countCommunitiesForConnection(this.getId(), current != null ? current.getId() : 0);
			return commCount;
		}

		public String getConnectionInfo() throws Exception
		{
			String returnString = "";
			User current = contextManager.getContext().getCurrentUser();
			if (current == null) // No action for User
				return "";
			if (current.getId() == this.getId()) // No action for User
				return "";
			try {
				Contact contact = contactFinder.getContact(current.getId(), this.getId());
				if (contact.getOwningUserId() == current.getId()) {
					if (contact.getStatus() == 0 || contact.getStatus() == 2 || contact.getStatus() == 3) {
						// connection request sent
						returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+this.getId()+"&currId="+current.getId()+"&userName="+this.getFullName()+"'>Request Sent</span>";
					} else if (contact.getStatus() == 1) {
						// Already connected
						returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+this.getId()+"&currId="+current.getId()+"&userName="+this.getFullName()+"'>Connected</span>";
					} else if (contact.getStatus() == 4) {
						// user has spammed you and you have cancelled the request...
						returnString = "<a class='btnmain normalTip' onClick='addConnectionInner("+this.getId()+", \""+this.getFullName()+"\");' href='javascript:void(0);'" +
						"title='Add "+this.getFullName()+" to my connections' >Add to my connections</a>";
					}
				} else {
					if (contact.getStatus() == 0) {
						returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+this.getId()+"&currId="+current.getId()+"&userName="+this.getFullName()+"'>Request Received</span>";
					} else if (contact.getStatus() == 1) {
						// Already connected
						returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+this.getId()+"&currId="+current.getId()+"&userName="+this.getFullName()+"'>Connected</span>";
					} else if (contact.getStatus() == 3 || contact.getStatus() == 4) {
						// Spammed case
						returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+this.getId()+"&currId="+current.getId()+"&userName="+this.getFullName()+"'>Spammed</span>";
					}
				}
			} catch (ElementNotFoundException e) { 
				// Add to my connections
				returnString = "<a class='btnmain normalTip' onClick='addConnectionInner("+this.getId()+", \""+this.getFullName()+"\");' href='javascript:void(0);'" +
				"title='Add "+this.getFullName()+" to my connections'>Add to my connections</a>";
			}
			return returnString;
		}
	}


	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}
}