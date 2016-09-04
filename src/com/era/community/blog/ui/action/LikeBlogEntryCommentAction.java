package com.era.community.blog.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogEntryCommentLike;
import com.era.community.blog.dao.BlogEntryCommentLikeFinder;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/blog/likeBlogEntry.ajx"
 */
public class LikeBlogEntryCommentAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	protected BlogEntryCommentLikeFinder blogEntryCommentLikeFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() != null) {
			int currentUserId = context.getCurrentUser().getId();
			JSONObject json = new JSONObject();
			Command cmd = (Command) data;
			if (context.getRequest().getParameter("commentId") != null 
					&& !"".equals(context.getRequest().getParameter("commentId"))) {
				int id = Integer.parseInt(context.getRequest().getParameter("commentId"));
				cmd.setCommentId(id);
			}

			if (context.getRequest().getParameter("entryId") != null 
					&& !"".equals(context.getRequest().getParameter("entryId"))) {
				int entryId = Integer.parseInt(context.getRequest().getParameter("entryId"));
				cmd.setEntryId(entryId);
			}
			BlogEntryCommentLike blogEntryCommentLike;
			if (cmd.getCommentId() > 0) {
				try {
					blogEntryCommentLike = blogEntryCommentLikeFinder.getLikeForBlogEntryCommentAndUser(cmd.getCommentId(), currentUserId);
					//If no like exists, delete this like record
					blogEntryCommentLike.delete();
					int count = blogEntryCommentLikeFinder.getCommentLikeCountForBlogEntry(cmd.getEntryId(), cmd.getCommentId());
					json.put("count", count);
					json.put("newLType", "Like");
				} catch (ElementNotFoundException e) {
					//If no like exists, create a new like record
					blogEntryCommentLike = blogEntryCommentLikeFinder.newBlogEntryCommentLike();

					blogEntryCommentLike.setBlogEntryId(cmd.getEntryId());
					blogEntryCommentLike.setBlogEntryCommentId(cmd.getCommentId());
					blogEntryCommentLike.setPosterId(currentUserId);
					blogEntryCommentLike.update();     

					int count = blogEntryCommentLikeFinder.getCommentLikeCountForBlogEntry(cmd.getEntryId(), cmd.getCommentId());
					json.put("count", count);
					json.put("newLType", "Unlike");
				}

				HttpServletResponse resp = contextManager.getContext().getResponse();
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
		}
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int commentId;
		private int entryId;

		public int getCommentId() {
			return commentId;
		}

		public void setCommentId(int commentId) {
			this.commentId = commentId;
		}

		public int getEntryId() {
			return entryId;
		}

		public void setEntryId(int entryId) {
			this.entryId = entryId;
		}
	}

	/**
	 * @param contextManager the contextManager to set
	 */
	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setBlogEntryCommentLikeFinder(
			BlogEntryCommentLikeFinder blogEntryCommentLikeFinder) {
		this.blogEntryCommentLikeFinder = blogEntryCommentLikeFinder;
	}
}