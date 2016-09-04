package com.era.community.communities.ui.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.config.dao.MailMessageConfig;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/cid/[cec]/members/expel-member.do"
 * TODO need to add later
 */
public class ExpelMemberAction extends AbstractFormAction
{
	protected CommunityEraContextManager contextManager;
	protected MailSender mailSender;
	protected UserFinder userFinder;     
	protected SubscriptionFinder subscriptionFinder;
	protected MailMessageConfig mailMessageConfig;

	@Override
	protected CommandValidator createValidator()
	{     
		return new Validator();
	}

	@Override
	protected String getView()
	{     
		return "comm/member-expel";
	}

	@Override
	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();

		if (context.getCurrentUser() != null) {
			Community comm = contextManager.getContext().getCurrentCommunity();
			User user = userFinder.getUserEntity(cmd.getId());
			cmd.setUserName(user.getFullName());

			/*
			 * Parameters to substitute into the body text of the Email.
			 */
			Map<String, String> params = new HashMap<String, String>(11);
			params.put("#communityName#", comm.getName());
			params.put("#userName#", user.getFullName());
			params.put("#currentUserName#", context.getCurrentUser().getFullName());
			/*
			 * Create the mail message.
			 */
			SimpleMailMessage msg = mailMessageConfig.createMailMessage("expel-member-message", params);
			cmd.setMessageText(msg.getText());
		}
	}

	@Override
	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext(); 

		if (context.getCurrentUser() == null) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

		Community comm = context.getCurrentCommunity();

		comm.getMemberList().removeMember(cmd.getId());

		// Find all the user subscriptions for this community and delete
		subscriptionFinder.deleteSubscriptionsForUserAndCommunity(cmd.getId(), context.getCurrentCommunity().getId());

		/* Send mail to expelled user */
		if (cmd.isNoMessage() == false) {
			User user = userFinder.getUserEntity(cmd.getId());
			if (!user.isSuperAdministrator()) {
				//Parameters to substitute into the body text of the Email.
				Map<String, String> params = new HashMap<String, String>(11);
				params.put("#communityName#", comm.getName());
				params.put("#userName#", user.getFullName());
				params.put("#currentUserName#", context.getCurrentUser().getFullName());

				// Create and send the mail message.
				SimpleMailMessage msg = mailMessageConfig.createMailMessage("expel-member-message", params);
				msg.setTo(user.getEmailAddress());
				msg.setText(cmd.getMessageText());
				mailSender.send(msg);    
			}
		}

		return REDIRECT_TO_BACKLINK;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private String userName;
		private int id;
		private String messageText;
		private boolean noMessage;

		public String getUserName()
		{
			return userName;
		}

		public void setUserName(String userName)
		{
			this.userName = userName;
		}

		public final int getId()
		{
			return id;
		}

		public final void setId(int id)
		{
			this.id = id;
		}

		public String getMessageText()
		{
			return messageText;
		}

		public void setMessageText(String messageText)
		{
			this.messageText = messageText;
		}

		public boolean isNoMessage()
		{
			return noMessage;
		}

		public void setNoMessage(boolean noMessage)
		{
			this.noMessage = noMessage;
		}

	}

	public class Validator extends CommandValidator 
	{       
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public final void setMailMessageConfig(MailMessageConfig mailMessageConfig)
	{
		this.mailMessageConfig = mailMessageConfig;
	}

}
