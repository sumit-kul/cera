package com.era.community.pers.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.CommandValidator;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/pers/unsubscribeMe.do"
 */
public class UnSubscribeMeAction extends AbstractFormAction
{
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;
	protected UserFinder userFinder;
	
	protected ModelAndView onSubmit(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command) data;
		try {
			if ("new".equals(cmd.getType())) {
				User user = userFinder.getUserEntity(cmd.getMid());
				if (cmd.getKey() != null && cmd.getKey().equals(user.getFirstKey())) {
					if (user.isValidated()) {
						// user already validated
					} else {
						//user.delete();
						cmd.setReturnString("You have been successfully unsubscribed");
					}
				} else {
					return new ModelAndView("pageNotFound");
				}
			}
		} catch (ElementNotFoundException e) {
			return new ModelAndView("pageNotFound");
		}
		return new ModelAndView("/pers/unsubscribeMe");
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int mid;
		private String key;
		private String type;
		private String returnString;

		public int getMid() {
			return mid;
		}
		public void setMid(int mid) {
			this.mid = mid;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getReturnString() {
			return returnString;
		}
		public void setReturnString(String returnString) {
			this.returnString = returnString;
		}
	}
	
	protected String getView()
	{
		return "pers/unsubscribeMe";
	}

	protected void onDisplay(Object data) throws Exception
	{
	}
	
	protected CommandValidator createValidator()
	{
		return new Validator();
	}
	
	public class Validator extends CommandValidator {
	}

	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}
}