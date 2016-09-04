package com.era.community.faqs.ui.action;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.BlobData;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.faqs.dao.Faq;
import com.era.community.faqs.dao.FaqFinder;

/**
 * @spring.bean name="/faq/get-file.do" 
 */
public class FileGetAction extends AbstractCommandAction
{
	/*
	 * Injected references. 
	 */
	FaqFinder faqFinder;
	CommunityEraContextManager contextManager;

	/*
	 * Handle the request.
	 */
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		Faq faq;

		if (cmd.getId()==0)
			return null;
		else 
			faq = faqFinder.getFaqForId(cmd.getId());

		BlobData fileData = faq.getFile(); 
		if (fileData.isEmpty()) return null;

		InputStream is = fileData.getStream();

		HttpServletResponse response = contextManager.getContext().getResponse();

		String type = fileData.getContentType();
		response.setContentType(type==null?"image/jpeg":type);
		response.setContentLength((int)fileData.getLength());
		response.setHeader("Content-Disposition", "filename="+faq.getFileName());
		OutputStream out = response.getOutputStream();      

		int i;
		byte[] buf = new byte[4096];
		while ((i=is.read(buf, 0, buf.length))>0) out.write(buf,0,i);

		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int Id;
		public final int getId()
		{
			return Id;
		}
		public final void setId(int id)
		{
			Id = id;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setFaqFinder(FaqFinder faqFinder)
	{
		this.faqFinder = faqFinder;
	}
}
