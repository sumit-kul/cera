package com.era.community.newsfeed.ui.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import support.community.database.QueryScroller;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.communities.dao.Community;
import com.era.community.wiki.dao.Wiki;
import com.era.community.wiki.dao.WikiFinder;
import com.era.community.wiki.ui.dto.WikiEntryDto;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * @spring.bean name="/cid/[cec]/wiki/feed.rss"
*/
public class DefaultEventFeedAction extends AbstractFeedAction
{
    protected WikiFinder wikiFinder;
    
    @Override
    protected List<SyndEntry> getEntries(Object data) throws Exception
    {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<SyndEntry> entries = new ArrayList<SyndEntry>();

        Community comm = contextManager.getContext().getCurrentCommunity();
        Wiki wiki = wikiFinder.getWikiForCommunity(comm); 

        QueryScroller scroller = wiki.listEntriesByUpdateDate();
        scroller.setBeanClass(WikiEntryDto.class);
        scroller.setPageSize(50);
        
        for (Object o : scroller.readPage(1)) {
            
            WikiEntryDto bean = (WikiEntryDto)o;
            
            SyndEntry entry = new SyndEntryImpl();
            entry.setTitle(bean.getTitle());
            entry.setAuthor(bean.getLastEditedBy());
            entry.setPublishedDate(formatter.parse(bean.getDatePosted()));
            entry.setUpdatedDate(formatter.parse(bean.getModified()));
            entry.setLink(absoluteUrl("cid/"+comm.getId()+"/wiki/wikiDisplay.do?entryId="+bean.getEntryId()));
            entry.setUri(absoluteUrl("cid/"+comm.getId()+"/wiki/wikiDisplay.do?entryId="+bean.getEntryId()));

            SyndContent description;
            description = new SyndContentImpl();
            description.setType("text/html");
            description.setValue(bean.getBody());
            entry.setDescription(description);
            
            entries.add(entry);
        }

        return entries;
    }

    @Override
    protected String getFeedDescription(Object data) throws Exception
    {
        Community comm = contextManager.getContext().getCurrentCommunity();
        Wiki wiki = wikiFinder.getWikiForCommunity(comm); 
        return "";
    }

    @Override
    protected String getFeedLink(Object data) throws Exception
    {
        Community comm = contextManager.getContext().getCurrentCommunity();
        return absoluteUrl("cid/"+comm.getId()+"/wiki/showWikiEntries.do");
    }

    @Override
    protected String getFeedTitle(Object data) throws Exception
    {
        Community comm = contextManager.getContext().getCurrentCommunity();
        return "Wiki: "+comm.getName();
    }
    
    public static class Command extends CommandBeanImpl implements CommandBean
    {
    }

    public final void setWikiFinder(WikiFinder wikiFinder)
    {
        this.wikiFinder = wikiFinder;
    }



}
