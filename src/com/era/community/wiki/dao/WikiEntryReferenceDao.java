package com.era.community.wiki.dao; 

import support.community.database.QueryScroller;

interface WikiEntryReferenceDao extends com.era.community.wiki.dao.generated.WikiEntryReferenceDaoBase, WikiEntryReferenceFinder
{
	QueryScroller listReferenceForWikiEntry(WikiEntry entry) throws Exception;
}