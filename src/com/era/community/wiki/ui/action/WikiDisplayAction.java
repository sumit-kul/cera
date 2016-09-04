package com.era.community.wiki.ui.action;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.util.StringHelper;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.Taggable;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.era.community.upload.dao.ImageFinder;
import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryFinder;
import com.era.community.wiki.dao.WikiEntryLikeFinder;
import com.era.community.wiki.dao.WikiEntrySectionFinder;
import com.era.community.wiki.dao.generated.WikiEntryDaoBase;
import com.era.community.wiki.ui.dto.WikiEntryDto;

/**
 * @spring.bean name="/cid/[cec]/wiki/wikiDisplay.do" 
 */
public class WikiDisplayAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected WikiEntryFinder wikiEntryFinder; 
	protected WikiEntrySectionFinder wikiEntrySectionFinder;
	protected UserFinder userFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected WikiEntryDaoBase dao;
	protected TagFinder tagFinder;
	protected WikiEntryLikeFinder wikiEntryLikeFinder;
	protected ImageFinder imageFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		WikiEntry entry = null;

		try {
			if (cmd.getEntryId() > 0 && cmd.getEntrySequence() >0) {
				entry = wikiEntryFinder.getWikiEntryForEntryIdAndSequence(cmd.getEntryId(), cmd.getEntrySequence()); 
			}
			else if (cmd.getEntryId() > 0) {
				entry = wikiEntryFinder.getLatestWikiEntryForEntryId(cmd.getEntryId());
			}        
			else if (cmd.getId() > 0 ) {
				entry = wikiEntryFinder.getWikiEntryForId(cmd.getId());
			}
		} catch (ElementNotFoundException e) {
			return new ModelAndView("/pageNotFound");
		}

		if (entry != null) {
			cmd.setSearchType("Wiki");
			cmd.setQueryText(entry.getTitle());

			cmd.copyPropertiesFrom(entry);
			cmd.setPopularTags( entry.getPopularTags() );
			cmd.setWikiEntryFinder(wikiEntryFinder);
			User user = userFinder.getUserEntity( entry.getPosterId() );
			cmd.setLastEditUserName( user.getFirstName() + " "  + user.getLastName() );
			cmd.setPhotoPresent(user.isPhotoPresent());
			cmd.setNextSequenceNumber( wikiEntryFinder.getNextSequenceNumberForEntry(entry) );
			cmd.setDatePosted(entry.getDatePosted());

			List sections = wikiEntrySectionFinder.getWikiEntrySectionsForWikiEntryId(entry.getId());
			cmd.setWikiEntrySections(sections);

			int versionCount = wikiEntryFinder.getWikiEntryVersionCount(entry.getEntryId());
			cmd.setVersionCount(versionCount);
			WikiEntry firstVersion = entry;
			if (versionCount > 1) {
				firstVersion = wikiEntryFinder.getWikiEntryForEntryIdAndSequence(entry.getEntryId(), 1);
			}
			cmd.setCreatorId(firstVersion.getPosterId());
			cmd.setCreationTime(firstVersion.getCreated().toString());
			User creator = userFinder.getUserEntity( firstVersion.getPosterId() );
			cmd.setCreatorName(creator.getFullName());
			cmd.setCreatorPhotoPresent(creator.isPhotoPresent());

			List contrb = wikiEntryFinder.getContributors(entry.getEntryId());
			if (contrb != null && contrb.size() > 0) {
				cmd.setContributors(contrb);
			}

			if (context.getCurrentUser() == null || context.getCurrentUser().getId() != entry.getPosterId()) {
				HttpServletRequest request = contextManager.getContext().getRequest(); 
				Object sessionId = request.getSession().getAttribute("WikiEntry-"+entry.getId());
				if (sessionId == null) {
					request.getSession().setAttribute("WikiEntry-"+entry.getId(), entry.getId());
					int visitors = entry.getVisitors()+1;
					entry.setVisitors(visitors);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date now = new Date();
					String dt = sdf.format(now);
					Timestamp ts = Timestamp.valueOf(dt);
					entry.setLastVisitorsTime(ts);
					dao.store(entry, true);
				}
			}

			cmd.setImageCount(imageFinder.countImageForItemType(entry.getEntryId(), "WikiEntry"));
			if (cmd.getImageCount() == 0) {
				cmd.setSectionImageCount(imageFinder.countImageForItemType(entry.getEntryId(), "WikiEntrySectionAll"));
			}
			int likeCount = wikiEntryLikeFinder.getLikeCountForWikiEntry(entry.getId());
			cmd.setLikeCount(likeCount);
			cmd.setAlreadyLike(isAlreadyLike(entry));
			cmd.setLikeAllowed(isLikeAllowed(entry));
			cmd.setVisitCount(entry.getVisitors());
			cmd.setLastVisitTime(entry.getLastVisitorsTime());

			return new ModelAndView("wiki/wikiDisplay", "command" , cmd);    
		} else {
			return new ModelAndView("/pageNotFound");
		}
	}

	private boolean isAlreadyLike(WikiEntry entry) throws Exception {
		if (contextManager.getContext().getCurrentUser() == null ) return false;
		if (contextManager.getContext().isUserAuthenticated() == false) return false;
		int currentUserId = contextManager.getContext().getCurrentUser().getId();
		return entry.isAlreadyLike(currentUserId);
	}

	private boolean isLikeAllowed(WikiEntry entry) throws Exception {
		if (contextManager.getContext().getCurrentUser() == null ) return true;
		if (contextManager.getContext().isUserAuthenticated() == false) return true;
		int currentUserId = contextManager.getContext().getCurrentUser().getId();
		return entry.getPosterId() != currentUserId;
	}

	public class Command extends WikiEntryDto implements CommandBean, Taggable
	{        
		private String lastEditUserName;
		private int nextSequenceNumber;
		private String tags;
		private TreeMap popularTags;
		private List wikiHistoryEntries = new ArrayList();
		private int likeCount;
		private int visitCount;
		private String lastVisitTime;
		private boolean alreadyLike;
		private boolean likeAllowed;
		private boolean photoPresent;
		private int imageCount;
		private int sectionImageCount;
		private int creatorId;
		private String creatorName;
		private String creationTime;
		private boolean creatorPhotoPresent;
		private int versionCount;

		private List contributors = new ArrayList();

		public String getIsoPostedOn() throws Exception
		{
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
			df.setTimeZone(tz);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = formatter.parse(getDatePosted());
			String nowAsISO = df.format(date);
			return nowAsISO;
		}

		public String getMarkupBody()
		{
			if ( (this.getBody()==null) || (this.getBody().length()==0)) return "";

			String sBody = this.getBody();
			sBody = Jsoup.parse(sBody).text();
			sBody = StringHelper.escapeHTML(sBody);
			return sBody;
		}

		public String getMarkupImage()
		{
			String url = "";
			try {
				if (this.getImageCount() > 0) {
					url = contextManager.getContext().getContextUrl()+ "/common/showImage.img?showType=t&type=WikiEntry&itemId=" + this.getEntryId();
				} else if (this.getSectionImageCount() > 0) {
					url = contextManager.getContext().getContextUrl()+ "/common/showImage.img?showType=t&type=WikiEntrySectionAll&itemId=" + this.getEntryId();
				}else {
					url = contextManager.getContext().getContextUrl()+ "/img/community_Image.png";
				}
			} catch (Exception e) {
			}
			return url;
		}

		public boolean isMember() throws Exception
		{
			if (contextManager.getContext().getCurrentUser() == null ) return false;
			if (contextManager.getContext().isUserAuthenticated() == false) return false;
			int currentUser = contextManager.getContext().getCurrentUser().getId();
			return contextManager.getContext().getCurrentCommunity().isMember(currentUser);
		}

		public String getTaggedKeywords(){
			String taggedKeywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(this.getId(), 0, 20, "WikiEntry");
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='"+contextManager.getContext().getCurrentCommunityUrl()+"/search/searchByTagInCommunity.do?filterTag="+tag+"' class='euInfoSelect normalTip' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += ", ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

		public String getDatePostOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("dd MMM, yy");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				Date date = formatter.parse(getDatePosted());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return getDatePosted();
			}
		}

		public Date getDateOnPost() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return formatter.parse(getDatePosted());
		}

		public String getDatePostOnHover() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			try {
				Date date = formatter.parse(getDatePosted());
				return fmt2.format(date);
			} catch (ParseException e) {
				return getDatePosted();
			}
		}

		public String getCreatedOnHover() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			try {
				Date date = formatter.parse(getCreationTime());
				return fmt2.format(date);
			} catch (ParseException e) {
				return getCreationTime();
			}
		}

		public String getCreatedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("dd MMM, yy");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				Date date = formatter.parse(getCreationTime());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return getCreationTime();
			}
		}

		public String getTags() {
			return tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public boolean isCurrentUserSubscribed() throws Exception
		{
			try {
				if (contextManager.getContext().getCurrentUser() == null)
					return false;
				subscriptionFinder.getWikiEntrySubscriptionForUser(getEntryId(), contextManager.getContext().getCurrentUser().getId());
				return true;
			}
			catch (ElementNotFoundException x) {
				return false;
			}
		}

		public boolean isRollbackPossible() throws Exception
		{
			return wikiEntryFinder.getPreviousWikiEntryForEntryId(getEntryId()) !=null;
		}

		public String getLastEditUserName()
		{
			return lastEditUserName;
		}

		public void setLastEditUserName(String lastEditUserName)
		{
			this.lastEditUserName = lastEditUserName;
		}                 

		public String getDisplayBody()
		{
			return  StringHelper.formatForDisplay(getBody());
		}

		public boolean isOlderVersionAvailable() {
			if ( getEntrySequence() > 1  ) {
				return true;
			}
			else {
				return false;     
			}            
		}

		public int getOlderSequence() {
			if ( isOlderVersionAvailable() ) {
				return getEntrySequence() - 1;
			}
			else {
				return 0;    
			}            
		}

		public int getNewerSequence() {
			if ( isNewerVersionAvailable() ) {
				return getEntrySequence() + 1;
			}
			else {
				return 0;    
			}            
		}

		public boolean isNewerVersionAvailable() {
			if ( getEntrySequence() != Integer.MAX_VALUE  && 
					getNextSequenceNumber() != (getEntrySequence() + 1) ) {
				return true;
			}
			else {
				return false;     
			}
		}

		public int getNextSequenceNumber()
		{
			return nextSequenceNumber;
		}

		public void setNextSequenceNumber(int nextSequenceNumber)
		{
			this.nextSequenceNumber = nextSequenceNumber;
		}

		public TreeMap getPopularTags()
		{
			return popularTags;
		}

		public void setPopularTags(TreeMap popularTags)
		{
			this.popularTags = popularTags;
		}


		public List getWikiHistoryEntries()
		{
			return wikiHistoryEntries;
		}

		public void setWikiHistoryEntries(List wikiHistoryEntries)
		{
			this.wikiHistoryEntries = wikiHistoryEntries;
		}

		public boolean isFirstWikiEntry() throws Exception
		{

			WikiEntry wikiEntry = null;
			try {
				wikiEntry =  wikiEntryFinder.getFirstWikiEntry(this.getWikiId());
				if(wikiEntry.getEntryId() == this.getEntryId()){
					return true;
				}
				return false;
			}
			catch (ElementNotFoundException x) {
				return false;
			}
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

		public boolean isPhotoPresent() {
			return photoPresent;
		}

		public void setPhotoPresent(boolean photoPresent) {
			this.photoPresent = photoPresent;
		}

		public String getCreatorName() {
			return creatorName;
		}

		public void setCreatorName(String creatorName) {
			this.creatorName = creatorName;
		}

		public String getCreationTime() {
			return creationTime;
		}

		public void setCreationTime(String creationTime) {
			this.creationTime = creationTime;
		}

		public boolean isCreatorPhotoPresent() {
			return creatorPhotoPresent;
		}

		public void setCreatorPhotoPresent(boolean creatorPhotoPresent) {
			this.creatorPhotoPresent = creatorPhotoPresent;
		}

		public int getCreatorId() {
			return creatorId;
		}

		public void setCreatorId(int creatorId) {
			this.creatorId = creatorId;
		}

		public int getVersionCount() {
			return versionCount;
		}

		public void setVersionCount(int versionCount) {
			this.versionCount = versionCount;
		}

		public List getContributors() {
			return contributors;
		}

		public void setContributors(List contributors) {
			this.contributors = contributors;
		}

		public int getImageCount() {
			return imageCount;
		}

		public void setImageCount(int imageCount) {
			this.imageCount = imageCount;
		}

		public int getSectionImageCount() {
			return sectionImageCount;
		}

		public void setSectionImageCount(int sectionImageCount) {
			this.sectionImageCount = sectionImageCount;
		}
	} 

	public static class RowBean extends WikiEntryDto 
	{  
		private int resultSetIndex;        
		private String editedBy;

		public String getDatePostOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				Date date = formatter.parse(getDatePosted());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (Exception e) {
				return getDatePosted();
			}
		}

		public int getResultSetIndex()
		{
			return resultSetIndex; 
		}

		public void setResultSetIndex(int resultSetIndex) 
		{
			this.resultSetIndex = resultSetIndex;
		}

		public boolean isEvenRow()
		{
			return resultSetIndex%2==0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex%2==1;
		}

		public String getEditedBy()
		{
			return editedBy;
		}
		public void setEditedBy(String editedBy)
		{
			this.editedBy = editedBy; 
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder)
	{
		this.wikiEntryFinder = wikiEntryFinder; 
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}
	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setDao(WikiEntryDaoBase dao) {
		this.dao = dao;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}

	public void setWikiEntrySectionFinder(
			WikiEntrySectionFinder wikiEntrySectionFinder) {
		this.wikiEntrySectionFinder = wikiEntrySectionFinder;
	}

	public void setWikiEntryLikeFinder(WikiEntryLikeFinder wikiEntryLikeFinder) {
		this.wikiEntryLikeFinder = wikiEntryLikeFinder;
	}

	public void setImageFinder(ImageFinder imageFinder) {
		this.imageFinder = imageFinder;
	}
}