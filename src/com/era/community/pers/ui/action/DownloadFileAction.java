package com.era.community.pers.ui.action;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.BlobData;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.media.dao.Photo;
import com.era.community.media.dao.PhotoFinder;

/**
 * @spring.bean name="/pers/downloadMedia.do" 
 */
public class DownloadFileAction extends AbstractCommandAction
{
    CommunityEraContextManager contextManager;
    PhotoFinder photoFinder;
    
    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command)data;
        CommunityEraContext context = contextManager.getContext();
        Photo photo;
        
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
        	photo = photoFinder.getPhotoForId(cmd.getId());
        
        BlobData fileData = photo.readPhoto(); 
		if (fileData.isEmpty()) return null;

		InputStream is = fileData.getStream();

		HttpServletResponse response = contextManager.getContext().getResponse();
		String fname= photo.getFileName()==null? photo.getTitle()+".jpg":photo.getFileName();
		String type = fileData.getContentType();
		response.setContentType("application/force-download");
		response.setContentLength((int)fileData.getLength());
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "attachment; filename="+fname);
		OutputStream out = response.getOutputStream();      

		int i;
		byte[] buf = new byte[4096];
		while ((i=is.read(buf, 0, buf.length))>0) out.write(buf,0,i);
		
/*		photo.setDownloads(photo.getd + 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		doc.setLastDownloadTime(ts);
		doc.update();*/
        
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
                        
        public int getFile()
        {
            return file;
        }
        
        public void setFile(int file)
        {
            this.file = file;
        }
    }

    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
	
    public void setPhotoFinder(PhotoFinder photoFinder) {
		this.photoFinder = photoFinder;
	}
}