package com.era.community.search.index;

import java.util.*;

import support.community.lucene.index.*;
import com.era.community.forum.dao.*;

public class ForumResponseEntityHandler extends ForumEntityHandler
{
    public boolean supports(Class type) throws Exception
    {
        return ForumResponse.class.isAssignableFrom(type);
    }
    
    public String getDescription(Object rowBean) throws Exception
    {
        ForumItem doc = (ForumItem)rowBean;
        return doc.getBody();
    }

    public String getContent(Object rowBean, BinaryDataHandler handler, boolean fast) throws Exception
    {
        ForumItem doc = (ForumItem)rowBean;

        if (fast) return "";
        
        StringBuffer buf = new StringBuffer(4096);
        //getContentForFile(doc.getFileName1(), doc.getFile1(), doc.getFile1ContentType(), handler, buf, false);
        //getContentForFile(doc.getFileName2(), doc.getFile2(), doc.getFile2ContentType(), handler, buf, false);
        //getContentForFile(doc.getFileName3(), doc.getFile3(), doc.getFile3ContentType(), handler, buf, false);
        return buf.toString();
    }
    
    public Date getModified(Object rowBean) throws Exception
    {
        ForumItem doc = (ForumItem)rowBean;
        return doc.getDatePosted();
   }

    public String getCommunityRelativeUrl(Object entity, String query, EntityIndex index) throws Exception
    {
        ForumResponse resp = (ForumResponse)entity;
        return "forum/forumThread.do?id="+resp.getTopicId()+"#A"+resp.getId(); 
    }

    public String getDisplaySubTitle(Object entity) throws Exception
    {
        ForumResponse resp = (ForumResponse)entity;
        return resp.getTopic().getSubject();
    }
    
    public Map<String, Object> getAdditionalDataFields(Object entity) throws Exception
    {
        ForumResponse resp = (ForumResponse)entity;
        Map<String, Object> map = new HashMap<String, Object>(1);
        map.put(CecEntityIndex.TOPIC_ID_FIELD, resp.getTopicId().toString());
        return map;
    }
}
