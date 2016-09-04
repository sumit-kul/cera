package com.era.community.wiki.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.era.community.base.ImageManipulation;
import com.era.community.pers.dao.UserFinder;
import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryComment;
import com.era.community.wiki.dao.WikiEntryCommentFinder;
import com.era.community.wiki.dao.WikiEntryFinder;
import com.era.community.wiki.dao.generated.WikiEntryDaoBase;
import com.era.community.wiki.ui.dto.WikiEntryCommentDto;
import com.era.community.wiki.ui.dto.WikiEntryDto;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/cid/[cec]/wiki/wikiDiscussionDisplay.do" 
 * @spring.bean name="/cid/[cec]/wiki/wikiDiscussionDisplay.ajx"
 */
public class WikiDiscussionAction extends AbstractCommandAction
{
	protected WikiEntryFinder wikiEntryFinder; 
	protected WikiEntryCommentFinder wikiEntryCommentFinder; 
	protected UserFinder userFinder;
	protected CommunityEraContextManager contextManager;
	protected WikiEntryDaoBase dao;

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

		if (cmd.getIsSubmit() != null) {
			if(cmd.getComment()!=null && cmd.getComment().length()>0) {

				WikiEntryComment comment = wikiEntryCommentFinder.newWikiEntryComment();
				comment.setPosterId( context.getCurrentUser().getId() );
				comment.setComment( ImageManipulation.manageImages(context, cmd.getComment(), cmd.getTitle(), context.getCurrentUser().getId(), comment.getId(), "WikiEntryComment") );
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String dt = sdf.format(now);
				Timestamp ts = Timestamp.valueOf(dt);
				comment.setDatePosted(ts);
				comment.setWikiEntryId( cmd.getEntryId());
				comment.update();
				cmd.setComment(null);
				cmd.setErrorMsg(null);
			} else {
				cmd.setErrorMsg("No posting to add. Please fill in a value.");
			}
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

		cmd.copyPropertiesFrom(entry);

		QueryScroller scroller = entry.listComments();
		scroller.setBeanClass( RowBean.class );
		scroller.setPageSize(cmd.getPageSize());
		//pagination
		cmd.setPageCount(scroller.readPageCount());
		int cnt = scroller.readRowCount();
		cmd.setCommentCount(cnt);
		cmd.setRowCount(cnt);

		if (pNumber > 0) {
			int userId = context.getCurrentUser() == null ? 0 : context.getCurrentUser().getId();
			cmd.setIsSubmit("true");
			IndexedScrollerPage page = scroller.readPage(pNumber);
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = page.toJsonString(pNumber, userId);
			json.put("userId", cmd.getPosterId());
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
			return null;
		} else {
			cmd.setSearchType("Wiki");
			cmd.setQueryText(entry.getTitle());
			return new ModelAndView("/wiki/wikiDiscussionDisplay");
		}
	}

	public class Command extends WikiEntryDto implements CommandBean
	{        
		private String isSubmit;
		private String comment;
		private String errorMsg;
		private int commentCount;

		public String getIsSubmit() {
			return isSubmit;
		}

		public void setIsSubmit(String isSubmit) {
			this.isSubmit = isSubmit;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public String getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}

		public int getCommentCount() {
			return commentCount;
		}

		public void setCommentCount(int commentCount) {
			this.commentCount = commentCount;
		}
	} 

	public static class RowBean extends WikiEntryCommentDto implements EntityWrapper 
	{  
		private String displayName;
		private boolean photoPresent;

		public String getPostedOn() throws Exception
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

			} catch (ParseException e) {
				return getCreated();
			}
		}

		public String getDisplayName()
		{
			return displayName;
		}

		public void setDisplayName(String displayName)
		{
			this.displayName = displayName;
		}

		public boolean isPhotoPresent() {
			return photoPresent;
		}

		public void setPhotoPresent(boolean photoPresent) {
			this.photoPresent = photoPresent;
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

	public void setDao(WikiEntryDaoBase dao) {
		this.dao = dao;
	}

	public void setWikiEntryCommentFinder(
			WikiEntryCommentFinder wikiEntryCommentFinder) {
		this.wikiEntryCommentFinder = wikiEntryCommentFinder;
	}
}