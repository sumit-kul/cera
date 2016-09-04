package com.era.community.pers.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.RunAsServerCallback;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.Message;
import com.era.community.pers.dao.MessageFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * Validate a new user's email addres - (i.e. that it's a genuine email address)
 * @spring.bean name="/pers/validateMe.do"
 */
public class ValidateMeAction extends AbstractCommandAction
{
	protected UserFinder userFinder;
	protected CommunityEraContextManager contextManager;
	protected DashBoardAlertFinder dashBoardAlertFinder;
	protected MessageFinder messageFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;

	protected ModelAndView handle(Object data) throws Exception    
	{
		final Command cmd = (Command)data;
		final User user = userFinder.getUserEntity(cmd.getId());
		final User cemt = userFinder.getUserForEmailAddress("cemt@jhapak.com");
		
		getRunServerAsTemplate().execute(new RunAsServerCallback() {
			public Object doInSecurityContext() throws Exception
			{
				if (cmd.getMid()!=user.getDateRegistered().getTime() || cmd.getKey() == null 
						|| "".equals(cmd.getKey()) || !cmd.getKey().equals(user.getFirstKey())) {
					cmd.setLoginMessage("Activation link is not valid any more or your account has already been activated. <br />Please continue with the login.");
					return null;
				}

				user.setValidated(true);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String dt = sdf.format(now);
				Timestamp ts = Timestamp.valueOf(dt);
				user.setDateRegistered(ts);
				user.update();

				Message msg = messageFinder.newReceivedMessage();
				msg.setFromUserId(cemt.getId());
				msg.setToUserId(user.getId());
				msg.setSubject("Welcome to Jhapak, "+user.getFullName()+"! We're glad you're here.");
				String msgBody = "Hi "+user.getFullName()+","+
				"<br />" +
				"Thanks for joining Jhapak.<br />"+
				"We hope you will enjoy learning and sharing with us.<br />";
				msg.setBody(msgBody);
				msg.setDateSent(new Date());  
				msg.setAlreadyRead(false);
				msg.setReadFlag(0);// 0 for unread
				msg.update();
				
				DashBoardAlert dashBoardAlert = dashBoardAlertFinder.newDashBoardAlert();
				dashBoardAlert.setUserId(cmd.getId());
				dashBoardAlert.setConnectionReceivedCount(0);
				dashBoardAlert.setConnectionApprovedCount(0);
				dashBoardAlert.setNotificationCount(0);
				dashBoardAlert.setLikeCount(0);
				dashBoardAlert.setMessageCount(1);
				dashBoardAlert.setProfileVisitCount(0);
				dashBoardAlert.update();

				cmd.setEmailAddress(user.getEmailAddress());
				cmd.setLoginMessage("Your account has been activated. Please continue with the login.");
				
				CommunityEraContext context = contextManager.getContext();
				MimeMessage message = mailSender.createMimeMessage();
				message.setFrom(new InternetAddress("support@jhapak.com"));
				message.setRecipients(javax.mail.Message.RecipientType.TO, user.getEmailAddress());
				message.setSubject("Welcome to Jhapak!");

				Map model = new HashMap();    
				model.put("userName", user.getFirstName());
				model.put("login", user.getEmailAddress());
				model.put("password", user.getPassword());
				model.put("croot", context.getContextUrl());
				model.put("fullName", user.getFullName());
				
				model.put("cDate", dt);
				
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "main/resources/velocity/WelcomeToCommunityEra.vm", "UTF-8", model);
				message.setContent(text, "text/html");
				message.setSentDate(new Date());
				Multipart multipart = new MimeMultipart();
				BodyPart htmlPart = new MimeBodyPart();
				htmlPart.setContent(text, "text/html");
				multipart.addBodyPart(htmlPart);            
				message.setContent(multipart);

				mailSender.send(message);
				
				return null;
			}
		});

		return new ModelAndView("login");
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int id;
		private long mid;
		private String key;

		private String emailAddress;
		private String password;
		private String loginMessage;

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

	public void setMessageFinder(MessageFinder messageFinder) {
		this.messageFinder = messageFinder;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
}