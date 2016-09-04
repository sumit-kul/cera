package com.era.community.forum.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumItemFinder;
import com.era.community.forum.ui.dto.ForumItemDto;

/**
 * @spring.bean name="/cid/[cec]/forum/topicPinned.do"
 */
public class TopicPinnedAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager;
	private ForumItemFinder forumItemFinder; 

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		if (!context.isUserCommunityAdmin())
			return new ModelAndView("/pageNotFound");

		ForumItem forumItem = forumItemFinder.getForumItemForId(cmd.getId());
		forumItem.setSticky(true);
		forumItem.update();
		return REDIRECT_TO_BACKLINK;
	}

	public class Command extends ForumItemDto implements CommandBean
	{
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