package com.era.community.newsfeed.dao; 

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;

public class NewsFeedAggregatorDaoImpl extends com.era.community.newsfeed.dao.generated.NewsFeedAggregatorDaoBaseImpl implements NewsFeedAggregatorDao
{

    public NewsFeedAggregator getNewsFeedAggregatorForCommunity(Community comm) throws Exception
    {
        return (NewsFeedAggregator)getEntityWhere("CommunityId=?", comm.getId() );
    }
    
    public QueryScroller listEntriesByDate(NewsFeedAggregator aggregator) throws Exception
    {
        String query = "select E.* from NewsFeed F, NewsFeedEntry E " +
                                    " where F.AggregatorId = ? and E.FeedId = F.Id";
        QueryScroller scroller = getQueryScroller(query, NewsFeedEntry.class,  aggregator.getId());
        scroller.addScrollKey("STEMP.DatePublished", QueryScroller.DIRECTION_DESCENDING, QueryScroller.TYPE_DATE);
        return scroller;
    }
    
    public QueryScroller listFeedsByName(NewsFeedAggregator aggregator) throws Exception
    {
        String query = "select F.* from NewsFeed F where F.AggregatorId = ? ";
        QueryScroller scroller = getQueryScroller(query, NewsFeed.class, aggregator.getId());
        scroller.addScrollKey("STEMP.Name", QueryScroller.DIRECTION_ASCENDING, QueryScroller.TYPE_TEXT);
        return scroller;
    }
    
    public List getFeeds(NewsFeedAggregator aggregator) throws Exception
    {
        QueryScroller scroller = listFeedsByName(aggregator);
        scroller.setPageSize(9999);
        scroller.setBeanClass(NewsFeed.class);
        return scroller.readPage(1);
    }
    
    
}

