package com.era.community.pers.ui.action;

import java.io.Writer;
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
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.ContactDto;

/**
 * 
 * This action is called from a Search Result Page and adds the person to the current user's My Contacts list
 * 
 * @spring.bean name="/pers/addConnection.ajx"
 */
public class AddConnectionAction extends AbstractCommandAction
{
	protected UserFinder userFinder;
	protected ContactFinder contactFinder;
	protected DashBoardAlertFinder dashBoardAlertFinder;
	protected CommunityEraContextManager contextManager;
	protected RunAsAsyncThread taskExecutor;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;

	protected ModelAndView handle(Object data) throws Exception    
	{

		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();

		if (currentUser == null) {
			String reqUrl = context.getRequestUrl();

			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

		Command cmd = (Command)data;
		String returnString = "";
		try {
			User cuser = userFinder.getUserEntity(cmd.getId());

			Contact contact = contactFinder.newContact();
			contact.setOwningUserId(currentUser.getId());
			contact.setContactUserId(cmd.getId());
			contact.setStatus(0);
			contact.update();

			// update the dash board for recipients of this message
			DashBoardAlert dashBoardAlert;
			try {
				dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(cmd.getId());
			} catch (ElementNotFoundException e) {
				dashBoardAlert = dashBoardAlertFinder.newDashBoardAlert();
				dashBoardAlert.setUserId(cmd.getId());
				dashBoardAlert.setConnectionReceivedCount(1);
			}
			dashBoardAlert.setConnectionReceivedCount(dashBoardAlert.getConnectionReceivedCount() + 1);
			dashBoardAlert.update();

			mailForConnectionRequest(contact, currentUser, cuser);
			
			returnString = "<a class='buttonSecondary normalTip' onClick='updateConnection("+cmd.getId()+", "+contact.getId()+", "+1+", \""+cmd.getUserName()+"\")' href='javascript:void(0);' " +
			"title='Cancel connection request to "+cmd.getUserName()+"' >Cancel Request</a>";

		} catch (ElementNotFoundException e) {
		}
		
		HttpServletResponse resp = contextManager.getContext().getResponse();
		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write(returnString);
		out.close();
		return null;
	}

	private void mailForConnectionRequest(final Contact contact, final User currentUser, final User cUser) throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					if (!currentUser.isSuperAdministrator()) {
						CommunityEraContext context = contextManager.getContext();
						MimeMessage message = mailSender.createMimeMessage();
						message.setFrom(new InternetAddress("support@jhapak.com"));
						message.setRecipients(javax.mail.Message.RecipientType.TO, cUser.getEmailAddress());
						message.setSubject(cUser.getFirstName()+", please add me to your Jhapak network");

						Map model = new HashMap();    
						String sLink = context.getContextUrl() + "/pers/acceptrequest.do?id=" + contact.getId()+"&oid="+currentUser.getId()+"&cid="+cUser.getId();
						String pLink = context.getContextUrl() + "/pers/connectionResult.do?id="+currentUser.getId();
						model.put("userImgURL", context.getContextUrl()+"/pers/userPhoto.img?id="+currentUser.getId());
						model.put("contactLink", sLink);
						model.put("cName", cUser.getFirstName());
						model.put("userName", currentUser.getFullName());
						model.put("about", "");
						model.put("pLink", pLink);
						model.put("croot", context.getContextUrl());
						model.put("cEmail", cUser.getEmailAddress());
						model.put("cUnsubscribe", context.getContextUrl()+"/pers/unsubscribeMe.do?mid="+cUser.getId()+"&key="+cUser.getFirstKey()+"&type=crequest");
						model.put("mailPreferences", context.getContextUrl()+"/pers/manageSubscriptions.do");
						model.put("cPrivacy", context.getContextUrl()+"/privacy.do");
						model.put("cHelp", context.getContextUrl()+"/help.do");
						model.put("cFeedback", context.getContextUrl()+"/feedback.do");
						SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
						Date now = new Date();
						String dt = sdf.format(now);
						model.put("cDate", dt);

						String text = VelocityEngineUtils.mergeTemplateIntoString(
								velocityEngine, "main/resources/velocity/NewConnectionRequest.vm", "UTF-8", model);
						message.setContent(text, "text/html");
						message.setSentDate(new Date());

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

	public static class Command extends ContactDto implements CommandBean
	{
		private String userName;
		private int userId;

		public String getUserName()
		{
			return userName;
		}

		public void setUserName(String userName)
		{
			this.userName = userName;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setContactFinder(ContactFinder contactFinder)
	{
		this.contactFinder = contactFinder;
	}

	public void setDashBoardAlertFinder(DashBoardAlertFinder dashBoardAlertFinder) {
		this.dashBoardAlertFinder = dashBoardAlertFinder;
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