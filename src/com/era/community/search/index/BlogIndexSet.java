package com.era.community.search.index;

import support.community.lucene.index.*;
import support.community.database.*;

import com.era.community.blog.dao.*;

public class BlogIndexSet extends IndexSet
{
    private BlogEntryFinder finder;

    public QueryScroller getScroller() throws Exception
    {
        return finder.listAllEntries();
    }
 
    public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
    {
        return true;
    }

    public final void setFinder(BlogEntryFinder finder)
    {
        this.finder = finder;
    }
}