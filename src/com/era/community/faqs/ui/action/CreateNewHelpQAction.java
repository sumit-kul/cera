package com.era.community.faqs.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.AppConstants;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;

import com.era.community.faqs.dao.HelpQ;
import com.era.community.faqs.dao.HelpQFinder;
import com.era.community.faqs.dao.generated.HelpQEntity;

/**
 *  @spring.bean name="/faq/helpQCreate.do"
 */
public class CreateNewHelpQAction extends AbstractFormAction
{
	protected HelpQFinder helpQFinder;

	protected CommandValidator createValidator()
	{
		return new Validator();
	}
	
	public class Validator extends support.community.framework.CommandValidator
	{
	}

	protected String getView()
	{
		return "faq/createHelpQ";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command)data;
		cmd.setMode(AppConstants.MODE_CREATE);
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command)data;
		HelpQ helpQ = helpQFinder.newHelpQ();
		helpQ.setTitle(cmd.getTitle());
		helpQ.setParentId(cmd.getParentId());
		helpQ.setSequence(cmd.getSequence());
		helpQ.update();
		return new ModelAndView("redirect:/faq/showAllFaqs.do");
	}

	public class Command extends HelpQEntity implements CommandBean
	{
	}

	public void setHelpQFinder(HelpQFinder helpQFinder) {
		this.helpQFinder = helpQFinder;
	}
}