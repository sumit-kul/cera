package com.era.community.search.dao.generated; 

import com.era.community.search.dao.SearchSite;

public interface SearchSiteDaoBase extends SearchSiteFinderBase
{
  public void store(SearchSite o) throws Exception;
  public void deleteSearchSiteForId(int id) throws Exception;
  public void delete(SearchSite o) throws Exception;
}

