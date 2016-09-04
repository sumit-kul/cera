package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.pers.dao.Notification;

public abstract class NotificationDaoBaseImpl extends AbstractJdbcDaoSupport implements NotificationDaoBase
{
  public Notification getNotificationForId(int id) throws Exception
  {
      Object[] keys = new Object[] { new Integer(id) };
      return (Notification)getEntity(Notification.class, keys);
  }

    public void deleteNotificationForId(int id) throws Exception
    {
        Object[] keys = new Object[] { new Integer(id) };
        deleteEntity(keys);
    }

    public void delete(Notification o) throws Exception
    {
        deleteEntity(o);
    }

   public void store(Notification o) throws Exception
   {
       storeEntity(o);
   }

   public Notification newNotification() throws Exception
   {
       return (Notification)newEntity(Notification.class);
   }
}