package com.era.community.pers.ui.action;

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

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.Message;
import com.era.community.pers.dao.MessageFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.MessageDto;

/**
 * 
 * Send a private message to another registered user This should also alert the user to their regular email address
 * 
 * @spring.bean name="/pers/sendMessage.ajx"
 */
public class SendMessageAction extends AbstractCommandAction
{
	protected UserFinder userFinder;
	protected MessageFinder msgFinder;
	protected CommunityEraContextManager contextManager;
	protected DashBoardAlertFinder dashBoardAlertFinder;
	protected RunAsAsyncThread taskExecutor;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		String[] myJsonData = context.getRequest().getParameterValues("json[]");
		String sentToIds = "";
		for (int i = 0; i < myJsonData.length; i++) {
			sentToIds = sentToIds + myJsonData[i];
			if (i < myJsonData.length - 1) {
				sentToIds = sentToIds + ",";
			}
		}

		/* Get message we are displaying */
		if (cmd.getMsgAction() != null) {
			if (cmd.getMsgAction().equalsIgnoreCase("reply")) {
				Message msg = msgFinder.getMessageForId( cmd.getId() );
				cmd.copyPropertiesFrom(msg);
			}
		} 

		if (cmd.getMsgAction() != null) {
			if (cmd.getMsgAction().equalsIgnoreCase("share")) {
				Message msg = msgFinder.getMessageForId( cmd.getId() );
				cmd.copyPropertiesFrom(msg);
				cmd.setAddressLabel("");
				cmd.setToUserId(0);
			}
		}
		if(myJsonData != null && myJsonData.length > 0) {
			/* Create sent message copy - this will be displayed in the senders sent messages view */
			Message msg = msgFinder.newSentMessage();
			msg.setToUserId(context.getCurrentUser().getId());
			msg.setFromUserId(context.getCurrentUser().getId());
			msg.setSubject(cmd.getSubject());
			msg.setBody(cmd.getBody());
			msg.setDateSent(new Date());
			msg.setAlreadyRead(false);
			msg.update();
			msg.setAddressLabel(sentToIds);
			msg.update(); 

			/* Actually send messages */
			for (int i=0; i < myJsonData.length; ++i) {
				int toId = Integer.parseInt(myJsonData[i]);

				/* Create received message copy - this will be displayed in the recipients inbox */
				msg = msgFinder.newReceivedMessage();
				msg.setFromUserId(context.getCurrentUser().getId());
				msg.setToUserId(toId);
				msg.setSubject(cmd.getSubject());
				msg.setBody(cmd.getBody());
				msg.setDateSent(new Date());  
				msg.setAlreadyRead(false);
				msg.setReadFlag(0);// 0 for unread
				msg.update();

				// update the dash board for recipients of this message
				DashBoardAlert dashBoardAlert;
				try {
					dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(toId);
				} catch (ElementNotFoundException e) {
					dashBoardAlert = dashBoardAlertFinder.newDashBoardAlert();
					dashBoardAlert.setUserId(toId);
					dashBoardAlert.setMessageCount(1);
				}
				dashBoardAlert.setMessageCount(dashBoardAlert.getMessageCount() + 1);
				dashBoardAlert.update();

				mailToRecipient(msg, context.getCurrentUser());
			}
		}
		return null;
	}

	public static class Command extends MessageDto implements CommandBean
	{
		private int recipientCount;
		private String msgAction;

		public int getRecipientCount() {
			return recipientCount;
		}

		public void setRecipientCount(int recipientCount) {
			this.recipientCount = recipientCount;
		}

		public String getMsgAction() {
			return msgAction;
		}

		public void setMsgAction(String msgAction) {
			this.msgAction = msgAction;
		}
	}

	private void mailToRecipient(final Message copmsg, final User user) throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					/* Email the recipient to alert them to the fact they have been sent a message */
					User recipient = userFinder.getUserEntity(copmsg.getToUserId());

					/* Only send message if recipient has mail alerting switched on */
					if (recipient.isMsgAlert()) {
						if (!recipient.isSuperAdministrator()) {
							CommunityEraContext context = contextManager.getContext();
							MimeMessage message = mailSender.createMimeMessage();
							message.setFrom(new InternetAddress("support@jhapak.com"));
							message.setRecipients(javax.mail.Message.RecipientType.TO, recipient.getEmailAddress());
							message.setSubject("You have received a message from "+user.getFullName());

							Map model = new HashMap();    
							String sLink = context.getContextUrl() + "reg/messageDisplay.do?id=" + copmsg.getId();
							model.put("userImgURL", context.getContextUrl()+"/pers/userPhoto.img?id="+user.getId());
							model.put("messageLink", sLink);
							model.put("senderName", copmsg.getFromUserName());
							model.put("userName", recipient.getFullName());
							model.put("message", copmsg.getSubject());
							model.put("messageBody", copmsg.getBody());
							model.put("croot", context.getContextUrl());
							model.put("cEmail", recipient.getEmailAddress());
							model.put("cUnsubscribe", context.getContextUrl()+"/pers/unsubscribeMe.do?mid="+recipient.getId()+"&key="+recipient.getFirstKey()+"&type=msg");
							model.put("mailPreferences", context.getContextUrl()+"/pers/manageSubscriptions.do");
							model.put("cPrivacy", context.getContextUrl()+"/privacy.do");
							model.put("cHelp", context.getContextUrl()+"/help.do");
							model.put("cFeedback", context.getContextUrl()+"/feedback.do");
							SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
							Date now = new Date();
							String dt = sdf.format(now);
							model.put("cDate", dt);

							String text = VelocityEngineUtils.mergeTemplateIntoString(
									velocityEngine, "main/resources/velocity/NewMessage.vm", "UTF-8", model);
							message.setContent(text, "text/html");
							message.setSentDate(new Date());

							Multipart multipart = new MimeMultipart();
							BodyPart htmlPart = new MimeBodyPart();
							htmlPart.setContent(text, "text/html");
							multipart.addBodyPart(htmlPart);            
							message.setContent(multipart);
							mailSender.send(message);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setMsgFinder(MessageFinder msgFinder)
	{
		this.msgFinder = msgFinder;
	}

	public void setDashBoardAlertFinder(DashBoardAlertFinder dashBoardAlertFinder) {
		this.dashBoardAlertFinder = dashBoardAlertFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setTaskExecutor(RunAsAsyncThread taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
}