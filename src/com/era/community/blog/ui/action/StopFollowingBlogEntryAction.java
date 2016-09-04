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
import com.era.community.communities.dao.Community;
import com.era.community.monitor.dao.BlogEntrySubscription;
import com.era.community.monitor.dao.SubscriptionFinder;

/**
 * @spring.bean name="/cid/[cec]/blog/stopFollowingBlogEntry.ajx"
 */
public class StopFollowingBlogEntryAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Community currentCommunity = contextManager.getContext().getCurrentCommunity();
		String returnString = "";
		if (context.getCurrentUser() != null) {
			int currentUserId = context.getCurrentUser().getId();
			Command cmd = (Command) data;
			try {
				BlogEntrySubscription subs = null;
				if (currentCommunity != null) {
					subs = subscriptionFinder.getCommunityBlogEntrySubscriptionForUser(cmd.getId(), currentUserId, currentCommunity.getId());
				} else {
					subs = subscriptionFinder.getPersonalBlogEntrySubscriptionForUser(cmd.getId(), currentUserId);
				}
				subs.delete();
				returnString = "<a onclick='subscribeBlogEntry("+cmd.getId()+");' href='javascript:void(0);'" +
				" onmouseover='tip(this,&quot;Get email alerts for this blog entry&quot;)'>Follow this Blog Entry</a>";
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
		private int subsId;
		private int frequency;

		public int getFrequency()
		{
			return frequency;
		}

		public void setFrequency(int frequency)
		{
			this.frequency = frequency;
		}

		public int getSubsId()
		{
			return subsId;
		}

		public void setSubsId(int subsId)
		{
			this.subsId = subsId;
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

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}
}