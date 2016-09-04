package com.era.community.search.index;

import java.util.*;

import org.apache.lucene.index.*;
import org.apache.lucene.search.*;

import support.community.lucene.index.*;
import support.community.lucene.search.*;
import support.community.database.*;
import com.era.community.forum.dao.*;

public class ForumTopicEntityHandler extends ForumEntityHandler
{
    public boolean supports(Class type) throws Exception
    {
        return ForumTopic.class.isAssignableFrom(type);
    }
    
    public String getDescription(Object rowBean) throws Exception
    {
        ForumTopic doc = (ForumTopic)rowBean;
        return htmlToText(doc.getBody());
    }
    
    public String getContent(Object rowBean, final BinaryDataHandler handler, final boolean fast) throws Exception
    {
        ForumTopic doc = (ForumTopic)rowBean;
        
        final StringBuffer buf = new StringBuffer(4096);

        /*getContentForFile(doc.getFileName1(), doc.getFile1(), doc.getFile1ContentType(), handler, buf, fast);
        getContentForFile(doc.getFileName2(), doc.getFile2(), doc.getFile2ContentType(), handler, buf, fast);
        getContentForFile(doc.getFileName3(), doc.getFile3(), doc.getFile3ContentType(), handler, buf, fast);*/
        
        QueryScroller scroller = doc.listResponses();
        
        scroller.doForAllRows(new QueryScrollerCallback() {

            public void handleRow(Object rowBean, int rowNum) throws Exception
            {
                ForumResponse resp = (ForumResponse)rowBean;
                buf.append(". ");
                buf.append(resp.getSubject());
                buf.append(". ");
                buf.append(htmlToText(resp.getBody()));
                buf.append(". ");
                /*if (!fast) {
                    getContentForFile(resp.getFileName1(), resp.getFile1(), resp.getFile1ContentType(), handler, buf, false);
                    getContentForFile(resp.getFileName2(), resp.getFile2(), resp.getFile2ContentType(), handler, buf, false);
                    getContentForFile(resp.getFileName3(), resp.getFile3(), resp.getFile3ContentType(), handler, buf, false);
                }*/
            }
            
        });
        
        return buf.toString();
    }
    
    public Date getModified(Object rowBean) throws Exception
    {
        ForumTopic doc = (ForumTopic)rowBean;
        return doc.getLastPostDate();
     }

    public String getCommunityRelativeUrl(Object rowBean, String query, EntityIndex index) throws Exception
    {
        ForumTopic topic = (ForumTopic)rowBean;
        
        EntityIndexSearcher searcher = (EntityIndexSearcher)index.getIndexSearcher();
        
        if (topicIsHit(topic, query, searcher))
            return "forum/forumThread.do?id="+getEntityId(rowBean);
        
        ForumResponse resp = getFirstResponseHit(topic, query, searcher);
        if (resp==null)
            return "forum/forumThread.do?id="+getEntityId(rowBean);
        
         return "forum/forumThread.do?id="+getEntityId(rowBean)+"#A"+resp.getId();
    }

    private boolean topicIsHit(ForumTopic topic, String queryText, EntityIndexSearcher searcher) throws Exception
    {
        Query query = searcher.getParser(new String[]{EntityIndex.TITLE_FIELD,EntityIndex.DESCRIPTION_FIELD}).parse(queryText);
        BooleanQuery bq = new BooleanQuery();
        bq.add(query, BooleanClause.Occur.MUST);
        bq.add(new TermQuery(new Term(EntityIndex.ENTITY_ID_FIELD, ""+topic.getId())), BooleanClause.Occur.MUST);
        EntitySearchScroller scroller = searcher.search(queryText, bq, ForumTopic.class);
        return scroller.readRowCount()>0;
    }
    
    private ForumResponse getFirstResponseHit(ForumTopic topic, String queryText, EntityIndexSearcher searcher) throws Exception
    {
        Query query = searcher.getParser().parse(queryText);
        BooleanQuery bq = new BooleanQuery();
        bq.add(query, BooleanClause.Occur.MUST);
        bq.add(new TermQuery(new Term(CecEntityIndex.TOPIC_ID_FIELD, ""+topic.getId())), BooleanClause.Occur.MUST);
        EntitySearchScroller scroller = searcher.search(queryText, bq, ForumResponse.class);
        scroller.addScrollKey(EntityIndex.ENTITY_ID_FIELD, QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_INTEGER);
        scroller.setPageSize(1);
        List page = scroller.readPage(1);
        if (page.isEmpty()) return null;
        EntitySearchHit hit = (EntitySearchHit)page.get(0);
        //return (ForumResponse)hit.getEntity();
        return null;
    }
    
    
    public String getTitle3(Object entity, String query, EntityIndex index) throws Exception
    {
        ForumTopic topic = (ForumTopic)entity;
        return "Latest post: "+DATE_FORMAT.format(topic.getLastPostDate());
    }
    
    public Map<String, Object> getAdditionalDataFields(Object rowBean) throws Exception
    {
        return null;
    }
}
