package com.era.community.wiki.dao; 

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;
import com.era.community.wiki.ui.dto.WikiEntryHeaderDto;

public interface WikiEntryFinder extends com.era.community.wiki.dao.generated.WikiEntryFinderBase
{
	public WikiEntry getLatestWikiEntryForEntryId(int entryId) throws Exception; 
	public WikiEntry getPreviousWikiEntryForEntryId(int entryId) throws Exception; 
	public WikiEntry getWikiEntryForEntryIdAndSequence(int entryId, int sequence) throws Exception;     
	public int getNextSequenceNumberForEntry(WikiEntry entry) throws Exception;
	public QueryScroller listAllCurrentEntries() throws Exception;     

	public int getWikiEntryCount(int groupId) throws Exception;
	public int getWikiEditCount(int groupId) throws Exception;

	public int getWikiEntryCountForCommunity(Community comm) throws Exception;
	public int getWikiEditCountForCommunity(Community comm) throws Exception;

	public WikiEntry getFirstWikiEntry(int wikiId) throws Exception;
	public List getWikiVersionOptionList(int entryId) throws Exception;

	public int getWikiEntryVersionCount(int entryId) throws Exception;
	public List getContributors(int entryId) throws Exception;
	
	public WikiEntryHeaderDto getWikiEntryForHeader(int entryId) throws Exception;
}