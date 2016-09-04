package com.era.community.media.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.doclib.dao.DocumentComment;
import com.era.community.doclib.dao.DocumentCommentFinder;
import com.era.community.media.dao.PhotoComment;
import com.era.community.media.dao.PhotoCommentFinder;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/postMediaComment.ajx" 
 */
public class PostMediaCommentAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private PhotoCommentFinder photoCommentFinder;
	private DocumentCommentFinder documentCommentFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();
		HttpServletResponse resp = context.getResponse();
		String jsonString = "";
		JSONObject json;
		if ( currentUser != null && cmd.getMediaId() > 0 && !"".equals(cmd.getComment())) {
			if (cmd.getLibraryId() > 0) {
				DocumentComment documentComment = documentCommentFinder.newDocumentComment();
				documentComment.setComment(cmd.getComment());
				documentComment.setPosterId(currentUser.getId());
				documentComment.setDocumentId(cmd.getMediaId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String dt = sdf.format(now);
				Timestamp ts = Timestamp.valueOf(dt);
				documentComment.setDatePosted(ts);
				documentComment.update();
				
				json = new JSONObject();
				json.put("comment", documentComment.getComment().replace( "<br />", "\n"));
				json.put("posterId", documentComment.getPosterId());
				json.put("posterName", currentUser.getFullName());
				json.put("photoId", documentComment.getId());
				json.put("datePosted", documentComment.getDatePosted().toString());
				json.put("photoPresent", currentUser.isPhotoPresent());
			} else {
				PhotoComment photoComment = photoCommentFinder.newPhotoComment();
				photoComment.setComment(cmd.getComment());
				photoComment.setPosterId(currentUser.getId());
				photoComment.setPhotoId(cmd.getMediaId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String dt = sdf.format(now);
				Timestamp ts = Timestamp.valueOf(dt);
				photoComment.setDatePosted(ts);
				photoComment.update();
				
				json = new JSONObject();
				json.put("comment", photoComment.getComment().replace( "<br />", "\n"));
				json.put("posterId", photoComment.getPosterId());
				json.put("posterName", currentUser.getFullName());
				json.put("photoId", photoComment.getId());
				json.put("datePosted", photoComment.getDatePosted().toString());
				json.put("photoPresent", currentUser.isPhotoPresent());
			}
			jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
		}
		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private String comment;
		private int mediaId;
		private int libraryId;

		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public int getMediaId() {
			return mediaId;
		}
		public void setMediaId(int mediaId) {
			this.mediaId = mediaId;
		}
		public int getLibraryId() {
			return libraryId;
		}
		public void setLibraryId(int libraryId) {
			this.libraryId = libraryId;
		}

	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setPhotoCommentFinder(PhotoCommentFinder photoCommentFinder) {
		this.photoCommentFinder = photoCommentFinder;
	}

	public void setDocumentCommentFinder(DocumentCommentFinder documentCommentFinder) {
		this.documentCommentFinder = documentCommentFinder;
	}
}