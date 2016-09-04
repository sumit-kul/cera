
package support.community.util;

/**
 * 
 */
public interface Cache
{
    public void put(Object key, Object o);
    public Object get(Object key);
    public void remove(Object key);
    public void clear();
    public void clearConditional();
}