package com.era.community.communities.dao; 

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.pers.dao.User;

public interface CommunityJoiningRequestFinder extends com.era.community.communities.dao.generated.CommunityJoiningRequestFinderBase
{
	public CommunityJoiningRequest getRequestForUserAndCommunity(int userId, int communityId) throws Exception;
	public List getPendingJoiningRequestForCommunity(int communityId, int max) throws Exception;
	public int countPendingJoiningRequestForCommunity(int communityId) throws Exception;
	public QueryScroller listJoiningRequestForAdmin(User user, String sortBy) throws Exception;
	public QueryScroller listMySentJoiningRequest(User user, String sortBy) throws Exception;
}