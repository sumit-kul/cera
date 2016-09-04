package support.community.database;

import java.util.*;

/** 
 * An index page is the object returned by a QueryScroller. 
 *  
*/
public class KeyedScrollerPageImpl extends ArrayList<Object> implements KeyedScrollerPage
{
    private Object[] m_nextPageStartKeys = new Object[2];
    private Object[] m_prevPageStartKeys = new Object[2];
   
    public KeyedScrollerPageImpl()
    {
        super();
    }
   
    public KeyedScrollerPageImpl(int n)
    {
        super(n);
    }
   
    public Object getNextPageStartKey(int n)
    {
        return m_nextPageStartKeys[n];
    }
   
    public Object getprevPageStartKey(int n)
    {
        return m_prevPageStartKeys[n];
    }
   
    void setNextPageStartKey(int n, Object o)
    {
        m_nextPageStartKeys[n] = o;
    }
   
    void setPrevPageStartKey(int n, Object o)
    {
        m_prevPageStartKeys[n] = o;
    }
      
    /**
     * @return
     */
    public Object[] getNextPageStartKeys() {
        return m_nextPageStartKeys;
    }

    /**
     * @return
     */
    public Object[] getPrevPageStartKeys() {
        return m_prevPageStartKeys;
    }

}