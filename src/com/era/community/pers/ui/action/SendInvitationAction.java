package com.era.community.pers.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.EmailValidator;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.RandomString;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/pers/sendInvitation.do"
 */
public class SendInvitationAction extends AbstractFormAction 
{
	private CommunityEraContextManager contextManager;
	protected UserFinder userFinder;
	protected ContactFinder contactFinder;
	protected DashBoardAlertFinder dashBoardAlertFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected RunAsAsyncThread taskExecutor;

	protected String getView()
	{
		return "pers/sendInvitation";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command) data;
		HttpServletRequest request = contextManager.getContext().getRequest(); 
		String succmsg = (String) request.getSession().getAttribute("succMsg");
		if (succmsg != null && !"".equalsIgnoreCase(succmsg)) {
			cmd.setSuccessMsg(succmsg);
			request.getSession().setAttribute("succMsg", "");
		}
		String failmsg = (String) request.getSession().getAttribute("failmsg");
		if (failmsg != null && !"".equalsIgnoreCase(failmsg)) {
			cmd.setFailMsg(failmsg);
			request.getSession().setAttribute("failmsg", "");
		}
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

		Command cmd = (Command) data;
		HttpServletRequest request = contextManager.getContext().getRequest(); 
		EmailValidator emailValidator = EmailValidator.getInstance();
		List<String> list = cmd.getInvitedfriendsList();
		List<String> validated = new ArrayList<String>();
		for (int n = 0; n < list.size(); n++) {
			String email = list.get(n);
			if (emailValidator.isValid(email)) {
				validated.add(email);
			}
		}
		if (validated.size() > 0) {
			String succmsg = "Your Invitation Has Been Successfully Sent To Your Friends!";
			cmd.setSuccessMsg(succmsg);
			request.getSession().setAttribute("succMsg", succmsg);
			sendInvitations(context.getCurrentUser(), validated, cmd.getMessage());
		} else {
			String failmsg = "No valid email address found!";
			cmd.setFailMsg(failmsg);
			request.getSession().setAttribute("failmsg", failmsg);
		}
		return new ModelAndView("redirect:/pers/sendInvitation.do", "command" , cmd);
	}


	private void sendInvitations(final User user, final List<String> emalis, final String msg)  throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					for (String email : emalis) {
						CommunityEraContext context = contextManager.getContext(); 
						User invitee = null;
						String name = email.split("@")[0];
						try {
							invitee = userFinder.getUserForEmailAddress(email);
							try {
								contactFinder.getContact(user.getId(), invitee.getId());
							} catch (ElementNotFoundException e) {
								Contact contact = contactFinder.newContact();
								contact.setOwningUserId(user.getId());
								contact.setContactUserId(invitee.getId());
								contact.setStatus(0);
								contact.update();
								DashBoardAlert dashBoardAlert;
								try {
									dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(invitee.getId());
									dashBoardAlert.setConnectionReceivedCount(dashBoardAlert.getConnectionReceivedCount() + 1);
									dashBoardAlert.update();
								} catch (ElementNotFoundException ex) {
									dashBoardAlert = dashBoardAlertFinder.newDashBoardAlert();
									dashBoardAlert.setUserId(invitee.getId());
									dashBoardAlert.setConnectionReceivedCount(1);
									dashBoardAlert.setConnectionApprovedCount(0);
									dashBoardAlert.setLikeCount(0);
									dashBoardAlert.setMessageCount(0);
									dashBoardAlert.setProfileVisitCount(0);
									dashBoardAlert.update();
								}
							}
						} catch (ElementNotFoundException e) {
							invitee = userFinder.newUser();

							invitee.setFirstName(name);
							invitee.setLastName("");
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date now = new Date();
							String dt = sdf.format(now);
							Timestamp ts = Timestamp.valueOf(dt);
							invitee.setDateRegistered(ts);
							String renKey = RandomString.nextString();
							invitee.setFirstKey(renKey);
							invitee.setMsgAlert(true);
							invitee.setProfileName(RandomString.nextShortString());
							invitee.setEmailAddress(email.trim());
							invitee.setPassword(RandomString.nextShortString());
							invitee.setValidated(false);
							invitee.update();
							if (!user.isSuperAdministrator()) {
								MimeMessage message = mailSender.createMimeMessage();

								message.setFrom(new InternetAddress("support@jhapak.com"));
								message.setRecipients(Message.RecipientType.TO, invitee.getEmailAddress());
								message.setSubject(invitee.getFirstName()+", "+user.getFirstName()+" left a message for you");

								Map model = new HashMap();  
								String activationLink = context.getContextUrl()+"/pers/activateInvitation.do?mid="+invitee.getDateRegistered().getTime()+"&key="+invitee.getFirstKey()+"&id="+invitee.getId();
								model.put("activationLink", activationLink);
								model.put("activationGoogleLink", activationLink+"&action=googleAction");
								model.put("userName", user.getFirstName() + " " + user.getLastName());
								model.put("cEmail", invitee.getEmailAddress());
								model.put("userImgURL", context.getContextUrl()+"/pers/userPhoto.img?id="+user.getId());
								model.put("invitee", invitee.getFirstName());
								model.put("message", msg);
								model.put("croot", context.getContextUrl());
								model.put("cDate", dt);

								String text = VelocityEngineUtils.mergeTemplateIntoString(
										velocityEngine, "main/resources/velocity/SendInvitation.vm", "UTF-8", model);
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
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private String invitedFriends  = "";
		private String successMsg = "";
		private String failMsg = "";
		private String message = "";

		public String getInvitedFriends() {
			return invitedFriends;
		}

		public void setInvitedFriends(String invitedFriends) {
			this.invitedFriends = invitedFriends;
		}

		public List<String> getInvitedfriendsList() throws Exception
		{
			List<String> list = new ArrayList<String>(50);
			StringTokenizer tok = new StringTokenizer(invitedFriends, " ,\n\r", false);
			while (tok.hasMoreTokens())
				list.add(tok.nextToken());
			return list;
		}

		public String getSuccessMsg() {
			return successMsg;
		}

		public void setSuccessMsg(String successMsg) {
			this.successMsg = successMsg;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getFailMsg() {
			return failMsg;
		}

		public void setFailMsg(String failMsg) {
			this.failMsg = failMsg;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	public class Validator extends CommandValidator 
	{
		public String validateInvitedFriends(Object value, CommandBeanImpl data) throws Exception
		{
			if (isNullOrWhitespace(value) || value.toString().equals("E-mail Address")) return "Please enter Email address";
			return null;
		}
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

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}

	public void setDashBoardAlertFinder(DashBoardAlertFinder dashBoardAlertFinder) {
		this.dashBoardAlertFinder = dashBoardAlertFinder;
	}
}