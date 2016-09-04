package com.era.community.events.dao.generated; 
import com.era.community.events.dao.*;

public interface EventLikeFinderBase
{
    public EventLike getEventLikeForId(int id) throws Exception;
    public EventLike newEventLike() throws Exception;
}