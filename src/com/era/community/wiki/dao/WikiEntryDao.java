package com.era.community.wiki.dao; 

import java.util.Date;
import java.util.List;

import support.community.database.QueryScroller;

interface WikiEntryDao extends com.era.community.wiki.dao.generated.WikiEntryDaoBase, WikiEntryFinder
{        
	public QueryScroller listHistoryByEditDate(WikiEntry wikiEntry, boolean includeLatest) throws Exception;        
	public Date getLatestPostDate(WikiEntry wikiEntry) throws Exception;
	public List getItemsSince(WikiEntry wikiEntry, Date date) throws Exception;
	public void deleteWikiEntryWithAllVersions(int entryId) throws Exception;
}