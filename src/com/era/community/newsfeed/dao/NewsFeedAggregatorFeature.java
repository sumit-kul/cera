package com.era.community.newsfeed.dao;

import support.community.application.ElementNotFoundException;

import com.era.community.base.CommunityFeature;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;

public class NewsFeedAggregatorFeature implements CommunityFeature
{
    NewsFeedAggregatorDao dao;
    
    CommunityEraContextManager contextManager;
    
    public Object getFeatureForCurrentCommunity() throws Exception
    {
        return getFeatureForCommunity(contextManager.getContext().getCurrentCommunity());
    }

    public void setFeatureEnabledForCommunity(Community comm, boolean status) throws Exception
    {
        NewsFeedAggregator o = (NewsFeedAggregator)getFeatureForCommunity(comm);
        if (o==null && status==false) {
            return;
        }
        else if (o==null && status==true) {
            o = dao.newNewsFeedAggregator();
            o.setCommunityId(comm.getId());
            o.setName(comm.getName());
            o.setInactive(false);
            o.update();
            return;
        }
        else {
            o.setInactive(!status);
            o.update();
        }
    }

    public boolean isFeatureEnabledForCommunity(Community comm) throws Exception
    {
        NewsFeedAggregator o = (NewsFeedAggregator)getFeatureForCommunity(comm);
        if (o==null) return false;
        return !o.isInactive();
    }

    public Object getFeatureForCommunity(Community comm) throws Exception
    {
        try {
            return dao.getNewsFeedAggregatorForCommunity(comm);
        }
        catch (ElementNotFoundException e) {
            return null;
        }
    }

    public String getFeatureName() throws Exception
    {
        return "NewsFeeds";
    }

    public String getFeatureLabel() throws Exception
    {
        return "News feeds";
    }

    public String getFeatureUri() throws Exception
    {
        return "/news";
    }

    public boolean isFeatureMandatory() throws Exception
    {
        return false;
    }
    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public final void setDao(NewsFeedAggregatorDao dao)
    {
        this.dao = dao;
    }

    public boolean isFeatureAvailableForUser(User user) throws Exception
    {
        return true;
    }

    public String getFeatureTitle() throws Exception
    {
        return "News feeds";
    }
}
