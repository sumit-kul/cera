package com.era.community.faqs.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.faqs.dao.Faq;
import com.era.community.faqs.dao.FaqFinder;
import com.era.community.faqs.ui.dto.FaqDto;

/**
 *  @spring.bean name="/faq/faqRemove.do"
 */
public class DeleteFaqAction extends AbstractCommandAction
{
	protected FaqFinder faqFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		Faq faq = faqFinder.getFaqForId(cmd.getId());
		faq.delete();
		return new ModelAndView("redirect:/faq/showAllFaqs.do");
	}

	public static class Command extends FaqDto implements CommandBean
	{
	}

	public final void setFaqFinder(FaqFinder faqFinder)
	{
		this.faqFinder = faqFinder;
	}
}