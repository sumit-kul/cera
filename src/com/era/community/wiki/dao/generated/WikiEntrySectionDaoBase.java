package com.era.community.wiki.dao.generated; 

import com.era.community.wiki.dao.WikiEntrySection;

public interface WikiEntrySectionDaoBase extends WikiEntrySectionFinderBase
{
	public void store(WikiEntrySection o) throws Exception;
	public void deleteWikiEntrySectionForId(int id) throws Exception;
	public void delete(WikiEntrySection o) throws Exception;
	public String getSectionBody(WikiEntrySection o) throws Exception;
	public void setSectionBody(WikiEntrySection o, String value) throws Exception;
}