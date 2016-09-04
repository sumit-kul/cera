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
import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
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
import com.era.community.base.RunAsAsyncThread;
import com.era.community.base.VerifyRecaptcha;
import com.era.community.base.CecUserDetailsService.LoginFormBean;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;
import com.era.community.pers.ui.validator.UserValidator;

/**
 * @spring.bean name="/pers/fPassword.do"
 */
public class FMyPasswordAction extends AbstractFormAction 
{
	protected UserFinder userFinder;
	protected CommunityEraContextManager contextManager; 
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected RunAsAsyncThread taskExecutor;

	protected String getView()
	{
		return "pers/fMyPassword";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command) data;
		HttpServletRequest request = contextManager.getContext().getRequest(); 
		String failmsg = (String) request.getSession().getAttribute("failmsg");
		if (failmsg != null && !"".equalsIgnoreCase(failmsg)) {
			cmd.setFailMsg(failmsg);
			request.getSession().setAttribute("failmsg", "");
		}
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		final Command cmd = (Command)data;
		HttpServletRequest request = contextManager.getContext().getRequest();
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
		if(!verify) {
			throw new FormSubmitException("reCaptcha", "", "reCaptcha challenge failed");
		}
		User user = userFinder.getUserForEmailAddress(cmd.getEmailAddress());
		if (!user.isValidated()) {
			return new ModelAndView("redirect:/pers/validateResend.do");
		}

		String failmsg = (String) request.getSession().getAttribute("failmsg");
		if (failmsg != null && !"".equalsIgnoreCase(failmsg)) {
			cmd.setFailMsg("");
			request.getSession().setAttribute("failmsg", "");
		}

		String changeKey = RandomString.nextString();
		user.setChangeKey(changeKey);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		user.setDateDeactivated(ts); // deactivate date will be used to track the reset password time limit.
		user.update();
		mailForChangePassword(user, changeKey, contextManager.getContext());

		LoginFormBean bean = new LoginFormBean();
		bean.setLoginMessage("We've sent you an email that will allow you to reset your password quickly and easily.");
		contextManager.getContext().getRequest().getSession().setAttribute("cecFMyPassword", bean);
		return new ModelAndView("redirect:/login.do");
	}

	private void mailForChangePassword(final User user, final String changeKey, CommunityEraContext context) throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					if (!user.isSuperAdministrator()) {
						CommunityEraContext context = contextManager.getContext(); 
						MimeMessage message = mailSender.createMimeMessage();
						message.setFrom(new InternetAddress("support@jhapak.com"));
						message.setRecipients(Message.RecipientType.TO, user.getEmailAddress());
						message.setSubject("Change your Jhapak password");

						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date now = new Date();
						String dt = sdf.format(now);
						
						Map model = new HashMap();    
						String pChangeLink = context.getContextUrl()+"/pers/changePass.do?mid="+user.getDateRegistered().getTime()+"&key="+changeKey+"&id="+user.getId();
						model.put("pChangeLink", pChangeLink);
						model.put("croot", context.getContextUrl());
						model.put("profile", user.getProfileName());
						model.put("userName", user.getFirstName());
						model.put("cDate", dt);

						String text = VelocityEngineUtils.mergeTemplateIntoString(
								velocityEngine, "main/resources/velocity/PasswordReminder.vm", "UTF-8", model);
						message.setContent(text, "text/html");
						message.setSentDate(new Date());

						// Prepare a multipart HTML
						Multipart multipart = new MimeMultipart();
						// Prepare the HTML
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
		private String failMsg = "";
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

		public String getFailMsg() {
			return failMsg;
		}

		public void setFailMsg(String failMsg) {
			this.failMsg = failMsg;
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

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setTaskExecutor(RunAsAsyncThread taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
}