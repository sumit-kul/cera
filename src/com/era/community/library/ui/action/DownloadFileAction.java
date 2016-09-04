package com.era.community.library.ui.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.BlobData;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;

/**
 * @spring.bean name="/cid/[cec]/library/downloadFile.do" 
 */
public class DownloadFileAction extends AbstractCommandAction
{
	DocumentFinder documentFinder;
	CommunityEraContextManager contextManager;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();
		Document doc;

		if (context.getCurrentUser() == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()), "command", cmd);
		} else {
			context.getRequest().getSession().removeAttribute("url_prior_login");
		}

		if (cmd.getId()==0)
			return null;
		else 
			doc = documentFinder.getDocumentForId(cmd.getId());

		BlobData fileData = doc.getFile(); 
		if (fileData.isEmpty()) return null;

		InputStream is = fileData.getStream();

		HttpServletResponse response = contextManager.getContext().getResponse();

		String type = fileData.getContentType();
		response.setContentType("application/force-download");
		response.setContentLength((int)fileData.getLength());
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "filename="+doc.getFileName());
		OutputStream out = response.getOutputStream();      

		int i;
		byte[] buf = new byte[4096];
		while ((i=is.read(buf, 0, buf.length))>0) out.write(buf,0,i);

		doc.setDownloads(doc.getDownloads() + 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		doc.setLastDownloadTime(ts);
		doc.update();

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

	public final void setUserFinder(DocumentFinder docFinder)
	{
		this.documentFinder = docFinder;
	}
}
