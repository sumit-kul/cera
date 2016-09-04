package com.era.community.events.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.monitor.dao.EventCalendarSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.monitor.ui.dto.SubscribeDto;

/**
 * @spring.bean name="/cid/[cec]/event/followEventCalendar.ajx"
 */
public class FollowEventCalendarAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		String returnString = "";
		if (context.getCurrentUser() != null) {
			int currentUserId = context.getCurrentUser().getId();

			Command cmd = (Command) data;
			EventCalendarSubscription subs ;
			try {
				subs    = subscriptionFinder.getEventCalendarSubscriptionForUser(cmd.getId(), currentUserId);
			} catch (ElementNotFoundException e) {
				subs = subscriptionFinder.newEventCalendarSubscription();
				subs.setEventCalendarId(new Integer(cmd.getId()));
				subs.setUserId(new Integer(context.getCurrentUser().getId()));
				subs.setCommunityId(new Integer(context.getCurrentCommunity().getId()));
				subs.update();
				returnString = "<a onclick='unSubscribeEvents("+cmd.getId()+");' href='javascript:void(0);'" +
				" onmouseover='tip(this,&quot;Unsubscribe from email alerts for events in this community&quot;)'>Stop Following this Event Calendar</a>";
			}
			cmd.setSubsId(subs.getId());
		}
		HttpServletResponse resp = contextManager.getContext().getResponse();
		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write(returnString);
		out.close();
		return null;
	}

	public static class Command extends SubscribeDto implements CommandBean
	{
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}
}