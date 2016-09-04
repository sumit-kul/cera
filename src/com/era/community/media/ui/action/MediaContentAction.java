package com.era.community.media.ui.action;

import java.io.Writer;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.doclib.dao.DocumentCommentFinder;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.ui.dto.DocumentCommentDto;
import com.era.community.media.dao.PhotoCommentFinder;
import com.era.community.media.dao.PhotoFinder;
import com.era.community.media.ui.dto.MediaInfoDto;
import com.era.community.media.ui.dto.PhotoCommentDto;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/mediaContent.ajx" 
 */
public class MediaContentAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private PhotoFinder photoFinder;
	private PhotoCommentFinder photoCommentFinder;
	private DocumentFinder documentFinder;
	private DocumentCommentFinder documentCommentFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		User currentUser = context.getCurrentUser();
		
		try {
			if (cmd.getImgId() > 0) {
				MediaInfoDto info = photoFinder.extraInfoForPhoto(cmd.getImgId(), currentUser != null ? currentUser.getId() : 0);
				
				QueryScroller scroller = photoCommentFinder.listCommentsForPhoto(cmd.getImgId(), currentUser != null ? currentUser.getId() : 0);
				int pNumber = 1;
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(8);
				IndexedScrollerPage page = scroller.readPage(pNumber);
				JSONObject json = page.toJsonString(pNumber);
				json = toJsonString(json, info);
				json.put("editAllowed", isEditAllowed(currentUser, info));
				json.put("photoLikeAllowed", isPhotoLikeAllowed(currentUser, info));
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}else if (cmd.getMediaId() > 0) {
				MediaInfoDto info = documentFinder.extraInfoForMedia(cmd.getMediaId(), currentUser != null ? currentUser.getId() : 0);
				
				QueryScroller scroller = documentCommentFinder.listCommentsForDocumentId(cmd.getMediaId(), currentUser != null ? currentUser.getId() : 0);
				int pNumber = 1;
				scroller.setBeanClass(MediaRowBean.class, this);
				scroller.setPageSize(8);
				IndexedScrollerPage page = scroller.readPage(pNumber);
				JSONObject json = page.toJsonString(pNumber);
				json = toJsonString(json, info);
				json.put("editAllowed", isEditAllowed(currentUser, info));
				json.put("photoLikeAllowed", isPhotoLikeAllowed(currentUser, info));
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean isEditAllowed(User current, MediaInfoDto info) {
		if (current != null && current.getId() == info.getUserId()){
			return true;		
		}
		return false;
	}
	
	private boolean isPhotoLikeAllowed(User current, MediaInfoDto info) {
		if (current != null){ //currently public
			return true;		
		}
		return false;
	}
	
	public class RowBean extends PhotoCommentDto implements EntityWrapper
	{
		private int likeCommentCount;
		private int userLikeId;

		public int getLikeCommentCount() {
			return likeCommentCount;
		}

		public void setLikeCommentCount(Long likeCommentCount) {
			this.likeCommentCount = likeCommentCount.intValue();
		}

		public int getUserLikeId() {
			return userLikeId;
		}

		public void setUserLikeId(Long userLikeId) {
			this.userLikeId = userLikeId.intValue();
		}
	}
	
	public class MediaRowBean extends DocumentCommentDto implements EntityWrapper
	{
		private int likeCommentCount;
		private int userLikeId;

		public int getLikeCommentCount() {
			return likeCommentCount;
		}

		public void setLikeCommentCount(Long likeCommentCount) {
			this.likeCommentCount = likeCommentCount.intValue();
		}

		public int getUserLikeId() {
			return userLikeId;
		}

		public void setUserLikeId(Long userLikeId) {
			this.userLikeId = userLikeId.intValue();
		}
	}
	
	private JSONObject toJsonString(JSONObject json, MediaInfoDto info) throws Exception
    {
        //JSONObject json = new JSONObject();
            for (Method m : info.getClass().getMethods()) {
                String name = m.getName(); 
                if (name.startsWith("get")) {
                	if (name.equals("getClass")) continue;
                	json.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(info, new Object[] {}));
            }            
        }
        return json;
    }
	
	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int imgId;
		private int mediaId;

		public int getImgId() {
			return imgId;
		}

		public void setImgId(int imgId) {
			this.imgId = imgId;
		}

		public int getMediaId() {
			return mediaId;
		}

		public void setMediaId(int mediaId) {
			this.mediaId = mediaId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setPhotoFinder(PhotoFinder photoFinder) {
		this.photoFinder = photoFinder;
	}

	public final void setPhotoCommentFinder(PhotoCommentFinder photoCommentFinder) {
		this.photoCommentFinder = photoCommentFinder;
	}

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}

	public void setDocumentCommentFinder(DocumentCommentFinder documentCommentFinder) {
		this.documentCommentFinder = documentCommentFinder;
	}
}