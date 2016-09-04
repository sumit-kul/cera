package com.era.community.forum.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.monitor.dao.ThreadSubscription;
import com.era.community.monitor.ui.dto.SubscribeDto;

/**
 * @spring.bean name="/cid/[cec]/forum/followThread.ajx"
 */
public class FollowThreadAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command) data;
		String returnString = "";
		ThreadSubscription subs;
		try {
			subs = subscriptionFinder.getThreadSubscriptionForUser(cmd.getId(), context.getCurrentUser().getId());
		} catch (ElementNotFoundException e) {
			subs = subscriptionFinder.newThreadSubscription();
			subs.setThreadId(new Integer(cmd.getId()));
			subs.setUserId(new Integer(context.getCurrentUser().getId()));
			subs.setCommunityId(new Integer(context.getCurrentCommunity().getId()));
			subs.update();
			cmd.setThreadTitle(subs.getItemName());
			returnString = "<a onclick='unSubscribeTopic("+cmd.getId()+");' href='javascript:void(0);'" +
			" onmouseover='tip(this,&quot;Unsubscribe from email alerts when new responses are posted to this thread&quot;)'>Stop Following this Topic</a>";
		}
		cmd.setSubsId(subs.getId());
		HttpServletResponse resp = contextManager.getContext().getResponse();
		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write(returnString);
		out.close();
		return null;
	}

	public static class Command extends SubscribeDto implements CommandBean
	{

		private String ThreadTitle;

		public String getThreadTitle()
		{
			return ThreadTitle;
		}

		public void setThreadTitle(String threadTitle)
		{
			ThreadTitle = threadTitle;
		}
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}
}