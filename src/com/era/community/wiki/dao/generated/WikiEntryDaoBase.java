package com.era.community.wiki.dao.generated; 

import com.era.community.wiki.dao.WikiEntry;

public interface WikiEntryDaoBase extends WikiEntryFinderBase
{
	public void store(WikiEntry o) throws Exception;
	public void store(WikiEntry o, boolean isAllowed) throws Exception;
	public void deleteWikiEntryForId(int id) throws Exception;
	public void delete(WikiEntry o) throws Exception;
	public String getBody(WikiEntry o) throws Exception;
	public void setBody(WikiEntry o, String value) throws Exception;
}