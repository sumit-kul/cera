package com.era.community.forum.ui.dto; 

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import support.community.util.StringHelper;

public class ForumItemDto extends com.era.community.forum.dao.generated.ForumItemEntity 
{
	private String authorName;
	private String lastPosterName;
	private String photoPresent;
	private String lastPostPhotoPresent;
	private String lastUpdateName;
	private int lastUpdateId;
	private int lastPosterId;
	private String latestPostDate;
	private String itemClass;
	private int AuthorGlobalId;
	private int responseCount;
	private int alreadyLike;
	private int itemLike;
	private String itemType;
	private String topicSubject;
	
	private int parentAuthorId;
	private String parentAuthorName;
	private String parentAuthorPhotoPresent;
	private String parentPostDate;

	private boolean response;
	private String tags;
	private Long depth;
	
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
	
	public String getDisplaySubject()
	{
		if ( (this.getSubject()==null) || (this.getSubject().length()==0)) return "";

		String sub = this.getSubject();
		sub = StringHelper.escapeHTML(sub);
		return sub;
	}

	public String getPostedOn() throws Exception
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
	
	public String getLastPostedOn() throws Exception
	{
		SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
		SimpleDateFormat fmt2 = new SimpleDateFormat("dd MMM, yy");
		Date today = new Date();
		String sToday = fmter.format(today);

		try {

			Date date = formatter.parse(getLatestPostDate());
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);

		} catch (ParseException e) {
			return getLatestPostDate();
		} catch (NullPointerException e) {
			return "";
		}
	}
	
	public String getParentPostDateON() throws Exception
	{
		SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
		SimpleDateFormat fmt2 = new SimpleDateFormat("dd MMM, yy");
		Date today = new Date();
		String sToday = fmter.format(today);

		try {

			Date date = formatter.parse(getParentPostDate());
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);

		} catch (Exception e) {
			return getParentPostDate();
		}
	}
	
	public String getParentPostDateOnHover() throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
		try {
			Date date = formatter.parse(getParentPostDate());
			return fmt2.format(date);
		} catch (Exception e) {
			return getParentPostDate();
		}
	}
	
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}

	public final void setResponse(boolean response)
	{
		this.response = response;
	}

	public final boolean isResponse()
	{
		return response;
	}

	public final String getAuthorName()
	{
		return authorName;
	}
	
	public final void setAuthorName(String authorName)
	{
		this.authorName = authorName;
	}
	
	public final String getItemClass()
	{
		return itemClass;
	}
	
	public final void setItemClass(String itemClass)
	{
		this.itemClass = itemClass;
	}
	
	public String getLastUpdateName()
	{
		return lastUpdateName;
	}
	
	public void setLastUpdateName(String lastUpdateName)
	{
		this.lastUpdateName = lastUpdateName;
	}
	
	public String getLatestPostDate()
	{
		return latestPostDate;
	}
	
	public void setLatestPostDate(Date latestPostDate)
	{
		this.latestPostDate = latestPostDate.toString();
	}

	public final int getAuthorGlobalId()
	{
		return AuthorGlobalId;
	}

	public final void setAuthorGlobalId(int authorGlobalId)
	{
		AuthorGlobalId = authorGlobalId;
	}

	public final int getResponseCount()
	{
		return responseCount;
	}

	public final void setResponseCount(Long responseCount)
	{
		this.responseCount = responseCount.intValue();
	}

	public final String getItemType()
	{
		return itemType;
	}

	public final void setItemType(String itemType)
	{
		this.itemType = itemType;
	}

	public final String getTopicSubject()
	{
		return topicSubject;
	}

	public final void setTopicSubject(String topicSubject)
	{
		this.topicSubject = topicSubject;
	}

	public int getLastUpdateId() {
		return lastUpdateId;
	}
	
	public void setLastUpdateId(int lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}
	
	public String getPhotoPresent() {
		return photoPresent;
	}

	public void setPhotoPresent(String photoPresent) {
		this.photoPresent = photoPresent;
	}

	public Long getDepth() {
		return depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}

	public String getLastPostPhotoPresent() {
		return lastPostPhotoPresent;
	}

	public void setLastPostPhotoPresent(String lastPostPhotoPresent) {
		this.lastPostPhotoPresent = lastPostPhotoPresent;
	}

	public String getLastPosterName() {
		return lastPosterName;
	}

	public void setLastPosterName(String lastPosterName) {
		this.lastPosterName = lastPosterName;
	}

	public int getLastPosterId() {
		return lastPosterId;
	}

	public void setLastPosterId(int lastPosterId) {
		this.lastPosterId = lastPosterId;
	}

	public int getItemLike() {
		return itemLike;
	}

	public void setItemLike(Long itemLike) {
		if (itemLike == null) {
			this.itemLike = 0;
		} else {
			this.itemLike = itemLike.intValue();
		}
	}

	public int getAlreadyLike() {
		return alreadyLike;
	}

	public void setAlreadyLike(Long alreadyLike) {
		if (alreadyLike == null) {
			this.alreadyLike = 0;
		} else {
			this.alreadyLike = alreadyLike.intValue();
		}
	}

	public int getParentAuthorId() {
		return parentAuthorId;
	}

	public void setParentAuthorId(int parentAuthorId) {
		this.parentAuthorId = parentAuthorId;
	}

	public String getParentAuthorName() {
		return parentAuthorName;
	}

	public void setParentAuthorName(String parentAuthorName) {
		this.parentAuthorName = parentAuthorName;
	}

	public String getParentAuthorPhotoPresent() {
		return parentAuthorPhotoPresent;
	}

	public void setParentAuthorPhotoPresent(String parentAuthorPhotoPresent) {
		this.parentAuthorPhotoPresent = parentAuthorPhotoPresent;
	}

	public String getParentPostDate() {
		return parentPostDate;
	}

	public void setParentPostDate(Date parentPostDate) {
		this.parentPostDate = parentPostDate.toString();
	}
}