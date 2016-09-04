package com.era.community.faqs.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.faqs.dao.Faq;
import com.era.community.faqs.dao.FaqFinder;
import com.era.community.faqs.ui.dto.FaqDto;


/**
 * @spring.bean name="/faq/file-remove.do"
 */
public class FaqFileRemoveAction extends AbstractCommandAction
{
	protected FaqFinder faqFinder;
	CommunityEraContextManager contextManager;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;

		Faq faq = faqFinder.getFaqForId(cmd.getId());
		if ( faq.isFilePresent() ) {
			faq.clearFile();
		}

		CommunityEraContext context = contextManager.getContext();
		return new ModelAndView("redirect:" + "/faq/edit-item.do?id=" +faq.getId());
	}

	public static class Command extends FaqDto implements CommandBean
	{
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final FaqFinder getFaqFinder()
	{
		return faqFinder;
	}

	public final void setFaqFinder(FaqFinder faqFinder)
	{
		this.faqFinder = faqFinder;
	}
}