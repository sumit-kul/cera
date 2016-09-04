package com.era.community.wiki.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class WikiEntrySection extends CecBaseEntity
{
	protected int WikiEntryId;
	protected String SectionTitle;
	protected String SectionBody;
	protected int SectionSeq;
	protected WikiEntrySectionDao dao;

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		return false;
	}

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	} 

	public final void setDao(WikiEntrySectionDao dao)
	{
		this.dao = dao;
	}

	public final WikiEntrySectionDao getDao()
	{
		return dao;
	}
	
	public int getSectionSeq() {
		return SectionSeq;
	}

	public void setSectionSeq(int sectionSeq) {
		SectionSeq = sectionSeq;
	}

	public String getSectionTitle() {
		return SectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		SectionTitle = sectionTitle;
	}

	public String getSectionBody() throws Exception {
		return dao.getSectionBody(this);
	}

	public void setSectionBody(String value) throws Exception {
		dao.setSectionBody(this, value);
	}

	public int getWikiEntryId() {
		return WikiEntryId;
	}

	public void setWikiEntryId(int wikiEntryId) {
		WikiEntryId = wikiEntryId;
	}
}