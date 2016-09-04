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
import com.era.community.monitor.dao.WikiSubscription;
import com.era.community.monitor.ui.dto.SubscribeDto;

/**
 * @spring.bean name="/cid/[cec]/wiki/followWiki.ajx"
 */
public class FollowWikiAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		int currentUserId = context.getCurrentUser().getId();
		String returnString = "";
		Command cmd = (Command) data;
		WikiSubscription subs ;
		try {
			subs = subscriptionFinder.getWikiSubscriptionForUser(cmd.getId(), currentUserId);
		} catch (ElementNotFoundException e) {

			subs = subscriptionFinder.newWikiSubscription();
			subs.setWikiId(new Integer(cmd.getId()));
			subs.setUserId(new Integer(context.getCurrentUser().getId()));
			subs.setCommunityId(new Integer(context.getCurrentCommunity().getId()));
			subs.update();            
			returnString = "<a onclick='unSubscribeWiki("+cmd.getId()+");' href='javascript:void(0);'" +
			" onmouseover='tip(this,&quot;Unsubcribe from email alerts for this wiki&quot;)'>Stop Following this Wiki</a>";
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
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}
}