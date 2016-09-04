package com.era.community.search.index;

import support.community.lucene.index.IndexSet;
import support.community.database.QueryScroller;
import com.era.community.pers.dao.UserFinder;

public class UserIndexSet extends IndexSet
{
    private UserFinder finder;

    public QueryScroller getScroller() throws Exception
    {
        return finder.listActiveUsersByName();
    }
 
    public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
    {
        return true;
    }

    public final void setFinder(UserFinder finder)
    {
        this.finder = finder;
    }
}