package com.era.community.communities.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.CommunityBlog;
import com.era.community.blog.dao.CommunityBlogFinder;
import com.era.community.doclib.dao.DocumentLibrary;
import com.era.community.doclib.dao.DocumentLibraryFinder;
import com.era.community.events.dao.EventCalendar;
import com.era.community.events.dao.EventCalendarFinder;
import com.era.community.forum.dao.Forum;
import com.era.community.forum.dao.ForumFinder;
import com.era.community.monitor.dao.CommunityBlogSubscription;
import com.era.community.monitor.dao.CommunitySubscription;
import com.era.community.monitor.dao.DocLibSubscription;
import com.era.community.monitor.dao.EventCalendarSubscription;
import com.era.community.monitor.dao.ForumSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.monitor.dao.WikiSubscription;
import com.era.community.wiki.dao.Wiki;
import com.era.community.wiki.dao.WikiFinder;

/**
 * 
 * @spring.bean name="/cid/[cec]/community/updateSubscription.ajx"
 */
public class UpdateSubscriptionAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;
	protected CommunityBlogFinder communityBlogFinder;
	protected WikiFinder wikiFinder;
	protected DocumentLibraryFinder documentLibraryFinder;
	protected ForumFinder forumFinder;
	protected EventCalendarFinder eventCalendarFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() != null) {
			int currentUserId = context.getCurrentUser().getId();
			Command cmd = (Command) data;
			String returnString = "";
			try {
				if ("Community".equalsIgnoreCase(cmd.getType())) {
					CommunitySubscription subs;
					if (cmd.getActionFor() == 1) { // Start subs
						try {
							subs= subscriptionFinder.getCommunitySubscriptionForUser(cmd.getCommunityId(), currentUserId);
						} catch (ElementNotFoundException e) {
							subs = subscriptionFinder.newCommunitySubscription();
							subs.setUserId(currentUserId);
							subs.setCommunityId(cmd.getCommunityId());
							subs.setFrequency(1);
							subs.setWebSubscription(1);
							subs.setMailSubscription(1);
							subs.setPageSubscription(1);
							subs.update();            
						}
					} else if (cmd.getActionFor() == 2) { // stop seeing updates
						try {
							subs= subscriptionFinder.getCommunitySubscriptionForUser(cmd.getCommunityId(), currentUserId);
							subs.setPageSubscription(0);
							subs.update(); 
						} catch (ElementNotFoundException e) {
							subs = subscriptionFinder.newCommunitySubscription();
							subs.setUserId(currentUserId);
							subs.setCommunityId(cmd.getCommunityId());
							subs.setFrequency(1);
							subs.setWebSubscription(0);
							subs.setMailSubscription(1);
							subs.setPageSubscription(0);
							subs.update();
						}
					} else { // ...0 stop subs
						subs= subscriptionFinder.getCommunitySubscriptionForUser(cmd.getCommunityId(), currentUserId);
						subs.setWebSubscription(0);
						subs.setMailSubscription(0);
						subs.setPageSubscription(0);
						subs.update();
					}
				} else if ("BlogEntry".equalsIgnoreCase(cmd.getType())) {
					CommunityBlogSubscription subs;
					CommunityBlog communityBlog = communityBlogFinder.getCommunityBlogForCommunityId(cmd.getCommunityId());
					if (cmd.getActionFor() == 1) { // Start subs
						try {
							subs= subscriptionFinder.getCommunityBlogSubscriptionForUser(communityBlog.getId(), currentUserId);
						} catch (ElementNotFoundException e) {
							subs = subscriptionFinder.newCommunityBlogSubscription();
							subs.setUserId(currentUserId);
							subs.setCommunityId(cmd.getCommunityId());
							subs.setCommunityBlogId(communityBlog.getId());
							subs.setFrequency(1);
							subs.setWebSubscription(1);
							subs.setMailSubscription(1);
							subs.setPageSubscription(1);
							subs.update();            
						}
					} else if (cmd.getActionFor() == 2) { // stop seeing updates
						try {
							subs= subscriptionFinder.getCommunityBlogSubscriptionForUser(communityBlog.getId(), currentUserId);
							subs.setPageSubscription(0);
							subs.update(); 
						} catch (ElementNotFoundException e) {
							subs = subscriptionFinder.newCommunityBlogSubscription();
							subs.setUserId(currentUserId);
							subs.setCommunityId(cmd.getCommunityId());
							subs.setCommunityBlogId(communityBlog.getId());
							subs.setFrequency(1);
							subs.setWebSubscription(0);
							subs.setMailSubscription(1);
							subs.setPageSubscription(0);
							subs.update();
						}
					} else { // ...0 stop subs
						subs= subscriptionFinder.getCommunityBlogSubscriptionForUser(communityBlog.getId(), currentUserId);
						subs.setWebSubscription(0);
						subs.setMailSubscription(0);
						subs.setPageSubscription(0);
						subs.update();
					}					
				} else if ("WikiEntry".equalsIgnoreCase(cmd.getType())) {
					WikiSubscription subs;
					Wiki wiki = wikiFinder.getWikiForCommunityId(cmd.getCommunityId());
					if (cmd.getActionFor() == 1) { // Start subs
						try {
							subs= subscriptionFinder.getWikiSubscriptionForUser(wiki.getId(), currentUserId);
						} catch (ElementNotFoundException e) {
							subs = subscriptionFinder.newWikiSubscription();
							subs.setUserId(currentUserId);
							subs.setCommunityId(cmd.getCommunityId());
							subs.setWikiId(wiki.getId());
							subs.setFrequency(1);
							subs.setWebSubscription(1);
							subs.setMailSubscription(1);
							subs.setPageSubscription(1);
							subs.update();            
						}
					} else if (cmd.getActionFor() == 2) { // stop seeing updates
						try {
							subs= subscriptionFinder.getWikiSubscriptionForUser(wiki.getId(), currentUserId);
							subs.setPageSubscription(0);
							subs.update(); 
						} catch (ElementNotFoundException e) {
							subs = subscriptionFinder.newWikiSubscription();
							subs.setUserId(currentUserId);
							subs.setCommunityId(cmd.getCommunityId());
							subs.setWikiId(wiki.getId());
							subs.setFrequency(1);
							subs.setWebSubscription(0);
							subs.setMailSubscription(1);
							subs.setPageSubscription(0);
							subs.update();
						}
					} else { // ...0 stop subs
						subs= subscriptionFinder.getWikiSubscriptionForUser(wiki.getId(), currentUserId);
						subs.setWebSubscription(0);
						subs.setMailSubscription(0);
						subs.setPageSubscription(0);
						subs.update();
					}					
				} else if ("Document".equalsIgnoreCase(cmd.getType())) {
					DocLibSubscription subs;
					DocumentLibrary documentLibrary = documentLibraryFinder.getDocumentLibraryForCommunityId(cmd.getCommunityId());
					if (cmd.getActionFor() == 1) { // Start subs
						try {
							subs= subscriptionFinder.getDocLibSubscriptionForUser(documentLibrary.getId(), currentUserId);
						} catch (ElementNotFoundException e) {
							subs = subscriptionFinder.newDocLibSubscription();
							subs.setUserId(currentUserId);
							subs.setCommunityId(cmd.getCommunityId());
							subs.setDocLibId(documentLibrary.getId());
							subs.setFrequency(1);
							subs.setWebSubscription(1);
							subs.setMailSubscription(1);
							subs.setPageSubscription(1);
							subs.update();            
						}
					} else if (cmd.getActionFor() == 2) { // stop seeing updates
						try {
							subs= subscriptionFinder.getDocLibSubscriptionForUser(documentLibrary.getId(), currentUserId);
							subs.setPageSubscription(0);
							subs.update(); 
						} catch (ElementNotFoundException e) {
							subs = subscriptionFinder.newDocLibSubscription();
							subs.setUserId(currentUserId);
							subs.setCommunityId(cmd.getCommunityId());
							subs.setDocLibId(documentLibrary.getId());
							subs.setFrequency(1);
							subs.setWebSubscription(0);
							subs.setMailSubscription(1);
							subs.setPageSubscription(0);
							subs.update();
						}
					} else { // ...0 stop subs
						subs= subscriptionFinder.getDocLibSubscriptionForUser(documentLibrary.getId(), currentUserId);
						subs.setWebSubscription(0);
						subs.setMailSubscription(0);
						subs.setPageSubscription(0);
						subs.update();
					}					
				} else if ("ForumTopic".equalsIgnoreCase(cmd.getType())) {
					ForumSubscription subs;
					Forum forum = forumFinder.getForumForCommunityId(cmd.getCommunityId());
					if (cmd.getActionFor() == 1) { // Start subs
						try {
							subs= subscriptionFinder.getForumSubscriptionForUser(forum.getId(), currentUserId);
						} catch (ElementNotFoundException e) {
							subs = subscriptionFinder.newForumSubscription();
							subs.setUserId(currentUserId);
							subs.setCommunityId(cmd.getCommunityId());
							subs.setForumId(forum.getId());
							subs.setFrequency(1);
							subs.setWebSubscription(1);
							subs.setMailSubscription(1);
							subs.setPageSubscription(1);
							subs.update();            
						}
					} else if (cmd.getActionFor() == 2) { // stop seeing updates
						try {
							subs= subscriptionFinder.getForumSubscriptionForUser(forum.getId(), currentUserId);
							subs.setPageSubscription(0);
							subs.update(); 
						} catch (ElementNotFoundException e) {
							subs = subscriptionFinder.newForumSubscription();
							subs.setUserId(currentUserId);
							subs.setCommunityId(cmd.getCommunityId());
							subs.setForumId(forum.getId());
							subs.setFrequency(1);
							subs.setWebSubscription(0);
							subs.setMailSubscription(1);
							subs.setPageSubscription(0);
							subs.update();
						}
					} else { // ...0 stop subs
						subs= subscriptionFinder.getForumSubscriptionForUser(forum.getId(), currentUserId);
						subs.setWebSubscription(0);
						subs.setMailSubscription(0);
						subs.setPageSubscription(0);
						subs.update();
					}					
				} else if ("Event".equalsIgnoreCase(cmd.getType())) {
					EventCalendarSubscription subs;
					EventCalendar eventCalendar = eventCalendarFinder.getEventCalendarForCommunityId(cmd.getCommunityId());
					if (cmd.getActionFor() == 1) { // Start subs
						try {
							subs= subscriptionFinder.getEventCalendarSubscriptionForUser(eventCalendar.getId(), currentUserId);
						} catch (ElementNotFoundException e) {
							subs = subscriptionFinder.newEventCalendarSubscription();
							subs.setUserId(currentUserId);
							subs.setCommunityId(cmd.getCommunityId());
							subs.setEventCalendarId(eventCalendar.getId());
							subs.setFrequency(1);
							subs.setWebSubscription(1);
							subs.setMailSubscription(1);
							subs.setPageSubscription(1);
							subs.update();            
						}
					} else if (cmd.getActionFor() == 2) { // stop seeing updates
						try {
							subs= subscriptionFinder.getEventCalendarSubscriptionForUser(eventCalendar.getId(), currentUserId);
							subs.setPageSubscription(0);
							subs.update(); 
						} catch (ElementNotFoundException e) {
							subs = subscriptionFinder.newEventCalendarSubscription();
							subs.setUserId(currentUserId);
							subs.setCommunityId(cmd.getCommunityId());
							subs.setEventCalendarId(eventCalendar.getId());
							subs.setFrequency(1);
							subs.setWebSubscription(0);
							subs.setMailSubscription(1);
							subs.setPageSubscription(0);
							subs.update();
						}
					} else { // ...0 stop subs
						subs= subscriptionFinder.getEventCalendarSubscriptionForUser(eventCalendar.getId(), currentUserId);
						subs.setWebSubscription(0);
						subs.setMailSubscription(0);
						subs.setPageSubscription(0);
						subs.update();
					}					
				} 			

				returnString = "";
				HttpServletResponse resp = contextManager.getContext().getResponse();
				resp.setContentType("text/html");
				Writer out = resp.getWriter();
				out.write(returnString);
				out.close();
				return null;
			} catch (ElementNotFoundException e) {
			}
		}
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int communityId;
		private int actionFor;
		private String type;

		public int getCommunityId() {
			return communityId;
		}
		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
		public int getActionFor() {
			return actionFor;
		}
		public void setActionFor(int actionFor) {
			this.actionFor = actionFor;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}

	}

	/**
	 * @param contextManager the contextManager to set
	 */
	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	/**
	 * @param subscriptionFinder the subscriptionFinder to set
	 */
	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setWikiFinder(WikiFinder wikiFinder) {
		this.wikiFinder = wikiFinder;
	}

	public void setDocumentLibraryFinder(DocumentLibraryFinder documentLibraryFinder) {
		this.documentLibraryFinder = documentLibraryFinder;
	}

	public void setForumFinder(ForumFinder forumFinder) {
		this.forumFinder = forumFinder;
	}

	public void setEventCalendarFinder(EventCalendarFinder eventCalendarFinder) {
		this.eventCalendarFinder = eventCalendarFinder;
	}

	public void setCommunityBlogFinder(CommunityBlogFinder communityBlogFinder) {
		this.communityBlogFinder = communityBlogFinder;
	}
}