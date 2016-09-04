package com.era.community.pers.ui.action;

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
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;
import com.era.community.pers.ui.validator.UserValidator;

/**
 * @spring.bean name="/pers/validateResend.do"
 */
public class ValidateResendAction extends AbstractFormAction 
{
	protected UserFinder userFinder;
	protected CommunityEraContextManager contextManager; 
	protected ReCaptchaImpl recaptcha;
	protected TaskExecutor taskExecutor;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;

	protected String getView()
	{
		return "pers/validateResend";
	}

	protected void onDisplay(Object data) throws Exception
	{
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		final Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();
		
		String gRecaptchaResponse = context.getRequest().getParameter("g-recaptcha-response");
		boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);

		if(!verify) {
			throw new FormSubmitException("reCaptcha", "", "reCaptcha challenge failed");
		}
		
		User user = userFinder.getUserForEmailAddress(cmd.getEmailAddress());
		if (user.isValidated()) {
			return new ModelAndView("redirect:/login.do");
		}
		String changeKey = RandomString.nextString();
		user.setChangeKey(changeKey);
		user.update();

		mailForValidateResend(user);

		return new ModelAndView("redirect:/pers/registerConfirm.do");
	}

	private void mailForValidateResend(final User user) throws Exception
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
						String activationLink = context.getContextUrl()+"/pers/validateMe.do?mid="+user.getDateRegistered().getTime()+"&key="+user.getFirstKey()+"&id="+user.getId();
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

						String text = VelocityEngineUtils.mergeTemplateIntoString(
								velocityEngine, "main/resources/velocity/ValidateResend.vm", "UTF-8", model);
						message.setContent(text, "text/html");
						message.setSentDate(new Date());

						// Prepare a multipart HTML
						Multipart multipart = new MimeMultipart();
						BodyPart htmlPart = new MimeBodyPart();
						htmlPart.setContent(text, "text/html");

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

	protected Map referenceData(Object data) throws Exception
	{
		return new HashMap();
	}

	public class Validator extends UserValidator
	{
		public String validateEmailAddress(Object value, CommandBeanImpl data) throws Exception
		{
			String err = super.validateEmailAddress(value, data);
			if (err != null)
				return err;
			try {
				userFinder.getUserForEmailAddress(value.toString().trim());
				return null;
			}
			catch (ElementNotFoundException e) {
				return "Email address is not valid";
			}
		}
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	public static class Command extends UserDto implements CommandBean
	{
		private String recaptcha_challenge_field;
		private String recaptcha_response_field;
		private String reCaptcha;

		private User user = new User();

		public Command()
		{
			setMode("u");
		}

		public User getUser()
		{
			return user;   
		}

		public void setUser(User user)
		{
			this.user = user;
		}

		public String getRecaptcha_challenge_field() {
			return recaptcha_challenge_field;
		}

		public void setRecaptcha_challenge_field(String recaptcha_challenge_field) {
			this.recaptcha_challenge_field = recaptcha_challenge_field;
		}

		public String getRecaptcha_response_field() {
			return recaptcha_response_field;
		}

		public void setRecaptcha_response_field(String recaptcha_response_field) {
			this.recaptcha_response_field = recaptcha_response_field;
		}

		public String getReCaptcha() {
			return reCaptcha;
		}

		public void setReCaptcha(String reCaptcha) {
			this.reCaptcha = reCaptcha;
		}
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
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
}