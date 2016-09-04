package com.era.community.pers.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogEntryComment;
import com.era.community.blog.dao.BlogEntryCommentFinder;
import com.era.community.doclib.dao.DocumentComment;
import com.era.community.doclib.dao.DocumentCommentFinder;
import com.era.community.pers.dao.User;
import com.era.community.wiki.dao.WikiEntryComment;
import com.era.community.wiki.dao.WikiEntryCommentFinder;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/addComments.ajx" 
 */
public class AddCommentsAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private BlogEntryCommentFinder blogEntryCommentFinder;
	private WikiEntryCommentFinder wikiEntryCommentFinder;
	private DocumentCommentFinder documentCommentFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data; 
		User currentUser = context.getCurrentUser();
		HttpServletResponse resp = contextManager.getContext().getResponse();
		JSONObject json = new JSONObject();
		
		if (currentUser != null && cmd.getComment() != null && !"".equals(cmd.getComment())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String dt = sdf.format(now);
			Timestamp ts = Timestamp.valueOf(dt);
			
			int commentId = 0;
			if ("BlogEntry".equalsIgnoreCase(cmd.getType())) {
				BlogEntryComment comm = blogEntryCommentFinder.newBlogEntryComment();
				comm.setBlogEntryId(cmd.getEntryId());
				comm.setComment(cmd.getComment());
				comm.setPosterId(currentUser.getId());
				comm.setDatePosted(ts);		
				comm.update();
				commentId = comm.getId();
				json.put("id", commentId);
				
			} else if ("WikiEntry".equalsIgnoreCase(cmd.getType())) {
				WikiEntryComment comm = wikiEntryCommentFinder.newWikiEntryComment();
				comm.setWikiEntryId(cmd.getEntryId());
				comm.setComment(cmd.getComment());
				comm.setPosterId(currentUser.getId());
				comm.setDatePosted(ts);		
				comm.update();
				commentId = comm.getId();
				json.put("id", commentId);

			} else if ("Document".equalsIgnoreCase(cmd.getType())) {
				DocumentComment comm = documentCommentFinder.newDocumentComment();
				comm.setDocumentId(cmd.getEntryId());
				comm.setComment(cmd.getComment());
				comm.setPosterId(currentUser.getId());
				comm.setDatePosted(ts);		
				comm.update();
				commentId = comm.getId();
				json.put("id", commentId);
			} 
			
			if (commentId > 0) {
				json.put("entryId", cmd.getEntryId());
				json.put("comment", cmd.getComment());
				json.put("datePosted", "now");
				json.put("posterId", currentUser.getId());
				json.put("posterName", currentUser.getFullName());
				json.put("photoPresent", currentUser.isPhotoPresent());
				
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
		}
		return null;
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{
		private int entryId;
		private int communityId;
		private String type = "";
		private String comment = "";

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
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setBlogEntryCommentFinder(
			BlogEntryCommentFinder blogEntryCommentFinder) {
		this.blogEntryCommentFinder = blogEntryCommentFinder;
	}

	public void setWikiEntryCommentFinder(
			WikiEntryCommentFinder wikiEntryCommentFinder) {
		this.wikiEntryCommentFinder = wikiEntryCommentFinder;
	}

	public void setDocumentCommentFinder(DocumentCommentFinder documentCommentFinder) {
		this.documentCommentFinder = documentCommentFinder;
	}

}