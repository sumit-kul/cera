package com.era.community.pers.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;

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
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.wiki.dao.Wiki;
import com.era.community.wiki.dao.WikiFinder;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/subscriptionOptions.ajx" 
 */
public class SubscriptionOptionsAction extends AbstractCommandAction
{
	public static final String REQUIRES_AUTHENTICATION = "";
	private CommunityEraContextManager contextManager; 
	private SubscriptionFinder subscriptionFinder;
	private ContactFinder contactFinder;
	private UserFinder userFinder;
	private CommunityBlogFinder communityBlogFinder;
	private WikiFinder wikiFinder;
	private DocumentLibraryFinder documentLibraryFinder;
	private ForumFinder forumFinder;
	private EventCalendarFinder eventCalendarFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		Command cmd = (Command)data; 
		String info = getCommunityOption(context, cmd.getCommId(), cmd.getUserId(), cmd.getItemId(), cmd.getType(), cmd.getContentType(), cmd.getFromCommunity());
		JSONObject json = new JSONObject();
		json.put("optionInfo", info);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	public String getCommunityOption(CommunityEraContext context, int commId, int userId, int itemId, String type, String contentType, String fromCommunity) throws Exception
	{
		User currUser = context.getCurrentUser();
		String returnString = "";
		if (currUser != null) {
			returnString += "<ul style='list-style: none outside none;'>";
			if ("Community".equalsIgnoreCase(type) && commId > 0) {
				try {
					CommunitySubscription subs= subscriptionFinder.getCommunitySubscriptionForUser(commId, currUser.getId());
					if (subs.getPageSubscription() == 1) {
						if (!"YES".equalsIgnoreCase(fromCommunity)) {
							returnString += "<li onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+2+"&#39;, &#39;Community&#39;)' href='javascript:void(0);'><p>Hide updates from community</p><p class='explnt'>Stop seeing updates but keep following</p></li>";
						}
					}
					returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+0+"&#39;, &#39;Community&#39;)' href='javascript:void(0);'><p>Unfollow Community</p><p class='explnt'>Unfollow community completely</p></li>";
				} catch (ElementNotFoundException e) {	
					if (!"YES".equalsIgnoreCase(fromCommunity)) {
						returnString += "<li onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+2+"&#39;, &#39;Community&#39;)' href='javascript:void(0);'><p>Hide updates from community</p><p class='explnt'>You are not following, stop seeing updates</p></li>";
					}
					returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+1+"&#39;, &#39;Community&#39;)' href='javascript:void(0);'><p>Follow Community</p><p class='explnt'>Start following community</p></li>";
				}
			} else if ("BlogEntry".equalsIgnoreCase(type) && commId > 0) {
				try {
					CommunityBlog communityBlog = communityBlogFinder.getCommunityBlogForCommunityId(commId);
					try {
						CommunityBlogSubscription subs= subscriptionFinder.getCommunityBlogSubscriptionForUser(communityBlog.getId(), currUser.getId());
						if (subs.getPageSubscription() == 1) {
							returnString += "<li onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+2+"&#39;, &#39;BlogEntry&#39;)' href='javascript:void(0);'><p>Hide updates from community blog</p><p class='explnt'>Stop seeing updates but keep following</p></li>";
						}
						returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+0+"&#39;, &#39;BlogEntry&#39;)' href='javascript:void(0);'><p>Unfollow community blog</p><p class='explnt'>Unfollow community blog completely</p></li>";
					} catch (ElementNotFoundException e) {	
						returnString += "<li onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+2+"&#39;, &#39;BlogEntry&#39;)' href='javascript:void(0);'><p>Hide updates from community blog</p><p class='explnt'>You are not following, stop seeing updates</p></li>";
						returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+1+"&#39;, &#39;BlogEntry&#39;)' href='javascript:void(0);'><p>Follow community blog</p><p class='explnt'>Start following community blog</p></li>";
					}
				} catch (ElementNotFoundException exp) {}
			} else if ("WikiEntry".equalsIgnoreCase(type)) {
				try {
					Wiki wiki = wikiFinder.getWikiForCommunityId(commId);
					try {
						WikiSubscription subs= subscriptionFinder.getWikiSubscriptionForUser(wiki.getId(), currUser.getId());
						if (subs.getPageSubscription() == 1) {
							returnString += "<li onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+2+"&#39;, &#39;WikiEntry&#39;)' href='javascript:void(0);'><p>Hide updates from this wiki</p><p class='explnt'>Stop seeing updates but keep following</p></li>";
						}
						returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+0+"&#39;, &#39;WikiEntry&#39;)' href='javascript:void(0);'><p>Unfollow wiki</p><p class='explnt'>Unfollow this wiki completely</p></li>";
					} catch (ElementNotFoundException e) {
						returnString += "<li onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+2+"&#39;, &#39;WikiEntry&#39;)' href='javascript:void(0);'><p>Hide updates from this wiki</p><p class='explnt'>You are not following, stop seeing updates</p></li>";
						returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+1+"&#39;, &#39;WikiEntry&#39;)' href='javascript:void(0);'><p>Follow wiki</p><p class='explnt'>Start following this wiki</p></li>";
					}
				} catch (ElementNotFoundException exp) {}				
			} else if ("Document".equalsIgnoreCase(type)) {
				try {
					DocumentLibrary documentLibrary = documentLibraryFinder.getDocumentLibraryForCommunityId(commId);
					try {
						DocLibSubscription subs= subscriptionFinder.getDocLibSubscriptionForUser(documentLibrary.getId(), currUser.getId());
						if (subs.getPageSubscription() == 1) {
							returnString += "<li onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+2+"&#39;, &#39;Document&#39;)' href='javascript:void(0);'><p>Hide updates from this library</p><p class='explnt'>Stop seeing updates but keep following</p></li>";
						}
						returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+0+"&#39;, &#39;Document&#39;)' href='javascript:void(0);'><p>Unfollow library</p><p class='explnt'>Unfollow community library completely</p></li>";
					} catch (ElementNotFoundException e) {	
						returnString += "<li onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+2+"&#39;, &#39;Document&#39;)' href='javascript:void(0);'><p>Hide updates from this library</p><p class='explnt'>You are not following, stop seeing updates</p></li>";
						returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+1+"&#39;, &#39;Document&#39;)' href='javascript:void(0);'><p>Follow community library</p><p class='explnt'>Start following community library</p></li>";
					}
				} catch (ElementNotFoundException exp) {}				
			} else if ("ForumTopic".equalsIgnoreCase(type)) {
				try {
					Forum forum = forumFinder.getForumForCommunityId(commId);
					try {
						ForumSubscription subs= subscriptionFinder.getForumSubscriptionForUser(forum.getId(), currUser.getId());
						if (subs.getPageSubscription() == 1) {
							returnString += "<li onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+2+"&#39;, &#39;ForumTopic&#39;)' href='javascript:void(0);'><p>Hide updates from community forum</p><p class='explnt'>Stop seeing updates but keep following</p></li>";
						}
						returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;, &#39;"+0+"&#39;, &#39;ForumTopic&#39;)' href='javascript:void(0);'><p>Unfollow community forum</p><p class='explnt'>Unfollow community forum completely</p></li>";
					} catch (ElementNotFoundException e) {
						returnString += "<li onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+2+"&#39;, &#39;ForumTopic&#39;)' href='javascript:void(0);'><p>Hide updates from community forum</p><p class='explnt'>You are not following, stop seeing updates</p></li>";
						returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;, &#39;"+1+"&#39;, &#39;ForumTopic&#39;)' href='javascript:void(0);'><p>Follow community forum</p><p class='explnt'>Start following community forum</p></li>";
					}
				} catch (ElementNotFoundException exp) {}				
			} else if ("Event".equalsIgnoreCase(type)) {
				try {
					EventCalendar eventCalendar = eventCalendarFinder.getEventCalendarForCommunityId(commId);
					try {
						EventCalendarSubscription subs= subscriptionFinder.getEventCalendarSubscriptionForUser(eventCalendar.getId(), currUser.getId());
						if (subs.getPageSubscription() == 1) {
							returnString += "<li onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+2+"&#39;, &#39;Event&#39;)' href='javascript:void(0);'><p>Hide updates from community calendar</p><p class='explnt'>Stop seeing updates but keep following</p></li>";
						}
						returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+0+"&#39;, &#39;Event&#39;)' href='javascript:void(0);'><p>Unfollow community calendar</p><p class='explnt'>Unfollow community calendar completely</p></li>";
					} catch (ElementNotFoundException e) {	
						returnString += "<li onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+2+"&#39;, &#39;Event&#39;)' href='javascript:void(0);'><p>Hide updates from community calendar</p><p class='explnt'>You are not following, stop seeing updates</p></li>";
						returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateSubscription(&#39;"+commId+"&#39;, &#39;"+currUser.getId()+"&#39;,&#39;"+1+"&#39;, &#39;Event&#39;)' href='javascript:void(0);'><p>Follow community calendar</p><p class='explnt'>Start following community calendar</p></li>";
					}
				} catch (ElementNotFoundException exp) {}				
			}

			if (userId > 0 && currUser.getId() != userId) {
				User otherUser = userFinder.getUserEntity(userId);
				try { 
					Contact contact = contactFinder.getContact(currUser.getId(), userId);
					if (contact.getStatus() == 1) { 
						if (contact.getOwningUserId() == currUser.getId()) { // Current user is Owner
							if (contact.getFollowContact() == 1) { // Check FollowContact
								returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateFollowing(&#39;"+userId+"&#39;, &#39;"+currUser.getId()+"&#39;, &#39;"+currUser.getFirstName()+"&#39;, &#39;"+1+"&#39;, &#39;"+0+"&#39;)' href='javascript:void(0);'><p>Unfollow " + otherUser.getFirstName() + "</p><p class='explnt'>Stop seeing updates but stay connected</p></li>";
							} else {
								returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateFollowing(&#39;"+userId+"&#39;, &#39;"+currUser.getId()+"&#39;, &#39;"+currUser.getFirstName()+"&#39;,&#39;"+1+"&#39;, &#39;"+1+"&#39;)' href='javascript:void(0);'><p>Follow " + otherUser.getFirstName() + "</p><p class='explnt'>Start following " + otherUser.getFirstName() + "</p></li>";
							}
						} else { // Current user is Contact
							if (contact.getFollowOwner() == 1) { // Check FollowOwner
								returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateFollowing(&#39;"+userId+"&#39;, &#39;"+currUser.getId()+"&#39;, &#39;"+currUser.getFirstName()+"&#39;,&#39;"+0+"&#39;, &#39;"+0+"&#39;)' href='javascript:void(0);'><p>Unfollow " + otherUser.getFirstName() + "</p><p class='explnt'>Stop seeing updates but stay connected</p></li>";
							} else {
								returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateFollowing(&#39;"+userId+"&#39;, &#39;"+currUser.getId()+"&#39;, &#39;"+currUser.getFirstName()+"&#39;,&#39;"+0+"&#39;, &#39;"+1+"&#39;)' href='javascript:void(0);'><p>Follow " + otherUser.getFirstName() + "</p><p class='explnt'>Start following " + otherUser.getFirstName() + "</p></li>";
							}
						}
					}
				} catch (ElementNotFoundException e) {
					//returnString += "<li style='border-bottom: 1px solid #A7A9AB;' onClick='updateFollowing(&#39;"+userId+"&#39;, &#39;"+currUser.getId()+"&#39;, &#39;"+currUser.getFirstName()+"&#39;,&#39;"+0+"&#39;, &#39;"+1+"&#39;)' href='javascript:void(0);'><p>Follow " + otherUser.getFirstName() + "</p><p class='explnt'>Start following " + otherUser.getFirstName() + "</p></li>";
				}
			}
			if ("BlogEntry".equalsIgnoreCase(type) || "PersonalBlogEntry".equalsIgnoreCase(type) || "WikiEntry".equalsIgnoreCase(type) 
					|| ("Document".equalsIgnoreCase(type) && "Single".equalsIgnoreCase(contentType)) || "ForumTopic".equalsIgnoreCase(type) || "Event".equalsIgnoreCase(type)) {
				returnString += "<li onClick='copyLink(&#39;"+commId+"&#39;, &#39;"+userId+"&#39;, &#39;"+itemId+"&#39;, &#39;"+type+"&#39;)' href='javascript:void(0);'>Copy link to share</li>";
			}

			returnString += "</ul>";
		}
		return returnString;
	}

	public class Command extends IndexCommandBeanImpl implements CommandBean 
	{
		private int commId;
		private int userId;
		private int itemId;
		private String type;
		private String contentType;
		private String fromCommunity;

		public int getCommId() {
			return commId;
		}
		public void setCommId(int commId) {
			this.commId = commId;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getFromCommunity() {
			return fromCommunity;
		}
		public void setFromCommunity(String fromCommunity) {
			this.fromCommunity = fromCommunity;
		}
		public int getItemId() {
			return itemId;
		}
		public void setItemId(int itemId) {
			this.itemId = itemId;
		}
		public String getContentType() {
			return contentType;
		}
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public SubscriptionFinder getSubscriptionFinder() {
		return subscriptionFinder;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
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