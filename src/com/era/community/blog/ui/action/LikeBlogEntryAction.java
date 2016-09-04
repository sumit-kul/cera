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
import com.era.community.blog.dao.BlogEntryLike;
import com.era.community.blog.dao.BlogEntryLikeFinder;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/blog/likeBlogEntry.ajx"
 */
public class LikeBlogEntryAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	protected BlogEntryLikeFinder blogEntryLikeFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() != null) {
			JSONObject json = new JSONObject();
			
			int currentUserId = context.getCurrentUser().getId();
			Command cmd = (Command) data;
			if (context.getRequest().getParameter("blogEntryId") != null 
					&& !"".equals(context.getRequest().getParameter("blogEntryId"))) {
				int id = Integer.parseInt(context.getRequest().getParameter("blogEntryId"));
				cmd.setBlogEntryId(id);
			}
			BlogEntryLike blogEntryLike;
			if (cmd.getBlogEntryId() > 0) {
				try {
					blogEntryLike = blogEntryLikeFinder.getLikeForBlogEntryAndUser(cmd.getBlogEntryId(), currentUserId);
					//If no like exists, delete this like record
					blogEntryLike.delete();
					int count = blogEntryLikeFinder.getLikeCountForBlogEntry(cmd.getBlogEntryId());
					json.put("count", count);
					json.put("newLType", "Like");
				} catch (ElementNotFoundException e) {
					//If no like exists, create a new like record
					blogEntryLike = blogEntryLikeFinder.newBlogEntryLike();
					blogEntryLike.setBlogEntryId(cmd.getBlogEntryId());
					blogEntryLike.setPosterId(currentUserId);
					blogEntryLike.update();     
					
					int count = blogEntryLikeFinder.getLikeCountForBlogEntry(cmd.getBlogEntryId());
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
		private int blogEntryId;

		/**
		 * @return the blogEntryId
		 */
		public int getBlogEntryId() {
			return blogEntryId;
		}

		/**
		 * @param blogEntryId the blogEntryId to set
		 */
		public void setBlogEntryId(int blogEntryId) {
			this.blogEntryId = blogEntryId;
		}
	}

	/**
	 * @param blogEntryLikeFinder the blogEntryLikeFinder to set
	 */
	public void setBlogEntryLikeFinder(BlogEntryLikeFinder blogEntryLikeFinder) {
		this.blogEntryLikeFinder = blogEntryLikeFinder;
	}

	/**
	 * @param contextManager the contextManager to set
	 */
	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}
}