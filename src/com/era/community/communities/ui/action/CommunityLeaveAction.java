package com.era.community.communities.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.dao.CommunityJoiningRequest;
import com.era.community.communities.dao.CommunityJoiningRequestFinder;
import com.era.community.communities.ui.dto.CommunityDto;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;

/**
 * @spring.bean name="/cid/[cec]/comm/leaveCommunity.do"
 */
public class CommunityLeaveAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager;
	protected CommunityFinder communityFinder; 
	protected SubscriptionFinder subscriptionFinder;
	protected CommunityJoiningRequestFinder joiningRequestFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		if (context.getCurrentUser() == null) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

		User user = contextManager.getContext().getCurrentUser();
		Community comm = contextManager.getContext().getCurrentCommunity();

		if (comm.isMember(user) && !comm.isCommunityOwner(user)) {
			comm.getMemberList().removeMember(user); 
			subscriptionFinder.deleteSubscriptionsForUserAndCommunity(user.getId(), comm.getId()); 
			try {
				CommunityJoiningRequest joiningRequest = joiningRequestFinder.getRequestForUserAndCommunity(user.getId(), comm.getId());
				joiningRequest.delete();
			} catch (ElementNotFoundException e) {
			}
			return new ModelAndView("comm/leaveCommunity");
		} else {
			return new ModelAndView("pageNotFound");
		}
	}

	public static class Command extends CommunityDto implements CommandBean
	{
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setJoiningRequestFinder(
			CommunityJoiningRequestFinder joiningRequestFinder) {
		this.joiningRequestFinder = joiningRequestFinder;
	}
}