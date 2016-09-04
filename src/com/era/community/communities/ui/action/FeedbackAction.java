package com.era.community.communities.ui.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.LinkBuilderContext;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.Feedback;
import com.era.community.communities.dao.FeedbackFinder;
import com.era.community.communities.ui.dto.FeedbackDto;
import com.era.community.communities.ui.validator.FeedbackValidator;
import com.era.community.config.dao.MailMessageConfig;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 *  @spring.bean name="/cid/[cec]/comm/feedback.do" 
 *  TODO need to add later
 */
public class FeedbackAction extends AbstractFormAction
{
	protected MailSender mailSender;
	protected CommunityEraContextManager contextManager;
	protected UserFinder userFinder;
	protected FeedbackFinder feedbackFinder;
	protected MailMessageConfig mailMessageConfig;
	String url;

	protected String getView()
	{
		return "comm/feedback-submit";
	}

	protected void onDisplay(Object data) throws Exception
	{
	}

	protected Map referenceData(Object data) throws Exception
	{
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();
		LinkBuilderContext linkBuilder = context.getLinkBuilder();

		return new HashMap();
	}

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

		if(cmd.getRequestUrl() == null)
		{
			Feedback feedback = feedbackFinder.newFeedback();

			feedback.setSubject(cmd.getSubject());
			feedback.setBody(cmd.getBody()); 
			feedback.setCommunityId(context.getCurrentCommunity().getId());

			feedback.setSubmitterId(context.getCurrentUser().getId());
			feedback.setSubject(context.getCurrentCommunity().getName());
			feedback.update();

			mailAdmins(feedback, context, url);
			mailSubmitter(feedback, context);

			return new ModelAndView("comm/feedback-submit-confirm");
		} else
		{
			url = cmd.getRequestUrl();
			return new ModelAndView("comm/feedback-submit","command", cmd);
		}

	}

	/*
	 * Email the community admins to alert that a new feedback has been posted
	 */
	@SuppressWarnings("unchecked")
	private void mailAdmins(Feedback feedback, CommunityEraContext context, String url) throws Exception
	{

		int uid = context.getCurrentUser().getId();

		Community comm = context.getCurrentCommunity();

		String admins[] = comm.getAdminMemberEmailAddresses();
		if (admins.length==0)
			return;

		User submitter = userFinder.getUserEntity(uid);
		/*
            List <User>sysAdmins = userFinder.getSysAdminUsers();
            ArrayList<String> sysAdminAddresses= new ArrayList<String>();
            if (sysAdminAddresses.contains("boot@temp.com"))
                sysAdminAddresses.remove(sysAdminAddresses.indexOf("boot@temp.com"));

            for (User u  : sysAdmins)
            {
                if (u.getEmailAddress().equals("boot@temp.com")==false)
               sysAdminAddresses.add(u.getEmailAddress());
            }

		 */

		String[] superAdmins = new String[3];

		superAdmins[0] = "support@jhapak.com";

		/*
		 * Parameters to substitute into the body text of the Email.
		 */
		Map<String, String> params = new HashMap<String, String>(11);

		params.put("#communityName#", comm.getName());
		params.put("#communityNameUC#", comm.getName().toUpperCase());
		params.put("#submitterName#", submitter.getFullName());
		params.put("#submitterEmail#", submitter.getEmailAddress());
		params.put("#url#", url);
		params.put("#feedback#", feedback.getBody());

		/*
		 * Create and send the mail message.
		 */
		SimpleMailMessage msg = mailMessageConfig.createMailMessage("admin-feedback-message", params);
		msg.setTo(admins);
		try {
			mailSender.send(msg);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}


	/*
	 * Email the submitter so they have a copy of the message
	 */
	private void mailSubmitter(Feedback feedback, CommunityEraContext context) throws Exception
	{

		int uid = context.getCurrentUser().getId();

		Community comm = context.getCurrentCommunity();

		User submitter = userFinder.getUserEntity(uid);

		/*
		 * Parameters to substitute into the body text of the Email.
		 */
		Map<String, String> params = new HashMap<String, String>(11);

		/*
		 * Create and send the mail message.
		 */
		SimpleMailMessage msg = mailMessageConfig.createMailMessage("user-feedback-message", params);
		msg.setTo(submitter.getEmailAddress());            
		try {
			if (!submitter.isSuperAdministrator()) {
				mailSender.send(msg);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

	}

	public static class Command extends FeedbackDto implements CommandBean
	{
		boolean anon;
		String requestUrl;

		public final String getRequestUrl()
		{
			return requestUrl;
		}

		public final void setRequestUrl(String requestUrl)
		{
			this.requestUrl = requestUrl;
		}
	}

	public class Validator extends FeedbackValidator
	{

	}



	public MailSender getMailSender()
	{
		return mailSender;
	}
	public void setMailSender(MailSender mailSender)
	{
		this.mailSender = mailSender;
	}
	public CommunityEraContextManager getContextHolder()
	{
		return contextManager;
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	public UserFinder getUserFinder()
	{
		return userFinder;
	}
	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public  final void setFeedbackFinder(FeedbackFinder feedbackFinder)
	{
		this.feedbackFinder = feedbackFinder;
	}

	public final void setMailMessageConfig(MailMessageConfig mailMessageConfig)
	{
		this.mailMessageConfig = mailMessageConfig;
	}

}
