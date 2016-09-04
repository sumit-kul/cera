package support.community.framework;

import support.community.database.*;

/**
 * 
 */
public interface IndexCommandBean extends CommandBean
{
    public IndexedScrollerPage getScrollerPage();

    public void setScrollerPage(IndexedScrollerPage page);

    public int getPage();

    public void setPage(int page);

    public int getPageSize();

    public void setPageSize(int pageSize);
    
    public int getItemCount();
    
    public String getPageKey();
}