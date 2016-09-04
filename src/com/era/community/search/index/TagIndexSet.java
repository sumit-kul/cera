package com.era.community.search.index;

import support.community.database.QueryScroller;
import support.community.lucene.index.IndexSet;

import com.era.community.tagging.dao.TagFinder;

public class TagIndexSet extends IndexSet
{
    private TagFinder finder;

    public QueryScroller getScroller() throws Exception
    {
        return finder.getAllTagsSearchIndexing();
    }
 
    public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
    {
        return true;
    }

    public final void setFinder(TagFinder finder)
    {
        this.finder = finder;
    }
}