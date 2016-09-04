package com.era.community.newsfeed.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.newsfeed.dao.NewsFeed;
import com.era.community.newsfeed.dao.NewsFeedAggregator;
import com.era.community.newsfeed.dao.NewsFeedAggregatorFeature;
import com.era.community.newsfeed.dao.NewsFeedFinder;

/**
 * @spring.bean name="/cid/[cec]/news/feed-add.do"
 */
public class NewsFeedAddAction extends AbstractCommandAction
{
    /*
     * This action requires authentication
     */
    public static final String REQUIRES_AUTHENTICATION = "";

    /* Injected references */
    private CommunityEraContextManager contextManager;
    private NewsFeedAggregatorFeature aggregatorFeature;
    private NewsFeedFinder feedFinder;

    @Override
    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command) data;
        
        if (cmd.getFeedName()==null||cmd.getFeedName().trim().length()==0)
            throw new Exception("No feed name was entered");

        if (cmd.getFeedUrl()==null||cmd.getFeedUrl().trim().length()==0)
            throw new Exception("No feed URL was entered");

        CommunityEraContext context = contextManager.getContext();
        Community comm = context.getCurrentCommunity();
        NewsFeedAggregator aggregator = (NewsFeedAggregator) aggregatorFeature.getFeatureForCommunity(comm);

        NewsFeed feed = feedFinder.newNewsFeed();
        feed.setAggregatorId(aggregator.getId());
        feed.setUrl(cmd.getFeedUrl());
        feed.setName(cmd.getFeedName());
        
        feed.readFeed();
        
        return new ModelAndView("redirect:" + context.getCurrentCommunityUrl() + "/feed/newsFeeds.do?feedId="+ feed.getId());
    }

    public static class Command extends CommandBeanImpl implements CommandBean
    {
        private String feedUrl;
        private String feedName;

        public final String getFeedUrl()
        {
            return feedUrl;
        }

        public final void setFeedUrl(String feedUrl)
        {
            this.feedUrl = feedUrl;
        }

        public final String getFeedName()
        {
            return feedName;
        }

        public final void setFeedName(String feedName)
        {
            this.feedName = feedName;
        }
    }

    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public final void setAggregatorFeature(NewsFeedAggregatorFeature aggregatorFeature)
    {
        this.aggregatorFeature = aggregatorFeature;
    }

    public final void setFeedFinder(NewsFeedFinder feedFinder)
    {
        this.feedFinder = feedFinder;
    }

}
