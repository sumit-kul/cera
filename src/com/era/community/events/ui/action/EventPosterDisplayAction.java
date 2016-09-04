package com.era.community.events.ui.action;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.BlobData;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.events.dao.Event;
import com.era.community.events.dao.EventFinder;

/**
 * @spring.bean name="/event/eventPoster.img"
 */
public class EventPosterDisplayAction extends AbstractCommandAction
{   
    EventFinder eventFinder;
    CommunityEraContextManager contextManager;
    
    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command)data;
        
        Event event;
        if (cmd.getId()==0)
        	return null;
        else 
        	event = eventFinder.getEventForId(cmd.getId());
        
        if (event != null) {
        BlobData photoData = event.readPhoto(); 
        if (photoData.isEmpty()) return null;

        InputStream is = photoData.getStream();
        
        HttpServletResponse response = contextManager.getContext().getResponse();
        
        String type = event.getPhotoContentType();
        response.setContentType(type==null?"image/jpeg":type);
        response.setContentLength((int)photoData.getLength());
        OutputStream out = response.getOutputStream();      
        
        int i;
        byte[] buf = new byte[4096];
        while ((i=is.read(buf, 0, buf.length))>0) out.write(buf,0,i);
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

	public void setEventFinder(EventFinder eventFinder) {
		this.eventFinder = eventFinder;
	}
}