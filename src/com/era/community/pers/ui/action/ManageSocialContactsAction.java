package com.era.community.pers.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.RandomString;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.base.CecUserDetailsService.LoginFormBean;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.LoginSN;
import com.era.community.pers.dao.LoginSNFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/pers/manageSocialContacts.ajx"
 */
public class ManageSocialContactsAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected UserFinder userFinder;
	protected ContactFinder contactFinder;
	protected DashBoardAlertFinder dashBoardAlertFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected RunAsAsyncThread taskExecutor;
	protected LoginSNFinder loginSNFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		HttpServletRequest request = contextManager.getContext().getRequest();
		HttpServletResponse resp = contextManager.getContext().getResponse();
		User currentUser = context.getCurrentUser();
		String returnString = "";
		Command cmd = (Command) data;

		try {
			String[] myJsonData = request.getParameterValues("json[]");
			String userMail = request.getParameter("userMail");
			contextManager.getContext().getRequest().getSession().removeAttribute("cecLoginAttribute");
			if ("invite".equalsIgnoreCase(cmd.getAction())) {
				if (!currentUser.getEmailAddress().equalsIgnoreCase(userMail)) {
					LoginFormBean bean = new LoginFormBean();
					bean.setLoginMessage("You are not login using '"+userMail+"', Please login again.");
					contextManager.getContext().getRequest().getSession().setAttribute("cecLoginAttribute", bean);
					JSONObject json = new JSONObject();
					json.put("isNewUser", true);
					String jsonString = json.serialize();
					resp.setContentType("text/json");
					Writer out = resp.getWriter();
					out.write(jsonString);
					out.close();
					return null;
				} else {
					String succmsg = "Your Invitation Has Been Successfully Sent To Your Friends!";
					cmd.setSuccessMsg(succmsg);
					request.getSession().setAttribute("succMsg", succmsg);
				}
				sendInvitations(context.getCurrentUser(), myJsonData);
			} else {
				// while login
				String jsonString = request.getParameter("jsonString");
				jsonString = jsonString.replace("[", "{\"contact\":[");
				jsonString = jsonString.replace("]", "]}");
				inviteContacts(context.getCurrentUser(), jsonString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write(returnString);
		out.close();
		return null;
	}

	private void inviteContacts(final User user, final String jsonString)  throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					JSONObject json = JSONObject.parse(jsonString);
					Object contacts =json.get("contact");
					JSONArray contactArray = (JSONArray) contacts;
					@SuppressWarnings("rawtypes")
					Iterator iter = contactArray.iterator();
					while(iter.hasNext()){
						JSONObject selContact = (JSONObject) iter.next();
						CommunityEraContext context = contextManager.getContext(); 
						if (selContact != null && selContact.get("email") != null && !"".equals(selContact.get("email"))) {
							User invitee = null;
							String email = selContact.get("email").toString();
							String fname = "";
							String lname = "";
							if (selContact.get("firstName") == null) {
								fname = email.split("@")[0];
							} else {
								fname = selContact.get("firstName").toString();
								if (selContact.get("lastName") != null) {
									lname = selContact.get("lastName").toString();
								}
							}

							try {
								invitee = userFinder.getUserForEmailAddress(email);
							} catch (ElementNotFoundException e) {
								invitee = userFinder.newUser();

								invitee.setFirstName(fname);
								invitee.setLastName(lname);
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

								LoginSN loginSN = loginSNFinder.newLoginSN();
								loginSN.setUserId(invitee.getId());
								loginSN.setLoginId("");
								loginSN.setSnType(99); // 1 for google & 2 for FB & 3 for Lin & 99 for mail sending purpose only
								loginSN.setEmailAddress(invitee.getEmailAddress());
								loginSN.setLogin(false);
								loginSN.update();

								/*if (!user.isSuperAdministrator()) {
								MimeMessage message = mailSender.createMimeMessage();
								message.setFrom(new InternetAddress("support@jhapak.com"));
								message.setRecipients(Message.RecipientType.TO, invitee.getEmailAddress());
								message.setSubject("Invitation from Jhapak");

								Map model = new HashMap();  
								String activationLink = context.getContextUrl()+"/pers/activateInvitation.do?mid="+invitee.getDateRegistered().getTime()+"&key="+invitee.getFirstKey()+"&id="+invitee.getId();
								model.put("activationLink", activationLink);
								model.put("activationGoogleLink", activationLink+"&action=googleAction");
								model.put("invitee", invitee.getFirstName());
								model.put("croot", context.getContextUrl());

								String text = VelocityEngineUtils.mergeTemplateIntoString(
										velocityEngine, "main/resources/velocity/ContactInvitation.vm", "UTF-8", model);
								message.setContent(text, "text/html");
								message.setSentDate(new Date());

								Multipart multipart = new MimeMultipart();
								BodyPart htmlPart = new MimeBodyPart();
								htmlPart.setContent(text, "text/html");
								multipart.addBodyPart(htmlPart);            
								message.setContent(multipart);
								mailSender.send(message);
							}*/
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void sendInvitations(final User user, final String[] myJsonData)  throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					for (String selContact : myJsonData) {
						CommunityEraContext context = contextManager.getContext(); 
						User invitee = null;
						String email = "";
						String name = "";
						String[] myContact = selContact.split("#~~#");
						if (myContact.length > 1) {
							email = myContact[0];
							name = myContact[1];
						} else {
							email = myContact[0];
							name = email.split("@")[0];;
						}

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
									dashBoardAlert.setNotificationCount(0);
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
							
							LoginSN loginSN = loginSNFinder.newLoginSN();
							loginSN.setUserId(invitee.getId());
							loginSN.setLoginId("");
							loginSN.setSnType(98); // 1 for google & 2 for FB & 3 for Lin & 98 for mail sending purpose only
							loginSN.setEmailAddress(invitee.getEmailAddress());
							loginSN.setLogin(false);
							loginSN.update();
							
							try {
								contactFinder.getContact(user.getId(), invitee.getId());
							} catch (ElementNotFoundException ex) {
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
								} catch (ElementNotFoundException exp) {
									dashBoardAlert = dashBoardAlertFinder.newDashBoardAlert();
									dashBoardAlert.setUserId(invitee.getId());
									dashBoardAlert.setConnectionReceivedCount(1);
									dashBoardAlert.setConnectionApprovedCount(0);
									dashBoardAlert.setNotificationCount(0);
									dashBoardAlert.setLikeCount(0);
									dashBoardAlert.setMessageCount(0);
									dashBoardAlert.setProfileVisitCount(0);
									dashBoardAlert.update();
								}
							}
							
							/*if (!user.isSuperAdministrator()) {
								MimeMessage message = mailSender.createMimeMessage();
								message.setFrom(new InternetAddress("support@jhapak.com"));
								message.setRecipients(Message.RecipientType.TO, invitee.getEmailAddress());
								message.setSubject(invitee.getFirstName()+", "+user.getFirstName()+" left a message for you");

								Map model = new HashMap();  
								String activationLink = context.getContextUrl()+"/pers/activateInvitation.do?mid="+invitee.getDateRegistered().getTime()+"&key="+invitee.getFirstKey()+"&id="+invitee.getId();
								model.put("activationLink", activationLink);
								model.put("userName", user.getFirstName() + " " + user.getLastName());
								model.put("userImgURL", context.getContextUrl()+"/pers/userPhoto.img?id="+user.getId());
								model.put("invitee", invitee.getFirstName());
								
								model.put("croot", context.getContextUrl());
								model.put("cEmail", invitee.getEmailAddress());
								model.put("cUnsubscribe", context.getContextUrl()+"/pers/unsubscribeMe.do?mid="+invitee.getId()+"&key="+invitee.getFirstKey()+"&type=new");
								model.put("mailPreferences", context.getContextUrl()+"/pers/manageSubscriptions.do");
								model.put("cPrivacy", context.getContextUrl()+"/privacy.do");
								model.put("cHelp", context.getContextUrl()+"/help.do");
								model.put("cFeedback", context.getContextUrl()+"/feedback.do");
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
								try {
									mailSender.send(message);
								} catch (Exception e2) {
									e.printStackTrace();
								}
								
							}*/
						}
					}
				} catch (Exception e) {
					
				}
			}
		});
	}

	private String getMiddleName(String[] middleName){
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i < middleName.length-1; i++) {
			builder.append(middleName[i] + " ");
		}
		return builder.toString();
	}

	public static class Command extends IndexCommandBeanImpl implements CommandBean
	{
		private String action;
		private String successMsg = "";

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public String getSuccessMsg() {
			return successMsg;
		}

		public void setSuccessMsg(String successMsg) {
			this.successMsg = successMsg;
		}
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
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

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setTaskExecutor(RunAsAsyncThread taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setLoginSNFinder(LoginSNFinder loginSNFinder) {
		this.loginSNFinder = loginSNFinder;
	}
}