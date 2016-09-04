package com.era.community.upload.ui.action;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.BlobData;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.upload.dao.Image;
import com.era.community.upload.dao.ImageFinder;

/**
 * @spring.bean name="/upload/imageDisplay.img"
 */
public class ImageDisplayAction extends AbstractCommandAction
{   
	CommunityEraContextManager contextManager;
	ImageFinder imageFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();
		Image image = null;
		try {
			image = imageFinder.getImageForId(cmd.getId());
		} catch (ElementNotFoundException e) {
			return null;
		}

		if (context.getRequest().getParameter("showType") != null 
				&& !"".equals(context.getRequest().getParameter("showType"))) {
			cmd.setShowType(context.getRequest().getParameter("showType"));
		}

		if (image != null) {
			BlobData media = null;
			if ("t".equals(cmd.getShowType())) {
				media = image.getFileThumb();
			} else {
				media = image.getFile();
			}
			if (media ==  null || media.isEmpty()) return null;

			InputStream is = media.getStream();
			HttpServletResponse response = contextManager.getContext().getResponse();
			response.reset();
			response.setContentType("image/jpg");
			OutputStream out = response.getOutputStream();      
			int i;
			byte[] buf = new byte[4096];
			while ((i=is.read(buf, 0, buf.length))>0) out.write(buf,0,i);
			out.flush();
		}
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int Id;
		private String showType;

		public final int getId()
		{
			return Id;
		}
		public final void setId(int id)
		{
			Id = id;
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

	public void setImageFinder(ImageFinder imageFinder) {
		this.imageFinder = imageFinder;
	}
}