
package com.era.community.search.index;

import java.util.*;

import support.community.lucene.index.*;

import com.era.community.events.dao.*;

/**
 * 
 */
public class EventEntityHandler extends CecAbstractEntityHandler
{
    public boolean supports(Class type) throws Exception
    {
        return Event.class.isAssignableFrom(type);
    }

    public int getEntityId(Object o) throws Exception
    {
        return ((Event)o).getId();
    }

    public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
    {
        // TODO check sequence ............
        return true;
    }
    
    public String getContent(Object rowBean, BinaryDataHandler handler, boolean fast) throws Exception
    {
        Event doc = (Event)rowBean;
        return doc.getLocation();
    }

    public String getTitle(Object rowBean) throws Exception
    {
        Event doc = (Event)rowBean;
        return doc.getName();
    }

    public String getDescription(Object rowBean) throws Exception
    {
        Event doc = (Event)rowBean;
        return htmlToText(doc.getDescription());
    }

    public Date getDate(Object rowBean) throws Exception
    {
        Event doc = (Event)rowBean;
        return doc.getStartDate();
    }

    public Date getModified(Object rowBean) throws Exception
    {
        Event doc = (Event)rowBean;
        return doc.getStartDate();
    }

    public int getCommunityId(Object rowBean) throws Exception
    {
        Event doc = (Event)rowBean;
        return doc.getEventCalendar().getCommunityId();
    }

    public Map<String, Object> getAdditionalDataFields(Object rowBean) throws Exception
    {
        return null;
    }

    public String getTitle2(Object entity, String query, EntityIndex index) throws Exception
    {
        Event doc = (Event)entity;
        return doc.getLocation();
    }

    public String getTitle3(Object entity, String query, EntityIndex index) throws Exception
    {
        Event doc = (Event)entity;
        return DATE_FORMAT.format(doc.getStartDate());
    }

    public String getCommunityRelativeUrl(Object rowBean, String query, EntityIndex index) throws Exception
    {
         return "event/showEventEntry.do?id="+getEntityId(rowBean);
    }

}
