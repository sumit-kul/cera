package com.era.community.newsfeed.ui.action;

import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.AbstractIndexAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.newsfeed.dao.NewsFeed;
import com.era.community.newsfeed.dao.NewsFeedAggregator;
import com.era.community.newsfeed.dao.NewsFeedAggregatorFeature;
import com.era.community.newsfeed.dao.NewsFeedFinder;
import com.era.community.newsfeed.ui.dto.NewsFeedEntryDto;

/** 
 * @spring.bean name="/cid/[cec]/feed/newsFeeds.do"
 */
public class ShowNewsFeedsIndexAction extends AbstractIndexAction
{
	protected CommunityEraContextManager contextManager;
	protected NewsFeedAggregatorFeature newsFeature;
	protected NewsFeedFinder newsFeedFinder;

	protected String getView(IndexCommandBean bean) throws Exception
	{
		return "/news/newsIndex";
	}

	protected QueryPaginator getScroller(IndexCommandBean bean) throws Exception
	{
		Command cmd = (Command) bean;
		CommunityEraContext context = contextManager.getContext();

		NewsFeed feed = cmd.getFeed();

		QueryScroller scroller;
		if (feed==null) {
			NewsFeedAggregator aggregator = cmd.getAggregator();
			scroller = aggregator.listEntriesByDate();
		}
		else {
			scroller = feed.listEntriesByDate();
		}

		scroller.setBeanClass(RowBean.class, this);

		cmd.setNewsItemCount(scroller.readRowCount());

		return scroller;
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{  
		private int feedId = 0;
		private List feedList;

		private int newsItemCount;

		public int getNewsItemCount()
		{
			return newsItemCount;
		}

		public void setNewsItemCount(int newsItemCount)
		{
			this.newsItemCount = newsItemCount;
		}

		public Command() throws Exception
		{
			feedList = getAggregator().getFeeds();
		}

		public NewsFeedAggregator getAggregator() throws Exception
		{
			return (NewsFeedAggregator)newsFeature.getFeatureForCommunity(contextManager.getContext().getCurrentCommunity());
		}

		public NewsFeed getFeed() throws Exception
		{
			return feedId==0 ? null : newsFeedFinder.getNewsFeedForId(feedId);
		}

		public final int getFeedId()
		{
			return feedId;
		}

		public final void setFeedId(int feedId)
		{
			this.feedId = feedId;
		}

		public final List getFeedList()
		{
			return feedList;
		}

	}

	public class RowBean extends NewsFeedEntryDto
	{
		private int resultSetIndex;  


		public NewsFeed getFeed() throws Exception
		{
			return newsFeedFinder.getNewsFeedForId(getFeedId());
		}

		public int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}

		public boolean isEvenRow()
		{
			return resultSetIndex%2==0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex%2==1;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setNewsFeature(NewsFeedAggregatorFeature newsFeature)
	{
		this.newsFeature = newsFeature;
	}

	public final void setNewsFeedFinder(NewsFeedFinder newsFeedFinder)
	{
		this.newsFeedFinder = newsFeedFinder;
	}
}
