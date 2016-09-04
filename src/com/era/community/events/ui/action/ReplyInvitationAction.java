package com.era.community.events.ui.action;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.events.dao.Event;
import com.era.community.events.dao.EventFinder;
import com.era.community.events.dao.EventInviteeLink;
import com.era.community.events.dao.EventInviteeLinkFinder;

/**
 * @spring.bean name="/event/replyInvitation.ajx"
 */
public class ReplyInvitationAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	protected EventInviteeLinkFinder eventInviteeLinkFinder;
	protected EventFinder eventFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() != null) {
			int currentUserId = context.getCurrentUser().getId();
			Command cmd = (Command) data;
			if (context.getRequest().getParameter("eventId") != null 
					&& !"".equals(context.getRequest().getParameter("eventId"))) {
				int id = Integer.parseInt(context.getRequest().getParameter("eventId"));
				cmd.setEventId(id);
			}
			
			if (context.getRequest().getParameter("status") != null 
					&& !"".equals(context.getRequest().getParameter("status"))) {
				int id = Integer.parseInt(context.getRequest().getParameter("status"));
				cmd.setStatus(id);
			}
			
			EventInviteeLink link;
			String returnString = "";
			if (cmd.getEventId() > 0) {
				try {
					link = eventInviteeLinkFinder.getEventInviteeLinkForEventAndUser(cmd.getEventId(), currentUserId);
					Event event = eventFinder.getEventForId(cmd.getEventId());
					
					/*if (cmd.getStatus() == 1) { // confirmed
						if (link.getJoiningStatus() == 0) { //if invited only
							event.setConfirmed(event.getConfirmed()+1);
						} else if (link.getJoiningStatus() == 2) { //if maybe
							event.setConfirmed(event.getConfirmed()+1);
							event.setMayBe(event.getConfirmed()-1);
						}
						link.setJoiningStatus(cmd.getStatus());
					} else if (cmd.getStatus() == 2) { // maybe
						if (link.getJoiningStatus() == 0) { //if invited only
							event.setConfirmed(event.getConfirmed()+1);
						}
					} */
					
					if (cmd.getStatus() == 1) { // confirmed
						event.setConfirmed(event.getConfirmed()+1);
						link.setJoiningStatus(cmd.getStatus());
					} else if (cmd.getStatus() == 2) { // maybe
						event.setMayBe(event.getMayBe()+1);
						link.setJoiningStatus(cmd.getStatus());
					} else if (cmd.getStatus() == 3) { // decline
						event.setDeclined(event.getDeclined()+1);
						link.setJoiningStatus(cmd.getStatus());
					}
					link.update();
					event.update();
					returnString = "<div class='person' style='font-size:14px;'>Guests: "+event.getInvited()+" invited / "+event.getConfirmed()+" going / "+event.getMayBe()+" maybe / "+event.getDeclined()+" declined</div>";
									
				} catch (ElementNotFoundException e) {
				}
				
				HttpServletResponse resp = contextManager.getContext().getResponse();
				resp.setContentType("text/html");
				Writer out = resp.getWriter();
				out.write(returnString);
				out.close();
				return null;
			}
		}
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int eventId;
		private int status;;

		public int getEventId() {
			return eventId;
		}

		public void setEventId(int eventId) {
			this.eventId = eventId;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setEventFinder(EventFinder eventFinder) {
		this.eventFinder = eventFinder;
	}

	public void setEventInviteeLinkFinder(
			EventInviteeLinkFinder eventInviteeLinkFinder) {
		this.eventInviteeLinkFinder = eventInviteeLinkFinder;
	}
}