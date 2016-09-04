package com.era.community.newsfeed.ui.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import support.community.database.QueryScroller;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.communities.dao.Community;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentLibrary;
import com.era.community.doclib.dao.DocumentLibraryFinder;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * @spring.bean name="/cid/[cec]/doclib/feed.rss"
*/
public class DefaultDocumentFeedAction extends AbstractFeedAction
{
    DateFormat fmt = new SimpleDateFormat("d MMM yyyy");
    
    /*   
     * Injected references.
     */
    protected DocumentLibraryFinder documentLibraryFinder;
    
    @Override
    protected List<SyndEntry> getEntries(Object data) throws Exception
    {
        List<SyndEntry> entries = new ArrayList<SyndEntry>();

        Community comm = contextManager.getContext().getCurrentCommunity();
        DocumentLibrary doclib = documentLibraryFinder.getDocumentLibraryForCommunity(comm); 

        QueryScroller scroller = doclib.listDocumentsByDate(0, Document.class, true);
        scroller.setBeanClass(Document.class);
        scroller.setPageSize(50);
        
        for (Object o : scroller.readPage(1)) {
            
            Document bean = (Document)o;
            
            SyndEntry entry = new SyndEntryImpl();
            entry.setTitle(bean.getTitle());
            entry.setAuthor(bean.getPoster().getFullName());
            entry.setPublishedDate(bean.getDatePosted());
            entry.setUpdatedDate(bean.getDateRevised());
            entry.setLink(absoluteUrl("cid/"+comm.getId()+"/library/documentdisplay.do?id="+bean.getId()));
            entry.setUri(absoluteUrl("cid/"+comm.getId()+"/library/documentdisplay.do?id="+bean.getId()));

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
        return absoluteUrl("cid/"+comm.getId()+"/library/showLibraryItems.do");
    }

    @Override
    protected String getFeedTitle(Object data) throws Exception
    {
        Community comm = contextManager.getContext().getCurrentCommunity();
        return "Library: "+comm.getName();
    }
    
    public static class Command extends CommandBeanImpl implements CommandBean
    {
    }

    public final void setDocumentLibraryFinder(DocumentLibraryFinder documentLibraryFinder)
    {
        this.documentLibraryFinder = documentLibraryFinder;
    }



}
