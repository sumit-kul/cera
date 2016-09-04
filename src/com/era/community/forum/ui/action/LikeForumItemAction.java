package com.era.community.forum.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.forum.dao.ForumItemLike;
import com.era.community.forum.dao.ForumItemLikeFinder;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/forum/likeForumItem.ajx"
 */
public class LikeForumItemAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	protected ForumItemLikeFinder forumItemLikeFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() != null) {
			int currentUserId = context.getCurrentUser().getId();
			JSONObject json = new JSONObject();
			
			Command cmd = (Command) data;
			if (context.getRequest().getParameter("forumItemId") != null 
					&& !"".equals(context.getRequest().getParameter("forumItemId"))) {
				int id = Integer.parseInt(context.getRequest().getParameter("forumItemId"));
				cmd.setForumItemId(id);
			}
			ForumItemLike forumItemLike;
			if (cmd.getForumItemId() > 0) {
				try {
					forumItemLike = forumItemLikeFinder.getLikeForForumItemAndUser(cmd.getForumItemId(), currentUserId);
					//If like exists, delete this like record
					forumItemLike.delete();
					int count = forumItemLikeFinder.getLikeCountForForumItem(cmd.getForumItemId());
					json.put("count", count);
					json.put("newLType", "Like");
				} catch (ElementNotFoundException e) {
					//If no like exists, create a new like record
					forumItemLike = forumItemLikeFinder.newForumItemLike();

					forumItemLike.setForumItemId(cmd.getForumItemId());
					forumItemLike.setPosterId(currentUserId);
					forumItemLike.update();     

					int count = forumItemLikeFinder.getLikeCountForForumItem(cmd.getForumItemId());
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
		private int forumItemId;

		/**
		 * @return the forumItemId
		 */
		public int getForumItemId() {
			return forumItemId;
		}

		/**
		 * @param forumItemId the forumItemId to set
		 */
		public void setForumItemId(int forumItemId) {
			this.forumItemId = forumItemId;
		}
	}

	/**
	 * @param contextManager the contextManager to set
	 */
	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setForumItemLikeFinder(ForumItemLikeFinder forumItemLikeFinder) {
		this.forumItemLikeFinder = forumItemLikeFinder;
	}
}