package com.era.community.blog.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogEntry;
import com.era.community.blog.dao.BlogEntryCommentFinder;
import com.era.community.blog.dao.BlogEntryCommentLikeFinder;
import com.era.community.blog.dao.BlogEntryFinder;
import com.era.community.blog.dao.BlogEntryLikeFinder;
import com.era.community.blog.dao.CommunityBlogFinder;
import com.era.community.blog.dao.PersonalBlogFinder;
import com.era.community.communities.dao.CommunityActivityFinder;
import com.era.community.monitor.dao.SubscriptionFinder;

/**
 *
 * @spring.bean name="/reg/deleteBlogEntry.do"
 */
public class DeleteBlogEntryAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected BlogEntryFinder blogentryFinder; 
	protected CommunityBlogFinder communityBlogFinder;
	protected PersonalBlogFinder personalBlogFinder;
	protected BlogEntryCommentLikeFinder blogEntryCommentLikeFinder;
	protected BlogEntryCommentFinder blogEntryCommentFinder;
	protected BlogEntryLikeFinder blogEntryLikeFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected CommunityActivityFinder communityActivityFinder;

	public BlogEntryFinder getBlogentryFinder()
	{
		return blogentryFinder;
	}

	public void setBlogentryFinder(BlogEntryFinder blogentryFinder)
	{
		this.blogentryFinder = blogentryFinder;
	}

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		BlogEntry blogentry = blogentryFinder.getBlogEntryForId(cmd.getId());
		if ( blogentry != null && blogentry.isWriteAllowed( context.getCurrentUserDetails() )) {
			if (cmd.getPbId() > 0) {
				subscriptionFinder.deleteSubscriptionsForPersonalBlogEntry(blogentry.getId());
				blogEntryCommentLikeFinder.clearBlogEntryCommentLikesForBlogEntry(blogentry);
				blogEntryCommentFinder.clearBlogEntryCommentsForBlogEntry(blogentry);
				blogEntryLikeFinder.clearBlogEntryLikesForBlogEntry(blogentry);
				blogentry.delete();
				return new ModelAndView("redirect:/blog/viewBlog.do?bid="+cmd.getPbId());
			} else if (cmd.getCbId() > 0) {
				subscriptionFinder.deleteSubscriptionsForCommunityBlogEntry(blogentry.getId(), cmd.getCommId());
				blogEntryCommentLikeFinder.clearBlogEntryCommentLikesForBlogEntry(blogentry);
				blogEntryCommentFinder.clearBlogEntryCommentsForBlogEntry(blogentry);
				blogEntryLikeFinder.clearBlogEntryLikesForBlogEntry(blogentry);
				communityActivityFinder.clearCommunityActivityForBlogEntry(blogentry.getId(), cmd.getCommId());
				blogentry.delete();
			} 
			return new ModelAndView("redirect:/cid/"+cmd.getCommId()+"/blog/viewBlog.do");
		}
		return new ModelAndView("redirect:/error/pageNotFound.do");
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int id;
		private int cbId;
		private int pbId;
		private int commId;

		public int getId()
		{
			return id;
		}
		public void setId(int id)
		{
			this.id = id;
		}
		public int getCbId() {
			return cbId;
		}
		public void setCbId(int cbId) {
			this.cbId = cbId;
		}
		public int getPbId() {
			return pbId;
		}
		public void setPbId(int pbId) {
			this.pbId = pbId;
		}
		public int getCommId() {
			return commId;
		}
		public void setCommId(int commId) {
			this.commId = commId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setBlogEntryLikeFinder(BlogEntryLikeFinder blogEntryLikeFinder) {
		this.blogEntryLikeFinder = blogEntryLikeFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setBlogEntryCommentLikeFinder(
			BlogEntryCommentLikeFinder blogEntryCommentLikeFinder) {
		this.blogEntryCommentLikeFinder = blogEntryCommentLikeFinder;
	}

	public void setBlogEntryCommentFinder(
			BlogEntryCommentFinder blogEntryCommentFinder) {
		this.blogEntryCommentFinder = blogEntryCommentFinder;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setCommunityBlogFinder(CommunityBlogFinder communityBlogFinder) {
		this.communityBlogFinder = communityBlogFinder;
	}

	public void setPersonalBlogFinder(PersonalBlogFinder personalBlogFinder) {
		this.personalBlogFinder = personalBlogFinder;
	}

	public void setCommunityActivityFinder(
			CommunityActivityFinder communityActivityFinder) {
		this.communityActivityFinder = communityActivityFinder;
	}
}