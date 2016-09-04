package com.era.community.blog.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.monitor.dao.CommunityBlogSubscription;
import com.era.community.monitor.dao.PersonalBlogSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;

/**
 * @spring.bean name="/cid/[cec]/blog/followBlogCons.ajx"
 */
public class FollowCommunityBlogAction extends AbstractCommandAction 
{
	protected SubscriptionFinder subscriptionFinder;
	protected CommunityEraContextManager contextManager;
	protected CommunityFinder communityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		String returnString = "";
		if (context.getCurrentUser() != null) {
			int currentUserId = context.getCurrentUser().getId();
			Command cmd = (Command) data;

			if (context.getCurrentCommunity() != null) {
				CommunityBlogSubscription subs;
				try {
					subs= subscriptionFinder.getCommunityBlogSubscriptionForUser(cmd.getId(), currentUserId);
				} catch (ElementNotFoundException e) {
					subs = subscriptionFinder.newCommunityBlogSubscription();

					subs.setCommunityBlogId(new Integer(cmd.getId()));
					subs.setUserId(new Integer(context.getCurrentUser().getId()));
					subs.setCommunityId(new Integer(context.getCurrentCommunity().getId()));
					subs.setFrequency(1);
					subs.update();        
					returnString = "<a onclick='unSubscribeBlog("+cmd.getId()+");' href='javascript:void(0);' class='normalTip' " +
					" title='Unsubscribe from user blog entries published to this community'>Stop Following this Blog</a>";
				}
				cmd.setFrequency(subs.getFrequency());
				cmd.setSubsId(subs.getId());
			} else {
				PersonalBlogSubscription subs;
				try {
					subs= subscriptionFinder.getPersonalBlogSubscriptionForUser(cmd.getId(), currentUserId);
				} catch (ElementNotFoundException e) {
					subs = subscriptionFinder.newPersonalBlogSubscription();

					subs.setPersonalBlogId(new Integer(cmd.getId()));
					subs.setUserId(new Integer(context.getCurrentUser().getId()));
					subs.setFrequency(1);
					subs.update();        
					returnString = "<a onclick='unSubscribeBlog("+cmd.getId()+");' href='javascript:void(0);' class='normalTip' " +
					" title='Unsubscribe from user blog entries published to this blog'>Stop Following this Blog</a>";
				}
				cmd.setFrequency(subs.getFrequency());
				cmd.setSubsId(subs.getId());
			}
		}
		HttpServletResponse resp = contextManager.getContext().getResponse();
		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write(returnString);
		out.close();
		return null;
	}


	protected void onDisplay(Object data) throws Exception
	{
		/*
		 * Check that this user doesn't already have a subscription for this blog consolidator
		 */
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() != null) {

			int currentUserId = context.getCurrentUser().getId();

			Command cmd = (Command) data;

			CommunityBlogSubscription subs;

			try {
				subs= subscriptionFinder.getCommunityBlogSubscriptionForUser(cmd.getId(), currentUserId);
			} catch (ElementNotFoundException e) {

				/*
				 * If no subscription exists, create a new subscription record
				 */
				subs = subscriptionFinder.newCommunityBlogSubscription();

				/*
				 * Get the consolidator id and save this in the subscription record
				 */
				subs.setCommunityBlogId(new Integer(cmd.getId()));
				subs.setUserId(new Integer(context.getCurrentUser().getId()));
				subs.setCommunityId(new Integer(context.getCurrentCommunity().getId()));
				subs.setFrequency(1);

				/*
				 * Save the subscription record
				 */
				subs.update();            

			}
			cmd.setFrequency(subs.getFrequency());
			cmd.setSubsId(subs.getId());
		}

	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int Id;
		private int Frequency;
		private int subsId;

		public int getSubsId()
		{
			return subsId;
		}

		public void setSubsId(int subsId)
		{
			this.subsId = subsId;
		}

		public int getFrequency()
		{
			return Frequency;
		}

		public void setFrequency(int frequency)
		{
			Frequency = frequency;
		}

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


	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}
}