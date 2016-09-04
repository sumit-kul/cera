package support.community.database;

public interface QueryPaginator
{
    public final String DIRECTION_ASCENDING = "asc";
    public final String DIRECTION_DESCENDING = "desc";
    public final String TYPE_TEXT = "text";
    public final String TYPE_INTEGER = "int";
    public final String TYPE_DATE = "date";

    /**
     * Specify a scroll key for the scroller.
     * 
     * @param sName  The column name of the scroll key.
     * @param sDir  The direction "asc" or "desc".
     * @param sType The data type of the column "text", "int" or "date".
     * @throws Exception
     */
    public void addScrollKey(String sName, String sDir, String sType) throws Exception;

    public IndexedScrollerPage readPage(int pageNumber) throws Exception;

    public int getPageSize();

    public void setPageSize(int pageSize);

    /**
     * Get the number of rows in this scroller
     * 
     * @return int containing the number of rows
     * @throws Exception
     */
    public int readRowCount() throws Exception;
}