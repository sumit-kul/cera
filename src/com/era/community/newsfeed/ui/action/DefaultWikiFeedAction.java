package com.era.community.newsfeed.ui.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import support.community.database.QueryScroller;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.communities.dao.Community;
import com.era.community.events.dao.Event;
import com.era.community.events.dao.EventCalendar;
import com.era.community.events.dao.EventCalendarFinder;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * @spring.bean name="/cid/[cec]/event/feed.rss"
*/
public class DefaultWikiFeedAction extends AbstractFeedAction
{
    DateFormat fmt = new SimpleDateFormat("d MMM yyyy");
    
    /*   
     * Injected references.
     */
    protected EventCalendarFinder calendarFinder;
    
    @Override
    protected List<SyndEntry> getEntries(Object data) throws Exception
    {
        List<SyndEntry> entries = new ArrayList<SyndEntry>();

        Community comm = contextManager.getContext().getCurrentCommunity();
        EventCalendar calendar = calendarFinder.getEventCalendarForCommunity(comm); 

        QueryScroller scroller = calendar.listFutureEvents(0);
        scroller.setBeanClass(Event.class);
        scroller.setPageSize(50);
        
        for (Object o : scroller.readPage(1)) {
            
            Event bean = (Event)o;
            
            SyndEntry entry = new SyndEntryImpl();
            entry.setTitle(bean.getName()+" "+bean.getLocation()+" "+fmt.format(bean.getStartDate()));
            entry.setAuthor(bean.getContactName());
            entry.setPublishedDate(bean.getCreated());
            entry.setUpdatedDate(bean.getModified());
            entry.setLink(absoluteUrl("cid/"+comm.getId()+"/event/showEventEntry.do?id="+bean.getId()));
            entry.setUri(absoluteUrl("cid/"+comm.getId()+"/event/showEventEntry.do?id="+bean.getId()));

            SyndContent description;
            description = new SyndContentImpl();
            description.setType("text/html");
            description.setValue(bean.getDescription());
            entry.setDescription(description);
            
            entries.add(entry);
        }

        return entries;
    }

    @Override
    protected String getFeedDescription(Object data) throws Exception
    {
        Community comm = contextManager.getContext().getCurrentCommunity();
        return "";
    }

    @Override
    protected String getFeedLink(Object data) throws Exception
    {
        Community comm = contextManager.getContext().getCurrentCommunity();
        return absoluteUrl("cid/"+comm.getId()+"/event/showEvents.do");
    }

    @Override
    protected String getFeedTitle(Object data) throws Exception
    {
        Community comm = contextManager.getContext().getCurrentCommunity();
        return "Events: "+comm.getName();
    }
    
    public static class Command extends CommandBeanImpl implements CommandBean
    {
    }

    public final void setCalendarFinder(EventCalendarFinder calendarFinder)
    {
        this.calendarFinder = calendarFinder;
    }


}
