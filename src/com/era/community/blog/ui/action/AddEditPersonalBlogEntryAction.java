package com.era.community.blog.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.ImageManipulation;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.base.Taggable;
import com.era.community.blog.dao.BlogAuthorFinder;
import com.era.community.blog.dao.BlogEntry;
import com.era.community.blog.dao.BlogEntryFinder;
import com.era.community.blog.dao.PersonalBlog;
import com.era.community.blog.dao.PersonalBlogFinder;
import com.era.community.blog.ui.dto.BlogEntryDto;
import com.era.community.blog.ui.validator.BlogEntryValidator;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.monitor.dao.BlogEntrySubscription;
import com.era.community.monitor.dao.PersonalBlogSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserActivity;
import com.era.community.pers.dao.UserActivityFinder;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;

/**
 * @spring.bean name="/blog/addEditBlog.do"
 */
public class AddEditPersonalBlogEntryAction extends AbstractFormAction
{
	protected BlogEntryFinder blogEntryFinder;
	protected PersonalBlogFinder prsonalBlogFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected UserFinder userFinder;
	protected CommunityFinder communityFinder;
	protected CommunityEraContextManager contextManager;
	protected TagFinder tagFinder;
	protected BlogAuthorFinder blogAuthorFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected RunAsAsyncThread taskExecutor;
	protected UserActivityFinder userActivityFinder;

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	protected String getView()
	{
		return "blog/addEditPersBlogEntry";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		String beID = context.getRequest().getParameter("beID");
		int cmdId = 0;
		if (cmd.getId() == 0 && beID != null && !"".equals(beID)&& !"undefined".equals(beID)) {
			cmdId = Integer.parseInt(beID);
			cmd.setId(cmdId);
		}

		/* If there is a blog entry id in the command bean, load the details of the specified entry for editing */
		if (cmd.getId() != 0) {
			BlogEntry entry = blogEntryFinder.getBlogEntryForId(cmd.getId());
			cmd.copyPropertiesFrom(entry);
			cmd.setBlogActionLabel("Edit '"+entry.getTitle()+"'");
		}
	}

	protected Map referenceData(Object command) throws Exception
	{
		Command cmd = (Command) command;
		if (cmd.getId() == 0) {
			cmd.setBlogActionLabel("Post a new blog entry");
		}
		return new HashMap();
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command) data;
		User user =  context.getCurrentUser();
		boolean isNewEntry = false;
		if (cmd.getId()==0) isNewEntry=true;
		BlogEntry entry = null;
		PersonalBlog personalBlog = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);

		if (user == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

		if (isNewEntry) { // Creating a new blog entry
			entry = blogEntryFinder.newBlogEntry();
			entry.setPosterId(user.getId());
			entry.setDatePosted(ts);
			entry.setModified(ts);
			entry.setBody("");

		} else { // Editing an existing blog entry
			entry = blogEntryFinder.getBlogEntryForId(cmd.getId());
		} 
		entry.setTitle(cmd.getTitle());
		entry.update();

		try {
			personalBlog = prsonalBlogFinder.getPersonalBlogForId(cmd.getBid());
			if (isNewEntry) {
				entry.setPersonalBlogId(personalBlog.getId());
			}
			cmd.setPersonalBlog(personalBlog);
		} catch (ElementNotFoundException ex) {
			return new ModelAndView("/pageNotFound");
		}
		entry.setBody( ImageManipulation.manageImages(context, cmd.getBody(), cmd.getTitle(), context.getCurrentUser().getId(), entry.getId(), "BlogEntry") );
		entry.setModified(ts);
		entry.update();
		entry.setTags(cmd.getTags());

		if (isNewEntry) {
			UserActivity uActivity = userActivityFinder.newUserActivity();
			uActivity.setUserId(entry.getPosterId());
			uActivity.setBlogEntryId(entry.getId());
			uActivity.update();
			
			String ownerName = "";
			try {
				BlogEntrySubscription subs = subscriptionFinder.newBlogEntrySubscription();
				subs.setBlogEntryId(new Integer(entry.getId()));
				subs.setUserId(new Integer(context.getCurrentUser().getId()));
				subs.setCommunityId(new Integer(0));
				subs.update();
				User owner = userFinder.getUserEntity(personalBlog.getUserId());
				ownerName = owner.getFullName();
				mailPersonalSubscribers(entry, personalBlog, ownerName);
			}catch (ElementNotFoundException ex) {
			}
		}
		return new ModelAndView("redirect:/blog/blogEntry.do?id="+entry.getId());
	}

	private void mailPersonalSubscribers(final BlogEntry entry, final PersonalBlog personalBlog, final String ownerName) throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					CommunityEraContext context = contextManager.getContext(); 
					/* Get the list of subscriptions for this Personal blog */
					List<PersonalBlogSubscription> subs = subscriptionFinder.getSubscriptionsForPersonalBlog(personalBlog.getId());
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
								String sLink = context.getContextUrl() + "blog/blogEntry.do?id=" + entry.getId();
								String sUnSubscribe = context.getContextUrl() + "reg/watch.do";
								model.put("#ownerName#", ownerName);
								model.put("#blogEntryTitle#", entry.getTitle());
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

	public class Validator extends BlogEntryValidator
	{
		public String validateTitle(Object value, CommandBean cmd)
		{
			if (value.toString().equals("")) {
				return "You must set a title for this blog entry";
			}
			if (value.toString().length() > 100) {
				return "The maximum length of the title is 100 characters, please shorten your title";
			}
			return null;
		}

		public String validateBody(Object value, CommandBean cmd)
		{
			if (value.toString().equals("")) {
				return "You must enter some body text for this blog entry";
			}
			return null;
		}
	}

	public class Command extends BlogEntryDto implements CommandBean, Taggable
	{
		private String blogActionLabel = "Create a new";
		private int bid;
		private PersonalBlog personalBlog;

		public String getTaggedKeywords(){
			String taggedKeywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(personalBlog.getId(), 0, 20, "CommunityBlog");
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='blog/allBlogs.do?filterTag="+tag+"' class='normalTip euInfoSelect' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += " , ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

		public String getBlogActionLabel()
		{
			return blogActionLabel;
		}

		public void setBlogActionLabel(String blogActionLabel)
		{
			this.blogActionLabel = blogActionLabel;
		}

		public Map getPopularTags()
		{
			return null;
		}

		public int getBid() {
			return bid;
		}

		public void setBid(int bid) {
			this.bid = bid;
		}

		public PersonalBlog getPersonalBlog() {
			return personalBlog;
		}

		public void setPersonalBlog(PersonalBlog personalBlog) {
			this.personalBlog = personalBlog;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setBlogEntryFinder(BlogEntryFinder blogEntryFinder)
	{
		this.blogEntryFinder = blogEntryFinder;
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

	public final void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}

	public void setBlogAuthorFinder(BlogAuthorFinder blogAuthorFinder) {
		this.blogAuthorFinder = blogAuthorFinder;
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

	public void setPrsonalBlogFinder(PersonalBlogFinder prsonalBlogFinder) {
		this.prsonalBlogFinder = prsonalBlogFinder;
	}

	public void setUserActivityFinder(UserActivityFinder userActivityFinder) {
		this.userActivityFinder = userActivityFinder;
	}
}