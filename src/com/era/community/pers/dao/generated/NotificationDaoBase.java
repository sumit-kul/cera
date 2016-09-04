package com.era.community.pers.dao.generated; 

import com.era.community.pers.dao.Notification;

public interface NotificationDaoBase extends NotificationFinderBase
{
  public void store(Notification o) throws Exception;
  public void deleteNotificationForId(int id) throws Exception;
  public void delete(Notification o) throws Exception;
}