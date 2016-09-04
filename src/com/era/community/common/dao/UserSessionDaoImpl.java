package com.era.community.common.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

public class UserSessionDaoImpl extends com.era.community.common.dao.generated.UserSessionDaoBaseImpl implements UserSessionDao
{
	public UserSession getUserSessionForSessionKey(String sessionKey) throws Exception
	{
		return (UserSession) getEntityWhere(" SessionKey = ? ", sessionKey);
	}
	
	public int gettotalForIP() throws Exception
	{
		String query = "select count(distinct ip) from UserSession";
		return getSimpleJdbcTemplate().queryForInt(query);
	}
	
	public QueryScroller findAllUserSessions(String sortByOption) throws Exception
	{
		String query = "select US.*, (select COUNT(INS.Screen) from Insight INS where INS.SessionId = US.Id) ScreenCounts, " +
		" (select AVG(INS.timeSpent) from Insight INS where INS.SessionId = US.Id) StayTime,  " +
		" (select CONCAT_WS(' ',U.FirstName,U.LastName) from User U where U.Id = US.UserId ) UserName " +
		" from UserSession US ";
		QueryScroller scroller = getQueryScroller(query,null);
		scroller.addScrollKey("Created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}
}