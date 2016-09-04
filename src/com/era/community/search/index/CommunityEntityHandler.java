package com.era.community.search.index;

import java.util.*;

import support.community.lucene.index.*;

import com.era.community.communities.dao.*;
import com.era.community.base.*;

/**
 * 
 * The search covers the data returned by these 3 methods
 * getTitle()
 * getDescription()
 * getContent()
 */
public class CommunityEntityHandler extends EntityHandler
{
    private CommunityEraContextManager contextManager;
    
    public boolean supports(Class type) throws Exception
    {
        return Community.class.isAssignableFrom(type);
    }

    public int getEntityId(Object o) throws Exception
    {
        return ((Community)o).getId();
    }

    public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
    {
        return true;
    }
    
    public String getContent(Object rowBean, BinaryDataHandler handler, boolean fast) throws Exception
    {
        Community doc = (Community)rowBean;
        //return doc.getKeywords()+" "+doc.getWelcomeText();
        return doc.getWelcomeText();
    }

    public String getTitle(Object rowBean) throws Exception
    {
        Community doc = (Community)rowBean;
        return doc.getName();
    }

    public String getDescription(Object rowBean) throws Exception
    {
        Community doc = (Community)rowBean;
        return doc.getDescription();
    }

    public Date getDate(Object rowBean) throws Exception
    {
        Community doc = (Community)rowBean;
        return doc.getCreated();
    }

    public Date getModified(Object rowBean) throws Exception
    {
        Community doc = (Community)rowBean;
        return doc.getModified();
    }

    public Map<String, Object> getDataFields(Object entity) throws Exception
    {
        return null;
    }

    public String getLink(Object entity, String query, EntityIndex index) throws Exception
    {
        Community doc = (Community)entity;
        return contextManager.getContext().getContextUrl()+ "/cid/" +doc.getId()+"/home.do?backlink=ref";
    }
    
    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
    
    public String getTitle2(Object entity, String query, EntityIndex index) throws Exception
    {
        Community doc = (Community)entity;
        return doc.isPrivate()?"Private community":"Public community";
    }
}
