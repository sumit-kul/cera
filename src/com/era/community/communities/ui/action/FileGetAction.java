package com.era.community.communities.ui.action;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.BlobData;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
/**
 *
 * @spring.bean name="/cid/[cec]/comm/get-file.do" 
 */
public class FileGetAction extends AbstractCommandAction
{
    /*
     * Injected references. 
     */
    CommunityFinder communityFinder;
    CommunityEraContextManager contextManager;
    
    public final void setCommunityFinder(CommunityFinder communityFinder)
    {
        this.communityFinder = communityFinder;
    }

    /*
     * Handle the request.
     */
    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command)data;
        Community community;
        
        if (cmd.getId()==0)
            return null;
        else
            community=communityFinder.getCommunityForId(cmd.getId());
        
        BlobData fileData =  community.getCommunityLogo();        

        fileData = community.getCommunityLogo();
        if (fileData.isEmpty()) return null;


        InputStream is = fileData.getStream();
        
        HttpServletResponse response = contextManager.getContext().getResponse();

        response.setHeader("Content-Disposition", "filename="+community.getCommunityLogo());

        String type = fileData.getContentType();
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



}
