/*package com.era.community.newsfeed.ui.action;

import java.util.ArrayList;
import java.util.List;

import support.community.database.QueryScroller;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.blog.dao.generated.BlogEntryEntity;
import com.era.community.blog.ui.dto.BlogEntryDto;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

*//**
 * @spring.bean name="/feed/blog-feed.rss"
*//*
public class DefaultBlogFeedAction extends AbstractFeedAction
{
    protected BlogFinder blogFinder;
    
    @Override
    protected List<SyndEntry> getEntries(Object data) throws Exception
    {
        List<SyndEntry> entries = new ArrayList<SyndEntry>();

        Command cmd = (Command)data;

        Blog blog = blogFinder.getBlogForId(cmd.getId());

        QueryScroller scroller = blog.listEntriesByDate(null);
        scroller.setBeanClass(BlogEntryDto.class);
        scroller.setPageSize(50);
        
        for (Object o : scroller.readPage(1)) {
            
            BlogEntryEntity bean = (BlogEntryEntity)o;
            
            SyndEntry entry = new SyndEntryImpl();
            entry.setTitle(bean.getTitle());
            entry.setAuthor(blog.getOwner().getFullName());
           // entry.setPublishedDate(bean.getDatePosted()); will get Date TODO
           // entry.setUpdatedDate(bean.getModified());
            entry.setLink(absoluteUrl("reg/blog-display.do?id="+bean.getId()+"&userId="+blog.getOwnerId()));
            entry.setUri(absoluteUrl("reg/blog-display.do?id="+bean.getId()+"&userId="+blog.getOwnerId()));

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
        Command cmd = (Command)data;
        Blog blog = blogFinder.getBlogForId(cmd.getId());
        return blog.getDescription();
    }

    @Override
    protected String getFeedLink(Object data) throws Exception
    {
        Command cmd = (Command)data;
        Blog blog = blogFinder.getBlogForId(cmd.getId());
        return absoluteUrl("reg/blog-personal-index.do?userId="+blog.getOwnerId());
    }

    @Override
    protected String getFeedTitle(Object data) throws Exception
    {
        Command cmd = (Command)data;
        Blog blog = blogFinder.getBlogForId(cmd.getId());
        return "Blog: "+blog.getOwner().getFullName();
    }
    
    public static class Command extends CommandBeanImpl implements CommandBean
    {
        private int id;

        public final int getId()
        {
            return id;
        }

        public final void setId(int id)
        {
            this.id = id;
        }
    }

    public final void setBlogFinder(BlogFinder blogFinder)
    {
        this.blogFinder = blogFinder;
    }

}
*/