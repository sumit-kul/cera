package com.era.community.events.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.events.dao.Event;
import com.era.community.events.dao.EventFinder;

/**
 * @spring.bean name="/cid/[cec]/event/master-calendar-toggle.do" 
 */
public class MasterCalendarToggleAction extends AbstractCommandAction
{
	/*
	 * Access markers.
	 */
	public static final String REQUIRES_AUTHENTICATION = "";

	/* Injected */
	protected EventFinder eventFinder;   

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		Event event = eventFinder.getEventForId(cmd.getId());
		event.update();
		return new ModelAndView("redirect:showEventEntry.do?id=" + cmd.getId());
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

	public void setEventFinder(EventFinder eventFinder)
	{
		this.eventFinder = eventFinder;
	}



}
