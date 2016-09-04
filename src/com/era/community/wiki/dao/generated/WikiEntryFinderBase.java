package com.era.community.wiki.dao.generated; 

import com.era.community.wiki.dao.WikiEntry;

public interface WikiEntryFinderBase
{
	public WikiEntry getWikiEntryForId(int id) throws Exception;
	public WikiEntry newWikiEntry() throws Exception;
}