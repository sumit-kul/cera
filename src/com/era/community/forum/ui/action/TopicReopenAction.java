package com.era.community.forum.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumItemFinder;

/**
 * @spring.bean name="/cid/[cec]/forum/topicReopen.do"
 */
public class TopicReopenAction extends AbstractCommandAction
{

	public static final String REQUIRES_AUTHENTICATION = "";

	/* Injected references */
	private CommunityEraContextManager contextManager;
	private ForumItemFinder forumItemFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		if (!context.isUserCommunityAdmin()) {
			return new ModelAndView("/pageNotFound");
		}
		ForumItem item = forumItemFinder.getForumItemForId(cmd.getId());

		item.setClosed(false);
		item.update();
		
		Community comm = context.getCurrentCommunity();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		comm.setCommunityUpdated(ts);
		comm.update();

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
