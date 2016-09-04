package com.era.community.search.ui.action;

import support.community.database.QueryPaginator;
import support.community.framework.AbstractIndexAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;
import support.community.lucene.search.EntitySearchScroller;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogEntry;
import com.era.community.communities.dao.Community;
import com.era.community.doclib.dao.Document;
import com.era.community.events.dao.Event;
import com.era.community.forum.dao.ForumTopic;
import com.era.community.search.index.CecEntityIndex;
import com.era.community.search.index.CecEntitySearcher;
import com.era.community.wiki.dao.WikiEntry;

/**
 * This action searches within a single community.
 * 
 * @spring.bean name="/cid/[cec]/srch/search.do"
 */
public class SearchAction extends AbstractIndexAction
{   
	/* Injected references */
	private CommunityEraContextManager contextManager;

	private CecEntityIndex index;


	protected String getView(IndexCommandBean bean) throws Exception
	{
		return "/srch/search-results";
	}

	protected QueryPaginator getScroller(IndexCommandBean bean) throws Exception
	{
		Command cmd = (Command)bean;

		if (cmd.getQueryText()==null||cmd.getQueryText().trim().length()==0)
			throw new Exception("No search query has been entered>");

		searchCurrentCommunity(cmd);

		if (cmd.getSearchType().equals("frm"))
			return searchForum(cmd);
		else if (cmd.getSearchType().equals("doc"))
			return searchDoclib(cmd);
		else if (cmd.getSearchType().equals("wki"))
			return searchWiki(cmd);
		else if (cmd.getSearchType().equals("evt"))
			return searchEvents(cmd);
		else if (cmd.getSearchType().equals("blg"))
			return searchBlogs(cmd);
		else 
			throw new Exception("Invalid search type ["+cmd.getSearchType()+"]");
	}


	/*
	 * This method is getting the number of results, not getting the results.
	 */
	private void searchCurrentCommunity(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		EntitySearchScroller scroller = searcher.search(cmd.getQueryText(), getCurrentCommunity(), CecEntitySearcher.DEFAULT_ENTITY_TYPES);
		cmd.setHitCountForCommunity(scroller.readRowCount());
	}


	private QueryPaginator searchForum(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		return  searcher.search(cmd.getQueryText(), getCurrentCommunity(), ForumTopic.class);
	}

	private QueryPaginator searchDoclib(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		return searcher.search(cmd.getQueryText(), getCurrentCommunity(), Document.class);
	}

	private QueryPaginator searchWiki(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		return searcher.search(cmd.getQueryText(), getCurrentCommunity(), WikiEntry.class);
	}

	private QueryPaginator searchEvents(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		return searcher.search(cmd.getQueryText(), getCurrentCommunity(), Event.class);
	}

	private QueryPaginator searchBlogs(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		return searcher.search(cmd.getQueryText(), BlogEntry.class);
	}

	private Community getCurrentCommunity() throws Exception
	{
		return contextManager.getContext().getCurrentCommunity();
	}


	public static class Command extends IndexCommandBeanImpl implements CommandBean
	{
		private String queryText;
		private String searchType;
		private int hitCountForCommunity;

		public final String getQueryText()
		{
			return queryText;
		}
		public final void setQueryText(String queryText)
		{
			this.queryText = queryText;
		}
		public final String getSearchType()
		{
			return searchType;
		}
		public final void setSearchType(String searchType)
		{
			this.searchType = searchType;
		}
		public final String getSearchTypeText()
		{
			if (getSearchType().equals("frm"))
				return "forum";
			else if (getSearchType().equals("doc"))
				return "library";
			else if (getSearchType().equals("wki"))
				return "wiki";
			else if (getSearchType().equals("evt"))
				return "event calendar";
			else 
				return "";
		}

		public final int getHitCountForCommunity()
		{
			return hitCountForCommunity;
		}
		public final void setHitCountForCommunity(int hitCountForCommunity)
		{
			this.hitCountForCommunity = hitCountForCommunity;
		}

	}


	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setIndex(CecEntityIndex index)
	{
		this.index = index;
	}

}
