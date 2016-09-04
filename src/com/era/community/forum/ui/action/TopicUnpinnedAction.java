package com.era.community.forum.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumItemFinder;

/**
 * @spring.bean name="/cid/[cec]/forum/topicUnpinned.do"
 */
public class TopicUnpinnedAction extends AbstractCommandAction
{

	public static final String REQUIRES_AUTHENTICATION = "";

	/* Injected references */
	private CommunityEraContextManager contextManager;
	private ForumItemFinder forumItemFinder; 

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();


		if (!context.isUserCommunityAdmin())
			return new ModelAndView("/pageNotFound");

		ForumItem forumItem = forumItemFinder.getForumItemForId(cmd.getId());
		forumItem.setSticky(false);

		forumItem.update();

		return REDIRECT_TO_BACKLINK;

	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int id;

		public int getId()
		{
			return id;
		}
		public void setId(int id)
		{
			this.id = id;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
	public void setForumItemFinder(ForumItemFinder forumItemFinder)
	{
		this.forumItemFinder = forumItemFinder;
	}

}
