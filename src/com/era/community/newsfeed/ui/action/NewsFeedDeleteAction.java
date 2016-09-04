package com.era.community.newsfeed.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.newsfeed.dao.NewsFeed;
import com.era.community.newsfeed.dao.NewsFeedFinder;

/**
 * @spring.bean name="/cid/[cec]/news/feed-delete.do"
 */
public class NewsFeedDeleteAction extends AbstractCommandAction
{
    /*
     * This action requires authentication
     */
    public static final String REQUIRES_AUTHENTICATION = "";

    /* Injected references */
    private CommunityEraContextManager contextManager;
    private NewsFeedFinder feedFinder;
 
    @Override
    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command) data;

        CommunityEraContext context = contextManager.getContext();

        NewsFeed feed = feedFinder.getNewsFeedForId(cmd.getFeedId());
        feed.delete();
        
        return new ModelAndView("redirect:" + context.getCurrentCommunityUrl() + "/feed/newsFeeds.do?pageSize="+cmd.getPageSize());
    }

    public static class Command extends CommandBeanImpl implements CommandBean
    {
        private int feedId;
        private int pageSize;

        public final int getFeedId()
        {
            return feedId;
        }

        public final void setFeedId(int feedId)
        {
            this.feedId = feedId;
        }

        public final int getPageSize()
        {
            return pageSize;
        }

        public final void setPageSize(int pageSize)
        {
            this.pageSize = pageSize;
        }
    }

    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }


    public final void setFeedFinder(NewsFeedFinder feedFinder)
    {
        this.feedFinder = feedFinder;
    }

}
