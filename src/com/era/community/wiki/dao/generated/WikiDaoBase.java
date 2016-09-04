package com.era.community.wiki.dao.generated; 

import com.era.community.wiki.dao.Wiki;

public interface WikiDaoBase extends WikiFinderBase
{
	public void store(Wiki o) throws Exception;
	public void deleteWikiForId(int id) throws Exception;
	public void delete(Wiki o) throws Exception;
}