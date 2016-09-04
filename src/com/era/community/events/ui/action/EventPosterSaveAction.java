package com.era.community.events.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.events.dao.Event;
import com.era.community.events.dao.EventCalendar;
import com.era.community.events.dao.EventCalendarFinder;
import com.era.community.events.dao.EventFinder;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/event/saveEventPoster.img"
 */
public class EventPosterSaveAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	protected EventFinder eventFinder;
	protected EventCalendarFinder eventCalFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();
		try {
		if (context.getCurrentUser() == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()), "command", cmd);
		}

		if (cmd.getFile() != null && !cmd.getFile().isEmpty()) {
			Event event = null;
			if (cmd.getEventId() > 0) {
				event = eventFinder.getEventForId(cmd.getEventId());
			} else {
				EventCalendar eventCal = eventCalFinder.getEventCalendarForCommunityId(cmd.getCommunityId());
				event = eventFinder.newEvent();
				event.setCalendarId(eventCal.getId());
				event.setPosterId(context.getCurrentUser().getId());
				event.setHostCount(1);
				event.setConfirmed(1);
				event.setMayBe(0);
				event.setInvited(0);
				event.setDeclined(0);
				event.update();
			}
			cmd.setEventId(event.getId());
			event.storePhoto(cmd.getFile());
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		HttpServletResponse resp = context.getResponse();
		resp.setContentType("text/json");
		JSONObject json = new JSONObject();
		json.put("eventId", cmd.getEventId());
		String jsonString = json.serialize();
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int Id;
		private MultipartFile file;
		private int eventId;
		private int communityId;

		public final int getId()
		{
			return Id;
		}
		public final void setId(int id)
		{
			Id = id;
		}
		public MultipartFile getFile() {
			return file;
		}
		public void setFile(MultipartFile file) {
			this.file = file;
		}
		public int getEventId() {
			return eventId;
		}
		public void setEventId(int eventId) {
			this.eventId = eventId;
		}
		public int getCommunityId() {
			return communityId;
		}
		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setEventFinder(EventFinder eventFinder) {
		this.eventFinder = eventFinder;
	}

	public EventCalendarFinder getEventCalFinder() {
		return eventCalFinder;
	}

	public void setEventCalFinder(EventCalendarFinder eventCalFinder) {
		this.eventCalFinder = eventCalFinder;
	}
}