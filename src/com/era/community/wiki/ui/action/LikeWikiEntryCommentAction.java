package com.era.community.wiki.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.wiki.dao.WikiEntryCommentLike;
import com.era.community.wiki.dao.WikiEntryCommentLikeFinder;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/wiki/likeWikiEntryComment.ajx"
 */
public class LikeWikiEntryCommentAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	protected WikiEntryCommentLikeFinder wikiEntryCommentLikeFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() != null) {
			int currentUserId = context.getCurrentUser().getId();
			JSONObject json = new JSONObject();
			Command cmd = (Command) data;
			
			WikiEntryCommentLike wikiEntryCommentLike;
			if (cmd.getCommentId() > 0) {
				try {
					wikiEntryCommentLike = wikiEntryCommentLikeFinder.getLikeForWikiEntryCommentAndUser(cmd.getCommentId(), currentUserId);
					//If no like exists, delete this like record
					wikiEntryCommentLike.delete();
					int count = wikiEntryCommentLikeFinder.getCommentLikeCountForWikiEntry(cmd.getEntryId(), cmd.getCommentId());
					json.put("count", count);
					json.put("newLType", "Like");
				} catch (ElementNotFoundException e) {
					//If no like exists, create a new like record
					wikiEntryCommentLike = wikiEntryCommentLikeFinder.newWikiEntryCommentLike();

					wikiEntryCommentLike.setWikiEntryId(cmd.getEntryId());
					wikiEntryCommentLike.setWikiEntryCommentId(cmd.getCommentId());
					wikiEntryCommentLike.setPosterId(currentUserId);
					wikiEntryCommentLike.update();     

					int count = wikiEntryCommentLikeFinder.getCommentLikeCountForWikiEntry(cmd.getEntryId(), cmd.getCommentId());
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

	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setWikiEntryCommentLikeFinder(
			WikiEntryCommentLikeFinder wikiEntryCommentLikeFinder) {
		this.wikiEntryCommentLikeFinder = wikiEntryCommentLikeFinder;
	}
}