package com.era.community.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineUtils;

import support.community.framework.AppRequestContext;
import support.community.framework.AppRequestContextHolder;

import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;

public class CecUserDetailsService implements UserDetailsService
{
	private AppRequestContextHolder contextManager;
	private UserFinder userFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected RunAsAsyncThread taskExecutor;
	protected Log logger = LogFactory.getLog(getClass()); 

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
	{
		contextManager.getRequestContext().getRequest().getSession().removeAttribute("cecLoginAttribute");

		UserDetailsBean user;

		try {
			user = (UserDetailsBean)userFinder.getUserDataForEmailAddress(username, UserDetailsBean.class);
			if (user.getInactive()) throw new UsernameNotFoundException("User account for ["+username+"] is inactive.");
			if (user.getValidated()==false) {

				//mailForRegistration(user, user.getFirstKey(), contextManager.getRequestContext());

				LoginFormBean bean = new LoginFormBean();
				String lMessage = "Your account has not been activated.<br/>An activation email message has already been sent to you, open the link it contains to activate your account."
					+"<br/>Or click here to <a href='"+contextManager.getRequestContext().getContextUrl()+"/pers/validateResend.do' style='text-decoration: underline; color: #005689;'>resend activation email</a>.";
				bean.setLoginMessage(lMessage);
				contextManager.getRequestContext().getRequest().getSession().setAttribute("cecLoginAttribute", bean);
				throw new UsernameNotFoundException("User account for ["+username+"] has not been validated.");
			}
			return user;
		} catch (Exception e) {
			logger.error("Error finding ["+username+"]", e);
			throw new UsernameNotFoundException(e.toString());
		}
	}

	private void mailForRegistration(final UserDetailsBean user, final String renKey, final AppRequestContext context) throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					if (!user.getSuperAdministrator()) {

						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						long time = 0;
						try {
							Date date = formatter.parse(user.getDateRegistered());
							time = date.getTime();
						} catch (ParseException e) {
						}
						MimeMessage message = mailSender.createMimeMessage();
						message.setFrom(new InternetAddress("support@jhapak.com"));
						message.setRecipients(Message.RecipientType.TO, user.getEmailAddress());
						message.setSubject("Your user account at Jhapak");

						Map model = new HashMap();    
						String activationLink = contextManager.getRequestContext().getContextUrl()+"/pers/validateMe.do?mid="+time+"&key="+renKey+"&id="+user.getId();
						model.put("activationLink", activationLink);
						model.put("userName", user.getFirstName());
						model.put("croot", context.getContextUrl());

						String text = VelocityEngineUtils.mergeTemplateIntoString(
								velocityEngine, "main/resources/velocity/ActivateAccount.vm", "UTF-8", model);
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

	public static class UserDetailsBean extends UserEntity implements UserDetails
	{       
		public UserDetailsBean()
		{
			super();
		}

		public GrantedAuthority[] getAuthorities()
		{
			List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>(8);

			roles.add(new GrantedAuthorityImpl(UserRoleConstants.ROLE_USER));

			if (this.getSystemAdministrator()==true)
				roles.add(new GrantedAuthorityImpl(UserRoleConstants.ROLE_SYS_ADMIN));

			/*if (this.getSystemAdministrator()==true)
				roles.add(new GrantedAuthorityImpl(UserRoleConstants.ROLE_COMMUNITY_CREATOR)); */

			if (this.getSuperAdministrator()==true)
				roles.add(new GrantedAuthorityImpl(UserRoleConstants.ROLE_SUPER_ADMIN));

			GrantedAuthority[] a = new GrantedAuthority[roles.size()];
			for (int n=0; n<a.length; n++) a[n] = roles.get(n);

			return a;
		}

		public String getUsername()
		{
			return getEmailAddress();
		}

		public boolean isAccountNonExpired()
		{
			return true;
		}

		public boolean isAccountNonLocked()
		{
			return true;
		}

		public boolean isCredentialsNonExpired()
		{
			return true;
		}

		public boolean isEnabled()
		{
			return !getInactive();
		}        
	}

	public static class LoginFormBean 
	{
		private String loginMessage; 
		public final String getLoginMessage()
		{
			return loginMessage;
		}
		public final void setLoginMessage(String loginMessage)
		{
			this.loginMessage = loginMessage;
		}
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setContextHolder(AppRequestContextHolder contextManager)
	{
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