package com.era.community.events.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.RunAsServerCallback;
import support.community.util.StringHelper;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.events.dao.Event;
import com.era.community.events.dao.EventCalendar;
import com.era.community.events.dao.EventCalendarFinder;
import com.era.community.events.dao.EventFinder;
import com.era.community.events.ui.dto.EventDto;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/master-calendar/display.do" 
 */
public class MasterCalendarDisplayAction extends AbstractCommandAction
{       

	private EventFinder eventFinder;
	private EventCalendarFinder eventCalFinder;
	private UserFinder userFinder;
	private CommunityEraContextManager contextManager;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		Event event = eventFinder.getEventForId( cmd.getId() );
		cmd.copyPropertiesFrom( event );            
		User user =  userFinder.getUserEntity( cmd.getPosterId() );

		cmd.setUserName( user.getFirstName() + " " + user.getLastName() );

		final int calid = cmd.getCalendarId();            
		cmd.setCommunityName(getRunServerAsTemplate().execute(new RunAsServerCallback() {
			public Object doInSecurityContext() throws Exception
			{                
				EventCalendar cal = eventCalFinder.getEventCalendarForId(calid);
				return cal.getCommunity().getName();                                        
			}
		}).toString());

		cmd.setDisplayBody(StringHelper.formatForDisplay(event.getDescription()));
		return new ModelAndView("event/master-calendar-display", "command", cmd);  
	}

	public static class Command extends EventDto implements CommandBean
	{   
		private String userName;
		private String communityName;
		private String displayBody;    	        

		public String getCommunityName()
		{
			return communityName;
		}
		public void setCommunityName(String communityName)
		{
			this.communityName = communityName;
		}
		public String getDisplayBody()
		{
			return displayBody;
		}
		public void setDisplayBody(String displayBody)
		{
			this.displayBody = displayBody;
		}
		public String getUserName()
		{
			return userName;
		}        
		public void setUserName(String userName)
		{
			this.userName = userName;
		}

	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
	public void setEventFinder(EventFinder eventFinder)
	{
		this.eventFinder = eventFinder;
	}
	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}
	public void setEventCalFinder(EventCalendarFinder eventCalFinder)
	{
		this.eventCalFinder = eventCalFinder;
	}

}
