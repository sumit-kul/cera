package com.era.community.newsfeed.ui.action;

import java.util.ArrayList;
import java.util.List;

import support.community.database.QueryScroller;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.communities.dao.Community;
import com.era.community.forum.dao.Forum;
import com.era.community.forum.dao.ForumFinder;
import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumResponse;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * @spring.bean name="/cid/[cec]/forum/feed.rss"
*/
public class DefaultForumFeedAction extends AbstractFeedAction
{
    /*   
     * Injected references.
     */
    protected ForumFinder forumFinder;
    
    @Override
    protected List<SyndEntry> getEntries(Object data) throws Exception
    {
        List<SyndEntry> entries = new ArrayList<SyndEntry>();

        Community comm = contextManager.getContext().getCurrentCommunity();
        Forum forum = forumFinder.getForumForCommunity(comm);

        QueryScroller scroller = forum.listItemsByDate(0);
        scroller.setBeanClass(ForumItem.class);
        scroller.setPageSize(50);
        
        for (Object o : scroller.readPage(1)) {
            
            ForumItem bean = (ForumItem)o;
            
            SyndEntry entry = new SyndEntryImpl();
            entry.setTitle(bean.getSubject());
            entry.setAuthor(bean.getAuthor().getFullName());
            entry.setPublishedDate(bean.getDatePosted());
            entry.setUpdatedDate(bean.getModified());
            entry.setLink(absoluteUrl("cid/"+forum.getCommunityId()+"/forum/forumThread.do?id="+bean.getTopic().getId()));
            entry.setUri(absoluteUrl("cid/"+forum.getCommunityId()+"/forum/forumThread.do?id="+bean.getId()));

            SyndContent description;
            description = new SyndContentImpl();
            description.setType("text/html");
            
            if (bean instanceof ForumResponse) {
                ForumResponse resp = (ForumResponse)bean;
                description.setValue("<strong>Response to</strong>: "+resp.getTopic().getSubject()+"<br><br>"+bean.getBody());
            }
            else {
                description.setValue(bean.getBody());
            }
            
            entry.setDescription(description);
            
            entries.add(entry);
        }

        return entries;
    }

    @Override
    protected String getFeedDescription(Object data) throws Exception
    {
        Community comm = contextManager.getContext().getCurrentCommunity();
        Forum forum = forumFinder.getForumForCommunity(comm);
        return "";
    }

    @Override
    protected String getFeedLink(Object data) throws Exception
    {
        Community comm = contextManager.getContext().getCurrentCommunity();
        Forum forum = forumFinder.getForumForCommunity(comm);
        return absoluteUrl("cid/"+forum.getCommunityId()+"/forum/showTopics.do");
    }

    @Override
    protected String getFeedTitle(Object data) throws Exception
    {
        Community comm = contextManager.getContext().getCurrentCommunity();
        Forum forum = forumFinder.getForumForCommunity(comm);
        return "Forum: "+forum.getName();
    }
    
    public static class Command extends CommandBeanImpl implements CommandBean
    {
    }

    public final void setForumFinder(ForumFinder forumFinder)
    {
        this.forumFinder = forumFinder;
    }


}
