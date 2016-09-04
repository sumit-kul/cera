package com.era.community.wiki.dao.generated; 

import com.era.community.wiki.dao.WikiEntryReference;

public interface WikiEntryReferenceFinderBase
{
	public WikiEntryReference getWikiEntryReferenceForId(int id) throws Exception;
	public WikiEntryReference newWikiEntryReference() throws Exception;
}