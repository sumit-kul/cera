package com.era.community.search.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.IndexedScrollerPage;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;
import support.community.lucene.search.EntitySearchScroller;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.doclib.dao.Document;
import com.era.community.events.dao.Event;
import com.era.community.forum.dao.ForumTopic;
import com.era.community.search.index.CecEntityIndex;
import com.era.community.search.index.CecEntitySearcher;
import com.era.community.wiki.dao.WikiEntry;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * This is the action that gives you a separate list of results the top 3 results.  
 * It doesn't put in a scroller - it puts in the page 1 list
 * 
 * @spring.bean name="/search/searchOnTheFly.ajx"
 */
public class SearchOnTheFlyAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager;

	private CecEntityIndex index;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		HttpServletResponse resp = contextManager.getContext().getResponse();
		JSONObject json = new JSONObject();
		JSONArray jData = new JSONArray();
		if (!(cmd.getQueryText()==null||cmd.getQueryText().trim().length()==0)){
			if (cmd.getSearchType().equals("All Content"))
				searchAllContent(cmd);
			if (cmd.getSearchType().equals("Community"))
				//searchCommunity(cmd);
			//else if (cmd.getSearchType().equals("People"))
				//searchUser(cmd);
			//else if (cmd.getSearchType().equals("Forum"))
				searchForum(cmd);
			else if (cmd.getSearchType().equals("File"))
				searchDoclib(cmd);
			else if (cmd.getSearchType().equals("Wiki"))
				searchWiki(cmd);
			/*else if (cmd.getSearchType().equals("Event"))
				searchEvents(cmd);
			else if (cmd.getSearchType().equals("Blog"))
				searchBlogs(cmd);
			else {
				cmd.setErrorText("Invalid search type ["+cmd.getSearchType()+"]");
				return null;
			}*/
			
			json = cmd.getHits().toJsonSearchContent();
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
			return null;
		}
		return new ModelAndView("/srch/search-summary");
	}
	
	private void searchAllContent(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		EntitySearchScroller scroller = searcher.search(cmd.getQueryText()+"*", CecEntitySearcher.DEFAULT_ENTITY_TYPES);
		scroller.setPageSize(16);
		IndexedScrollerPage page = scroller.readPage(1);
		cmd.setHitCount(scroller.readRowCount());
		cmd.setHits(page);
	}

	private void searchForum(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();

		EntitySearchScroller scroller = searcher.search(cmd.getQueryText(), ForumTopic.class);
		scroller.setPageSize(cmd.getSummarySize());
		IndexedScrollerPage page = scroller.readPage(1);

		cmd.setForumHitCount(page.getRowCount());
		cmd.setForumHits(page);       
	}

	private void searchDoclib(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();

		EntitySearchScroller scroller = searcher.search(cmd.getQueryText(), Document.class);
		scroller.setPageSize(cmd.getSummarySize());
		IndexedScrollerPage page = scroller.readPage(1);

		cmd.setDocumentHitCount(page.getRowCount());
		cmd.setDocumentHits(page);       
	}

	private void searchEventCalendar(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();

		EntitySearchScroller scroller = searcher.search(cmd.getQueryText(), Event.class);
		scroller.setPageSize(cmd.getSummarySize());
		IndexedScrollerPage page = scroller.readPage(1);

		cmd.setEventHitCount(page.getRowCount());
		cmd.setEventHits(page);       
	}

	private void searchWiki(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();

		EntitySearchScroller scroller = searcher.search(cmd.getQueryText(), WikiEntry.class);
		scroller.setPageSize(cmd.getSummarySize());
		IndexedScrollerPage page = scroller.readPage(1);

		cmd.setWikiHitCount(page.getRowCount());
		cmd.setWikiHits(page);       
	}

	public static class Command extends IndexCommandBeanImpl implements CommandBean
	{
		private int summarySize = 3;

		private int hitCount;
		private IndexedScrollerPage hits;

		private int communityHitCount;
		private IndexedScrollerPage communityHits;

		private int documentHitCount;
		private IndexedScrollerPage documentHits;

		private int forumHitCount;
		private IndexedScrollerPage forumHits;

		private int wikiHitCount;
		private IndexedScrollerPage wikiHits;

		private int eventHitCount;
		private IndexedScrollerPage eventHits;
		
		public int getSummarySize() {
			return summarySize;
		}
		public void setSummarySize(int summarySize) {
			this.summarySize = summarySize;
		}
		public int getHitCount() {
			return hitCount;
		}
		public void setHitCount(int hitCount) {
			this.hitCount = hitCount;
		}
		public IndexedScrollerPage getHits() {
			return hits;
		}
		public void setHits(IndexedScrollerPage hits) {
			this.hits = hits;
		}
		public int getCommunityHitCount() {
			return communityHitCount;
		}
		public void setCommunityHitCount(int communityHitCount) {
			this.communityHitCount = communityHitCount;
		}
		public IndexedScrollerPage getCommunityHits() {
			return communityHits;
		}
		public void setCommunityHits(IndexedScrollerPage communityHits) {
			this.communityHits = communityHits;
		}
		public int getDocumentHitCount() {
			return documentHitCount;
		}
		public void setDocumentHitCount(int documentHitCount) {
			this.documentHitCount = documentHitCount;
		}
		public IndexedScrollerPage getDocumentHits() {
			return documentHits;
		}
		public void setDocumentHits(IndexedScrollerPage documentHits) {
			this.documentHits = documentHits;
		}
		public int getForumHitCount() {
			return forumHitCount;
		}
		public void setForumHitCount(int forumHitCount) {
			this.forumHitCount = forumHitCount;
		}
		public IndexedScrollerPage getForumHits() {
			return forumHits;
		}
		public void setForumHits(IndexedScrollerPage forumHits) {
			this.forumHits = forumHits;
		}
		public int getWikiHitCount() {
			return wikiHitCount;
		}
		public void setWikiHitCount(int wikiHitCount) {
			this.wikiHitCount = wikiHitCount;
		}
		public IndexedScrollerPage getWikiHits() {
			return wikiHits;
		}
		public void setWikiHits(IndexedScrollerPage wikiHits) {
			this.wikiHits = wikiHits;
		}
		public int getEventHitCount() {
			return eventHitCount;
		}
		public void setEventHitCount(int eventHitCount) {
			this.eventHitCount = eventHitCount;
		}
		public IndexedScrollerPage getEventHits() {
			return eventHits;
		}
		public void setEventHits(IndexedScrollerPage eventHits) {
			this.eventHits = eventHits;
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