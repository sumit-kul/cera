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

import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.RunAsServerCallback;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.CecUserDetailsService.LoginFormBean;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * Activate account via link in invitation mail.
 * @spring.bean name="/pers/activateInvitation.do"
 */
public class ActivateInvitationAction extends AbstractCommandAction
{
	protected UserFinder userFinder;
	protected CommunityEraContextManager contextManager;
	protected DashBoardAlertFinder dashBoardAlertFinder;
	protected TaskExecutor taskExecutor;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;

	protected ModelAndView handle(Object data) throws Exception    
	{
		final Command cmd = (Command)data;
		final User user;
		try {
			user = userFinder.getUserEntity(cmd.getId());
		} catch (ElementNotFoundException e) {
			return new ModelAndView("redirect:/error/pageNotFound.do");
		}

		getRunServerAsTemplate().execute(new RunAsServerCallback() {
			public Object doInSecurityContext() throws Exception
			{
				cmd.setLoginMessage("");
				if (cmd.getMid()!=user.getDateRegistered().getTime() || cmd.getKey() == null 
						|| "".equals(cmd.getKey()) || !cmd.getKey().equals(user.getFirstKey())) {
					cmd.setLoginMessage("Acceptance link is not valid any more or your account has already been activated. <br />Please continue with the login.");
					return null;
				}

				user.setValidated(true);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String dt = sdf.format(now);
				Timestamp ts = Timestamp.valueOf(dt);
				user.setDateRegistered(ts);
				user.update();

				DashBoardAlert dashBoardAlert;
				try {
					dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(cmd.getId());
				} catch (ElementNotFoundException ex) {
					dashBoardAlert = dashBoardAlertFinder.newDashBoardAlert();
					dashBoardAlert.setUserId(cmd.getId());
					dashBoardAlert.setConnectionReceivedCount(0);
					dashBoardAlert.setConnectionApprovedCount(0);
					dashBoardAlert.setNotificationCount(0);
					dashBoardAlert.setLikeCount(0);
					dashBoardAlert.setMessageCount(0);
					dashBoardAlert.setProfileVisitCount(0);
					dashBoardAlert.update();
				}

				cmd.setEmailAddress(user.getEmailAddress());
				cmd.setLoginMessage("Your account has been activated. Please check your mail for sign in details.");
				return null;
			}
		});

		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					if (!user.isSuperAdministrator()) {
						CommunityEraContext context = contextManager.getContext();
						MimeMessage message = mailSender.createMimeMessage();
						message.setFrom(new InternetAddress("support@jhapak.com"));
						message.setRecipients(Message.RecipientType.TO, user.getEmailAddress());
						message.setSubject("Log in details!");

						Map model = new HashMap();    
						model.put("userName", user.getFirstName());
						model.put("login", user.getEmailAddress());
						model.put("password", user.getPassword());
						model.put("croot", context.getContextUrl());
						model.put("fullName", user.getFullName());
						
						SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
						Date now = new Date();
						String dt = sdf.format(now);
						model.put("cDate", dt);

						String text = VelocityEngineUtils.mergeTemplateIntoString(
								velocityEngine, "main/resources/velocity/ActivateInvitation.vm", "UTF-8", model);
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
						
						
						MimeMessage message2 = mailSender.createMimeMessage();
						message2.setFrom(new InternetAddress("support@jhapak.com"));
						message2.setRecipients(Message.RecipientType.TO, user.getEmailAddress());
						message2.setSubject("Welcome to Jhapak!");
						
						String text2 = VelocityEngineUtils.mergeTemplateIntoString(
								velocityEngine, "main/resources/velocity/WelcomeToCommunityEra.vm", "UTF-8", model);
						message2.setContent(text2, "text/html");
						message2.setSentDate(new Date());
						Multipart multipart2 = new MimeMultipart();
						BodyPart htmlPart2 = new MimeBodyPart();
						htmlPart2.setContent(text, "text/html");
						multipart2.addBodyPart(htmlPart2);            
						message2.setContent(multipart2);

						mailSender.send(message2);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		if (cmd.getAction() != null && "googleAction".equalsIgnoreCase(cmd.getAction())) {
			return new ModelAndView("loginWithSn");
		} else {
			if (contextManager.getContext().getCurrentUser() != null) {
				LoginFormBean bean = new LoginFormBean();
				bean.setLoginMessage("Your account has been activated. Please check your mail for sign in details.");
				contextManager.getContext().getRequest().getSession().setAttribute("sucsFMyPassword", bean);
				return new ModelAndView("redirect:/jlogout.do");
			} 
			return new ModelAndView("login");
		}
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int id;
		private long mid;
		private String key;

		private String emailAddress;
		private String password;
		private String loginMessage;
		private String action;

		public final int getId()
		{
			return id;
		}
		public final void setId(int id)
		{
			this.id = id;
		}
		public final String getEmailAddress()
		{
			return emailAddress;
		}
		public final void setEmailAddress(String emailAddress)
		{
			this.emailAddress = emailAddress;
		}
		public final String getPassword()
		{
			return password;
		}
		public final void setPassword(String password)
		{
			this.password = password;
		}
		public final String getLoginMessage()
		{
			return loginMessage;
		}
		public final void setLoginMessage(String loginMessage)
		{
			this.loginMessage = loginMessage;
		}
		public long getMid() {
			return mid;
		}
		public void setMid(long mid) {
			this.mid = mid;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
	}


	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setDashBoardAlertFinder(DashBoardAlertFinder dashBoardAlertFinder) {
		this.dashBoardAlertFinder = dashBoardAlertFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
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