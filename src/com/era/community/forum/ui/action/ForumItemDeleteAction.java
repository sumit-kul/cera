package com.era.community.forum.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumItemFinder;
import com.era.community.forum.dao.ForumResponse;
import com.era.community.forum.ui.dto.ForumItemDto;
import com.era.community.monitor.dao.SubscriptionFinder;

/**
 * @spring.bean name="/cid/[cec]/forum/item-delete.do"
 */
public class ForumItemDeleteAction extends AbstractCommandAction
{
	private ForumItemFinder forumItemFinder;
	private CommunityEraContextManager contextManager;
	private SubscriptionFinder subscriptionFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;

		ForumItem item = forumItemFinder.getForumItemForId(cmd.getId());

		CommunityEraContext context = contextManager.getContext();

		/* Delete */
		item.setDeleteStatus(ForumItem.STATUS_DELETED);

		item.update();

		subscriptionFinder.deleteSubscriptionsForThread(item.getId());
		if (item instanceof ForumResponse) 
		{            
			ForumResponse resp = (ForumResponse)item;
			return new ModelAndView("redirect:" + context.getCurrentCommunityUrl() + "/forum/forumThread.do?id="   +resp.getTopicId());
		}
		else
		{
			return new ModelAndView("redirect:" + context.getCurrentCommunityUrl() + "/forum/showTopics.do");
		}
	}

	public static class Command extends ForumItemDto implements CommandBean 
	{
	}

	public ForumItemFinder getForumItemFinder()
	{
		return forumItemFinder;
	}


	public void setForumItemFinder(ForumItemFinder forumItemFinder)
	{
		this.forumItemFinder = forumItemFinder;
	}

	public CommunityEraContextManager getContextHolder()
	{
		return contextManager;
	}


	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final SubscriptionFinder getSubscriptionFinder()
	{
		return subscriptionFinder;
	}


	public final void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}


}
