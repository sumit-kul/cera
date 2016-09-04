package com.era.community.search.index;

import java.util.*;

import support.community.lucene.index.*;

import com.era.community.wiki.dao.*;

/**
 * For every entry, we want :
 * 
 * id, title, description, content, date created, date modified, [community id] , [additional data fieldes]
 * 
 * title is the title
 * description is the abstract
 * content is the full body content
 * community id
 * additional data fields
 * 
 * 
 */
public class WikiEntityHandler extends CecAbstractEntityHandler
{
    public boolean supports(Class type) throws Exception
    {
        /* If the Indexer is holding a wiki entry, return true */
        return WikiEntry.class.isAssignableFrom(type);
    }

    public int getEntityId(Object o) throws Exception
    {
        return ((WikiEntry)o).getId();
    }

    public int getCurrentIndexEntry(Object entity, EntityIndex index) throws Exception
    {
        WikiEntry doc = (WikiEntry)entity;
        return index.getEntryId(CecEntityIndex.WIKI_ENTRY_ID_FIELD, ""+doc.getEntryId());
    }

    public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
    {
        return true;
    }
    
    public String getContent(Object rowBean, BinaryDataHandler handler, boolean fast) throws Exception
    {
        WikiEntry doc = (WikiEntry)rowBean;
        return "";
    }

    public String getTitle(Object rowBean) throws Exception
    {
        WikiEntry doc = (WikiEntry)rowBean;
        return doc.getTitle();
    }

    public String getDescription(Object rowBean) throws Exception
    {
        WikiEntry doc = (WikiEntry)rowBean;
        return htmlToText(doc.getBody());
    }

    public Date getDate(Object rowBean) throws Exception
    {
        WikiEntry doc = (WikiEntry)rowBean;
        return doc.getDatePosted();
    }

    public Date getModified(Object rowBean) throws Exception
    {
        WikiEntry doc = (WikiEntry)rowBean;
        return doc.getDatePosted();
    }

    public int getCommunityId(Object rowBean) throws Exception
    {
        WikiEntry doc = (WikiEntry)rowBean;
        return doc.getWiki().getCommunityId();
    }

    public Map<String, Object> getAdditionalDataFields(Object entity) throws Exception
    {
        WikiEntry doc = (WikiEntry)entity;
        Map<String, Object> map = new HashMap<String, Object>(1);
        map.put(CecEntityIndex.WIKI_ENTRY_ID_FIELD, new Integer(doc.getEntryId()));
        return map;
    }

    public String getTitle3(Object entity, String query, EntityIndex index) throws Exception
    {
        WikiEntry doc = (WikiEntry)entity;
        return "Last updated: "+DATE_FORMAT.format(doc.getDatePosted());
    }
    
    public String getCommunityRelativeUrl(Object rowBean, String query, EntityIndex index) throws Exception
    {
        WikiEntry doc = (WikiEntry)rowBean;
        return "wiki/wikiDisplay.do?entryId="+doc.getEntryId();
    }
}