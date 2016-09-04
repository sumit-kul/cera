package com.era.community.monitor.dao;

import java.util.Date;

import com.era.community.doclib.dao.DocumentLibraryFinder;

public class DocLibSubscription extends Subscription
{
	protected Integer DocLibId  ;
	protected  DocumentLibraryFinder documentLibraryFinder;

	public Integer getDocLibId()
	{
		return DocLibId;
	}
	
	public void setDocLibId(Integer docLibId)
	{
		DocLibId = docLibId;
	}

	@Override
	public String getItemName() throws Exception
	{
		return "Library";
	}
	
	@Override
	public String getItemUrl() throws Exception
	{
		return  "cid/"+this.getCommunityId()+"/library/showLibraryItems.do";
	}

	@Override
	public String getItemType() throws Exception
	{
		return "Community Library";
	}

	@Override
	public Date getItemLastUpdateDate() throws Exception
	{
		return documentLibraryFinder.getDocumentLibraryForId(this.getDocLibId()).getLatestPostDate();
	}

	public final void setDocumentLibraryFinder(DocumentLibraryFinder documentLibraryFinder)
	{
		this.documentLibraryFinder = documentLibraryFinder;
	}

	@Override
	public Object getItem() throws Exception
	{
		return documentLibraryFinder.getDocumentLibraryForId(this.getDocLibId());
	}
	
	@Override
	public int getSortOrder() throws Exception
	{
		return 6;
	}

	public int compareTo(Subscription o) 
	{
		return this.sortOrder-o.sortOrder;
	}
}