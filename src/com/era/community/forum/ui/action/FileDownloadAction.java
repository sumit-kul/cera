package com.era.community.forum.ui.action;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.BlobData;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumItemFinder;

/**
 * @spring.bean name="/cid/[cec]/forum/fileDownload.do" 
 */
public class FileDownloadAction extends AbstractCommandAction
{

	public static final String REQUIRES_AUTHENTICATION = "";
	ForumItemFinder itemFinder;
	CommunityEraContextManager contextManager;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		ForumItem item;

		if (cmd.getId()==0)
			return null;
		else
			item=itemFinder.getForumItemForId(cmd.getId());

		BlobData fileData =  item.getFile1(); 
		String type = item.getFile1ContentType();

		if (cmd.getFile()==1) {
			fileData = item.getFile1(); 

		}
		else if (cmd.getFile()==2) {
			fileData = item.getFile2(); 
			type = item.getFile2ContentType();
		}
		else if (cmd.getFile()==3) {
			fileData = item.getFile3(); 
			type = item.getFile3ContentType();
		}
		if (fileData.isEmpty()) return null;


		InputStream is = fileData.getStream();
		HttpServletResponse response = contextManager.getContext().getResponse();

		response.setHeader("Content-Disposition", "filename="+item.getFileName1());
		if (cmd.getFile()==2) {
			response.setHeader("Content-Disposition", "filename="+item.getFileName2());

		}
		if (cmd.getFile()==3) {        
			response.setHeader("Content-Disposition", "filename="+item.getFileName3());

		}

		response.setContentType(type==null?"image/jpeg":type);
		response.setContentLength((int)fileData.getLength());

		OutputStream out = response.getOutputStream();      

		int i;
		byte[] buf = new byte[4096];
		while ((i=is.read(buf, 0, buf.length))>0) out.write(buf,0,i);

		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int Id;

		private int file;

		public final int getId()
		{
			return Id;
		}
		public final void setId(int id)
		{
			Id = id;
		}
		public final int getFile()
		{
			return file;
		}
		public final void setFile(int file)
		{
			this.file = file;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}


	public final ForumItemFinder getItemFinder()
	{
		return itemFinder;
	}
	public final void setItemFinder(ForumItemFinder itemFinder)
	{
		this.itemFinder = itemFinder;
	}
}
