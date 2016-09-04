package com.era.community.wiki.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.monitor.dao.WikiEntrySubscription;
import com.era.community.monitor.ui.dto.SubscribeDto;

/**
 * @spring.bean name="/cid/[cec]/wiki/followWikiEntry.ajx"
 */
public class FollowWikiEntryAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() != null) {
			int currentUserId = context.getCurrentUser().getId();
			String returnString = "";
			Command cmd = (Command) data;
			WikiEntrySubscription subs ;
			try {
				subs = subscriptionFinder.getWikiEntrySubscriptionForUser(cmd.getId(), currentUserId);
			} catch (ElementNotFoundException e) {
				subs = subscriptionFinder.newWikiEntrySubscription();
				subs.setWikiEntryId(new Integer(cmd.getId()));
				subs.setUserId(new Integer(context.getCurrentUser().getId()));
				subs.update();
				returnString = "<a onclick='unSubscribeWikiEntry("+cmd.getId()+");' href='javascript:void(0);'" +
				" onmouseover='tip(this,&quot;Unsubcribe from email alerts for updates to this wiki page&quot;)'>Stop Following this Wiki page</a>";
			}
			cmd.setSubsId(subs.getId());
			HttpServletResponse resp = contextManager.getContext().getResponse();
			resp.setContentType("text/html");
			Writer out = resp.getWriter();
			out.write(returnString);
			out.close();
			return null;
		} else {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}
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