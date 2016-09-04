package com.era.community.newsfeed.dao; 

public class NewsFeedEntryDaoImpl extends com.era.community.newsfeed.dao.generated.NewsFeedEntryDaoBaseImpl implements NewsFeedEntryDao
{
    public NewsFeedEntry getEntryForFeedAndUri(NewsFeed feed, String uri) throws Exception
    {
        return (NewsFeedEntry)getEntityWhere("FeedId = ? and Uri = ?",feed.getId(), uri );
    }
    
    
}

