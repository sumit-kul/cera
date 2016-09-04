package com.era.community.wiki.dao.generated; 

import com.era.community.wiki.dao.WikiEntryReference;


public interface WikiEntryReferenceDaoBase extends WikiEntryReferenceFinderBase
{
	public void store(WikiEntryReference o) throws Exception;
	public void deleteWikiEntryReferenceForId(int id) throws Exception;
	public void delete(WikiEntryReference o) throws Exception;
}