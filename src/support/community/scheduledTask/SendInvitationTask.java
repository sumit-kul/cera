package support.community.scheduledTask;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineUtils;

import support.community.application.ElementNotFoundException;

import com.era.community.base.RunAsAsyncThread;
import com.era.community.jobs.dao.ScheduledJobFinder;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.LoginSN;
import com.era.community.pers.dao.LoginSNFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

public class SendInvitationTask implements InitializingBean, Runnable, ApplicationListener
{ 
	protected Log logger = LogFactory.getLog(getClass()); 
	protected String linkPrefix;
	protected ScheduledJobFinder scheduledJobFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected RunAsAsyncThread taskExecutor;
	protected LoginSNFinder loginSNFinder;
	protected UserFinder userFinder;
	protected ContactFinder contactFinder;

	public void run()  
	{
		User newInvitee = null;
		User invitee = null;
		try {
			int count = loginSNFinder.getLoginSNForTypeCount(99);
			if (count > 0) {
				LoginSN loginSN = loginSNFinder.getLoginSNForType(99);
				//long diff = (new Date()).getTime() - loginSN.getCreated().getTime();

				//if ( TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 2) {
					newInvitee = userFinder.getUserEntity(loginSN.getUserId());
					
					MimeMessage message = mailSender.createMimeMessage();
					message.setFrom(new InternetAddress("support@jhapak.com"));
					message.setRecipients(Message.RecipientType.TO, newInvitee.getEmailAddress());
					message.setSubject(newInvitee.getFirstName()+", your invitation from Jhapak");

					Map model = new HashMap();  
					String activationLink = getLinkPrefix()+"/pers/activateInvitation.do?mid="+newInvitee.getDateRegistered().getTime()+"&key="+newInvitee.getFirstKey()+"&id="+newInvitee.getId();
					model.put("activationLink", activationLink);
					model.put("activationGoogleLink", activationLink+"&action=googleAction");
					model.put("invitee", newInvitee.getFirstName());
					model.put("croot", getLinkPrefix());
					model.put("cEmail", newInvitee.getEmailAddress());
					model.put("cUnsubscribe", getLinkPrefix()+"/pers/unsubscribeMe.do?mid="+newInvitee.getId()+"&key="+newInvitee.getFirstKey()+"&type=new");
					model.put("mailPreferences", getLinkPrefix()+"/pers/manageSubscriptions.do");
					model.put("cPrivacy", getLinkPrefix()+"/privacy.do");
					model.put("cHelp", getLinkPrefix()+"/help.do");
					model.put("cFeedback", getLinkPrefix()+"/feedback.do");
					SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
					Date now = new Date();
					String dt = sdf.format(now);
					model.put("cDate", dt);

					String text = VelocityEngineUtils.mergeTemplateIntoString(
							velocityEngine, "main/resources/velocity/ContactInvitation.vm", "UTF-8", model);
					message.setContent(text, "text/html");
					message.setSentDate(new Date());

					Multipart multipart = new MimeMultipart();
					BodyPart htmlPart = new MimeBodyPart();
					htmlPart.setContent(text, "text/html");
					multipart.addBodyPart(htmlPart);            
					message.setContent(multipart);
					try {
						mailSender.send(message);
					} catch (Exception e) {
						e.printStackTrace();
					}
					loginSN.delete();
				//}
			}
		} catch (ElementNotFoundException e) {
			logger.error("No more user to Invite", e);
		} catch (javax.mail.internet.AddressException addExp) {
			try {
				LoginSN loginSN = loginSNFinder.getLoginSNForEmailAddress(newInvitee.getEmailAddress());
				loginSN.delete();
				newInvitee.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			logger.error("Error in job task", e);
		}

		try {
			int countSN = loginSNFinder.getLoginSNForTypeCount(98);
			if (countSN > 0) {
				LoginSN loginSN = loginSNFinder.getLoginSNForType(98);
				invitee = userFinder.getUserEntity(loginSN.getUserId());
				User user = null;
				try {
					Contact contact = contactFinder.getContact(invitee.getId(), invitee.getId());
					user = userFinder.getUserEntity(contact.getOwningUserId());
				} catch (ElementNotFoundException e) {
					MimeMessage message = mailSender.createMimeMessage();
					message.setFrom(new InternetAddress("support@jhapak.com"));
					message.setRecipients(Message.RecipientType.TO, invitee.getEmailAddress());
					message.setSubject(invitee.getFirstName()+", your invitation from Jhapak");

					Map model = new HashMap();  
					String activationLink = getLinkPrefix()+"/pers/activateInvitation.do?mid="+invitee.getDateRegistered().getTime()+"&key="+invitee.getFirstKey()+"&id="+invitee.getId();
					model.put("activationLink", activationLink);
					model.put("activationGoogleLink", activationLink+"&action=googleAction");
					model.put("invitee", invitee.getFirstName());
					model.put("croot", getLinkPrefix());
					model.put("cEmail", invitee.getEmailAddress());
					model.put("cUnsubscribe", getLinkPrefix()+"/pers/unsubscribeMe.do?mid="+invitee.getId()+"&key="+invitee.getFirstKey()+"&type=new");
					model.put("mailPreferences", getLinkPrefix()+"/pers/manageSubscriptions.do");
					model.put("cPrivacy", getLinkPrefix()+"/privacy.do");
					model.put("cHelp", getLinkPrefix()+"/help.do");
					model.put("cFeedback", getLinkPrefix()+"/feedback.do");
					SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
					Date now = new Date();
					String dt = sdf.format(now);
					model.put("cDate", dt);

					String text = VelocityEngineUtils.mergeTemplateIntoString(
							velocityEngine, "main/resources/velocity/ContactInvitation.vm", "UTF-8", model);
					message.setContent(text, "text/html");
					message.setSentDate(new Date());

					Multipart multipart = new MimeMultipart();
					BodyPart htmlPart = new MimeBodyPart();
					htmlPart.setContent(text, "text/html");
					multipart.addBodyPart(htmlPart);            
					message.setContent(multipart);
					try {
						mailSender.send(message);
					} catch (Exception exp) {
						e.printStackTrace();
					}
					loginSN.delete();
				}

				if (user != null) {
					MimeMessage message = mailSender.createMimeMessage();
					message.setFrom(new InternetAddress("support@jhapak.com"));
					message.setRecipients(Message.RecipientType.TO, invitee.getEmailAddress());
					message.setSubject(invitee.getFirstName()+", "+invitee.getFirstName()+" left a message for you");

					Map model = new HashMap();  
					String activationLink = getLinkPrefix()+"/pers/activateInvitation.do?mid="+invitee.getDateRegistered().getTime()+"&key="+invitee.getFirstKey()+"&id="+invitee.getId();
					model.put("activationLink", activationLink);
					model.put("activationGoogleLink", activationLink+"&action=googleAction");
					model.put("userName", user.getFirstName() + " " + user.getLastName());
					model.put("userImgURL", getLinkPrefix()+"/pers/userPhoto.img?id="+user.getId());
					model.put("invitee", invitee.getFirstName());

					model.put("croot", getLinkPrefix());
					model.put("cEmail", invitee.getEmailAddress());
					model.put("cUnsubscribe", getLinkPrefix()+"/pers/unsubscribeMe.do?mid="+invitee.getId()+"&key="+invitee.getFirstKey()+"&type=new");
					model.put("mailPreferences", getLinkPrefix()+"/pers/manageSubscriptions.do");
					model.put("cPrivacy", getLinkPrefix()+"/privacy.do");
					model.put("cHelp", getLinkPrefix()+"/help.do");
					model.put("cFeedback", getLinkPrefix()+"/feedback.do");
					SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
					Date now = new Date();
					String dt = sdf.format(now);
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
					} catch (Exception e) {
						e.printStackTrace();
					}
					loginSN.delete();
				}
			}
		} catch (ElementNotFoundException e) {
			logger.error("No more user to Invite", e);
		} catch (javax.mail.internet.AddressException addExp) {
			try {
				LoginSN loginSN = loginSNFinder.getLoginSNForEmailAddress(invitee.getEmailAddress());
				loginSN.delete();
				invitee.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			logger.error("Error in job task", e);
		}

	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	public void setScheduledJobFinder(ScheduledJobFinder scheduledJobFinder) {
		this.scheduledJobFinder = scheduledJobFinder;
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

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		// TODO Auto-generated method stub

	}

	public String getLinkPrefix() {
		return linkPrefix;
	}

	public void setLinkPrefix(String linkPrefix) {
		this.linkPrefix = linkPrefix;
	}

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}
}