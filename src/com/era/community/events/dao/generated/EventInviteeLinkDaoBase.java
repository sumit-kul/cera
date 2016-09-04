package com.era.community.events.dao.generated; 

import com.era.community.events.dao.EventInviteeLink;

public interface EventInviteeLinkDaoBase extends EventInviteeLinkFinderBase
{
  public void store(EventInviteeLink o) throws Exception;
  public void deleteEventInviteeLinkForId(int id) throws Exception;
  public void delete(EventInviteeLink o) throws Exception;
}

