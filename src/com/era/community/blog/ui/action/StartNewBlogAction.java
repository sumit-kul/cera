package com.era.community.blog.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogAuthor;
import com.era.community.blog.dao.BlogAuthorFinder;
import com.era.community.blog.dao.BlogEntry;
import com.era.community.blog.dao.PersonalBlog;
import com.era.community.blog.dao.PersonalBlogFinder;
import com.era.community.blog.dao.generated.PersonalBlogEntity;
import com.era.community.config.dao.MailMessageConfig;
import com.era.community.monitor.dao.PersonalBlogSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/pers/startBlog.do"
 */
public class StartNewBlogAction extends AbstractFormAction
{
	protected PersonalBlogFinder personalBlogFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected UserFinder userFinder;
	protected BlogAuthorFinder blogAuthorFinder;

	protected CommunityEraContextManager contextManager;
	protected MailSender mailSender;
	protected MailMessageConfig mailMessageConfig;
	protected TaskExecutor taskExecutor;

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	protected String getView()
	{
		return "blog/startNewBlog";
	}

	protected void onDisplay(Object data) throws Exception
	{
	}

	protected Map referenceData(Object command) throws Exception
	{
		return new HashMap();
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command) data;
		User user =  context.getCurrentUser();
		if (user == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		
		PersonalBlog perBlog = null;
		perBlog = personalBlogFinder.newPersonalBlog();
		perBlog.setUserId(user.getId());
		perBlog.setName(cmd.getName());
		perBlog.setDescription(cmd.getDescription());
		perBlog.setModified(ts);
		perBlog.update();
		BlogAuthor author = blogAuthorFinder.newBlogAuthor();
		author.setPersonalBlogId(perBlog.getId());
		author.setUserId(user.getId());
		author.setRole(1);
		author.setActive(1);
		author.update();
		
		PersonalBlogSubscription subs = subscriptionFinder.newPersonalBlogSubscription();

		subs.setPersonalBlogId(new Integer(perBlog.getId()));
		subs.setUserId(new Integer(user.getId()));
		subs.setFrequency(1);
		subs.update();

		//TODO mail required for user following
		return new ModelAndView("redirect:/blog/viewBlog.do?bid="+perBlog.getId());
	}

	/*
	 * Mail the subscribers to this blog
	 */
	private void mailSubscribers(BlogEntry entry, CommunityEraContext context) throws Exception
	{

		/* Get the list of subscriptions for this person's blog */
		List subs = subscriptionFinder.getSubscriptionsForBlog(entry.getPersonalBlogId());

		//User owner = entry.getBlog().getOwner();

		Iterator it = subs.iterator();

		/* Loop thru the list of Subscriptions for this blog */
		while (it.hasNext()) {
			PersonalBlogSubscription sub = (PersonalBlogSubscription) it.next();

			if (sub.getFrequency() == 0) { // Only email the subscribers who want immediate alerts

				/*
				 * Email the blog subscribers who have set immediate to alert that a new blog entry has been posted Skip
				 * the current user
				 */
				int uid = sub.getUserId();
				if (context.getCurrentUser() == null || uid == context.getCurrentUser().getId())
					continue;
				User subscriber = userFinder.getUserEntity(uid);

				/*
				 * Parameters to substitute into the body text of the Email.
				 */
				Map<String, String> params = new HashMap<String, String>(11);

				String sLink = context.getContextUrl() + "reg/blog-personal-index.do?userId=" + entry.getPosterId();
				String sUnSubscribe = context.getContextUrl() + "reg/watch.do";

				//params.put("#ownerName#", owner.getFullName());
				params.put("#blogEntryTitle#", entry.getTitle());
				params.put("#blogEntryLink#", sLink);
				params.put("#unSubscribe#", sUnSubscribe);

				/*
				 * Create and send the mail message.
				 */
				SimpleMailMessage msg = mailMessageConfig.createMailMessage("blog-alert", params);
				msg.setTo(subscriber.getEmailAddress());       
				try {
					if (!subscriber.isSuperAdministrator()) {
					mailSender.send(msg);
					}
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public class Validator extends support.community.framework.CommandValidator
	{
		public String validateName(Object value, CommandBean cmd)
		{
			if (value.toString().equals("")) {
				return "You must set a name for this blog entry";
			}
			if (value.toString().length() > 100) {
				return "The maximum length of the blog name is 100 characters, please shorten your title";
			}
			return null;
		}
	}

	public class Command extends PersonalBlogEntity implements CommandBean
	{
	}

	public void setMailSender(MailSender mailSender)
	{
		this.mailSender = mailSender;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public UserFinder getUserFinder()
	{
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setMailMessageConfig(MailMessageConfig mailMessageConfig)
	{
		this.mailMessageConfig = mailMessageConfig;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setBlogAuthorFinder(BlogAuthorFinder blogAuthorFinder) {
		this.blogAuthorFinder = blogAuthorFinder;
	}

	public void setPersonalBlogFinder(PersonalBlogFinder personalBlogFinder) {
		this.personalBlogFinder = personalBlogFinder;
	}
}