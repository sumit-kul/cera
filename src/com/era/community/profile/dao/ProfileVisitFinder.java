package com.era.community.profile.dao; 

import java.util.List;

import support.community.database.QueryScroller;

public interface ProfileVisitFinder extends com.era.community.profile.dao.generated.ProfileVisitFinderBase
{
	public ProfileVisit getProfileVisit(int profileUserId, int visitingUserId) throws Exception;
	public void deleteAllProfileVisitsForUser(int profileUserId) throws Exception;
	public void deleteProfileVisitsForUserAndId(int profileUserId, int profileVisitId) throws Exception;
	public int getUnseenProfileVisitCountForCurrentUser(int profileUserId) throws Exception;
	public QueryScroller getUnseenProfileVisitorListForCurrentUser(int profileUserId, String filterBy, String sortBy) throws Exception;
	public void markAllVisitingAsSeen(int profileUserId) throws Exception;
	public List getProfileVisitorsForUser(int profileUserId, int currentUserId, int max) throws Exception;
}

