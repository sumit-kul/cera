package com.era.community.monitor.dao;

import java.util.Date;

import com.era.community.wiki.dao.WikiFinder;

public class WikiSubscription extends Subscription
{
	protected Integer WikiId  ;
	protected WikiFinder wikiFinder;

	public Integer getWikiId()
	{
		return WikiId;
	}

	public void setWikiId(Integer wikiId)
	{
		WikiId = wikiId;
	}

	@Override
	public String getItemName() throws Exception
	{
		return "Wiki";
	}

	@Override
	public String getItemUrl() throws Exception
	{
		return  "cid/"+this.getCommunityId()+"/wiki/showWikiEntries.do";
	}

	@Override
	public String getItemType() throws Exception
	{
		return "Community Wiki";
	}

	public final void setWikiFinder(WikiFinder wikiFinder)
	{
		this.wikiFinder = wikiFinder;
	}

	@Override
	public Date getItemLastUpdateDate() throws Exception
	{
		return wikiFinder.getWikiForId(getWikiId()).getLatestPostDate();
	}

	@Override
	public Object getItem() throws Exception
	{
		return wikiFinder.getWikiForId(this.getWikiId());
	}

	@Override
	public int getSortOrder() throws Exception
	{
		return 4;
	}

	public int compareTo(Subscription o) 
	{
		return this.sortOrder-o.sortOrder;
	}
}