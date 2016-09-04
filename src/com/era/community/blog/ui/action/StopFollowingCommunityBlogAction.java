package com.era.community.blog.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.monitor.dao.CommunityBlogSubscription;
import com.era.community.monitor.dao.PersonalBlogSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;


/**
 * @spring.bean name="/cid/[cec]/blog/stopFollowingBlogCons.ajx"
 * @spring.bean name="/blog/stopFollowingBlogCons.ajx"
 */
public class StopFollowingCommunityBlogAction extends AbstractCommandAction
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

		Community currComm = context.getCurrentCommunity();
		int currentUserId = context.getCurrentUser().getId();
		Command cmd = (Command) data;

		if (currComm != null) {
			try {
				CommunityBlogSubscription subs = subscriptionFinder.getCommunityBlogSubscriptionForUser(cmd.getId(), currentUserId);
				subs.delete();
				returnString = "<a onclick='subscribeBlog("+cmd.getId()+");' href='javascript:void(0);' class='normalTip' " +
				" title='Subscribe for email alerts for blog entries published to this community'>Follow this Blog</a>";
			} catch (ElementNotFoundException e) {
			}
		} else {
			try {
				PersonalBlogSubscription subs = subscriptionFinder.getPersonalBlogSubscriptionForUser(cmd.getId(), currentUserId);
				subs.delete();
				returnString = "<a onclick='subscribeBlog("+cmd.getId()+");' href='javascript:void(0);' class='normalTip' " +
				" title='Subscribe for email alerts for blog entries published to this blog'>Follow this Blog</a>";
			} catch (ElementNotFoundException e) {
			}
		}

		HttpServletResponse resp = contextManager.getContext().getResponse();
		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write(returnString);
		out.close();
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

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}
}