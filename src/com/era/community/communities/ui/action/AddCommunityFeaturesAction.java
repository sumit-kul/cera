package com.era.community.communities.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.EntityWrapper;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.CommunityFeature;
import com.era.community.blog.dao.CommunityBlog;
import com.era.community.blog.dao.CommunityBlogFeature;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.events.dao.EventCalendar;
import com.era.community.events.dao.EventCalendarFeature;
import com.era.community.faqs.dao.generated.HelpEntryEntity;
import com.era.community.forum.dao.Forum;
import com.era.community.forum.dao.ForumFeature;
import com.era.community.monitor.dao.CommunityBlogSubscription;
import com.era.community.monitor.dao.EventCalendarSubscription;
import com.era.community.monitor.dao.ForumSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.monitor.dao.WikiSubscription;
import com.era.community.pers.dao.User;
import com.era.community.wiki.dao.Wiki;
import com.era.community.wiki.dao.WikiFeature;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/communities/addCommunityFeatures.ajx" 
 */
public class AddCommunityFeaturesAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private CommunityFinder communityFinder; 
	protected SubscriptionFinder subscriptionFinder;
	protected CommunityBlogFeature communityBlogFeature;  
	protected EventCalendarFeature eventCalendarFeature;
	protected ForumFeature forumFeature;
	protected WikiFeature wikiFeature;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data; 
		HttpServletResponse resp = context.getResponse();

		User currentUser = context.getCurrentUser();
		if (currentUser == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}
		
		Community currCommunity = communityFinder.getCommunityForId(cmd.getCommunityId());
		if (currCommunity != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String dt = sdf.format(now);
			Timestamp ts = Timestamp.valueOf(dt);
			
			Iterator i = contextManager.getContext().getFeatures().iterator();
			while (i.hasNext()) {
				CommunityFeature feature = (CommunityFeature) i.next();
				if (!(feature.isFeatureMandatory() || feature.isFeatureEnabledForCommunity(currCommunity))) {
					if (cmd.getFeatureName().equalsIgnoreCase(feature.getFeatureName())) {
						feature.setFeatureEnabledForCommunity(currCommunity, true);
						
						if (cmd.getFeatureName().equalsIgnoreCase("Assignments")) {
							
						} else if (cmd.getFeatureName().equalsIgnoreCase("Blog")) {
							CommunityBlogSubscription blogConSub = subscriptionFinder.newCommunityBlogSubscription();
							CommunityBlog cons = (CommunityBlog)communityBlogFeature.getFeatureForCommunity(currCommunity);
							blogConSub.setCommunityBlogId(Integer.valueOf(cons.getId()));
							blogConSub.setUserId(currentUser.getId());
							blogConSub.setCommunityId(cmd.getCommunityId());
							blogConSub.setFrequency(1);
							blogConSub.setWebSubscription(1);
							blogConSub.setMailSubscription(1);
							blogConSub.setPageSubscription(1);
							blogConSub.setModified(ts);
							blogConSub.update();							
						} else if (cmd.getFeatureName().equalsIgnoreCase("Events")) {
							EventCalendarSubscription eventSub = subscriptionFinder.newEventCalendarSubscription();
							EventCalendar ev = (EventCalendar)eventCalendarFeature.getFeatureForCommunity(currCommunity);
							eventSub.setEventCalendarId(Integer.valueOf(ev.getId()));
							eventSub.setUserId(currentUser.getId());
							eventSub.setCommunityId(cmd.getCommunityId());
							eventSub.setFrequency(1);
							eventSub.setWebSubscription(1);
							eventSub.setMailSubscription(1);
							eventSub.setPageSubscription(1);
							eventSub.setModified(ts);
							eventSub.update();
						} else if (cmd.getFeatureName().equalsIgnoreCase("Forum")) {
							ForumSubscription forumSub = subscriptionFinder.newForumSubscription();
							Forum forum = (Forum)forumFeature.getFeatureForCommunity(currCommunity);
							forumSub.setForumId(Integer.valueOf(forum.getId()));
							forumSub.setUserId(currentUser.getId());
							forumSub.setCommunityId(cmd.getCommunityId());
							forumSub.setFrequency(1);
							forumSub.setWebSubscription(1);
							forumSub.setMailSubscription(1);
							forumSub.setPageSubscription(1);
							forumSub.setModified(ts);
							forumSub.update();
						} else if (cmd.getFeatureName().equalsIgnoreCase("Calendar")) {
							
						} else if (cmd.getFeatureName().equalsIgnoreCase("NewsFeeds")) {
							
						} else if (cmd.getFeatureName().equalsIgnoreCase("Wiki")) {
							WikiSubscription wikiSub = subscriptionFinder.newWikiSubscription();
							Wiki wiki = (Wiki)wikiFeature.getFeatureForCommunity(currCommunity);
							wikiSub.setWikiId(Integer.valueOf(wiki.getId()));
							wikiSub.setUserId(currentUser.getId());
							wikiSub.setCommunityId(cmd.getCommunityId());
							wikiSub.setFrequency(1);
							wikiSub.setWebSubscription(1);
							wikiSub.setMailSubscription(1);
							wikiSub.setPageSubscription(1);
							wikiSub.setModified(ts);
							wikiSub.update();
						} 
					}
				}
			}
		}
		JSONObject json = new JSONObject();
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int communityId;
		private String featureName;

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}

		public String getFeatureName() {
			return featureName;
		}

		public void setFeatureName(String featureName) {
			this.featureName = featureName;
		}
	}

	public class RowBean extends HelpEntryEntity implements EntityWrapper
	{
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setEventCalendarFeature(EventCalendarFeature eventCalendarFeature) {
		this.eventCalendarFeature = eventCalendarFeature;
	}

	public void setForumFeature(ForumFeature forumFeature) {
		this.forumFeature = forumFeature;
	}

	public void setWikiFeature(WikiFeature wikiFeature) {
		this.wikiFeature = wikiFeature;
	}

	public void setCommunityBlogFeature(CommunityBlogFeature communityBlogFeature) {
		this.communityBlogFeature = communityBlogFeature;
	}
}