package com.era.community.library.ui.action;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.BlobData;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;

/**
 * @spring.bean name="/library/mediaDisplay.img"
 */
public class MediaDisplayAction extends AbstractCommandAction
{   
    CommunityEraContextManager contextManager;
    DocumentFinder documentFinder;
    
    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command)data;
        Document document = null;
        try {
        	document = documentFinder.getDocumentForId(cmd.getId());
		} catch (Exception e) {
			 return null;
		}
        if (document != null) {
        BlobData media = document.getFile(); 
        if (media.isEmpty()) return null;

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

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}
}