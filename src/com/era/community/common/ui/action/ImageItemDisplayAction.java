package com.era.community.common.ui.action;

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
 * @spring.bean name="/common/showImage.img"
 */
public class ImageItemDisplayAction extends AbstractCommandAction
{   
	CommunityEraContextManager contextManager;
	ImageFinder imageFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();
		String type = cmd.getType();
		
		if (context.getRequest().getParameter("showType") != null 
				&& !"".equals(context.getRequest().getParameter("showType"))) {
			cmd.setShowType(context.getRequest().getParameter("showType"));
		}
		
		Image image = null;
		if (cmd.getItemId() > 0 && type != null & !"".equals(type)) {
			try {
				image = imageFinder.getImageForItemType(cmd.getItemId(), type);
			} catch (ElementNotFoundException e) {
			}
		}

		if (image != null) {
			BlobData imageData = null;
			if ("t".equals(cmd.getShowType())) {
				imageData = image.getFileThumb();
			} else {
				imageData = image.getFile();
			}
			if (imageData ==  null || imageData.isEmpty()) return null;
			
			InputStream is = imageData.getStream();

			HttpServletResponse response = contextManager.getContext().getResponse();
			response.setContentType("image/jpeg");
			response.setContentLength((int)imageData.getLength());

			OutputStream out = response.getOutputStream();      

			int i;
			byte[] buf = new byte[4096];
			while ((i=is.read(buf, 0, buf.length))>0) out.write(buf,0,i);
		}
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int itemId;
		private String type;
		private String showType;

		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getItemId() {
			return itemId;
		}
		public void setItemId(int itemId) {
			this.itemId = itemId;
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