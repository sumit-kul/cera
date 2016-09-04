package com.era.community.pers.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;

/**
 * @spring.bean name="/pers/showCalendar.do"
 */
public class ShowCalendarAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		cmd.setUser(context.getCurrentUser());
		cmd.setId(cmd.getUser().getId());
		return new ModelAndView("/pers/showCalendar");
	}

	
	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private User user;
		private int id;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
}