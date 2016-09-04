package com.era.community.communities.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.monitor.dao.CommunitySubscription;
import com.era.community.monitor.dao.SubscriptionFinder;

/**
 * 
 * @spring.bean name="/cid/[cec]/community/unSubscribeCommunity.ajx"
 */
public class UnSubscribeCommunityAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		/*
		 * Check if  user has a subscription for this blog consolidator
		 */
		CommunityEraContext context = contextManager.getContext();
		Community community = context.getCurrentCommunity();
		if (context.getCurrentUser() != null && community != null) {
			int currentUserId = context.getCurrentUser().getId();
			Command cmd = (Command) data;
			String returnString = "";
			try {
				CommunitySubscription subs= subscriptionFinder.getCommunitySubscriptionForUser(community.getId(), currentUserId);
				subs.delete();
				returnString = "<a href='javascript:void(0);' class='normalTip' onclick='subscribeCommunity()' title='Subscribe to e-mail notifications for this community' >Follow</a>";
				HttpServletResponse resp = contextManager.getContext().getResponse();
				resp.setContentType("text/html");
				Writer out = resp.getWriter();
				out.write(returnString);
				out.close();
				return null;
			} catch (ElementNotFoundException e) {
			}
		}
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int Id;

		public final int getId()
		{
			return Id;
		}

		public final void setId(int id)
		{
			Id = id;
		}
	}

	/**
	 * @param contextManager the contextManager to set
	 */
	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	/**
	 * @param subscriptionFinder the subscriptionFinder to set
	 */
	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}
}