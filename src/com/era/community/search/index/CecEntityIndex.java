package com.era.community.search.index;

import org.apache.lucene.search.*;

import support.community.lucene.index.*;
import support.community.lucene.search.*;

public class CecEntityIndex extends SimpleEntityIndex
{
    public static final String COMMUNITY_ID_FIELD = "CommunityId";
    public static final String TOPIC_ID_FIELD = "TopicId";
    public static final String WIKI_ENTRY_ID_FIELD = "WikiEntryId";
    public static final String USER_ID_FIELD = "UserId";
    public static final String TAG_ID_FIELD = "TagId";

    protected EntityIndexSearcher createIndexSearcher(IndexSearcher searcher) throws Exception
    {
        return new CecEntitySearcher(this, searcher);
    }
}