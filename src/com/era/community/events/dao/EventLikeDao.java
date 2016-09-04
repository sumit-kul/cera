package com.era.community.events.dao; 

import support.community.database.QueryScroller;

interface EventLikeDao extends com.era.community.events.dao.generated.EventLikeDaoBase, EventLikeFinder
{
    QueryScroller listLikesForEvent(Event event) throws Exception;
    
}

