package com.era.community.search.index;

import support.community.lucene.index.*;
import support.community.database.*;

import com.era.community.wiki.dao.*;

public class WikiIndexSet extends IndexSet
{
    private WikiEntryFinder finder;

    public QueryScroller getScroller() throws Exception
    {
        return finder.listAllCurrentEntries();
    }
 
    public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
    {
        return true;
    }

    public final void setFinder(WikiEntryFinder finder)
    {
        this.finder = finder;
    }
}