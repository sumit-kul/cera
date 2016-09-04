package com.era.community.library.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.doclib.dao.DocumentCommentLike;
import com.era.community.doclib.dao.DocumentCommentLikeFinder;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/library/likeFileComment.ajx"
 */
public class LikeFileCommentAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	protected DocumentCommentLikeFinder documentCommentLikeFinder;

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
			
			if (context.getRequest().getParameter("fileId") != null 
					&& !"".equals(context.getRequest().getParameter("fileId"))) {
				int fileId = Integer.parseInt(context.getRequest().getParameter("fileId"));
				cmd.setFileId(fileId);
			}
			DocumentCommentLike documentCommentLike;
			if (cmd.getCommentId() > 0) {
				try {
					documentCommentLike = documentCommentLikeFinder.getLikeForDocumentCommentAndUser(cmd.getFileId(), cmd.getCommentId(), currentUserId);
					//If no like exists, delete this like record
					documentCommentLike.delete();
					int count = documentCommentLikeFinder.getLikeCountForDocumentComment(cmd.getFileId(), cmd.getCommentId());
					json.put("count", count);
					json.put("newLType", "Like");
				} catch (ElementNotFoundException e) {
					//If no like exists, create a new like record
					documentCommentLike = documentCommentLikeFinder.newDocumentCommentLike();
					
					documentCommentLike.setDocumentId(cmd.getFileId());
					documentCommentLike.setDocumentCommentId(cmd.getCommentId());
					documentCommentLike.setPosterId(currentUserId);
					documentCommentLike.update();     
					
					int count = documentCommentLikeFinder.getLikeCountForDocumentComment(cmd.getFileId(), cmd.getCommentId());
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
		private int fileId;

		public int getCommentId() {
			return commentId;
		}

		public void setCommentId(int commentId) {
			this.commentId = commentId;
		}

		public int getFileId() {
			return fileId;
		}

		public void setFileId(int fileId) {
			this.fileId = fileId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setDocumentCommentLikeFinder(
			DocumentCommentLikeFinder documentCommentLikeFinder) {
		this.documentCommentLikeFinder = documentCommentLikeFinder;
	}
}