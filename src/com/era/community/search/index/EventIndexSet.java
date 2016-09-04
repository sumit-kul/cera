package com.era.community.search.index;

import support.community.lucene.index.*;
import support.community.database.*;

import com.era.community.events.dao.*;

public class EventIndexSet extends IndexSet
{
    private EventFinder finder;

    public QueryScroller getScroller() throws Exception
    {
        return finder.listAllEvents();
    }
 
    public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
    {
        return true;
    }
    
    public final void setFinder(EventFinder finder)
    {
        this.finder = finder;
    }
}