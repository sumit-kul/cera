package com.era.community.blog.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;
import support.community.framework.Option;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogAuthorFinder;
import com.era.community.blog.dao.BlogEntryLikeFinder;
import com.era.community.blog.dao.CommunityBlog;
import com.era.community.blog.dao.CommunityBlogFinder;
import com.era.community.blog.dao.PersonalBlog;
import com.era.community.blog.dao.PersonalBlogFinder;
import com.era.community.communities.dao.Community;
import com.era.community.monitor.dao.CommunityBlogSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.TagFinder;

/**
 * @spring.bean name="/cid/[cec]/blog/editBlog.do"
 * @spring.bean name="/blog/editBlog.do"
 */
public class EditBlogAction extends AbstractFormAction
{
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;
	protected BlogEntryLikeFinder blogEntryLikeFinder;
	protected PersonalBlogFinder personalBlogFinder;
	protected CommunityBlogFinder communityBlogFinder;
	protected BlogAuthorFinder blogAuthorFinder;
	protected UserFinder userFinder;
	protected TagFinder tagFinder;

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command) data;
		User currentUser =  context.getCurrentUser();
		if (currentUser == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}
		Community currComm = context.getCurrentCommunity();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);

		if (currComm != null) {
			CommunityBlog bc = null;
			try {
				bc = communityBlogFinder.getCommunityBlogForCommunity(currComm);
			} catch (ElementNotFoundException ex) {
				return new ModelAndView("/pageNotFound");
			}
			bc.setName(cmd.getName());
			bc.setModified(ts);
			bc.setInactive(cmd.isInactive() ? false : true);
			bc.setAllowCoEdit(cmd.isAllowCoEdit());
			bc.setAllowComments(cmd.isAllowComments());
			bc.setModerateComments(cmd.isModerateComments());
			bc.setDefaultAllowComments(cmd.isDefaultAllowComments());
			bc.setDefaultCommentDays(cmd.getDefaultCommentDays());
			bc.update();
			currComm.setCommunityUpdated(ts);
			currComm.update();
			return new ModelAndView("redirect:"+context.getCurrentCommunityUrl()+"/blog/viewBlog.do");
		} else {
			PersonalBlog bpc = null;
			if (cmd.getBid() > 0){
				try {
					bpc = personalBlogFinder.getPersonalBlogForId(cmd.getBid());
				} catch (ElementNotFoundException ex) {
					return new ModelAndView("/pageNotFound");
				}
				if (currentUser.getId() != bpc.getUserId()) {
					return new ModelAndView("/pageNotFound");
				}
			} else {
				return new ModelAndView("/pageNotFound");
			}
			bpc.setName(cmd.getName());
			bpc.setDescription(cmd.getDescription());
			bpc.setModified(ts);
			bpc.setInactive(cmd.isInactive() ? false : true);
			bpc.setAllowCoEdit(cmd.isAllowCoEdit());
			bpc.setAllowComments(cmd.isAllowComments());
			bpc.setModerateComments(cmd.isModerateComments());
			bpc.setDefaultAllowComments(cmd.isDefaultAllowComments());
			bpc.setDefaultCommentDays(cmd.getDefaultCommentDays());
			bpc.update();
			cmd.setQueryText(bpc.getName());
			cmd.setSearchType("Blog");
			//mailSubscribers(perBlog, contextManager.getContext());
			return new ModelAndView("redirect:/blog/viewBlog.do?bid="+cmd.getBid());
		}
	}

	protected void onDisplay(Object data) throws Exception
	{
	}

	protected Map referenceData(Object command) throws Exception
	{
		Command cmd = (Command) command;
		Community currComm = contextManager.getContext().getCurrentCommunity();


		if (currComm != null) {
			CommunityBlog bc = null;
			try {
				bc = communityBlogFinder.getCommunityBlogForCommunity(currComm);
			} catch (ElementNotFoundException ex) {
			}
			cmd.setName(bc.getName());
			cmd.setInactive(bc.isInactive() ? false : true);
			cmd.setAllowCoEdit(bc.isAllowCoEdit());
			cmd.setAllowComments(bc.isAllowComments());
			cmd.setModerateComments(bc.isModerateComments());
			cmd.setDefaultAllowComments(bc.isDefaultAllowComments());
			cmd.setDefaultCommentDays(bc.getDefaultCommentDays());
		} else {
			PersonalBlog bpc = null;
			if (cmd.getBid() > 0){
				try {
					bpc = personalBlogFinder.getPersonalBlogForId(cmd.getBid());
				} catch (ElementNotFoundException ex) {
				}
				cmd.setName(bpc.getName());
				cmd.setDescription(bpc.getDescription());
				cmd.setInactive(bpc.isInactive() ? false : true);
				cmd.setAllowCoEdit(bpc.isAllowCoEdit());
				cmd.setAllowComments(bpc.isAllowComments());
				cmd.setModerateComments(bpc.isModerateComments());
				cmd.setDefaultAllowComments(bpc.isDefaultAllowComments());
				cmd.setDefaultCommentDays(bpc.getDefaultCommentDays());
			}
		}
		return new HashMap();
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	public class Validator extends CommandValidator
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

	protected String getView()
	{
		return "blog/editBlog";
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{              
		private int bid;
		private String name;
		private String description;
		private Date modified;
		private String tags;
		private String keywords;
		
		private boolean inactive;
		private boolean allowCoEdit;
		private boolean allowComments;
		private boolean moderateComments;
		private boolean defaultAllowComments;
		
		private int defaultCommentDays;
				
		public List getDefaultCommentDaysList() throws Exception
		{
			List<Option> defaultCommentDaysList = new ArrayList<Option>(12);
			Option opt0 = new Option();
			opt0.setLabel("Unlimited days");
			opt0.setValue(0);
			defaultCommentDaysList.add(opt0);

			Option opt1 = new Option();
			opt1.setLabel("1 day");
			opt1.setValue(1);
			defaultCommentDaysList.add(opt1);

			Option opt2 = new Option();
			opt2.setLabel("2 day");
			opt2.setValue(2);
			defaultCommentDaysList.add(opt2);
			
			Option opt3 = new Option();
			opt3.setLabel("3 day");
			opt3.setValue(3);
			defaultCommentDaysList.add(opt3);
			
			Option opt4 = new Option();
			opt4.setLabel("4 day");
			opt4.setValue(4);
			defaultCommentDaysList.add(opt4);
			
			Option opt5 = new Option();
			opt5.setLabel("5 day");
			opt5.setValue(5);
			defaultCommentDaysList.add(opt5);
			
			Option opt7 = new Option();
			opt7.setLabel("7 day");
			opt7.setValue(7);
			defaultCommentDaysList.add(opt7);
			
			Option opt10 = new Option();
			opt10.setLabel("10 day");
			opt10.setValue(10);
			defaultCommentDaysList.add(opt10);
			
			Option opt20 = new Option();
			opt20.setLabel("20 day");
			opt20.setValue(20);
			defaultCommentDaysList.add(opt20);
			
			Option opt30 = new Option();
			opt30.setLabel("30 day");
			opt30.setValue(30);
			defaultCommentDaysList.add(opt30);
			
			Option opt60 = new Option();
			opt60.setLabel("60 day");
			opt60.setValue(60);
			defaultCommentDaysList.add(opt60);
			
			Option opt90 = new Option();
			opt90.setLabel("90 day");
			opt90.setValue(90);
			defaultCommentDaysList.add(opt90);
			
			return defaultCommentDaysList;
		}

		public boolean isAuthor() throws Exception
		{
			User user = contextManager.getContext().getCurrentUser();
			if (user == null ) return false;
			if (contextManager.getContext().isUserAuthenticated() == false) return false;
			try {
				blogAuthorFinder.getBlogAuthorForBlogAndUser(getBid(), user.getId());
			} catch (ElementNotFoundException ex) {
				return false;
			}
			return true;
		}

		public List getSortByOptionOptions() throws Exception
		{
			List sortByOptionList = new ArrayList();
			sortByOptionList.add("Most Recent");
			sortByOptionList.add("Title");
			return sortByOptionList;
		}

		public boolean isCurrentUserSubscribed() throws Exception
		{
			try {
				if (contextManager.getContext().getCurrentUser() == null) return false;
				CommunityBlogSubscription subs = subscriptionFinder.getCommunityBlogSubscriptionForUser(this.getBid(), contextManager.getContext().getCurrentUser().getId());
				return true;
			}
			catch (ElementNotFoundException x) {
				return false;
			}
		}

		public int getBid() {
			return bid;
		}

		public void setBid(int bid) {
			this.bid = bid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Date getModified() {
			return modified;
		}

		public void setModified(Date modified) {
			this.modified = modified;
		}

		public String getTags() {
			return tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public boolean isInactive() {
			return inactive;
		}

		public void setInactive(boolean inactive) {
			this.inactive = inactive;
		}

		public boolean isAllowCoEdit() {
			return allowCoEdit;
		}

		public void setAllowCoEdit(boolean allowCoEdit) {
			this.allowCoEdit = allowCoEdit;
		}

		public boolean isAllowComments() {
			return allowComments;
		}

		public void setAllowComments(boolean allowComments) {
			this.allowComments = allowComments;
		}

		public boolean isModerateComments() {
			return moderateComments;
		}

		public void setModerateComments(boolean moderateComments) {
			this.moderateComments = moderateComments;
		}

		public boolean isDefaultAllowComments() {
			return defaultAllowComments;
		}

		public void setDefaultAllowComments(boolean defaultAllowComments) {
			this.defaultAllowComments = defaultAllowComments;
		}
		public int getDefaultCommentDays() {
			return defaultCommentDays;
		}
		public void setDefaultCommentDays(int defaultCommentDays) {
			this.defaultCommentDays = defaultCommentDays;
		}

		public String getKeywords() {
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setBlogEntryLikeFinder(BlogEntryLikeFinder blogEntryLikeFinder) {
		this.blogEntryLikeFinder = blogEntryLikeFinder;
	}

	public void setBlogAuthorFinder(BlogAuthorFinder blogAuthorFinder) {
		this.blogAuthorFinder = blogAuthorFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setPersonalBlogFinder(PersonalBlogFinder personalBlogFinder) {
		this.personalBlogFinder = personalBlogFinder;
	}

	public void setCommunityBlogFinder(CommunityBlogFinder communityBlogFinder) {
		this.communityBlogFinder = communityBlogFinder;
	}
}