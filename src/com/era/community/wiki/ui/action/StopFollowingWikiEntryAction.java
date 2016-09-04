package com.era.community.wiki.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.monitor.dao.WikiEntrySubscription;
import com.era.community.monitor.ui.dto.SubscribeDto;

/**
 * @spring.bean name="/cid/[cec]/wiki/stopFollowingWikiEntry.ajx"
 */
public class StopFollowingWikiEntryAction extends AbstractCommandAction
{
	protected SubscriptionFinder subscriptionFinder;
	protected CommunityEraContextManager contextManager;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		int currentUserId = context.getCurrentUser().getId();
		String returnString = "";
		Command cmd = (Command) data;

		try {
			WikiEntrySubscription subs = subscriptionFinder.getWikiEntrySubscriptionForUser(cmd.getId(), currentUserId);
			subs.delete();
			returnString = "<a onclick='subscribeWikiEntry("+cmd.getId()+");' href='javascript:void(0);'" +
			" onmouseover='tip(this,&quot;Get email alerts for updates to this wiki page&quot;)'>Follow this Wiki page</a>";
		} catch (ElementNotFoundException e) {
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

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}
}