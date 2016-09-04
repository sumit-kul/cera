package com.era.community.newsfeed.ui.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.upload.dao.UploadFinder;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

public abstract class AbstractFeedAction extends AbstractCommandAction
{
    /*   
     * Access markers.
     */
    public static final String REQUIRES_AUTHENTICATION = "";

    /*
     * Injected references.
     */
    protected CommunityEraContextManager contextManager;
    protected UploadFinder uploadFinder;

    /*
     * Properties.
     */
    private String feedType = "rss_2.0";
    private String mimeType = "application/xml; charset=UTF-8";
    
    @Override
    protected ModelAndView handle(Object data) throws Exception
    {
        HttpServletResponse resp = contextManager.getContext().getResponse();

        try {
            SyndFeed feed = getFeed(data);
            feed.setFeedType(feedType);
            resp.setContentType(mimeType);
            SyndFeedOutput output = new SyndFeedOutput();
            output.output(feed, resp.getWriter());
        }
        catch (Exception x) {
            logger.error(x.toString());
            x.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, x.toString());
        }
        
        return null;
    }

    protected abstract String getFeedTitle(Object data) throws Exception;
    protected abstract String getFeedLink(Object data) throws Exception;
    protected abstract String getFeedDescription(Object data) throws Exception;
    protected abstract List getEntries(Object data) throws Exception;
    

    protected SyndFeed getFeed(Object data) throws Exception 
    {
        SyndFeed feed = new SyndFeedImpl();

        feed.setTitle("jhapak.com "+getFeedTitle(data));
        feed.setLink(getFeedLink(data));
        String s = getFeedDescription(data);
        feed.setDescription(s==null||s.trim().length()==0?"jhapak.com":s);

        feed.setEntries(getEntries(data));

        return feed;
    }

    protected String absoluteUrl(String url) throws Exception 
    {
        String s = contextManager.getContext().getContextUrl();
        if (s.endsWith("/")) s = s.substring(0, s.length()-1);
        return url.startsWith("/") ? s+url : s+"/"+url;
     }

        
    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public final void setUploadFinder(UploadFinder uploadFinder)
    {
        this.uploadFinder = uploadFinder;
    }

    public final void setFeedType(String feedType)
    {
        this.feedType = feedType;
    }
    
}
