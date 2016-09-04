package support.community.database;

import java.util.*;
import java.util.Map.*;

import org.apache.commons.logging.*;

import support.community.util.*;

public class TransactionCache implements Cache
{
    private static Log logger = LogFactory.getLog(TransactionCache.class);
    
    private Map map;
    private int maxSize;
    
    public TransactionCache(int initialCapacity, final int maxSize)
    {
        this.map = new LinkedHashMap(initialCapacity) {
            protected boolean removeEldestEntry(Entry eldest)
            {
                if (map.size()>=maxSize) {
                    logger.warn("Eldest entry dropped from thread cache");
                    return true;
                }
                return false;
            }
        }; 
            
        this.maxSize = maxSize;
    }

    public void clear()
    {
        map.clear();
    }

    public void clearConditional()
    {
        if (map.size()>=(2*maxSize)/5) 
        map.clear();
    }

    public Object get(Object key)
    {
        return map.get(key);
    }

    @SuppressWarnings("unchecked")
    public void put(Object key, Object o) 
    {
        map.put(key, o);
    }

    public void remove(Object key)
    {
        map.remove(key);
    }
    
}