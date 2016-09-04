
package support.community.database;

import java.util.*;

import org.springframework.util.*;

/**
 * 
 */
public class ListPaginator implements QueryPaginator
{
    private int pageSize = 16;
    private List list;

    public ListPaginator(List data)
    {
        list = data;
    }
    
    public void addScrollKey(String sName, String sDir, String sType) throws Exception
    {
        throw new Exception("Not implemented");
    }

    public IndexedScrollerPage readPage(int pageNumber) throws Exception
    {
        Assert.isTrue(pageNumber>0, "Invalid page number ["+pageNumber+"]. Must be > 0.");

        IndexedScrollerPageImpl data = new IndexedScrollerPageImpl();
        
        int rowCount = readRowCount();
        int pageCount = rowCount==0 ? 0 : ((int)((rowCount-1)/pageSize))+1;
        
        if (pageNumber>pageCount&&pageCount>0) pageNumber = pageCount; 
        
        int start = (pageNumber-1)*pageSize;
        int end = start + pageSize - 1;
       
         /*
          */
        for (int n=start; n<=end&&n<rowCount; n++) 
            data.add(list.get(n));
          
        data.setRowCount(rowCount);
        data.setPageCount(pageCount);
        data.setCurrentPageNumber(pageNumber);
        data.setScrollerPageSize(pageSize);
          
        return data;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int readRowCount() throws Exception
    {
        return list.size();
    }

}
