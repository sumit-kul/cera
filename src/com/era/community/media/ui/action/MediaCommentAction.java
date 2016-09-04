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
import com.era.community.communities.ui.action.AllCommunityIndexAction.RowBean;
import com.era.community.communities.ui.dto.CommunityDto;
import com.era.community.media.dao.PhotoComment;
import com.era.community.media.dao.PhotoCommentFinder;
import com.era.community.media.dao.PhotoFinder;
import com.era.community.media.dao.generated.PhotoCommentEntity;
import com.era.community.media.ui.dto.MediaInfoDto;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/mediaComments.ajx" 
 */
public class MediaCommentAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private PhotoCommentFinder photoCommentFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		User currentUser = context.getCurrentUser();
		
		try {
			if (cmd.getImgId() > 0) {
				QueryScroller scroller = photoCommentFinder.listCommentsForPhoto(cmd.getImgId(), currentUser != null ? currentUser.getId() : 0);
				int pNumber = cmd.getPageNumber() + 1;
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(8);
				IndexedScrollerPage page = scroller.readPage(pNumber);
				JSONObject json = page.toJsonString(pNumber);
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
	
	public class RowBean extends PhotoCommentEntity implements EntityWrapper
	{
		
	}
	
	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int imgId;
		private int pageNumber;

		public int getImgId() {
			return imgId;
		}

		public void setImgId(int imgId) {
			this.imgId = imgId;
		}

		public final int getPageNumber() {
			return pageNumber;
		}

		public final void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setPhotoCommentFinder(PhotoCommentFinder photoCommentFinder) {
		this.photoCommentFinder = photoCommentFinder;
	}
}