package support.community.framework;

import java.io.*;

import support.community.database.*;

public class IndexDataBeanImpl extends DataBeanImpl implements Serializable, IndexDataBean
{
    private int page = 1;
    private int pageSize = 10;
    private IndexedScrollerPage scrollerPage;
    private String pageKey;
    private int rowCount = 0;
    private QueryScroller queryScroller;
    
    private int pageCount = 0;
    
    public final IndexedScrollerPage getScrollerPage()
    {
        return scrollerPage;
    }
    public final void setScrollerPage(IndexedScrollerPage page)
    {
        this.scrollerPage = page;
    }
    public final int getPage()
    {
        return page;
    }
    public final void setPage(int page)
    {
        this.page = page;
    }
    public final int getPageSize()
    {
        return pageSize;
    }
    public final void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }    
    public final int getItemCount()
    {
        return scrollerPage==null?0:scrollerPage.size();
    }
    public final int getRowCount()
    {
    	if (scrollerPage != null) {
    		return scrollerPage.getRowCount();
		}
    	return this.rowCount;
    }
    public final void setRowCount(int rowCount)
    {
        this.rowCount = rowCount;
    } 
    public final String getPageKey()
    {
        return pageKey;
    }
    public final void setPageKey(String pageKey)
    {
        this.pageKey = pageKey;
    }
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public final QueryScroller getQueryScroller() {
		return queryScroller;
	}
	public final void setQueryScroller(QueryScroller queryScroller) {
		this.queryScroller = queryScroller;
	}
}