package com.era.community.search.index;

import support.community.lucene.index.*;
import support.community.database.*;

import com.era.community.forum.dao.*;

public class ForumIndexSet extends IndexSet
{
    private ForumItemFinder forumItemFinder;

    public QueryScroller getScroller() throws Exception
    {
        return forumItemFinder.listAllItems();
    }
 
    public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
    {
        return true;
    }
    
    public final void setForumItemFinder(ForumItemFinder forumItemFinder)
    {
        this.forumItemFinder = forumItemFinder;
    }
}