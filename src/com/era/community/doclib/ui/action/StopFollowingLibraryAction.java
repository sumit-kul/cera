package com.era.community.doclib.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.monitor.dao.DocLibSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.monitor.ui.dto.SubscribeDto;

/**
 * @spring.bean name="/cid/[cec]/library/stopFollowingLibrary.ajx"
 */
public class StopFollowingLibraryAction extends AbstractCommandAction
{
	protected SubscriptionFinder subscriptionFinder;
	protected CommunityEraContextManager contextManager;
	
	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		String returnString = "";
		if (context.getCurrentUser() == null) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}
		int currentUserId = context.getCurrentUser().getId();
		Command cmd = (Command) data;
		try {
			DocLibSubscription subs = subscriptionFinder.getDocLibSubscriptionForUser(cmd.getId(), currentUserId);
			subs.delete();
			returnString = "<a onclick='subscribeDocLib("+cmd.getId()+");' href='javascript:void(0);' class='normalTip'" +
			" title='Send me email alerts when new documents are posted to this library'>Follow this Library</a>";
		} catch (ElementNotFoundException e) {
		}
		HttpServletResponse resp = contextManager.getContext().getResponse();
		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write(returnString);
		out.close();
		return null;
	}

	public static class Command extends SubscribeDto implements CommandBean
	{
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}
}