package com.era.community.events.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.events.dao.Event;
import com.era.community.events.dao.EventFinder;

/**
 * @spring.bean name="/cid/[cec]/event/deleteEvent.do"
 */
public class DeleteEventAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager;
	private EventFinder eventFinder; 

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		Event event = eventFinder.getEventForId(cmd.getId());
		if ( event  != null && event.isWriteAllowed( context.getCurrentUserDetails() )) {
			event.delete();
			return new ModelAndView("event/event-delete-confirm");
		}
		else {
			return null;
		}
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int id;

		public int getId()
		{
			return id;
		}
		public void setId(int id)
		{
			this.id = id;
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
}