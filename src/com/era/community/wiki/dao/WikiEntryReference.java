package com.era.community.wiki.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class WikiEntryReference extends CecBaseEntity
{
	protected int WikiEntryId;
	protected int ReferenceType;
	protected String ReferenceTitle;
	protected String ReferenceURL;
	protected WikiEntryReferenceDao dao;

	public int getWikiEntryId()
	{
		return WikiEntryId;
	}

	public void setWikiEntryId(int wikiEntryId)
	{
		WikiEntryId = wikiEntryId;
	}

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

	public final void setDao(WikiEntryReferenceDao dao)
	{
		this.dao = dao;
	}

	public final WikiEntryReferenceDao getDao()
	{
		return dao;
	}

	public int getReferenceType() {
		return ReferenceType;
	}

	public void setReferenceType(int referenceType) {
		ReferenceType = referenceType;
	}

	public String getReferenceTitle() {
		return ReferenceTitle;
	}

	public void setReferenceTitle(String referenceTitle) {
		ReferenceTitle = referenceTitle;
	}

	public String getReferenceURL() {
		return ReferenceURL;
	}

	public void setReferenceURL(String referenceURL) {
		ReferenceURL = referenceURL;
	}
}