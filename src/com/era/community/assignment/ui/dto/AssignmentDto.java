package com.era.community.assignment.ui.dto; 

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;

import support.community.util.StringHelper;

public class AssignmentDto extends com.era.community.assignment.dao.generated.AssignmentEntity
{
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String creatorName;
	private String lastAssigneeName;
	private String photoPresent;
	private String lastAssigneePhotoPresent;
	private int lastAssigneeId;
	private int entryCount;
	private int toDoCount;
	private String itemType;
	private String entryTitle;
	private int parentCreatorId;
	private String parentCreatorName;
	private String parentCreatorPhotoPresent;
	private String parentPostDate;
	private String ddate;

	private String tags;
	private Long depth;
	private String body;
	
	public boolean isPassedDue() {
		try {
			Date date = formatter.parse(this.getDueDate());
			Date ndate = new Date();
			if(DateUtils.isSameDay(date, ndate)) {
				return false;
			} else if (date.before(ndate)) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	public int getLevel() throws Exception
	{
		if (getLvl() == null) return 1;
		return getLvl()+1;
	}
	
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
	
	public String getDisplayTitle()
	{
		if ( (this.getTitle() == null) || (this.getTitle().length() == 0)) return "";

		String sub = this.getTitle();
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
	
	public String getPostedOnHover() throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
		try {
			Date date = formatter.parse(getDatePosted());
			return fmt2.format(date);
		} catch (Exception e) {
			return getDatePosted();
		}
	}
	
	public String getDueOn() throws Exception
	{
		SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt2 = new SimpleDateFormat("dd MMM, yy");
		Date today = new Date();
		String sToday = fmter.format(today);

		try {

			Date date = formatter.parse(getDueDate());
			if (fmter.format(date).equals(sToday)) {
				return "Today";
			}
			return fmt2.format(date);

		} catch (ParseException e) {
			return getDueDate();
		}
	}
	
	public String getDueOnHover() throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
		try {
			Date date = formatter.parse(getDueDate());
			return fmt2.format(date);
		} catch (Exception e) {
			return getDueDate();
		}
	}
	
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	public final String getItemType()
	{
		return itemType;
	}

	public final void setItemType(String itemType)
	{
		this.itemType = itemType;
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

	public String getParentPostDate() {
		return parentPostDate;
	}

	public void setParentPostDate(Date parentPostDate) {
		this.parentPostDate = parentPostDate.toString();
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public int getEntryCount() {
		return entryCount;
	}

	public void setEntryCount(Long entryCount) {
		this.entryCount = entryCount.intValue();
	}

	public String getEntryTitle() {
		return entryTitle;
	}

	public void setEntryTitle(String entryTitle) {
		this.entryTitle = entryTitle;
	}

	public int getParentCreatorId() {
		return parentCreatorId;
	}

	public void setParentCreatorId(int parentCreatorId) {
		this.parentCreatorId = parentCreatorId;
	}

	public String getParentCreatorName() {
		return parentCreatorName;
	}

	public void setParentCreatorName(String parentCreatorName) {
		this.parentCreatorName = parentCreatorName;
	}

	public String getParentCreatorPhotoPresent() {
		return parentCreatorPhotoPresent;
	}

	public void setParentCreatorPhotoPresent(String parentCreatorPhotoPresent) {
		this.parentCreatorPhotoPresent = parentCreatorPhotoPresent;
	}

	public String getLastAssigneePhotoPresent() {
		return lastAssigneePhotoPresent;
	}

	public void setLastAssigneePhotoPresent(String lastAssigneePhotoPresent) {
		this.lastAssigneePhotoPresent = lastAssigneePhotoPresent;
	}

	public String getLastAssigneeName() {
		return lastAssigneeName;
	}

	public void setLastAssigneeName(String lastAssigneeName) {
		this.lastAssigneeName = lastAssigneeName;
	}

	public int getLastAssigneeId() {
		return lastAssigneeId;
	}

	public void setLastAssigneeId(int lastAssigneeId) {
		this.lastAssigneeId = lastAssigneeId;
	}

	public int getToDoCount() {
		return toDoCount;
	}

	public void setToDoCount(Long toDoCount) {
		this.toDoCount = toDoCount.intValue();
	}

	public String getDdate() {
		return ddate;
	}

	public void setDdate(String ddate) {
		this.ddate = ddate;
	}
}