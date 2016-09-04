package com.era.community.newsfeed.ui.action;


import org.springframework.web.servlet.ModelAndView;

import support.community.database.EntityWrapper;
import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.AbstractIndexFormAction;
import support.community.framework.CommandValidator;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.newsfeed.dao.NewsFeed;
import com.era.community.newsfeed.dao.NewsFeedFinder;
import com.era.community.newsfeed.dao.generated.NewsFeedEntity;

/** 
 * 
 * List the registered users - with option to validate
 * 
 * @spring.bean name="/admin/news-feed-index.do"
 */
public class NewsFeedIndexAction extends AbstractIndexFormAction
{
	protected CommunityEraContextManager contextManager;
	protected NewsFeedFinder newsFeedFinder;

	public NewsFeedFinder getNewsFeedFinder()
	{
		return newsFeedFinder;
	}

	public void setNewsFeedFinder(NewsFeedFinder newsFeedFinder)
	{
		this.newsFeedFinder = newsFeedFinder;
	}


	protected QueryPaginator getScroller(IndexCommandBean bean) throws Exception
	{
		Command cmd = (Command) bean;
		CommunityEraContext context = contextManager.getContext();

		QueryScroller scroller = null;

		scroller = newsFeedFinder.listAllFeedsByAggregatorId();

		//  When you have the RowBean as non-static, you must use this 2nd arguement, otherwise it doesn't know what the enclosing instance should be
		scroller.setBeanClass(RowBean.class, this);
		scroller.setPageSize(15);
		return scroller;
	}

	public class RowBean extends NewsFeedEntity implements EntityWrapper

	{      
		private String communityName;

		private int resultSetIndex;

		private NewsFeed newsFeed;


		public boolean isEvenRow()
		{
			return resultSetIndex%2==0;
		}
		public boolean isOddRow()
		{
			return resultSetIndex%2==1;
		}


		public final int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public final void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}

		public void setCommunityName(String communityName)
		{
			this.communityName = communityName;
		}


		public NewsFeed getNewsFeed()
		{
			return newsFeed;
		}


		public void setNewsFeed(NewsFeed newsFeed)
		{
			this.newsFeed = newsFeed;
		}       


	}

	public  class Command extends IndexCommandBeanImpl
	{

		private int[] selectedIds;


		public int[] getSelectedIds()
		{
			return selectedIds;
		}

		public void setSelectedIds(int[] selectedIds)
		{
			this.selectedIds = selectedIds;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}


	@Override
	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	@Override
	protected String getView()
	{
		return "/admin/news-feed-index";
	}

	public class Validator extends CommandValidator 
	{        
	}


	/*
	 * Process a list of selected user ids
	 */
	@Override
	protected ModelAndView onSubmit(Object data) throws Exception
	{


		return null;
	}

}
