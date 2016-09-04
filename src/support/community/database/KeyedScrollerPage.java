
package support.community.database;


/**
 * 
 */
public interface KeyedScrollerPage extends ScrollerPage
{
    public Object getNextPageStartKey(int n);

    public Object getprevPageStartKey(int n);

    /**
     * @return
     */
    public Object[] getNextPageStartKeys();

    /**
     * @return
     */
    public Object[] getPrevPageStartKeys();
}