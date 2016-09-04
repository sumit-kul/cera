package com.era.community.events.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.events.dao.generated.EventEntity;

public class EventLikeDaoImpl extends com.era.community.events.dao.generated.EventLikeDaoBaseImpl implements EventLikeDao
{
    public QueryScroller listLikesForEvent(Event entry) throws Exception
    {
        final String SQL = "select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent " + 
            "from EventLike C, User U " +
            "where C.EventId = ? " +
            "and C.PosterId = U.Id";
        
        QueryScroller scroller = getQueryScroller(SQL, EventEntity.class, entry.getId());
        scroller.addScrollKey("T.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
        return scroller;
    }

    public int getLikeCount() throws Exception
    {
        String query = "select count(*) from EventLike";
        return getJdbcTemplate().queryForInt(query);
    }
    
    public int getLikeCountForEvent(int entryId) throws Exception
    {
        String query = "select count(*) from EventLike where EventLike.EventId = ? ";
        return getSimpleJdbcTemplate().queryForInt(query, entryId);
    }
    
    public EventLike getLikeForEventAndUser(int entryId, int userId) throws Exception
    {
    	return (EventLike)getEntityWhere("EventId = ? and PosterId = ?", entryId, userId);
    }
}