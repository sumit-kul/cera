package com.era.community.pers.ui.action;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.BlobData;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.media.dao.Photo;
import com.era.community.media.dao.PhotoFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/common/showImage.img"
 */
public class UserPhotoDisplayAction extends AbstractCommandAction
{   
	CommunityEraContextManager contextManager;
	UserFinder userFinder;
	PhotoFinder photoFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();
		User user;
		if (cmd.getId()==0)
			user = context.getCurrentUser();
		else 
			user = userFinder.getUserEntity(cmd.getId());
		
		if (context.getRequest().getParameter("showType") != null 
				&& !"".equals(context.getRequest().getParameter("showType"))) {
			cmd.setShowType(context.getRequest().getParameter("showType"));
		}

		if (user != null) {
			BlobData photoData = null;
			if ("editThimbnail".equalsIgnoreCase(cmd.getType())) {
				if ("Cover".equalsIgnoreCase(cmd.getImgType())) {
					Photo photo = photoFinder.getCurrentCoverPhoto(cmd.getId());
					photoData = photo.readPhoto();
				} else {
					Photo photo = photoFinder.getCurrentProfilePhoto(cmd.getId());
					photoData = photo.readPhoto();
				}
			} else {
				if ("Cover".equalsIgnoreCase(cmd.getImgType())) {
					if ("m".equals(cmd.getShowType())) {
						photoData = user.readCover();
					} else {
						photoData = user.readCoverThumb();
					}
				} else {
					if ("m".equals(cmd.getShowType())) {
						photoData = user.readPhoto();
					} else {
						photoData = user.readPhotoThumb();
					}
				}
			}

			if (photoData.isEmpty()) return null;

			InputStream is = photoData.getStream();

			HttpServletResponse response = contextManager.getContext().getResponse();

			String type = user.getPhotoContentType();
			response.setContentType(type==null?"image/jpeg":type);
			response.setContentLength((int)photoData.getLength());

			OutputStream out = response.getOutputStream();      

			int i;
			byte[] buf = new byte[4096];
			while ((i=is.read(buf, 0, buf.length))>0) out.write(buf,0,i);
		}
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int Id;
		private String type;
		private String imgType;
		private String showType;

		public final int getId()
		{
			return Id;
		}
		public final void setId(int id)
		{
			Id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getImgType() {
			return imgType;
		}
		public void setImgType(String imgType) {
			this.imgType = imgType;
		}
		public String getShowType() {
			return showType;
		}
		public void setShowType(String showType) {
			this.showType = showType;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setPhotoFinder(PhotoFinder photoFinder) {
		this.photoFinder = photoFinder;
	}
}
