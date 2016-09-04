package com.era.community.forum.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.forum.dao.ForumFeature;
import com.era.community.forum.dao.ForumFinder;
import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumItemFinder;
import com.era.community.forum.ui.dto.ForumItemDto;
import com.era.community.monitor.dao.SubscriptionFinder;

/**
 *  @spring.bean name="/cid/[cec]/forum/item-file-remove.do"
 */
public class ForumItemFileRemoveAction extends AbstractCommandAction
{
	private ForumFeature forumFeature;
	private ForumFinder forumFinder; 
	private ForumItemFinder itemFinder;
	private SubscriptionFinder subscriptionFinder;
	CommunityEraContextManager contextManager;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		ForumItem item = itemFinder.getForumItemForId(cmd.getId());
		if ( cmd.getFile() == 1 && item.isFile1Present() ) {
			item.clearFile1();
		}         
		else if ( cmd.getFile() == 2 && item.isFile2Present() ) {
			item.clearFile2();
		}        
		else if ( cmd.getFile() == 3 && item.isFile3Present() ) {
			item.clearFile3();
		}
		CommunityEraContext context = contextManager.getContext();
		return new ModelAndView("redirect:" + context.getCurrentCommunityUrl() +"/forum/edit-item.do?id=" +item.getId());
	}

	public static class Command extends ForumItemDto implements CommandBean
	{
		private int file;

		public int getFile()
		{
			return file;
		}

		public void setFile(int file)
		{
			this.file = file;
		}
	}

	/* Used by Spring to inject reference */
	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	/* Used by Spring to inject reference */


	public ForumFeature getForumFeature()
	{
		return forumFeature;
	}
	public void setForumFeature(ForumFeature forumFeature)
	{
		this.forumFeature = forumFeature;
	}
	public ForumFinder getForumFinder()
	{
		return forumFinder;
	}
	public void setForumFinder(ForumFinder forumFinder)
	{
		this.forumFinder = forumFinder;
	}
	public ForumItemFinder getItemFinder()
	{
		return itemFinder;
	}
	public void setItemFinder(ForumItemFinder itemFinder)
	{
		this.itemFinder = itemFinder;
	}
	public CommunityEraContextManager getContextHolder()
	{
		return contextManager;
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
