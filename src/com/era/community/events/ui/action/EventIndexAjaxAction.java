package com.era.community.events.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.events.dao.EventCalendar;
import com.era.community.events.dao.EventCalendarFeature;

/** 
 * @spring.bean name="/cid/[cec]/event/json-index.do"
 */
public class EventIndexAjaxAction extends AbstractCommandAction
{
	/*
	 * Access markers.
	 */
	public static final String REQUIRES_AUTHENTICATION = "";

	/*
	 * Injected references
	 */
	protected CommunityEraContextManager contextManager;
	protected EventCalendarFeature feature;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;

		EventCalendar forum = (EventCalendar)feature.getFeatureForCurrentCommunity();
		QueryScroller scroller = forum.listFutureEvents(0);
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
		private String Name;

		public String getUrl() 
		{
			return  "/cid/"+contextManager.getContext().getCurrentCommunity().getId()+"/event/showEventEntry.do?backlink=ref&id="+Id;
		}

		public String getTitle()
		{
			return Name;
		}

		public final int getId()
		{
			return Id;
		}
		public final void setId(int id)
		{
			Id = id;
		}

		public final String getName()
		{
			return Name;
		}

		public final void setName(String name)
		{
			Name = name;
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


	public final void setFeature(EventCalendarFeature feature)
	{
		this.feature = feature;
	}


}
