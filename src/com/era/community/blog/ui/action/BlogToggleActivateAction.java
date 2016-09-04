package com.era.community.blog.ui.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.blog.dao.PersonalBlog;
import com.era.community.blog.dao.PersonalBlogFinder;
import com.era.community.monitor.dao.PersonalBlogSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/blog/toggleAactivate.do" 
 */
public class BlogToggleActivateAction extends AbstractCommandAction
{
	protected PersonalBlogFinder personalBlogFinder;
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;
	protected UserFinder userFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected RunAsAsyncThread taskExecutor;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();
		if (currentUser == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}
		PersonalBlog blog = personalBlogFinder.getPersonalBlogForUser(cmd.getBid(), currentUser.getId());
		if (cmd.getActive() == 'Y') {
			blog.setInactive( false );
		}
		else {
			blog.setInactive( true );
		}
		//TODO mail required
		mailPersonalSubscribers(blog,currentUser);
		blog.update();
		return new ModelAndView("redirect:/blog/personalBlog.do");
	}

	private void mailPersonalSubscribers(final PersonalBlog perBlog, final User currentUser) throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					CommunityEraContext context = contextManager.getContext(); 
					/* Get the list of subscriptions for this Personal blog */
					List<PersonalBlogSubscription> subs = subscriptionFinder.getSubscriptionsForPersonalBlog(perBlog.getId());
					for(PersonalBlogSubscription sub : subs) {
						if (sub.getFrequency() == 0) { // Only email the subscribers who want immediate alerts
							//Skip the current user
							int uid = sub.getUserId();
							if (context.getCurrentUser() == null || uid == context.getCurrentUser().getId())
								continue;
							User subscriber = userFinder.getUserEntity(uid);
							if (!subscriber.isSuperAdministrator()) {
								MimeMessage message = mailSender.createMimeMessage();
								MimeMessageHelper helper = new MimeMessageHelper(message, true);
								helper.setTo(subscriber.getEmailAddress());
								helper.setFrom(new InternetAddress("support@jhapak.com ") );
								helper.setSubject("New blog entry");
								helper.setSentDate(new Date());
								Map model = new HashMap();  
								String sLink = context.getContextUrl() + "blog/blogEntry.do?id=" + perBlog.getId();
								String sUnSubscribe = context.getContextUrl() + "reg/watch.do";
								model.put("#ownerName#", currentUser.getFullName());
								model.put("#blogName#", perBlog.getName());
								model.put("#blogEntryLink#", sLink);
								model.put("#unSubscribe#", sUnSubscribe);

								String text = VelocityEngineUtils.mergeTemplateIntoString(
										velocityEngine, "main/resources/velocity/NewPersonalBlogEntry.vm", "UTF-8", model);
								helper.setText(text, true);
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

	public static class Command extends CommandBeanImpl implements CommandBean
	{              
		private int bid;
		private char active;

		public char getActive()
		{
			return active;
		}
		public void setActive(char active)
		{
			this.active = active;
		}
		public int getBid() {
			return bid;
		}
		public void setBid(int bid) {
			this.bid = bid;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
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

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setPersonalBlogFinder(PersonalBlogFinder personalBlogFinder) {
		this.personalBlogFinder = personalBlogFinder;
	}
}