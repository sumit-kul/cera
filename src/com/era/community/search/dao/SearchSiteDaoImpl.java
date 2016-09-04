package com.era.community.search.dao; 

import java.util.*;

import com.era.community.communities.dao.*;

public class SearchSiteDaoImpl extends com.era.community.search.dao.generated.SearchSiteDaoBaseImpl implements SearchSiteDao
{

	@SuppressWarnings("unchecked")
	public List<SearchSite> getSitesForCommunity(Community comm) throws Exception
	{
		String query = "select * from SearchSite where CommunityId = ? order by sequence asc";
		return getEntityList(query, new Object[] {comm.getId()});
	}

	public SearchSite getSearchSiteForCommunityIdAndSequence(int commId, int sequence) throws Exception
	{
		return (SearchSite)getEntityWhere("CommunityId = ? and Sequence = ?", commId , sequence);
	}

	public SearchSite getSearchSiteForCommunityIdAndHostName(int commId, String hostname) throws Exception
	{
		return (SearchSite)getEntityWhere("CommunityId = ? and Hostname = ?",commId , hostname);
	}


	public Integer getHighestSequenceSiteForCommunity(int commId) throws Exception
	{
		String query = "select max(Sequence) from SearchSite where CommunityId = ?";
		return (Integer) getSimpleJdbcTemplate().queryForObject(query, Integer.class, commId);
	}

	public Integer getLowestSequenceSiteForCommunity(int commId) throws Exception
	{
		String query = "select min(Sequence) from SearchSite where CommunityId = ?";
		return (Integer) getSimpleJdbcTemplate().queryForObject(query, Integer.class, commId);
	}


}

