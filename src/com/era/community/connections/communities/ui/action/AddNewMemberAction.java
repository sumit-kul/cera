package com.era.community.connections.communities.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.CommunityBlog;
import com.era.community.blog.dao.CommunityBlogFeature;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.doclib.dao.DocumentLibrary;
import com.era.community.doclib.dao.DocumentLibraryFeature;
import com.era.community.events.dao.EventCalendar;
import com.era.community.events.dao.EventCalendarFeature;
import com.era.community.forum.dao.Forum;
import com.era.community.forum.dao.ForumFeature;
import com.era.community.monitor.dao.CommunityBlogSubscription;
import com.era.community.monitor.dao.CommunitySubscription;
import com.era.community.monitor.dao.DocLibSubscription;
import com.era.community.monitor.dao.EventCalendarSubscription;
import com.era.community.monitor.dao.ForumSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.monitor.dao.WikiSubscription;
import com.era.community.pers.dao.User;
import com.era.community.wiki.dao.Wiki;
import com.era.community.wiki.dao.WikiFeature;

/**
 *  @spring.bean name="/connections/addNewMember.ajx" 
 */
public class AddNewMemberAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	protected CommunityFinder communityFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected CommunityBlogFeature communityBlogFeature;  
	protected EventCalendarFeature eventCalendarFeature;
	protected ForumFeature forumFeature;
	protected WikiFeature wikiFeature;
	protected DocumentLibraryFeature documentLibraryFeature;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		User currUser = context.getCurrentUser();
		Community comm = communityFinder.getCommunityForId(cmd.getCommunityId());

		String[] myJsonData = context.getRequest().getParameterValues("json[]");
		if(myJsonData != null && myJsonData.length > 0) {
			for (int i=0; i < myJsonData.length; ++i) {
				String usrInfo = myJsonData[i];
				if (usrInfo != null && !"".equals(usrInfo)) {
					String[] member = usrInfo.split("#", 2);
					int memberId = Integer.parseInt(member[0]);
					int memberRole = Integer.parseInt(member[1]);

					comm.getMemberList().addMember(memberId, memberRole == 0 ? "Member" : "Community Admin", currUser.getId());
					CommunitySubscription commSub;
					CommunityBlogSubscription blogConSub;
					EventCalendarSubscription eventSub;
					ForumSubscription forumSub;
					DocLibSubscription libSub;
					WikiSubscription wikiSub;

					Integer commId = Integer.valueOf(comm.getId());

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date now = new Date();
					String dt = sdf.format(now);
					Timestamp ts = Timestamp.valueOf(dt);

					try {
						commSub= subscriptionFinder.getCommunitySubscriptionForUser(comm.getId(), memberId);
					} catch (ElementNotFoundException e) {
						commSub = subscriptionFinder.newCommunitySubscription();
						commSub.setUserId(memberId);
						commSub.setCommunityId(commId);
						commSub.setFrequency(1);
						commSub.setWebSubscription(1);
						commSub.setMailSubscription(1);
						commSub.setPageSubscription(1);
						commSub.setModified(ts);
						commSub.update();            
					}

					CommunityBlog cons = (CommunityBlog)communityBlogFeature.getFeatureForCommunity(comm);
					try {
						blogConSub= subscriptionFinder.getCommunityBlogSubscriptionForUser(cons.getId(), memberId);
					} catch (ElementNotFoundException e) {
						blogConSub = subscriptionFinder.newCommunityBlogSubscription();
						blogConSub.setCommunityBlogId(Integer.valueOf(cons.getId()));
						blogConSub.setUserId(memberId);
						blogConSub.setCommunityId(commId);
						blogConSub.setFrequency(1);
						blogConSub.setWebSubscription(1);
						blogConSub.setMailSubscription(1);
						blogConSub.setPageSubscription(1);
						blogConSub.setModified(ts);
						blogConSub.update();           
					}

					EventCalendar ev = (EventCalendar)eventCalendarFeature.getFeatureForCommunity(comm);
					try {
						eventSub= subscriptionFinder.getEventCalendarSubscriptionForUser(ev.getId(), memberId);
					} catch (ElementNotFoundException e) {
						eventSub = subscriptionFinder.newEventCalendarSubscription();
						eventSub.setEventCalendarId(ev.getId());
						eventSub.setUserId(memberId);
						eventSub.setCommunityId(commId);
						eventSub.setFrequency(1);
						eventSub.setWebSubscription(1);
						eventSub.setMailSubscription(1);
						eventSub.setPageSubscription(1);
						eventSub.setModified(ts);
						eventSub.update();           
					}

					Forum forum = (Forum)forumFeature.getFeatureForCommunity(comm);
					try {
						forumSub = subscriptionFinder.getForumSubscriptionForUser(forum.getId(), memberId);
					} catch (ElementNotFoundException e) {
						forumSub = subscriptionFinder.newForumSubscription();
						forumSub.setForumId(forum.getId());
						forumSub.setUserId(memberId);
						forumSub.setCommunityId(commId);
						forumSub.setFrequency(1);
						forumSub.setWebSubscription(1);
						forumSub.setMailSubscription(1);
						forumSub.setPageSubscription(1);
						forumSub.setModified(ts);
						forumSub.update();         
					}

					DocumentLibrary lib = (DocumentLibrary)documentLibraryFeature.getFeatureForCommunity(comm);
					try {
						libSub = subscriptionFinder.getDocLibSubscriptionForUser(lib.getId(), memberId);
					} catch (ElementNotFoundException e) {
						libSub = subscriptionFinder.newDocLibSubscription();
						libSub.setDocLibId(lib.getId());
						libSub.setUserId(memberId);
						libSub.setCommunityId(commId);
						libSub.setFrequency(1);
						libSub.setWebSubscription(1);
						libSub.setMailSubscription(1);
						libSub.setPageSubscription(1);
						libSub.setModified(ts);
						libSub.update();       
					}

					Wiki wiki = (Wiki)wikiFeature.getFeatureForCommunity(comm);
					try {
						wikiSub = subscriptionFinder.getWikiSubscriptionForUser(wiki.getId(), memberId);
					} catch (ElementNotFoundException e) {
						wikiSub = subscriptionFinder.newWikiSubscription();
						wikiSub.setWikiId(wiki.getId());
						wikiSub.setUserId(memberId);
						wikiSub.setCommunityId(commId);
						wikiSub.setFrequency(1);
						wikiSub.setWebSubscription(1);
						wikiSub.setMailSubscription(1);
						wikiSub.setPageSubscription(1);
						wikiSub.setModified(ts);
						wikiSub.update();       
					}
					//mailToJoinee(comm, user, cmd);

				}
			}
		}

		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write("");
		out.close();
		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int communityId;

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
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

	public void setDocumentLibraryFeature(
			DocumentLibraryFeature documentLibraryFeature) {
		this.documentLibraryFeature = documentLibraryFeature;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setCommunityBlogFeature(CommunityBlogFeature communityBlogFeature) {
		this.communityBlogFeature = communityBlogFeature;
	}
}