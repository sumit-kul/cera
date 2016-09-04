package com.era.community.wiki.dao.generated; 

import com.era.community.wiki.dao.Wiki;

public interface WikiFinderBase
{
	public Wiki getWikiForId(int id) throws Exception;
	public Wiki newWiki() throws Exception;
}