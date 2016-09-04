package com.era.community.common.dao; 

import support.community.database.QueryScroller;

public interface InsightFinder extends com.era.community.common.dao.generated.InsightFinderBase
{
	public Insight findPreviousScreenForUserSession(int sessionId) throws Exception;
	public QueryScroller viewInsightForSession(int sessionId) throws Exception;
}