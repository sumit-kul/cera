package com.era.community.search.index;

import support.community.lucene.index.*;
import support.community.database.*;

import com.era.community.communities.dao.*;

public class CommunityIndexSet extends IndexSet
{
    private CommunityFinder finder;

    public QueryScroller getScroller() throws Exception
    {
        return finder.listActiveCommunitiesByName();
    }
 
    public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
    {
        return true;
    }

    public final void setFinder(CommunityFinder finder)
    {
        this.finder = finder;
    }
}