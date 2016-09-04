package com.era.community.wiki.dao; 

import java.util.List;
import com.era.community.wiki.ui.dto.Section;

public interface WikiEntrySectionFinder extends com.era.community.wiki.dao.generated.WikiEntrySectionFinderBase
{
	public List<WikiEntrySection> getWikiEntrySectionsForWikiEntryId(int entryId) throws Exception;
	public List<Section> getWikiEntrySectionDtosForWikiEntryId(int entryId) throws Exception;
	
	public void deleteWikiEntrySectionsForWikiEntryVersion(int entryId) throws Exception;
	public void deleteWikiEntrySectionsForAllWikiEntryVersions(int entryId) throws Exception;
}