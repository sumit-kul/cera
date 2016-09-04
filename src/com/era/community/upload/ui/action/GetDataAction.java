package com.era.community.upload.ui.action;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.BlobData;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.upload.dao.Upload;
import com.era.community.upload.dao.UploadFinder;
import com.era.community.upload.ui.dto.UploadDto;

/**
 * @spring.bean name="/upld/get-data.do"
 */
public class GetDataAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected UploadFinder uploadFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;

		HttpServletResponse resp = contextManager.getContext().getResponse();

		try {
			Upload upload = uploadFinder.getUploadForId(cmd.getId());

			BlobData photoData = upload.readData(); 
			if (photoData.isEmpty()) return null;

			InputStream is = photoData.getStream();

			HttpServletResponse response = contextManager.getContext().getResponse();

			String type = upload.getDataContentType();
			response.setContentType(type==null?"image/jpeg":type);
			response.setContentLength((int)photoData.getLength());

			OutputStream out = response.getOutputStream();      

			int i;
			byte[] buf = new byte[4096];
			while ((i=is.read(buf, 0, buf.length))>0) out.write(buf,0,i);

		}
		catch (ElementNotFoundException x) {
			resp.sendError(404, "Cannot find Upload with id "+cmd.getId());
		}
		catch (Throwable t) {
			logger.error("", t);
			resp.sendError(500, t.toString());
		}

		return null;
	}

	public static class Command extends UploadDto implements CommandBean
	{
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setUploadFinder(UploadFinder uploadFinder)
	{
		this.uploadFinder = uploadFinder;
	}

}
