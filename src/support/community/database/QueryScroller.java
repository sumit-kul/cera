package support.community.database;

public interface QueryScroller extends QueryPaginator 
{
    public void addScrollKey(String sName, String sDir, String sType, boolean bUpperCase) throws Exception;

    public void addScrollKey(String sName, String sResultSetName, String sDir, String sType) throws Exception;

    public void addScrollKey(String sName, String sResultSetName, String sDir, String sType, boolean bUpperCase) throws Exception;

    public void addScrollKey(String sName, String sResultSetName, String sDir, String sType, boolean bUpperCase, Object oBase) throws Exception;

    public Class getBeanClass();
    
    public void setBeanClass(Class beanClass);
    
    public void setBeanClass(Class beanClass, Object enclosingInstance);

    public KeyedScrollerPage readPage(Object skey1, Object skey2, boolean strict, boolean bReverse) throws Exception;

    public IndexedScrollerPage readPage(Object key) throws Exception;

    public int readPageCount() throws Exception;
    
    public void doForAllRows(QueryScrollerCallback callback) throws Exception;
    
    public void doForAllRows(final QueryScrollerCallback callback, boolean commitAfterEachPage) throws Exception;
    
}