package com.era.community.search.dao; 

import java.util.List;

import com.era.community.communities.dao.Community;

public interface SearchSiteFinder extends com.era.community.search.dao.generated.SearchSiteFinderBase
{
	public List<SearchSite> getSitesForCommunity(Community comm) throws Exception;

	public SearchSite getSearchSiteForCommunityIdAndSequence(int commId, int sequence) throws Exception;

	public SearchSite getSearchSiteForCommunityIdAndHostName(int commId, String hostname) throws Exception;

	public Integer getHighestSequenceSiteForCommunity(int commId) throws Exception;
}

