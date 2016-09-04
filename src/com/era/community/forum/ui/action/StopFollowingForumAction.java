package com.era.community.forum.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.monitor.dao.ForumSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.monitor.ui.dto.SubscribeDto;

/**
 * @spring.bean name="/cid/[cec]/forum/stopFollowingForum.ajx"
 */
public class StopFollowingForumAction extends AbstractCommandAction
{
	protected SubscriptionFinder subscriptionFinder;
	protected CommunityEraContextManager contextManager;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		String returnString = "";
		if (context.getCurrentUser() == null) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}
		int currentUserId = context.getCurrentUser().getId();
		Command cmd = (Command) data;

		try {
			ForumSubscription subs = subscriptionFinder.getForumSubscriptionForUser(cmd.getId(), currentUserId);
			subs.delete();
			returnString = "<a onclick='subscribeForum("+cmd.getId()+");' href='javascript:void(0);'" +
			" onmouseover='tip(this,&quot;Send me email alerts when new topics are posted to forum&quot;)'>Follow this Forum</a>";
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