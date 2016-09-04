package com.era.community.wiki.dao; 

import java.util.List;

import com.era.community.wiki.ui.dto.Section;

public class WikiEntrySectionDaoImpl extends com.era.community.wiki.dao.generated.WikiEntrySectionDaoBaseImpl implements WikiEntrySectionDao
{
	public List<WikiEntrySection> getWikiEntrySectionsForWikiEntryId(int entryId) throws Exception
	{
		final String sQuery = "Select S.* from WikiEntrySection S where S.WikiEntryId = ? order by S.SectionSeq asc ";
		return getBeanList(sQuery, WikiEntrySection.class, entryId);
	}
	
	public List<Section> getWikiEntrySectionDtosForWikiEntryId(int entryId) throws Exception
	{
		final String sQuery = "Select S.SectionTitle as Header, S.SectionBody as Body, S.SectionSeq as SectionSeq, S.Id as SectionId " +
			" from TBWES S where S.WikiEntryId = ? order by S.SectionSeq asc ";
				
		return getBeanList(sQuery, Section.class, entryId);
	}
	
	public void deleteWikiEntrySectionsForWikiEntryVersion(int entryId) throws Exception
	{
		String sql="delete from WikiEntrySection where WikiEntryId = ?";
		getSimpleJdbcTemplate().update(sql, entryId);       
	}
	
	public void deleteWikiEntrySectionsForAllWikiEntryVersions(int entryId) throws Exception
	{
		String sql="delete from WikiEntrySection where WikiEntryId in ( select W.ID from WikiEntry W where W.EntryId =  ? ) ";
		getSimpleJdbcTemplate().update(sql, entryId);       
	}
}