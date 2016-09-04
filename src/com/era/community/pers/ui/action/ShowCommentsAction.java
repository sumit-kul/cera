package com.era.community.pers.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;
import support.community.util.StringHelper;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogEntryCommentLike;
import com.era.community.blog.dao.BlogEntryCommentLikeFinder;
import com.era.community.doclib.dao.DocumentCommentLike;
import com.era.community.doclib.dao.DocumentCommentLikeFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.wiki.dao.WikiEntryCommentLike;
import com.era.community.wiki.dao.WikiEntryCommentLikeFinder;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/showComments.ajx" 
 */
public class ShowCommentsAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private UserFinder userFinder;
	private BlogEntryCommentLikeFinder blogEntryCommentLikeFinder;
	private WikiEntryCommentLikeFinder wikiEntryCommentLikeFinder;
	private DocumentCommentLikeFinder documentCommentLikeFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data; 
		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		QueryScroller scroller = userFinder.showCommentsForType(cmd.getEntryId(), cmd.getType());
		HttpServletResponse resp = contextManager.getContext().getResponse();
		JSONObject json = null;
		if (pNumber > 0) {
			scroller.setBeanClass(RowBean.class, this);
			scroller.setPageSize(3);
			IndexedScrollerPage page = scroller.readPage(pNumber);
			json = page.toJsonString(pNumber);
			json.put("rowCnt", scroller.readRowCount());
			json.put("pgCnt", scroller.readPageCount());
			json.put("nxtPage", pNumber + 1);
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
			return null;
		} else {
			scroller.setPageSize(3);
			scroller.setBeanClass(RowBean.class, this);
			IndexedScrollerPage page = scroller.readPage(1);
			json = page.toJsonString(1);
			json.put("rowCnt", scroller.readRowCount());
			json.put("pgCnt", scroller.readPageCount());
			json.put("nxtPage", 2);
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
			return null;
		}
	}

	public class RowBean
	{
		private int resultSetIndex;
		private int id;
		private int entryId;
		private String comment;
		private String datePosted; 
		private int PosterId;
		private String firstName;
		private String lastName;
		private boolean photoPresent;
		private String type;

		public boolean isEvenRow()
		{
			return resultSetIndex % 2 == 0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex % 2 != 0;
		}

		public int getResultSetIndex() {
			return resultSetIndex;
		}

		public void setResultSetIndex(int resultSetIndex) {
			this.resultSetIndex = resultSetIndex;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getEntryId() {
			return entryId;
		}

		public void setEntryId(int entryId) {
			this.entryId = entryId;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public String getDatePostedOn() {
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
				return getDatePosted();
			}
		}

		public String getDatePosted() {
			return datePosted;
		}

		public void setDatePosted(Date datePosted) {
			this.datePosted = datePosted.toString();
		}

		public int getPosterId() {
			return PosterId;
		}

		public void setPosterId(int posterId) {
			PosterId = posterId;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public boolean isPhotoPresent() {
			return photoPresent;
		}

		public void setPhotoPresent(boolean photoPresent) {
			this.photoPresent = photoPresent;
		}

		public String getcommentToDisplay() {
			if ( (this.getComment() == null) || (this.getComment().length() == 0)) return "";

			String sBody = this.getComment();
			sBody = Jsoup.parse(sBody).text();
			sBody = StringHelper.escapeHTML(sBody);

			/*if (sBody.length() >= 400) {
				sBody = sBody.substring(0, 395).concat("..."); //.concat("  <a class='rMore' href='"+contextManager.getContext().getContextUrl()+"/"+this.getItemUrl()+"' target='_blank'>read more</a>");
			}*/
			return sBody;
		}

		public String getLikeString(){
			try {
				User cUser = contextManager.getContext().getCurrentUser();
				if (cUser == null) {
					return "Like";
				} else {
					try {
						if ("WikiEntry".equalsIgnoreCase(this.getType())) {
							WikiEntryCommentLike wikiEntryCommentLike = wikiEntryCommentLikeFinder.getLikeForWikiEntryCommentAndUser(this.getId(), cUser.getId());
						} else if ("BlogEntry".equalsIgnoreCase(this.getType())) {
							BlogEntryCommentLike blogEntryCommentLike = blogEntryCommentLikeFinder.getLikeForBlogEntryCommentAndUser(this.getId(), cUser.getId());
						} else if ("Document".equalsIgnoreCase(this.getType())) {
							DocumentCommentLike documentCommentLike = documentCommentLikeFinder.getLikeForDocumentCommentAndUser(this.getEntryId(), this.getId(), cUser.getId());
						} else if ("ForumTopic".equalsIgnoreCase(this.getType()) || "ForumResponse".equalsIgnoreCase(this.getType())) {
							
						}
						return "Unlike";
					}
					catch (ElementNotFoundException e) {
						return "Like";
					}
				}
			} catch (Exception e) {
				return "Like";
			}
		}

		public int getLikeCount(){
			try {
				if ("WikiEntry".equalsIgnoreCase(this.getType())) {
					int count = wikiEntryCommentLikeFinder.getCommentLikeCountForWikiEntry(this.getEntryId(), this.getId());
					return count;
				} else if ("BlogEntry".equalsIgnoreCase(this.getType())) {
					int count = blogEntryCommentLikeFinder.getCommentLikeCountForBlogEntry(this.getEntryId(), this.getId());
					return count;
				} else if ("Document".equalsIgnoreCase(this.getType())) {
					int count = documentCommentLikeFinder.getLikeCountForDocumentComment(this.getEntryId(), this.getId());
					return count;
				} else if ("ForumTopic".equalsIgnoreCase(this.getType()) || "ForumResponse".equalsIgnoreCase(this.getType())) {
					return 0;
				}
			} catch (Exception e) {
				return 0;
			}
			return 0;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{
		private int entryId;
		private int communityId;
		private String type = "";

		public int getEntryId() {
			return entryId;
		}
		public void setEntryId(int entryId) {
			this.entryId = entryId;
		}
		public int getCommunityId() {
			return communityId;
		}
		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setBlogEntryCommentLikeFinder(
			BlogEntryCommentLikeFinder blogEntryCommentLikeFinder) {
		this.blogEntryCommentLikeFinder = blogEntryCommentLikeFinder;
	}

	public void setWikiEntryCommentLikeFinder(
			WikiEntryCommentLikeFinder wikiEntryCommentLikeFinder) {
		this.wikiEntryCommentLikeFinder = wikiEntryCommentLikeFinder;
	}

	public void setDocumentCommentLikeFinder(
			DocumentCommentLikeFinder documentCommentLikeFinder) {
		this.documentCommentLikeFinder = documentCommentLikeFinder;
	}
}