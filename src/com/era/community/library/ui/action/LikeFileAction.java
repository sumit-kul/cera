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
import com.era.community.doclib.dao.DocumentLike;
import com.era.community.doclib.dao.DocumentLikeFinder;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/library/likeFile.ajx"
 */
public class LikeFileAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	protected DocumentLikeFinder documentLikeFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() != null) {
			int currentUserId = context.getCurrentUser().getId();
			JSONObject json = new JSONObject();
			Command cmd = (Command) data;
			if (context.getRequest().getParameter("fileId") != null 
					&& !"".equals(context.getRequest().getParameter("fileId"))) {
				int fileId = Integer.parseInt(context.getRequest().getParameter("fileId"));
				cmd.setFileId(fileId);
			}
			DocumentLike documentLike;
			if (cmd.getFileId() > 0) {
				try {
					documentLike = documentLikeFinder.getLikeForDocumentAndUser(cmd.getFileId(), currentUserId);
					//If no like exists, delete this like record
					documentLike.delete();
					int count = documentLikeFinder.getLikeCountForDocument(cmd.getFileId());
					json.put("count", count);
					json.put("newLType", "Like");
				} catch (ElementNotFoundException e) {
					//If no like exists, create a new like record
					documentLike = documentLikeFinder.newDocumentLike();
					
					documentLike.setDocumentId(cmd.getFileId());
					documentLike.setPosterId(currentUserId);
					documentLike.update();     
					
					int count = documentLikeFinder.getLikeCountForDocument(cmd.getFileId());
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

	public void setDocumentLikeFinder(DocumentLikeFinder documentLikeFinder) {
		this.documentLikeFinder = documentLikeFinder;
	}
}