package com.era.community.common.dao; 

import support.community.database.QueryScroller;

public interface UserSessionFinder extends com.era.community.common.dao.generated.UserSessionFinderBase
{
	public UserSession getUserSessionForSessionKey(String sessionKey) throws Exception;
	public int gettotalForIP() throws Exception;
	public QueryScroller findAllUserSessions(String sortByOption) throws Exception;
}