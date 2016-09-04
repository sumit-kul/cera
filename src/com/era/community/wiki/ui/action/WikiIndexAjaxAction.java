package com.era.community.wiki.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.wiki.dao.Wiki;
import com.era.community.wiki.dao.WikiFeature;

/** 
 * @spring.bean name="/cid/[cec]/wiki/json-index.do"
 */
public class WikiIndexAjaxAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected WikiFeature feature;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;

		Wiki forum = (Wiki)feature.getFeatureForCurrentCommunity();
		QueryScroller scroller = forum.listEntriesByUpdateDate();
		scroller.setBeanClass(RowBean.class, this);
		scroller.setPageSize(cmd.getPageSize());
		IndexedScrollerPage page = scroller.readPage(cmd.getPageNumber());

		HttpServletResponse resp = contextManager.getContext().getResponse();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(page.toJsonString());
		out.close();

		return null;
	}

	public class RowBean 
	{
		private int Id;
		private int EntryId;
		private String Title;

		public String getUrl() 
		{
			return  "/cid/"+contextManager.getContext().getCurrentCommunity().getId()+"/wiki/wikiDisplay.do?backlink=ref&entryId="+EntryId;
		}

		public final int getId()
		{
			return Id;
		}
		public final void setId(int id)
		{
			Id = id;
		}

		public final int getEntryId()
		{
			return EntryId;
		}

		public final void setEntryId(int entryId)
		{
			EntryId = entryId;
		}

		public final String getTitle()
		{
			return Title;
		}

		public final void setTitle(String title)
		{
			Title = title;
		}

	}

	public class Command extends CommandBeanImpl implements CommandBean 
	{
		private int pageNumber;
		private int pageSize = 10;

		public final int getPageNumber()
		{
			return pageNumber;
		}
		public final void setPageNumber(int pageNum)
		{
			this.pageNumber = pageNum;
		}
		public final int getPageSize()
		{
			return pageSize;
		}
		public final void setPageSize(int pageSize)
		{
			this.pageSize = pageSize;
		}
	}


	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}


	public final void setFeature(WikiFeature feature)
	{
		this.feature = feature;
	}


}
