package com.era.community.media.ui.action;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.BlobData;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.media.dao.Photo;
import com.era.community.media.dao.PhotoFinder;

/**
 * @spring.bean name="/pers/photoDisplay.img"
 */
public class PhotoDisplayAction extends AbstractCommandAction
{   
	PhotoFinder photoFinder;
	DocumentFinder documentFinder;
	CommunityEraContextManager contextManager;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		BlobData photoData = null;
		Photo photo = null;
		Document document = null;
		try {
			if (cmd.getMediaId() > 0) {
				document= documentFinder.getDocumentForId(cmd.getMediaId());
			} else {
				photo = photoFinder.getPhotoForId(cmd.getId());
			}
		} catch (ElementNotFoundException e) {
		}

		if (photo != null) {
			photoData = photo.readPhoto(); 
			if (photoData.isEmpty()) return null;

			InputStream is = photoData.getStream();
			HttpServletResponse response = contextManager.getContext().getResponse();
			response.reset();
			response.setContentType("image/jpg");
			OutputStream out = response.getOutputStream();      
			int i;
			byte[] buf = new byte[4096];
			while ((i=is.read(buf, 0, buf.length))>0) out.write(buf,0,i);
			out.flush();
		}
		if (document != null) {
			photoData = document.getFile(); 
			if (photoData.isEmpty()) return null;

			InputStream is = photoData.getStream();
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
		private int mediaId;
		public final int getId()
		{
			return Id;
		}
		public final void setId(int id)
		{
			Id = id;
		}
		public int getMediaId() {
			return mediaId;
		}
		public void setMediaId(int mediaId) {
			this.mediaId = mediaId;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setPhotoFinder(PhotoFinder photoFinder) {
		this.photoFinder = photoFinder;
	}

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}
}