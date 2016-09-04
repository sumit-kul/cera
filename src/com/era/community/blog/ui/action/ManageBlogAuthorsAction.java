package com.era.community.blog.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogAuthor;
import com.era.community.blog.dao.BlogAuthorFinder;
import com.era.community.blog.dao.PersonalBlog;
import com.era.community.blog.dao.PersonalBlogFinder;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.ui.dto.UserDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/blog/manageBlogAuthors.do" 
 */
public class ManageBlogAuthorsAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager;
	protected ContactFinder contactFinder;
	protected PersonalBlogFinder personalBlogFinder;
	protected BlogAuthorFinder blogAuthorFinder;
	
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();

		if (currentUser == null) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do");
		} else {
			cmd.setUser(currentUser);
		}

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		if (context.getRequest().getParameter("sortByOption") != null 
				&& !"".equals(context.getRequest().getParameter("sortByOption"))) {
			cmd.setSortByOption(context.getRequest().getParameter("sortByOption"));
		}

		PersonalBlog bpc = null;
		if (cmd.getBid() > 0){
			try {
				bpc = personalBlogFinder.getPersonalBlogForId(cmd.getBid());
				cmd.setCons(bpc);
			} catch (ElementNotFoundException ex) {
				return new ModelAndView("/pageNotFound");
			}
			BlogAuthor blogAuthor = null;
			try {
				blogAuthor = blogAuthorFinder.getPersonalBlogAuthorForBlogAndUser(bpc.getId(), currentUser.getId());
				if (blogAuthor.getRole() != 1) {
					return new ModelAndView("/pageNotFound");
				}
			} catch (ElementNotFoundException e) {
				return new ModelAndView("/pageNotFound");
			}
			if (blogAuthor != null && blogAuthor.getRole() != 1) {
				return new ModelAndView("/pageNotFound");
			}
		} else {
			return new ModelAndView("/pageNotFound");
		}
		
		QueryScroller scroller = blogAuthorFinder.getPersonalBlogAuthorsListForBlog(bpc.getId());
		scroller.addScrollKey("STEMP.FirstName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(cmd.getPageSize());
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = page.toJsonString(pNumber);
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
			scroller.setBeanClass(RowBean.class, this);
			cmd.setScrollerPage(scroller.readPage(cmd.getPage()));
			cmd.setPage(cmd.getPage() + 1);
			return new ModelAndView("blog/manageBlogAuthors");
		}
	}

	public class RowBean extends UserDto
	{              
		private int resultSetIndex;
		private int role;
		private int authId;
		
		public RowBean()
		{
		}
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
		
		public String getRoleInfo() throws Exception
		{
			String returnString = "";
			if (this.getRole() == 1) {
				returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?authId="+this.getAuthId()+"&authRole="+this.getRole()+"'>Owner</span>";
			} else if (this.getRole() == 2) {
				returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?authId="+this.getAuthId()+"&authRole="+this.getRole()+"'>Author</span>";
			}
			return returnString;
		}
		
		public String getStaticRoleInfo() throws Exception
		{
			String returnString = "";
			if (this.getRole() == 1) {
				returnString = "<span class='staticDropDown' >Owner</span>";
			} else if (this.getRole() == 2) {
				returnString = "<span class='staticDropDown' >Author</span>";
			}
			return returnString;
		}

		public String getConnectionInfo() throws Exception
		{
			String returnString = "";
			return returnString;
		}
		public int getRole() {
			return role;
		}
		public void setRole(int role) {
			this.role = role;
		}
		public int getAuthId() {
			return authId;
		}
		public void setAuthId(int authId) {
			this.authId = authId;
		}
	}

	public static class Command extends IndexCommandBeanImpl implements CommandBean 
	{
		private User user;
		private String sortByOption;
		private int bid;
		private PersonalBlog cons;
		private boolean owner;

		public List getSortByOptionOptions() throws Exception
		{
			List sortByOptionList = new ArrayList();
			sortByOptionList.add("Visit Date");
			sortByOptionList.add("Name");
			return sortByOptionList;
		}
		
		public String getStartedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				Date date = formatter.parse(this.getCons().getCreated().toString());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return this.getCons().getCreated().toString();
			}
		}
		
		/**
		 * @return the sortByOption
		 */
		public String getSortByOption() {
			return sortByOption;
		}

		/**
		 * @param sortByOption the sortByOption to set
		 */
		public void setSortByOption(String sortByOption) {
			this.sortByOption = sortByOption;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public int getBid() {
			return bid;
		}


		public void setBid(int bid) {
			this.bid = bid;
		}


		public PersonalBlog getCons() {
			return cons;
		}


		public void setCons(PersonalBlog cons) {
			this.cons = cons;
		}

		public boolean isOwner() {
			return owner;
		}

		public void setOwner(boolean owner) {
			this.owner = owner;
		}   
	}

	public void setContactFinder(ContactFinder contactFinder)
	{
		this.contactFinder = contactFinder;
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setBlogAuthorFinder(BlogAuthorFinder blogAuthorFinder) {
		this.blogAuthorFinder = blogAuthorFinder;
	}

	public void setPersonalBlogFinder(PersonalBlogFinder personalBlogFinder) {
		this.personalBlogFinder = personalBlogFinder;
	}
}