package com.era.community.search.index;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import support.community.lucene.index.EntityIndex;
import support.community.lucene.search.EntityIndexSearcher;
import support.community.lucene.search.EntitySearchScroller;

import com.era.community.blog.dao.BlogEntry;
import com.era.community.communities.dao.Community;
import com.era.community.doclib.dao.Document;
import com.era.community.events.dao.Event;
import com.era.community.forum.dao.ForumResponse;
import com.era.community.forum.dao.ForumTopic;
import com.era.community.pers.dao.User;
import com.era.community.wiki.dao.WikiEntry;

public class CecEntitySearcher extends EntityIndexSearcher
{
    public static final Class[] DEFAULT_ENTITY_TYPES = new Class[] { Community.class, BlogEntry.class, ForumTopic.class,  ForumResponse.class, Document.class, WikiEntry.class, Event.class, User.class };
    
    public CecEntitySearcher(EntityIndex index, IndexSearcher searcher) throws Exception
    {
        super(index, searcher);
    }
    
    public EntitySearchScroller search(String queryText, Community comm) throws Exception
    {
        Query query = getParser().parse(queryText);
        return search(queryText, addCommunityRestiction(query, comm.getId()));
    }
    
    public EntitySearchScroller search(String queryText, Community comm, Class entity) throws Exception
    {
        Query query = getParser().parse(queryText);
        return search(queryText, addCommunityRestiction(query, comm.getId()), entity);
    }
    
    public EntitySearchScroller search(String queryText, Community comm, Class[] entities) throws Exception
    {
        Query query = getParser().parse(queryText);
        return search(queryText, addCommunityRestiction(query, comm.getId()), entities);
    }
    
    private Query addCommunityRestiction(Query query, int communityId) throws Exception
    {
        Query communityRestriction = new TermQuery(new Term(CecEntityIndex.COMMUNITY_ID_FIELD, ""+communityId));
        communityRestriction.setBoost(0.001f);
        return conjunction(query, communityRestriction);
    }
}