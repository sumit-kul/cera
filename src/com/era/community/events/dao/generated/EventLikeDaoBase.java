package com.era.community.events.dao.generated; 

import com.era.community.events.dao.EventLike;

public interface EventLikeDaoBase extends EventLikeFinderBase
{
  public void store(EventLike o) throws Exception;
  public void deleteEventLikeForId(int id) throws Exception;
  public void delete(EventLike o) throws Exception;
}

