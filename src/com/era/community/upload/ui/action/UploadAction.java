package com.era.community.upload.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.upload.dao.Upload;
import com.era.community.upload.dao.UploadFinder;

/**
 * @spring.bean name="/upld/create-upload.do"
 */
public class UploadAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected UploadFinder uploadFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;

		HttpServletResponse resp = contextManager.getContext().getResponse();
		resp.setContentType("text/html; charset=UTF-8");

		Upload upload;
		try {
			upload = uploadFinder.newUpload();
			upload.setCreatorId(contextManager.getContext().getCurrentUser().getId());
			upload.setDescription("");
			upload.setTitle("");
			upload.update();
			upload.storeData(cmd.getFile());
		}
		catch (Throwable t) {
			logger.error("", t);
			resp.setStatus(500);
			Writer out = resp.getWriter();
			out.write("<html><body><form><textarea>'ERR:"+t.toString()+"'</textarea></form></body></html>");
			out.close();
			return null;
		}
		Writer out = resp.getWriter();
		out.write("<html><body><form><textarea>'/upld/get-data.do?id="+upload.getId()+"'</textarea></form></body></html>");
		out.close();

		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private MultipartFile file;

		public final MultipartFile getFile()
		{
			return file;
		}

		public final void setFile(MultipartFile file)
		{
			this.file = file;
		}
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
