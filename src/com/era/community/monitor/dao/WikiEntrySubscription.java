package com.era.community.monitor.dao;

import java.util.Date;

import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryFinder;

public class WikiEntrySubscription extends Subscription
{
	protected Integer WikiEntryId  ;
	protected WikiEntryFinder wikiEntryFinder;

	public Integer getWikiEntryId()
	{
		return WikiEntryId;
	}

	public void setWikiEntryId(Integer wikiEntryId)
	{
		WikiEntryId = wikiEntryId;
	}

	@Override
	public String getItemName() throws Exception
	{
		WikiEntry entry = wikiEntryFinder.getWikiEntryForId(this.getWikiEntryId());
		return entry.getTitle();
	}

	public final void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder)
	{
		this.wikiEntryFinder = wikiEntryFinder;
	}

	@Override
	public String getItemUrl() throws Exception
	{
		return  "cid/"+this.getCommunityId()+"/wiki/wikiDisplay.do?id="+this.getWikiEntryId().intValue();
	}

	@Override
	public String getItemType() throws Exception
	{
		return "Wiki Entry";
	}
	
	@Override
	public Date getItemLastUpdateDate() throws Exception
	{
		return wikiEntryFinder.getWikiEntryForId(this.getWikiEntryId()).getLatestPostDate();
	}

	@Override
	public Object getItem() throws Exception
	{
		return wikiEntryFinder.getWikiEntryForId(this.getWikiEntryId());
	}
	
	@Override
	public int getSortOrder() throws Exception
	{
		return 3;
	}

	public int compareTo(Subscription o) 
	{
		return this.sortOrder-o.sortOrder;
	}
}