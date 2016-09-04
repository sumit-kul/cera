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
import javax.servlet.http.HttpServletRequest;

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
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.Notification;
import com.era.community.pers.dao.NotificationFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.ContactDto;

/**
 * @spring.bean name="/pers/acceptrequest.do"
 */
public class AcceptConnectionRequestAction extends AbstractCommandAction
{
    protected UserFinder userFinder;
    protected ContactFinder contactFinder;
    protected DashBoardAlertFinder dashBoardAlertFinder;
    protected CommunityEraContextManager contextManager;
    protected  NotificationFinder notificationFinder;
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
			return new ModelAndView("redirect:/login.do");
		}

        Command cmd = (Command)data;
        String returnString = "";
    	try {
    		Contact contact = contactFinder.getContactForId(cmd.getId());
    		User owningUser = userFinder.getUserEntity( cmd.getOid());
    		User contactUser = userFinder.getUserEntity( cmd.getCid());
    		if (contact.getStatus() == 1) {
    			String lnk = "<a href='"+context.getContextUrl()+"/pers/connectionResult.do?id="+owningUser.getId()+"' class='memberInfo' "+
										" title='"+context.getContextUrl()+"/pers/connectionInfo.do?id="+owningUser.getId()+"' >"+owningUser.getFullName()+"</a>";
    			returnString = "Error confirming network request from <span class='person'>"+lnk+"</span>: You are connected with this person.";
			} else if (contact.getOwningUserId() == cmd.getOid() && contact.getContactUserId() == cmd.getCid() 
    				&& contact.getStatus() == 0) { // if request is for confirmation, NewStatus = 4
    				contact.setStatus(1); // marked approved
    				contact.setFollowOwner(1); // contact is following Owner
    				contact.setFollowContact(1); // Owner is following Contact
    				
    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		    	Date now = new Date();
    		    	String dt = sdf.format(now);
    		    	Timestamp ts = Timestamp.valueOf(dt);
    				contact.setDateConnection(ts);
					contact.update();
					
					int connectionCountForOwner = owningUser.getConnectionCount();
					connectionCountForOwner = connectionCountForOwner + 1;
					owningUser.setConnectionCount(connectionCountForOwner);
					owningUser.update();
					
					int connectionCountForCurrent = currentUser.getConnectionCount();
					connectionCountForCurrent = connectionCountForCurrent + 1;
					currentUser.setConnectionCount(connectionCountForCurrent);
					currentUser.update();
					
					// update the dash board for recipients of this message
			        DashBoardAlert dashBoardAlert;
			        try {
			        	dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(owningUser.getId());
					} catch (ElementNotFoundException e) {
						dashBoardAlert = new DashBoardAlert();
						dashBoardAlert.setUserId(owningUser.getId());
						dashBoardAlert.setConnectionApprovedCount(1);
					}
					dashBoardAlert.setConnectionApprovedCount(dashBoardAlert.getConnectionApprovedCount() + 1);
					dashBoardAlert.update();
					
					Notification notification = notificationFinder.newNotification();
					notification.setCommunityId(0);
					notification.setItemType("Contact");
					notification.setItemId(currentUser.getId());
					notification.setUserId(owningUser.getId());
					notification.update();
					
					returnString = "You and "+owningUser.getFullName()+" are connected now.";
					
					mailForConnectionRequest(contact, currentUser, owningUser);
					
					String lnk = "<a href='"+context.getContextUrl()+"/pers/connectionResult.do?id="+owningUser.getId()+"' class='memberInfo' "+
					" title='"+context.getContextUrl()+"/pers/connectionInfo.do?id="+owningUser.getId()+"' >"+owningUser.getFullName()+"</a>";
					returnString = "You and <span class='person'>"+lnk+"</span> are connected now.";
					
    			} else {
    				returnString = "Sorry, we can't confirm this connection request right now";
    			}
    	} catch (ElementNotFoundException e) {
    		returnString = "Sorry, we can't confirm this connection request right now";
    	}
    	HttpServletRequest request = contextManager.getContext().getRequest(); 
    	request.getSession().setAttribute("retMess", returnString);
    	return new ModelAndView("redirect:/pers/connectionList.do");
    }
    
    private void mailForConnectionRequest(final Contact contact, final User currentUser, final User owningUser) throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					if (!owningUser.isSuperAdministrator()) {
						CommunityEraContext context = contextManager.getContext();
						MimeMessage message = mailSender.createMimeMessage();
						message.setFrom(new InternetAddress("support@jhapak.com"));
						message.setRecipients(javax.mail.Message.RecipientType.TO, owningUser.getEmailAddress());
						message.setSubject(currentUser.getFirstName()+" confirmed your Jhapak network request");

						Map model = new HashMap();    
						model.put("userImgURL", context.getContextUrl()+"/pers/userPhoto.img?id="+currentUser.getId());
						model.put("ownerName", owningUser.getFirstName());
						model.put("userName", currentUser.getFullName());
						model.put("croot", context.getContextUrl());
						model.put("cEmail", owningUser.getEmailAddress());
						model.put("cUnsubscribe", context.getContextUrl()+"/pers/unsubscribeMe.do?mid="+owningUser.getId()+"&key="+owningUser.getFirstKey()+"&type=crequest");
						model.put("mailPreferences", context.getContextUrl()+"/pers/manageSubscriptions.do");
						model.put("cPrivacy", context.getContextUrl()+"/privacy.do");
						model.put("cHelp", context.getContextUrl()+"/help.do");
						model.put("cFeedback", context.getContextUrl()+"/feedback.do");
						SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
						Date now = new Date();
						String dt = sdf.format(now);
						model.put("cDate", dt);

						String text = VelocityEngineUtils.mergeTemplateIntoString(
								velocityEngine, "main/resources/velocity/ConfirmConnectionRequest.vm", "UTF-8", model);
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
        private int newStatus;
        private int userId;
        private int oid;
        private int cid;

        public String getUserName()
        {
            return userName;
        }

        public void setUserName(String userName)
        {
            this.userName = userName;
        }

		public int getNewStatus() {
			return newStatus;
		}

		public void setNewStatus(int newStatus) {
			this.newStatus = newStatus;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public int getCid() {
			return cid;
		}

		public void setCid(int cid) {
			this.cid = cid;
		}

		public int getOid() {
			return oid;
		}

		public void setOid(int oid) {
			this.oid = oid;
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

	public void setNotificationFinder(NotificationFinder notificationFinder) {
		this.notificationFinder = notificationFinder;
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