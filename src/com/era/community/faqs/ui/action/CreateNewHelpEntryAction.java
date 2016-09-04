package com.era.community.faqs.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.AppConstants;
import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;

import com.era.community.faqs.dao.HelpEntry;
import com.era.community.faqs.dao.HelpEntryFinder;
import com.era.community.faqs.dao.generated.HelpEntryEntity;

/**
 *  @spring.bean name="/faq/helpEntryCreate.do"
 */
public class CreateNewHelpEntryAction extends AbstractFormAction
{
	protected HelpEntryFinder helpEntryFinder;

	protected CommandValidator createValidator()
	{
		return new Validator();
	}
	
	public class Validator extends support.community.framework.CommandValidator
	{
	}

	protected String getView()
	{
		return "faq/createHelpEntry";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command)data;
		if ("e".equals(cmd.getMode())) {
			try {
				HelpEntry helpEntry = helpEntryFinder.getHelpEntryForId(cmd.getId());
				cmd.setAnswer(helpEntry.getAnswer());
				cmd.setQuestion(helpEntry.getQuestion());
				cmd.setId(helpEntry.getId());
				cmd.setParentId(helpEntry.getParentId());
				cmd.setSequence(helpEntry.getSequence());
			} catch (ElementNotFoundException e) {
			}
		} else {
			cmd.setMode(AppConstants.MODE_CREATE);
		}
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command)data;
		HelpEntry helpEntry = null;
		if ("e".equals(cmd.getMode())) {
			try {
				helpEntry = helpEntryFinder.getHelpEntryForId(cmd.getId());
			} catch (ElementNotFoundException e) {
				return new ModelAndView("/pageNotFound");
			}
		} else {
			helpEntry = helpEntryFinder.newHelpEntry();
		}
		helpEntry.setQuestion(cmd.getQuestion());
		helpEntry.setAnswer(cmd.getAnswer());
		helpEntry.setParentId(cmd.getParentId());
		helpEntry.setSequence(cmd.getSequence());
		helpEntry.update();

		return new ModelAndView("redirect:/help.do");
	}

	public class Command extends HelpEntryEntity implements CommandBean
	{
	}

	public void setHelpEntryFinder(HelpEntryFinder helpEntryFinder) {
		this.helpEntryFinder = helpEntryFinder;
	}
}