package com.era.community.forum.dao;

import java.util.Date;
import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

public class ForumTopic extends ForumItem
{
	ThemeTopicLinkDao themeTopicLinkDao;
	ThemeTopicLinkFinder themeTopicLinkFinder;

	public final ThemeTopicLinkFinder getThemeTopicLinkFinder()
	{
		return themeTopicLinkFinder;
	}

	public final void setThemeTopicLinkFinder(ThemeTopicLinkFinder themeTopicLinkFinder)
	{
		this.themeTopicLinkFinder = themeTopicLinkFinder;
	}

	public final void setThemeTopicLinkDao(ThemeTopicLinkDao themeTopicLinkDao)
	{
		this.themeTopicLinkDao = themeTopicLinkDao;
	}

	public QueryScroller listResponses() throws Exception
	{
		return dao.listResponsesForTopic(this);
	}

	public Date getLastPostDate() throws Exception
	{
		QueryScroller scroller = listResponses();
		scroller.addScrollKey("Id", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_INTEGER);
		scroller.setPageSize(1);
		List list = scroller.readPage(1);
		if (list.isEmpty()) return getDatePosted();
		ForumResponse resp = (ForumResponse)list.get(0);
		return resp.getDatePosted();
	}

	public ForumTopic getTopic() throws Exception
	{
		return this;
	}

	public void linkToTheme(int themeId) throws Exception
	{
		if (themeId > 0) {
			clearThemesForTopic();
			ThemeTopicLink link = themeTopicLinkDao.newThemeTopicLink();

			link.setItemId(this.getId());
			link.setThemeId(themeId);
			link.update();
		}
	}

	public void clearThemesForTopic() throws Exception
	{
		themeTopicLinkDao.clearThemesForTopic(this.getId());
	}
}