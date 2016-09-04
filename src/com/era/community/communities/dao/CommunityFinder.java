package com.era.community.communities.dao; 

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.blog.ui.dto.BlogEntryPannelDto;
import com.era.community.communities.ui.dto.CommunityEntryPannelDto;
import com.era.community.events.ui.dto.EventPannelDto;
import com.era.community.forum.ui.dto.ForumTopicPannelDto;
import com.era.community.pers.dao.User;
import com.era.community.wiki.ui.dto.WikiEntryPannelDto;

public interface CommunityFinder extends com.era.community.communities.dao.generated.CommunityFinderBase
{
	public QueryScroller listActiveCommunities(String filterTagList, String communityOption, String sortByOption) throws Exception;
	public QueryScroller listActiveCommunitiesForMember(User user, String filterTagList, String communityOption, String sortByOption) throws Exception;
	public QueryScroller listActiveCommunitiesForFollower(User follower, String filterTagList, String sortByOption) throws Exception;
	
	public QueryScroller listActiveCommunitiesByName() throws Exception;
	public QueryScroller listActiveCommunitiesForTag(String tag) throws Exception;
	public QueryScroller listActiveCommunitiesForMemberByName(User user, User currUser) throws Exception;
	public QueryScroller listActiveUserCommunitiesForMemberByName(User user, String type, String role, String sortBy) throws Exception;
	public QueryScroller listActiveCommunitiesForMemberByDateJoined(User user, User currUser) throws Exception; 
	public QueryScroller listActiveCommunitiesForMemberByDateJoined(User user, User currUser, Class bean) throws Exception; 
	public QueryScroller listActiveCommunitiesForMemberByDateVisited(User user, User currUser) throws Exception; 
	public QueryScroller listActiveCommunitiesForMemberByDateVisited(User user, User currUser, Class bean) throws Exception; 

	public QueryScroller getItemsForCommunityHome(Community comm) throws Exception;
	public QueryScroller getItemsForSelectedTags(String filterTagList, String typeBy, String sortBy) throws Exception;

	public int getContributorCount(Community comm) throws Exception;

	public int getCommunityCount(int count) throws Exception;
	public int getCommunityCountSoFar() throws Exception;
	
	public Community getCommunityForName(String name) throws Exception;
	public List getActiveCommunitiesForMember(User user) throws Exception;
	public List getActiveOtherCommunitiesForMember(User user, int communityId) throws Exception;
	public int countCommunitiesForBlogEntry(int blogentryId) throws Exception;
	public int countCommunitiesForConnection(int userId, int currUserID) throws Exception;
	
	public QueryScroller listCommunityInvitationsForMember(User user, String sortBy) throws Exception;
	public List<CommunityEntryPannelDto> getMostActiveCommunities(int max) throws Exception;
    public List<CommunityEntryPannelDto> getMostRecentCommunities(int max) throws Exception;
    public List<CommunityEntryPannelDto> getMostViewedCommunities(int max) throws Exception;
    public List<BlogEntryPannelDto> listAllTopStories(int max) throws Exception;
    public List<ForumTopicPannelDto> listAllTopTopics(int max) throws Exception;
    public List<WikiEntryPannelDto> listAllWikiEntries(int max) throws Exception;
    public List<EventPannelDto> getUpcomingEvents(int max) throws Exception;
    
    public List getAllCommunities() throws Exception;
}