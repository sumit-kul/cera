package com.era.community.search.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.search.dao.SearchSite;

public abstract class SearchSiteDaoBaseImpl extends AbstractJdbcDaoSupport implements SearchSiteDaoBase
{
	public SearchSite getSearchSiteForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (SearchSite)getEntity(SearchSite.class, keys);
	}

	/*
	 *
	 */
	public void deleteSearchSiteForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(SearchSite o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(SearchSite o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public SearchSite newSearchSite() throws Exception
	{
		return (SearchSite)newEntity(SearchSite.class);
	}

}
