package com.era.community.forum.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.monitor.dao.ForumSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.monitor.ui.dto.SubscribeDto;

/**
 * @spring.bean name="/cid/[cec]/forum/followForum.ajx"
 */
public class FollowForumAction extends AbstractCommandAction
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
			ForumSubscription subs ;
			try {
				subs = subscriptionFinder.getForumSubscriptionForUser(cmd.getId(), currentUserId);
			} catch (ElementNotFoundException e) {
				subs = subscriptionFinder.newForumSubscription();
				subs.setForumId(new Integer(cmd.getId()));
				subs.setUserId(new Integer(context.getCurrentUser().getId()));
				subs.setCommunityId(new Integer(context.getCurrentCommunity().getId()));
				subs.update();     
				returnString = "<a onclick='unSubscribeForum("+cmd.getId()+");' href='javascript:void(0);'" +
				" onmouseover='tip(this,&quot;Unsubscribe from email alerts from this forum&quot;)'>Stop Following this Forum</a>";
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

	public static class Command extends SubscribeDto  implements CommandBean
	{
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}
}