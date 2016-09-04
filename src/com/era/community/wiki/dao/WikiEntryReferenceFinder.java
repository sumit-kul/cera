package com.era.community.wiki.dao; 

public interface WikiEntryReferenceFinder extends com.era.community.wiki.dao.generated.WikiEntryReferenceFinderBase
{
	public int getReferenceCountForWikiEntry(int entryId) throws Exception;
}