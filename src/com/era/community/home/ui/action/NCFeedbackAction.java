package com.era.community.home.ui.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.CommandValidator;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.communities.ui.validator.FeedbackValidator;
import com.era.community.home.dao.NonCommunityFeedback;
import com.era.community.home.dao.NonCommunityFeedbackFinder;
import com.era.community.home.ui.dto.FeedbackDto;
import com.era.community.pers.dao.MasterData;
import com.era.community.pers.dao.MasterDataFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/feedback.do"
 */
public class NCFeedbackAction extends AbstractFormAction
{
	protected CommunityEraContextManager contextManager;
	protected UserFinder userFinder;
	protected NonCommunityFeedbackFinder feedbackFinder;
	protected MasterDataFinder masterDataFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected RunAsAsyncThread taskExecutor;

	protected String getView()
	{
		return "feedback";
	}

	protected void onDisplay(Object data) throws Exception
	{
	}

	protected Map referenceData(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() == null)
			cmd.setAnon(true);
		else
		{
			cmd.setName(context.getCurrentUser().getFullName());
			cmd.setEmailAddress(context.getCurrentUser().getEmailAddress());
		}
		return new HashMap();
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		NonCommunityFeedback feedback = feedbackFinder.newNonCommunityFeedback();

		if (context.getCurrentUser() == null) {
			cmd.setAnon(true);
			feedback.setName(cmd.getName());
			feedback.setEmailAddress(cmd.getEmailAddress()); 
		} else {
			cmd.setName(context.getCurrentUser().getFullName());
			cmd.setEmailAddress(context.getCurrentUser().getEmailAddress());
			cmd.setSubmitterId(context.getCurrentUser().getId());
			feedback.setName(context.getCurrentUser().getFullName());
			feedback.setEmailAddress(context.getCurrentUser().getEmailAddress()); 
		}

		feedback.setSubject(cmd.getSubject());
		feedback.setType(cmd.gettype());
		feedback.setBody(cmd.getBody()); 
		feedback.update();
		mailToCera(feedback, context, cmd.getName(), cmd.getEmailAddress());

		return new ModelAndView("/feedbackConfirm");
	}

	private void mailToCera(final NonCommunityFeedback feedback, final CommunityEraContext context, final String name, final String email)  throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
						boolean authenticated = false;
						if (context.getCurrentUser() != null)
							authenticated = true;

						int uid;
						User submitter=null;
						if (authenticated) {
							uid = context.getCurrentUser().getId();
							submitter = userFinder.getUserEntity(uid);
						}
						if (!submitter.isSuperAdministrator()) {
							String sname = "";
							MasterData masterData = masterDataFinder.getMasterDataForId(feedback.getType());
							Map model = new HashMap();
							if (authenticated) {
								String fromSubmitter = "from "+submitter.getFullName();
								String eMail = "(" + submitter.getEmailAddress() + ")";
								String sProfile = "Click here to view the profile of this registered user: " + context.getContextUrl()+ "pers/connectionResult.do?id="+submitter.getId();
								model.put("fromSubmitter", fromSubmitter);
								model.put("submitterName", submitter.getFullName());
								model.put("submitterEmail", eMail);
								model.put("feedback", feedback.getBody());
								model.put("category", masterData.getLabel());
								model.put("profile", sProfile);
							} else {
								sname = "name: " + name;
								String eMail = "\n\nEmail: "+email;
								model.put("fromSubmitter", "");
								model.put("submitterName", sname);
								model.put("submitterEmail", eMail);
								model.put("feedback", feedback.getBody());
								model.put("category", masterData.getLabel());
								model.put("profile", "");

							} 
							model.put("croot", context.getContextUrl());
							MimeMessage message = mailSender.createMimeMessage();
							MimeMessageHelper helper = new MimeMessageHelper(message, true);
							helper.setTo("support@jhapak.com");
							helper.setFrom(new InternetAddress(email) );
							helper.setSubject("Feedback: "+feedback.getSubject());
							helper.setSentDate(new Date());

							String text = VelocityEngineUtils.mergeTemplateIntoString(
									velocityEngine, "main/resources/velocity/Feedback.vm", "UTF-8", model);
							helper.setText(text, true);
							mailSender.send(message);
						}
					} catch (Exception e) {
						logger.error(e);
					}
				}
			});
		}

		public static class Command extends FeedbackDto implements CommandBean
		{
			boolean anon;

			public final boolean isAnon()
			{
				return anon;
			}

			public final void setAnon(boolean anon)
			{
				this.anon = anon;
			}
		}

		public class Validator extends FeedbackValidator
		{
			public String validateName(Object value, CommandBeanImpl data) throws Exception
			{
				Command cmd = (Command) data;
				if (cmd.anon && isNullOrWhitespace(value) ) return "Please enter Name";
				if (cmd.anon && value.toString()!=null && value.toString().trim().length()> 160)
					return ("Name cannot exceed 160 characters");

				return null;
			}

			public String validateEmailAddress(Object value, CommandBeanImpl data) throws Exception
			{
				Command cmd = (Command) data;
				if (cmd.anon && isNullOrWhitespace(value) ) return "Please enter Email address";
				if (cmd.anon && !emailValidator.isValid(value.toString())) return "Email address is invalid";
				if (cmd.anon && value.toString()!=null && value.toString().trim().length()> 120)
					return ("Email address length cannot exceed 120 characters");
				return null;
			}
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

		public final void setFeedbackFinder(NonCommunityFeedbackFinder feedbackFinder)
		{
			this.feedbackFinder = feedbackFinder;
		}

		public void setMailSender(JavaMailSenderImpl mailSender) {
			this.mailSender = mailSender;
		}

		public void setVelocityEngine(VelocityEngine velocityEngine) {
			this.velocityEngine = velocityEngine;
		}

		public void setTaskExecutor(RunAsAsyncThread taskExecutor) {
			this.taskExecutor = taskExecutor;
		}

		public void setMasterDataFinder(MasterDataFinder masterDataFinder) {
			this.masterDataFinder = masterDataFinder;
		}
	}