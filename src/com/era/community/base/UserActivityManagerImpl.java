package com.era.community.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import support.community.util.LRUCache;
import support.community.util.StringHelper;

public class UserActivityManagerImpl implements UserActivityManager
{
    Log logger = LogFactory.getLog(getClass());
    
    private int expires = 7;
    private int listSize = 24;
    private String[] urlList;
    
 //   protected TaskExecutor taskExecutor;
    
    public String GLOBAL_PLACE = "*global";

    private Map<String, LRUCache> placeMap = new HashMap<String, LRUCache>(listSize);
    
    public synchronized void add(int userId, String userName, String placeName)
    {
        LRUCache  place = placeMap.get(placeName);
        if (place==null) { 
            place = new LRUCache(listSize, expires); 
            placeMap.put(placeName, place);
        }
        place.put(userId, userName);
        if (placeName!=GLOBAL_PLACE) add(userId, userName, GLOBAL_PLACE);
    }

    @SuppressWarnings("unchecked")
    public List getActiveUsersForPlace(String placeName)
    {
        LRUCache  place = placeMap.get(placeName);
        if (place==null) return new ArrayList();
        
        List list = new ArrayList(64);
        for (Object key : place.getKeys()) {
            ActiveUserBean bean = new ActiveUserBean();
            bean.userId = (Integer)key;
            bean.userName = (String)place.get(key);
            list.add(bean);
        }
        
        return list;
    }

    public void addActivity(final int userId, final String userName, final String place) 
    {
           /* taskExecutor.execute(new Runnable() {
                public void run() {
                    for (String spec : urlList) { 
                        try {
                            logger.debug(spec);
                            URL url = new URL(spec+"?userId="+userId+"&userName="+URLEncoder.encode(userName, "UTF-8")+"&placeName="+URLEncoder.encode(place, "UTF-8")); 
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setConnectTimeout(1000); 
                            connection.setRequestMethod("GET");
                            logger.debug(connection.getResponseCode()+" "+url);
                        }
                        catch (Exception x) { 
                            logger.error(x.toString()+" "+spec);
                        }
                    }
                }
            });*/
      }

    public static class ActiveUserBean
    {
        private int userId;
        private String userName;
        public final int getUserId()
        {
            return userId;
        }
        public final void setUserId(int userId)
        {
            this.userId = userId;
        }
        public final String getUserName()
        {
            return userName;
        }
        public final void setUserName(String userName)
        {
            this.userName = userName;
        }
    }
    
    
    public final void setUrlList(String commaSeparatedList)
    {
        this.urlList = StringHelper.split(commaSeparatedList, ",");
    }

    public final void setExpires(int expires)
    {
        this.expires = expires;
    }

    public final void setListSize(int listSize)
    {
        this.listSize = listSize;
    }

    /*public final void setTaskExecutor(TaskExecutor taskExecutor)
    {
        this.taskExecutor = taskExecutor;
    }*/
    
}
