package com.era.community.common.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

public class InsightDaoImpl extends com.era.community.common.dao.generated.InsightDaoBaseImpl implements InsightDao
{
	public Insight findPreviousScreenForUserSession(int sessionId) throws Exception
	{
		String query = "select * from Insight where SessionId = ? order by Id desc LIMIT 1 ";
		return getBean(query, Insight.class, sessionId);
	}
	
	public QueryScroller viewInsightForSession(int sessionId) throws Exception
	{
		String query = "select * from (select I.*, (select CAST(IT.screen as CHAR(100) )  from Insight IT where IT.Id = I.previousPageId) previousPage, U.Ip " +
		" from Insight I, UserSession U where I.SessionId = U.Id and I.SessionId = ? ) as T";
		QueryScroller scroller = getQueryScroller(query,true, null, sessionId);
		scroller.addScrollKey("Created", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}
}