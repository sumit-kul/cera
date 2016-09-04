package com.era.community.accesslog.dao.generated; 

import support.community.database.*;
import com.era.community.accesslog.dao.*;

public abstract class AccessLogDaoBaseImpl extends AbstractJdbcDaoSupport implements AccessLogDaoBase
{
   /*
   *
  */
  public AccessLog getAccessLogForId(int id) throws Exception
  {
      Object[] keys = new Object[] { new Integer(id) };
      return (AccessLog)getEntity(AccessLog.class, keys);
  }

    /*
     *
    */
    public void deleteAccessLogForId(int id) throws Exception
    {
        Object[] keys = new Object[] { new Integer(id) };
        deleteEntity(keys);
    }

    /*
     *
    */
    public void delete(AccessLog o) throws Exception
    {
        deleteEntity(o);
    }

    /*
    *
   */
   public void store(AccessLog o) throws Exception
   {
       storeEntity(o);
   }

    /*
    *
   */
   public AccessLog newAccessLog() throws Exception
   {
       return (AccessLog)newEntity(AccessLog.class);
   }

}
