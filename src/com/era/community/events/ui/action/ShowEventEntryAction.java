package com.era.community.events.ui.action;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.util.StringHelper;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.LinkBuilderContext;
import com.era.community.base.Taggable;
import com.era.community.events.dao.Event;
import com.era.community.events.dao.EventFinder;
import com.era.community.events.dao.EventLikeFinder;
import com.era.community.events.ui.dto.EventDto;
import com.era.community.location.dao.Country;
import com.era.community.location.dao.CountryFinder;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;

/**
 * @spring.bean name="/event/showEventEntry.do"
 * @spring.bean name="/cid/[cec]/event/showEventEntry.do" 
 */
public class ShowEventEntryAction extends AbstractCommandAction
{
	private EventFinder eventFinder;
	private UserFinder userFinder;
	private CommunityEraContextManager contextManager;
	private TagFinder tagFinder;
	private CountryFinder countryFinder;
	private EventLikeFinder eventLikeFinderer;
	private SubscriptionFinder subscriptionFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		Event event = eventFinder.getEventForId( cmd.getId() );
		if (event.getStatus() == 0 && 
				(context.getCurrentUser() == null || event.getPosterId() != context.getCurrentUser().getId())) {
			return new ModelAndView("/pageNotFound");
		}

		cmd.copyPropertiesFrom( event );
		cmd.setLatitude(BigDecimal.valueOf(event.getLatitude()));
		cmd.setLongitude(BigDecimal.valueOf(event.getLongitude()));
		cmd.setKeywords(event.getKeywords());
		cmd.setStartDate(event.getStartDate());
		cmd.setEndDate(event.getEndDate());
		cmd.setStatus(event.getStatus());
		cmd.setPopularTags( event.getPopularTags());
		User user = userFinder.getUserEntity( event.getPosterId() );
		cmd.setUserName( user.getFirstName() + " " + user.getLastName() );
		cmd.setUserPhotoPresent(user.isPhotoPresent());
		cmd.setEventPosterPresent(event.isPhotoPresent());
		cmd.setDisplayBody(getDisplayBody(event.getDescription()));
		cmd.setLikeCount(eventLikeFinderer.getLikeCountForEvent(event.getId()));
		if (context.getCurrentUser() != null) {
			int userID = context.getCurrentUser().getId();
			cmd.setInvitee(event.isInvitee(userID));
			cmd.setLikeAllowed(event.getPosterId() != userID ? true : false);
			cmd.setReplied(event.isReplied(userID));
			cmd.setAlreadyLike(event.isAlreadyLike(userID));
		} else {
			cmd.setInvitee(false);
			cmd.setLikeAllowed(false);
			cmd.setReplied(false);
			cmd.setAlreadyLike(false);
		}
		
		User poster = userFinder.getUserEntity(event.getPosterId());
		cmd.setPosterPhotoPresent(poster.isPhotoPresent());
		cmd.setPosterId(event.getPosterId());
		cmd.setPosterName(poster.getFullName());
		cmd.setPosterWeb(event.getWeblink());
		cmd.setPosterEmail(poster.getEmailAddress());
		//cmd.setPosterAbout(poster.getAbout());
		/*String phoneCountryCode = "";
		if (poster.getCuntryCodeId() > 0) {
			try {
				Country country = countryFinder.getCountryForId(poster.getCuntryCodeId());
				phoneCountryCode = country.getPhoneCountryCode();
			} catch (ElementNotFoundException e) {}
		}*/
		cmd.setPosterMobile(event.getContactTel());
		
		int eventCount = eventFinder.getEventCountForHost(event.getPosterId());
		cmd.setEventCountForHost(eventCount);

		LinkBuilderContext linkBuilder = context.getLinkBuilder();
		linkBuilder.addToolLink("Add a new event", context.getCurrentCommunityUrl() +  "/event/addEvent.do", "Add a new event", "plus");                

		cmd.setWriteAllowed(event.isWriteAllowed( context.getCurrentUserDetails()));
		cmd.setSearchType("Event");
		cmd.setQueryText(event.getName());

		return new ModelAndView("event/showEventEntry", "command", cmd);
	}
	
	public String getDisplayBody(String description)
	{
		if ( (description == null) || (description.length() == 0)) return "";

		String sBody = description;
		sBody = Jsoup.parse(sBody).text();
		sBody = StringHelper.escapeHTML(sBody);

		return sBody;
	}

	public class Command extends EventDto implements CommandBean, Taggable
	{   
		private String userName;
		private String displayBody;
		private String tags;
		private TreeMap popularTags;
		private int commentCount;
		private int likeCount;
		private int visitCount;
		private String lastVisitTime;
		private boolean alreadyLike;
		private boolean likeAllowed;
		private boolean writeAllowed;
		private boolean invitee;
		private boolean replied;
		private boolean userPhotoPresent;
		private boolean eventPosterPresent;
		
		private boolean posterPhotoPresent;
		private String posterName;
		private String posterMobile;
		private String posterEmail;
		private String posterWeb;
		private String posterAbout;
		private int eventCountForHost;
		private String keywords;
		
		public String getMarkupImage()
		{
			String url = "";
			try {
				if (contextManager.getContext().getCurrentCommunity() != null) {
					if (this.isEventPosterPresent()) {
						url = contextManager.getContext().getContextUrl()+ "/event/eventPoster.img?id=" + this.getId();
					} else if (contextManager.getContext().getCurrentCommunity().isLogoPresent()) {
						url = contextManager.getContext().getContextUrl()+ "/commLogoDisplay.img?showType=m&communityId=" + contextManager.getContext().getCurrentCommunity().getId();
					} else {
						url = contextManager.getContext().getContextUrl()+ "/img/community_Image.png";
					}
				}
			} catch (Exception e) {
			}
			return url;
		}
		
		public String getIsoStartDate() throws Exception
		{
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
			df.setTimeZone(tz);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = formatter.parse(getStartDate());
			String nowAsISO = df.format(date);
			return nowAsISO;
		}
		
		public String getIsoEndDate() throws Exception
		{
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
			df.setTimeZone(tz);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = formatter.parse(getEndDate());
			String nowAsISO = df.format(date);
			return nowAsISO;
		}

		public String getTaggedKeywords(){
			String taggedKeywords = "";
			int communityId = contextManager.getContext().getCurrentCommunity().getId();
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(this.getId(), communityId, 20, "Event");
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='cid/"+communityId+"/search/searchByTagInCommunity.do?filterTag="+tag+" ' class='euInfoSelect' style='display: inline;' onmouseover='tip(this,&quot;Click to filter by tag &#39;"+tag+"&#39;&quot;)'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += " , ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

		public String getPostedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");     
			Date today = new Date();
			String sToday = fmter.format(today);

			Date date = fmter.parse(getCreated());
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);
		}

		public String getDateStarted() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd");   
			Date today = new Date();
			String sToday = fmter.format(today);
			try {
				Date date = fmter.parse(getStartDate());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date) + " at " + fmt.format(date);
			} catch (Exception e) {
				return "";
			}
		}

		public String getDateEnded() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd");      
			Date today = new Date();
			String sToday = fmter.format(today);
			try {
				Date date = fmter.parse(getEndDate());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date) + " at " + fmt.format(date);
			} catch (Exception e) {
				return "";
			}
		}

		public void setLastVisitTime(Date lastVisitorsTime) throws Exception {
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			Date date = lastVisitorsTime != null ? lastVisitorsTime : fmter.parse(getLastVisitTime());
			if (fmter.format(date).equals(sToday)) {
				lastVisitTime =  "Today " + fmt.format(date);
			}
			lastVisitTime =  fmt2.format(date);
		}

		public boolean isAddTagAllowed() {
			try {
				if (contextManager.getContext().getCurrentUser() == null ) return false;
				if (contextManager.getContext().isUserAuthenticated() == false) return false;
				User user = contextManager.getContext().getCurrentUser();

				return user != null && user.getId() == getPosterId();
			} catch (Exception exp) {
				return false;
			}
		}

		public boolean isCurrentUserSubscribed() throws Exception
		{
			if (contextManager.getContext().getCurrentUser() == null) return false;
			int userId = contextManager.getContext().getCurrentUser().getId();
			try {
				subscriptionFinder.getEventSubscriptionForUser(this.getId(), userId);
				return true;
			}
			catch (ElementNotFoundException x) {
				return false;
			}
		}

		public String getTags() {
			return tags;
		}
		public void setTags(String tags) {
			this.tags = tags;
		}
		public String getDisplayBody()
		{
			return displayBody;
		}
		public void setDisplayBody(String displayBody)
		{
			this.displayBody = displayBody;
		}
		public String getUserName()
		{
			return userName;
		}        
		public void setUserName(String userName)
		{
			this.userName = userName;
		}
		public TreeMap getPopularTags()
		{
			return popularTags;
		}
		public void setPopularTags(TreeMap popularTags)
		{
			this.popularTags = popularTags;
		}
		public int getCommentCount() {
			return commentCount;
		}
		public void setCommentCount(int commentCount) {
			this.commentCount = commentCount;
		}
		public int getLikeCount() {
			return likeCount;
		}
		public void setLikeCount(int likeCount) {
			this.likeCount = likeCount;
		}
		public int getVisitCount() {
			return visitCount;
		}
		public void setVisitCount(int visitCount) {
			this.visitCount = visitCount;
		}
		public String getLastVisitTime() {
			return lastVisitTime;
		}
		public void setLastVisitTime(String lastVisitTime) {
			this.lastVisitTime = lastVisitTime;
		}

		public boolean isUserPhotoPresent() {
			return userPhotoPresent;
		}

		public void setUserPhotoPresent(boolean userPhotoPresent) {
			this.userPhotoPresent = userPhotoPresent;
		}

		public boolean isEventPosterPresent() {
			return eventPosterPresent;
		}

		public void setEventPosterPresent(boolean eventPosterPresent) {
			this.eventPosterPresent = eventPosterPresent;
		}

		public boolean isAlreadyLike() {
			return alreadyLike;
		}

		public void setAlreadyLike(boolean alreadyLike) {
			this.alreadyLike = alreadyLike;
		}

		public boolean isLikeAllowed() {
			return likeAllowed;
		}

		public void setLikeAllowed(boolean likeAllowed) {
			this.likeAllowed = likeAllowed;
		}

		public boolean isInvitee() {
			return invitee;
		}

		public void setInvitee(boolean invitee) {
			this.invitee = invitee;
		}

		public boolean isReplied() {
			return replied;
		}

		public void setReplied(boolean replied) {
			this.replied = replied;
		}

		public boolean isPosterPhotoPresent() {
			return posterPhotoPresent;
		}

		public void setPosterPhotoPresent(boolean posterPhotoPresent) {
			this.posterPhotoPresent = posterPhotoPresent;
		}

		public String getPosterName() {
			return posterName;
		}

		public void setPosterName(String posterName) {
			this.posterName = posterName;
		}

		public String getPosterMobile() {
			return posterMobile;
		}

		public void setPosterMobile(String posterMobile) {
			this.posterMobile = posterMobile;
		}

		public String getPosterEmail() {
			return posterEmail;
		}

		public void setPosterEmail(String posterEmail) {
			this.posterEmail = posterEmail;
		}

		public String getPosterWeb() {
			return posterWeb;
		}

		public void setPosterWeb(String posterWeb) {
			this.posterWeb = posterWeb;
		}

		public String getPosterAbout() {
			return posterAbout;
		}

		public void setPosterAbout(String posterAbout) {
			this.posterAbout = posterAbout;
		}

		public int getEventCountForHost() {
			return eventCountForHost;
		}

		public void setEventCountForHost(int eventCountForHost) {
			this.eventCountForHost = eventCountForHost;
		}

		public String getKeywords() {
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}

		public boolean isWriteAllowed() {
			return writeAllowed;
		}

		public void setWriteAllowed(boolean writeAllowed) {
			this.writeAllowed = writeAllowed;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
	public void setEventFinder(EventFinder eventFinder)
	{
		this.eventFinder = eventFinder;
	}
	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}
	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}
	public void setEventLikeFinderer(EventLikeFinder eventLikeFinderer) {
		this.eventLikeFinderer = eventLikeFinderer;
	}
	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}
	public void setCountryFinder(CountryFinder countryFinder) {
		this.countryFinder = countryFinder;
	}
}