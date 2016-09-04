package com.era.community.announcement.ui.action;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.AbstractIndexAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.announcement.dao.AnnouncementFinder;
import com.era.community.announcement.ui.dto.AnnouncementDto;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;

/** 
 * @spring.bean name="/announcement/announcement-index.do"
 */
public class AnnouncementsIndexAction extends AbstractIndexAction
{
    public static final String REQUIRES_AUTHENTICATION = "";

    protected CommunityEraContextManager contextManager;
    protected AnnouncementFinder announcementFinder;

    protected String getView(IndexCommandBean bean) throws Exception
    {
        return "/announcement/announcements-index";           // JSP name
    }

    protected QueryPaginator getScroller(IndexCommandBean bean) throws Exception
    {
        Command cmd = (Command) bean;
        CommunityEraContext context = contextManager.getContext();

        context.setH2Header("Announcements");

        QueryScroller scroller = announcementFinder.listAnnouncementsByDatePosted();

        scroller.setBeanClass(RowBean.class);

        return scroller;
    }

    public class Command extends IndexCommandBeanImpl implements IndexCommandBean
    {

    }

    public static class RowBean extends AnnouncementDto
    {
        /*
         * These properties are referenced in the JSP
         */
        private int resultSetIndex;   

        private String bodyDisplay;
        
        public String getBodyDisplay() throws Exception
        {
            // The first 50 chars of the body display in the view. If these chars contain any links, just display the part before the link.
            if ( (this.getBody()==null) || (this.getBody().length()==0)) return "";

            String sBody = this.getBody();

            sBody = sBody.replaceAll("<p>","");
            sBody = sBody.replaceAll("</p>"," ");
            sBody = sBody.replaceAll("   ", "");

            if (sBody.contains("<")) {
    			sBody = sBody.substring(0, sBody.indexOf("<"));
    			if(sBody.length() >= 300)sBody.substring(0, 300);
    				sBody.concat("...");
    		} else if (sBody.length() >= 300) {
    			sBody = sBody.substring(0, 300).concat("...");
    		}
    		return sBody;
        }

        public void setBodyDisplay(String bodyDisplay)
        {
            this.bodyDisplay = bodyDisplay;
        }        

        public final int getResultSetIndex()
        {
            return resultSetIndex;
        }
        public final void setResultSetIndex(int resultSetIndex)
        {
            this.resultSetIndex = resultSetIndex;
        }
    }

    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public final void setAnnouncementFinder(AnnouncementFinder announcementFinder)
    {
        this.announcementFinder = announcementFinder;
    }

}
