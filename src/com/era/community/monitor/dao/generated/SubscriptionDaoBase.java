package com.era.community.monitor.dao.generated; 

import com.era.community.monitor.dao.Subscription;

public interface SubscriptionDaoBase extends SubscriptionFinderBase
{
  public void store(Subscription o) throws Exception;
  public void deleteSubscriptionForId(int id) throws Exception;
  public void delete(Subscription o) throws Exception;
}

