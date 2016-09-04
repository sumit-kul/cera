package com.era.community.pers.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import net.tanesha.recaptcha.ReCaptchaImpl;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.CommandValidator;
import support.community.framework.FormSubmitException;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.RandomString;
import com.era.community.base.VerifyRecaptcha;
import com.era.community.pers.dao.ExtraInfoUser;
import com.era.community.pers.dao.ExtraInfoUserFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;
import com.era.community.pers.ui.validator.UserValidator;

/**
 * @spring.bean name="/pers/registerMe.do"
 * 
 * New user registration action.
 * 
 */
public class RegisterMeAction extends AbstractFormAction
{
	protected CommunityEraContextManager contextManager;
	protected UserFinder userFinder;
	protected ReCaptchaImpl recaptcha;
	protected TaskExecutor taskExecutor;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	private ExtraInfoUserFinder extraInfoUserFinder;

	protected String getView()
	{
		return "pers/registerMe";
	}

	protected void onDisplay(Object data) throws Exception
	{
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		final Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		String gRecaptchaResponse = context.getRequest().getParameter("g-recaptcha-response");
		boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);

		if(!verify) {
			throw new FormSubmitException("reCaptcha", "", "reCaptcha challenge failed");
		}
		
		final User user = userFinder.newUser();
		String renKey = RandomString.nextString();
		cmd.copyNonNullPropertiesTo(user);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		user.setDateRegistered(ts);
		user.setModified(ts);
		user.setFirstKey(renKey);
		user.setMsgAlert(true);
		if (user.getProfileName() == null || user.getProfileName().equals("")) {
			user.setProfileName(RandomString.nextShortString());
		}
		user.update();
		
		ExtraInfoUser extraInfoUser = extraInfoUserFinder.newExtraInfoUser();
		extraInfoUser.setUserId(user.getId());
		extraInfoUser.update();

		mailForRegistration(user, renKey);

		return new ModelAndView("redirect:/pers/registerConfirm.do");
	}

	private void mailForRegistration(final User user, final String renKey) throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					if (!user.isSuperAdministrator()) {
						CommunityEraContext context = contextManager.getContext();
						MimeMessage message = mailSender.createMimeMessage();
						message.setFrom(new InternetAddress("support@jhapak.com"));
						message.setRecipients(Message.RecipientType.TO, user.getEmailAddress());
						message.setSubject(user.getFirstName() +", confirm your registration with Jhapak");

						Map model = new HashMap();    
						String activationLink = context.getContextUrl()+"/pers/validateMe.do?mid="+user.getDateRegistered().getTime()+"&key="+renKey+"&id="+user.getId();
						model.put("activationLink", activationLink);
						model.put("userName", user.getFirstName());
						model.put("login", user.getEmailAddress());
						model.put("croot", context.getContextUrl());
						model.put("cEmail", user.getEmailAddress());
						model.put("cUnsubscribe", context.getContextUrl()+"/pers/unsubscribeMe.do?mid="+user.getEmailAddress());
						model.put("mailPreferences", context.getContextUrl()+"/pers/manageSubscriptions.do");
						model.put("cPrivacy", context.getContextUrl()+"/privacy.do");
						model.put("cHelp", context.getContextUrl()+"/help.do");
						model.put("cFeedback", context.getContextUrl()+"/feedback.do");
						SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
						Date now = new Date();
						String dt = sdf.format(now);
						model.put("cDate", dt);

						// Prepare a multipart HTML
						String text = VelocityEngineUtils.mergeTemplateIntoString(
								velocityEngine, "main/resources/velocity/RegisterMe.vm", "UTF-8", model);
						message.setContent(text, "text/html");
						message.setSentDate(new Date());
						BodyPart htmlPart = new MimeBodyPart();
						htmlPart.setContent(text, "text/html");

						Multipart multipart = new MimeMultipart();
						multipart.addBodyPart(htmlPart);            
						message.setContent(multipart);

						mailSender.send(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	public static class Command extends UserDto implements CommandBean
	{
		private String confirmPassword;
		private String confirmEmailAddress;
		private String reCaptcha;

		public final String getConfirmPassword()
		{
			return confirmPassword;
		}

		public final void setConfirmPassword(String confirmPassword)
		{
			this.confirmPassword = confirmPassword;
		}

		public final String getConfirmEmailAddress()
		{
			return confirmEmailAddress;
		}

		public final void setConfirmEmailAddress(String confirmEmailAddress)
		{
			this.confirmEmailAddress = confirmEmailAddress;
		}

		public String getReCaptcha() {
			return reCaptcha;
		}

		public void setReCaptcha(String reCaptcha) {
			this.reCaptcha = reCaptcha;
		}
	}

	public class Validator extends UserValidator
	{
		/* You only need the bean (data) occasionally */
		public String validateEmailAddress(Object value, CommandBeanImpl data) throws Exception
		{
			/*
			 * Check for valid email address format
			 */
			String err = super.validateEmailAddress(value, data);
			if (err != null)
				return err;

			/*
			 * Check that the user email address is not already registered
			 */
			try {
				userFinder.getUserForEmailAddress(value.toString());
				return "Email address " + value + " is already registered";
			} catch (ElementNotFoundException e) { }

			/*Command cmd = (Command) data;
			if (cmd.hasRequestDataFor("emailAddress")) {
				if (!cmd.getEmailAddress().equals(cmd.getConfirmEmailAddress()))
					return "Email Address and confirmation do not match";
			}*/
			/* Passed validation, return null, return 'required' */
			return null;
		}

		public String validatePassword(Object value, CommandBeanImpl data) throws Exception
		{
			String err = super.validatePassword(value, data);
			if (err != null)
				return err;

			/*Command cmd = (Command) data;
			if (cmd.hasRequestDataFor("password")) {
				if (!cmd.getPassword().equals(cmd.getConfirmPassword()))
					return "Password and confirmation do not match";
			}*/

			return null;
		}

		public String validateFirstName(Object value, CommandBeanImpl data) throws Exception
		{ 
			String err = super.validateFirstName(value, data);
			String name = value.toString();
			if (err != null)
				return err;

			for (int i = 0; i < name.length(); i++) {

				//If we find a digit character we return message.
				if (Character.isDigit(name.charAt(i)))
					return "Please enter a valid first name";;
			}

			return null;
		}

		public String validateLastName(Object value, CommandBeanImpl data) throws Exception
		{
			String err = super.validateLastName(value, data);
			String name = value.toString();
			if (err != null)
				return err;

			for (int i = 0; i < name.length(); i++) {

				//If we find a digit character we return message.
				if (Character.isDigit(name.charAt(i)))
					return "Please enter a valid last name";;
			}

			return null;
		}
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setRecaptcha(ReCaptchaImpl recaptcha) {
		this.recaptcha = recaptcha;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setExtraInfoUserFinder(ExtraInfoUserFinder extraInfoUserFinder) {
		this.extraInfoUserFinder = extraInfoUserFinder;
	}
}