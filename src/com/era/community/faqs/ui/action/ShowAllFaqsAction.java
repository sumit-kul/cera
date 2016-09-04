package com.era.community.faqs.ui.action;

import java.util.ArrayList;
import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.AbstractIndexAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.faqs.dao.FaqFinder;
import com.era.community.faqs.ui.dto.FaqDto;

/**
 * @spring.bean name="/faq/showAllFaqs.do"
 */
public class ShowAllFaqsAction extends AbstractIndexAction
{
	protected FaqFinder faqFinder;

	protected String getView(IndexCommandBean bean) throws Exception
	{
		return "faq/allFaqs";
	}

	protected QueryPaginator getScroller(IndexCommandBean bean) throws Exception
	{
		QueryScroller scroller = faqFinder.listAllFaqs();
		scroller.setBeanClass(RowBean.class);
		return scroller;
	}

	public static class RowBean extends FaqDto
	{
	}

	public class Command extends IndexCommandBeanImpl
	{
		private List faqList = new ArrayList();

		public List getFaqList() throws Exception
		{
			QueryScroller scroller = faqFinder.listAllFaqs();
			scroller.setBeanClass(FaqDto.class);
			scroller.setPageSize(999);
			return (scroller.readPage(1));
		}

		public void setFaqList(List faqList)
		{
			this.faqList = faqList;
		}
	}

	public final void setFaqFinder(FaqFinder faqFinder)
	{
		this.faqFinder = faqFinder;
	}
}
