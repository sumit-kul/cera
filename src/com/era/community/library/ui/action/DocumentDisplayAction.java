package com.era.community.library.ui.action;

import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;
import support.community.util.StringHelper;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.FileManager;
import com.era.community.base.LinkBuilderContext;
import com.era.community.base.Taggable;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentComment;
import com.era.community.doclib.dao.DocumentCommentFinder;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.DocumentLibraryFinder;
import com.era.community.doclib.dao.DocumentRating;
import com.era.community.doclib.dao.DocumentRatingFinder;
import com.era.community.doclib.ui.dto.DocumentCommentDto;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/cid/[cec]/library/documentDisplay.do"
 */
public class DocumentDisplayAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected DocumentLibraryFinder doclibFinder;
	protected DocumentFinder documentFinder; 
	protected SubscriptionFinder subscriptionFinder;
	protected UserFinder userFinder;
	protected DocumentCommentFinder docCommentFinder;
	protected DocumentRatingFinder ratingFinder;
	protected TagFinder tagFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		String fileID = context.getRequest().getParameter("fileID");
		int cmdId = 0;
		if (cmd.getId() == 0 && fileID != null && !"".equals(fileID)&& !"undefined".equals(fileID)) {
			cmdId = Integer.parseInt(fileID);
			cmd.setId(cmdId);
		}

		Document doc = null;
		try {
			doc = documentFinder.getDocumentForId(cmd.getId());
		} catch (ElementNotFoundException e) {
			return new ModelAndView("/pageNotFound");
		}

		if (fileID == null || "".equals(fileID)) {
			User poster = userFinder.getUserEntity( doc.getPosterId() );

			User currentUser = context.getCurrentUser();
			if (currentUser != null)
				doc.recordAccessForItem(currentUser.getId(), context.getCurrentCommunity().getId());

			cmd.setDisplayName( poster.getFullName() );  
			cmd.setPhotoPresent(poster.isPhotoPresent());
			cmd.copyPropertiesFrom(doc);
			cmd.setMetaDescription(doc.getDescription());

			cmd.setId(doc.getId());
			cmd.setDatePosted(doc.getDatePosted() );
			cmd.setModified(doc.getModified());
			cmd.setTitle(doc.getTitle());
			cmd.setPosterId(doc.getPosterId());
			cmd.setNumberOfRaters(doc.getNumberOfRaters());
			cmd.setFileName(doc.getFileName());
			cmd.setFileContentType(doc.getFileContentType());
			cmd.setSizeInBytes( doc.getFile().getLength() );
			cmd.setDescription(  StringHelper.formatForDisplay(doc.getDescription() ));
			cmd.setLastDownloadTime(doc.getLastDownloadTime());
			//cmd.setThemeBeans(doc.getThemeLinkBeans());
			//cmd.setThemeIds(doc.getThemeIds());
			cmd.setTags(doc.getTags());
			cmd.setPopularTags(doc.getPopularTags());
			cmd.setSearchType("File");
			cmd.setQueryText(doc.getTitle());

			cmd.setIconImage(FileManager.computeIconImage(doc.getFileContentType()));
			cmd.setOverallRating(""+doc.getStarRating());
			cmd.setRatingImage("/img/icon-"+cmd.getOverallRating()+"stars.gif" ); // alt=\" stars\" width=\"61\" height=\"12

			DocumentRating rating;
			try {
				if (currentUser != null) {
					rating  = ratingFinder.getRatingForUserAndDocument(currentUser.getId(), doc.getId());
					cmd.setRating(""+rating.getStars());
				}
			} catch (ElementNotFoundException e) {

			}
			cmd.setWriteAllowed(doc.isWriteAllowed( context.getCurrentUserDetails() ));
		}

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}
		int userId = context.getCurrentUser() == null ? 0 : context.getCurrentUser().getId();
		QueryScroller scroller = docCommentFinder.listCommentsForDocumentId(cmd.getId(), userId, RowBean.class);
		scroller.setBeanClass( RowBean.class );
		scroller.setPageSize(cmd.getPageSize());
		//pagination
		cmd.setPageCount(scroller.readPageCount());
		cmd.setCommentCount(scroller.readRowCount());

		if (pNumber > 0) {
			IndexedScrollerPage page = scroller.readPage(pNumber);
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = page.toJsonString(pNumber, userId);
			json.put("userId", cmd.getPosterId());
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
			return null;
		} else {
			return new ModelAndView("/library/documentDisplay");
		}
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean, Taggable
	{       
		private int id;
		private String description;       
		private String title;     
		private int posterId;
		private boolean photoPresent;
		private long sizeInBytes;
		private String iconImage;
		private String ratingImage;
		private String fileName;
		private String displayName;
		private Date datePosted;
		private Date modified;
		private boolean hasAlreadyCommented;
		private String rating;                  // The usefulness rating of the document from the current user as a number of stars
		private int numberOfRaters;        		// The number of members who rated this document
		private String overallRating;         	// The overall rating of the document
		private String fileContentType;

		private String tags;
		private TreeMap popularTags;    

		/* Data for comment */
		private String comment;
		private int commentCount;

		private String body;

		private int downloads;
		private String lastDownloadTime;
		private String keywords;
		private boolean writeAllowed;
		
		public String getIconType() {
	    	if (getFileContentType() != null) {
	    		if (getFileContentType().equalsIgnoreCase("application/vnd.ms-excel") || getFileContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
	        		return "xls";
	    		} else if (getFileContentType().equalsIgnoreCase("application/x-tar") || getFileContentType().equalsIgnoreCase("application/zip")) {
	    			return "zip";
	    		} else if (getFileContentType().equalsIgnoreCase("application/pdf")) {
	    			return "pdf";
	    		} else if (getFileContentType().equalsIgnoreCase("application/xml")) {
	    			return "xml";
	    		} else if (getFileContentType().equalsIgnoreCase("image/jpeg")) {
	    			return "img";
	    		} else if (getFileContentType().equalsIgnoreCase("text/richtext") || getFileContentType().equalsIgnoreCase("application/msword") || getFileContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
	    			return "doc";
	    		} else if (getFileContentType().equalsIgnoreCase("application/vnd.ms-powerpoint") || getFileContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
	    			return "ppt";
	    		} 
	    		return "file";
			} else {
				return "file";
			}
	    }
		
		public String getIsoPostedOn() throws Exception
		{
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
			df.setTimeZone(tz);
			String nowAsISO = df.format(getDatePosted());
			return nowAsISO;
		}
		
		public String getMarkupImage()
		{
			String url = "";
			try {
				if (contextManager.getContext().getCurrentCommunity() != null) {
					if (getFileContentType() != null) {
			    		if (getFileContentType().equalsIgnoreCase("application/vnd.ms-excel") || getFileContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			    			url = contextManager.getContext().getContextUrl()+ "/img/file/excelicon.gif";
			    		} else if (getFileContentType().equalsIgnoreCase("application/x-tar") || getFileContentType().equalsIgnoreCase("application/zip")) {
			    			url = contextManager.getContext().getContextUrl()+ "/img/file/zipicon.gif";
			    		} else if (getFileContentType().equalsIgnoreCase("application/pdf")) {
			    			url = contextManager.getContext().getContextUrl()+ "/img/file/pdficon.gif";
			    		} else if (getFileContentType().equalsIgnoreCase("application/xml")) {
			    			url = contextManager.getContext().getContextUrl()+ "/img/file/excelicon.gif";
			    		} else if (getFileContentType().equalsIgnoreCase("image/jpeg")) {
			    			url = contextManager.getContext().getContextUrl()+ "/community/getCommunityMedia.img?id="+this.getId();
			    		} else if (getFileContentType().equalsIgnoreCase("text/richtext") || getFileContentType().equalsIgnoreCase("application/msword") || getFileContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
			    			url = contextManager.getContext().getContextUrl()+ "/img/file/wordicon.gif";
			    		} else if (getFileContentType().equalsIgnoreCase("application/vnd.ms-powerpoint") || getFileContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
			    			url = contextManager.getContext().getContextUrl()+ "/img/file/powerpointicon.gif";
			    		} else if (contextManager.getContext().getCurrentCommunity().isLogoPresent()) {
							url = contextManager.getContext().getContextUrl()+ "/commLogoDisplay.img?communityId=" + contextManager.getContext().getCurrentCommunity().getId();
						} else {
							url = contextManager.getContext().getContextUrl()+ "/img/community_Image.png";
						}
					} else if (contextManager.getContext().getCurrentCommunity().isLogoPresent()) {
						url = contextManager.getContext().getContextUrl()+ "/commLogoDisplay.img?communityId=" + contextManager.getContext().getCurrentCommunity().getId();
					} else {
						url = contextManager.getContext().getContextUrl()+ "/img/community_Image.png";
					}
				}
			} catch (Exception e) {
			}
			return url;
		}

		public boolean isCurrentUserSubscribed() throws Exception
		{
			if (contextManager.getContext().getCurrentUser() == null) return false;
			int userId = contextManager.getContext().getCurrentUser().getId();
			try {
				subscriptionFinder.getDocumentSubscriptionForUser(this.getId(), userId);
				return true;
			}
			catch (ElementNotFoundException x) {
				return false;
			}
		}
		
		public final List<String> getThemeOptions() throws Exception
		{
			CommunityEraContext context = contextManager.getContext();
			return  doclibFinder.getLibraryThemeOptionList(context.getCurrentCommunity().getId());
		}

		public String getComment()
		{
			return StringHelper.formatForDisplay(comment);
		}

		public String getDescription()
		{
			return description;
		}

		public void setDescription(String description)
		{
			this.description = description;
		}

		public int getId()
		{
			return id;
		}

		public void setId(int id)
		{
			this.id = id;
		}

		public String getTitle()
		{
			return title;
		}

		public void setTitle(String title)
		{
			this.title = title;
		}

		public void setComment(String comment)
		{
			this.comment = comment;
		}

		public void setSizeInBytes(long size) {
			this.sizeInBytes = size;
		}

		public long getSizeInBytes() {
			return this.sizeInBytes;
		}

		public String getSizeInKb() {
			return FileManager.getSizeInKb( getSizeInBytes() );
		}

		public String getIconImage()
		{
			return iconImage;
		}

		public void setIconImage(String iconImage)
		{
			this.iconImage = iconImage;
		}

		public String getFileName()
		{
			return fileName;
		}

		public void setFileName(String fileName)
		{
			this.fileName = fileName;
		}

		public String getDisplayName()
		{
			return displayName;
		}

		public void setDisplayName(String displayName)
		{
			this.displayName = displayName;
		}

		public Date getDatePosted()
		{
			return datePosted;
		}

		public void setDatePosted(Date datePosted)
		{
			this.datePosted = datePosted;
		}

		public final String getRating()
		{
			return rating;
		}

		public final void setRating(String rating)
		{
			this.rating = rating;
		}

		public final String getOverallRating()
		{
			return overallRating;
		}

		public final void setOverallRating(String overallRating)
		{
			this.overallRating = overallRating;
		}

		public final String getRatingImage()
		{
			return ratingImage;
		}

		public final void setRatingImage(String ratingImage)
		{
			this.ratingImage = ratingImage;
		}

		public String getTags() {
			return tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public TreeMap getPopularTags()
		{
			return popularTags;
		}

		public void setPopularTags(TreeMap popularTags)
		{
			this.popularTags = popularTags;
		}

		public int getNumberOfRaters()
		{
			return numberOfRaters;
		}

		public void setNumberOfRaters(int numberOfRaters)
		{
			this.numberOfRaters = numberOfRaters;
		}

		public int getPosterId()
		{
			return posterId;
		}

		public void setPosterId(int posterId)
		{
			this.posterId = posterId;
		}

		public boolean isHasAlreadyCommented()
		{
			return hasAlreadyCommented;
		}

		public void setHasAlreadyCommented(boolean hasAlreadyCommented)
		{
			this.hasAlreadyCommented = hasAlreadyCommented;
		}

		public boolean isPhotoPresent() {
			return photoPresent;
		}

		public void setPhotoPresent(boolean photoPresent) {
			this.photoPresent = photoPresent;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public int getCommentCount() {
			return commentCount;
		}

		public void setCommentCount(int commentCount) {
			this.commentCount = commentCount;
		}

		public String getTaggedKeywords(){
			String taggedKeywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(this.getId(), 0, 15, "Document");
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='community/showCommunities.do?filterTag="+tag+" ' title='Click to filter by tag &#39;"+tag+"&#39; all items in the community'>"+tag+"</a>";
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

			Date date = getDatePosted();
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);
		}
		
		public String getModifyiedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");     
			Date today = new Date();
			String sToday = fmter.format(today);

			Date date = getModified();
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);
		}

		public void setLastVisitTime(Date lastVisitorsTime) throws Exception {
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			Date date = lastVisitorsTime != null ? lastVisitorsTime : fmter.parse(getLastVisitTime());
			if (fmter.format(date).equals(sToday)) {
				lastDownloadTime =  "Today " + fmt.format(date);
			}
			lastDownloadTime =  fmt2.format(date);
		}

		public String getLastVisitTime() throws Exception
		{
			return lastDownloadTime;
		}

		public int getDownloads() {
			return downloads;
		}

		public void setDownloads(int downloads) {
			this.downloads = downloads;
		}

		public String getKeywords() {
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}

		public String getFileContentType() {
			return fileContentType;
		}

		public void setFileContentType(String fileContentType) {
			this.fileContentType = fileContentType;
		}

		public Date getModified() {
			return modified;
		}

		public void setModified(Date modified) {
			this.modified = modified;
		}

		public String getLastDownloadTime() {
			return lastDownloadTime;
		}

		public void setLastDownloadTime(Date lastDownloadTime) {
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);
			
			if (lastDownloadTime != null) {
				if (fmter.format(lastDownloadTime).equals(sToday)) {
					this.lastDownloadTime =  "Today " + fmt.format(lastDownloadTime);
				}
				this.lastDownloadTime =  fmt2.format(lastDownloadTime);
			} else {
				this.lastDownloadTime =  "";
			}
		}

		public boolean isWriteAllowed() {
			return writeAllowed;
		}

		public void setWriteAllowed(boolean writeAllowed) {
			this.writeAllowed = writeAllowed;
		}
	}

	public static class RowBean extends DocumentCommentDto implements EntityWrapper 
	{ 
		private int likeCommentCount;
		private DocumentComment entry;
		private boolean alreadyCommentLike;
		private boolean commentLikeAllowed;

		public String getPostedOn() throws Exception
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

			} catch (ParseException e) {
				return getCreated();
			}
		}

		public int getLikeCommentCount() {
			return likeCommentCount;
		}

		public void setLikeCommentCount(Long likeCommentCount) {
			this.likeCommentCount = likeCommentCount.intValue();
		}

		public boolean isAlreadyCommentLike(int userID) throws Exception {
			return entry.isAlreadyLike(this.getId(), userID);
		}

		public boolean isCommentLikeAllowed(int userID) throws Exception {
			return entry.getPosterId() != userID;
		}
		
		public void setAlreadyCommentLike(boolean alreadyCommentLike) {
			this.alreadyCommentLike = alreadyCommentLike;
		}

		public void setCommentLikeAllowed(boolean commentLikeAllowed) {
			this.commentLikeAllowed = commentLikeAllowed;
		}

		public void setEntry(DocumentComment entry) {
			this.entry = entry;
		}
	}

	public final void setCommentFinder(DocumentCommentFinder docCommentFinder)
	{
		this.docCommentFinder = docCommentFinder;
	}

	public final void setDocumentFinder(DocumentFinder documentFinder)
	{
		this.documentFinder = documentFinder;
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public final void setRatingFinder(DocumentRatingFinder ratingFinder)
	{
		this.ratingFinder = ratingFinder;
	}

	public final void setDoclibFinder(DocumentLibraryFinder doclibFinder)
	{
		this.doclibFinder = doclibFinder;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}
}