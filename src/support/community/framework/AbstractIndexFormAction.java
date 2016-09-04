package support.community.framework;

import java.util.*;



import support.community.database.*;

/** 
 * This is useful when you have an index with checkboxes
 */
public abstract class AbstractIndexFormAction extends AbstractFormAction
{
    @Override
    protected final Map referenceData(Object data) throws Exception
    {
        IndexCommandBeanImpl cmd = (IndexCommandBeanImpl)data;
        
        /*
         * If the scroller page has already been set then return;
         */
        if (cmd.getScrollerPage()!=null) getReferenceData(cmd); 
        
        QueryPaginator paginator =  getScroller(cmd);
        if (paginator != null) {
        	paginator.setPageSize(cmd.getPageSize());

        	if (paginator instanceof QueryScroller) {
        		QueryScroller scroller = (QueryScroller)paginator;
        		if (cmd.getPageKey()!=null)  
        			cmd.setScrollerPage(scroller.readPage(cmd.getPageKey()));
        		else
        			cmd.setScrollerPage(scroller.readPage(cmd.getPage()));
        	}
        	else {
        		cmd.setScrollerPage(paginator.readPage(cmd.getPage()));
        	}
        }

        return getReferenceData(cmd);
    }
    
    
    protected void onDisplay(Object data) throws Exception
    {
    }

    protected Map getReferenceData(Object data) throws Exception
    {
        return new HashMap();
    }

    protected abstract QueryPaginator getScroller(IndexCommandBean data) throws Exception;

    
}
