package com.era.community.newsfeed.dao; 

import support.community.database.QueryScroller;

public class NewsFeedDaoImpl extends com.era.community.newsfeed.dao.generated.NewsFeedDaoBaseImpl implements NewsFeedDao
{
    public QueryScroller listEntriesByDate(NewsFeed feed) throws Exception
    {
        String query = "select E.* from NewsFeedEntry E where E.FeedId = ?";
        QueryScroller scroller = getQueryScroller(query, NewsFeedEntry.class, feed.getId());
        scroller.addScrollKey("STEMP.DatePublished", QueryScroller.DIRECTION_DESCENDING, QueryScroller.TYPE_DATE);
        return scroller;
    }
    
    public QueryScroller listAllFeeds() throws Exception
    {
        String query = "select F.* from NewsFeed F ";
        QueryScroller scroller = getQueryScroller(query, NewsFeed.class);
        scroller.addScrollKey("STEMP.Id", QueryScroller.DIRECTION_DESCENDING, QueryScroller.TYPE_INTEGER);
        return scroller;
    }
    
       public QueryScroller listAllFeedsByAggregatorId() throws Exception
    {
        String query = "select F.* from NewsFeed F ";
        QueryScroller scroller = getQueryScroller(query, NewsFeed.class);
        scroller.addScrollKey("STEMP.AggregatorId", QueryScroller.DIRECTION_DESCENDING, QueryScroller.TYPE_INTEGER);
        return scroller;
    }
    
}

