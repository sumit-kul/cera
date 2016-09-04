package support.community.util;

import java.util.*;

/**
 * Implements a least recently used cache.
 */
@SuppressWarnings("unchecked")

public class LRUCache implements Cache
{
	private int iMax;
	private int iLife;
	private Map map;
	private LinkedList list;

   /**		
    *    Create with a given map size and entry expiration interval.
    * 
    *    @param   iMapSize    The size of the map used by the cache
    *    @param   iLife           The time in minutes after which entries expire
    * 
    */
	public LRUCache(int iMapSize, int iLife) 
	{
		this.iMax = (int)(iMapSize*0.75);
		this.iLife = iLife;
		map = new HashMap(iMapSize, 0.75f);
		list = new LinkedList();
	}

   /**
    *   Create a cache with given map size and no expiration.
    *    
    *   @param   iMapSize    The size of the map used by the cache
    * 
    */
	public LRUCache(int iMapSize) 
	{
		this(iMapSize, -1);
	}

   /**
    *   Create a cache with default map size and no expiration.
    *    
    *   @param   iMapSize    The size of the map used by the cache
    * 
    */
	public LRUCache() 
	{
		this(89, -1);
	}

    public synchronized void put (Object oKey, Object o)
	{
		/*
			Add the object into the map.
		*/
		map.put(oKey, iLife<0?o:new LRUCacheEntry(o));

		/*
			Bring the key to the front of the list.
		*/
		list.remove(oKey);
		list.addFirst(oKey);
		
		/*
			Trim the cache if necessary.
		*/
		while (map.size() > iMax) {
            map.remove(list.removeLast());
		}
		
	}

	public synchronized Object get(Object oKey)
	{
		/*
			Get the entry from the map.
		*/
		Object o = map.get(oKey);

		/*
			Exit if key not present.
		*/
		if (o==null) return null;

		/*
			Bring the key to the front of the list.
		*/
		list.remove(oKey);
		list.addFirst(oKey);
		
		/*
			If we are not tracking liftimes then the map entry is the object to return.
		*/
		if (iLife<0) return o;

		/*
			Otherwise we have a CacheEntry.
		*/
		LRUCacheEntry e = (LRUCacheEntry)o;
		
		/*
			If not expired then return the payload object.
		*/
		if (!e.isExpired(iLife)) return e.oPayload;
		
		/*
			If expired remove and return null.
		*/
		remove(oKey);
		return null;
	}

	public synchronized void remove(Object oKey)
	{
		map.remove(oKey);
		list.remove(oKey);
	}

   /**
    * Inner class to describe a cache entry in the case where an expiration
    * interval has been set for the cache.
    */
	class LRUCacheEntry 
	{
      /**
       * The object carried by the cache enty.
       */
		Object oPayload;

      /**
       * The time the entry was created in seconds since the origin 
       * of currentTimeMillis.
       */
		int  created;
	
	    LRUCacheEntry(Object o) 
	    {
	    	this.oPayload = o;
	    	this.created = (int)(System.currentTimeMillis()/60000);	
	    }  
	
	    boolean isExpired(int iLife) 
	    {
	    	int now = (int)(System.currentTimeMillis()/60000);	
	    	return (now-created) > iLife;
	    }  
	
	}
    
    /**
     * Return the list of keys currently in the cache.
     */
    public List getKeys() 
    {
        List result = new ArrayList(list.size());
        for (Object key : list) {
            if (iLife<0) {
                result.add(key);
            }
            else {
                LRUCacheEntry e = (LRUCacheEntry)map.get(key);
                if (!e.isExpired(iLife)) result.add(key);
            }
        }
        return result;
    }
    
    public void clear()
    {
        map.clear();
        list.clear();
    }

    public void clearConditional()
    {
        if (map.size()>=(2*iMax)/5) 
            clear();
    }

}


