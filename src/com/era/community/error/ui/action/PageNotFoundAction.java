package com.era.community.error.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;

/**
 * @spring.bean name="/error/pageNotFound.do"
 */
public class PageNotFoundAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		return new ModelAndView("/pageNotFound");		
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
}