package com.era.community.forum.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumItemFinder;
import com.era.community.forum.ui.dto.ForumItemDto;

/**
 * @spring.bean name="/cid/[cec]/forum/topicClose.do"
 */
public class TopicCloseAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager;
	private ForumItemFinder forumItemFinder; 

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		if (!context.isUserCommunityAdmin())
			return new ModelAndView("/pageNotFound");

		ForumItem item = forumItemFinder.getForumItemForId(cmd.getId());
		item.setClosed(true);
		item.setClosedById(context.getCurrentUser().getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		item.setClosedOn(ts);
		item.update();
		cmd.copyPropertiesFrom(item);
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
