package com.era.community.assignment.dao;

import java.util.Date;

import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;
import com.era.community.tagging.dao.TaggedEntity;

/**
 *
 *  @entity name="ASNMT"
 *  @entity.longtext name="Body"
 * 
 */
public abstract class Assignment extends TaggedEntity
{
	/**
	 * @column integer not null
	 */
	protected int AssignmentsId;
	
	/**
	 * @column integer not null
	 */
	protected int ParentId;
	
	/**
	 * @column long varchar 
	 */
	protected String Body ;
	
	/**
	 * @column integer not null
	 */
	protected int SectionId;

	/**
	 * @column varchar(150) not null with default
	 */
	protected String Title;
	
	/**
	 * @column integer not null default 0
	 */
	protected int EntryType;
	
	/**
	 * @column timestamp not null with default
	 */
	protected Date DatePosted = new Date();
	
	/**
	 * @column timestamp not null with default
	 */
	protected Date DueDate = new Date();

	/**
	 * @column integer not null
	 */
	protected int CreatorId ;
	
	/**
	 * @column char(1) not null with default
	 */
	protected boolean Completed = false;

	/**
	 * @column integer 
	 */
	protected Integer CompletedById = null; 

	/**
	 * @column timestamp not null with default
	 */
	protected Date CompletedOn;
	
	protected Integer lft;
	protected Integer rht;
	protected Integer lvl;
	
	/**
	 * @column char(1) not null with default
	 */
	protected boolean Sticky = false;

	public static final int STATUS_LIVE = 0;
	public static final int STATUS_DELETED = 9;
	protected int DeleteStatus = 0;

	/*
	 * Injected references.
	 */
	protected AssignmentDao dao;
	protected UserFinder userFinder;

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		super.delete();
		dao.delete(this);
	}     

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user == null) return false;
		return true;
	}
	
	public boolean isTask() throws Exception
	{
		if (this instanceof AssignmentTask)
			return true;
		else
			return false;
	}
	
	public abstract AssignmentTask getTask() throws Exception;
	
	public User getPoster() throws Exception
	{
		return userFinder.getUserEntity(getCreatorId());
	}
	
	public String getBody()
	{
		return Body;
	}
	
	public void setBody(String body)
	{
		Body = body;
	}

	public Date getDatePosted()
	{
		return DatePosted;
	}

	public void setDatePosted(Date datePosted)
	{
		DatePosted = datePosted;
	}

	public String getTitle()
	{
		return Title;
	}

	public void setTitle(String title)
	{
		Title = title;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public int getCreatorId() {
		return CreatorId;
	}

	public void setCreatorId(int creatorId) {
		CreatorId = creatorId;
	}

	public int getParentId() {
		return ParentId;
	}

	public void setParentId(int parentId) {
		ParentId = parentId;
	}

	public int getSectionId() {
		return SectionId;
	}

	public void setSectionId(int sectionId) {
		SectionId = sectionId;
	}

	public boolean isCompleted() {
		return Completed;
	}

	public void setCompleted(boolean completed) {
		Completed = completed;
	}

	public Integer getCompletedById() {
		return CompletedById;
	}

	public void setCompletedById(Integer completedById) {
		CompletedById = completedById;
	}

	public Date getCompletedOn() {
		return CompletedOn;
	}

	public void setCompletedOn(Date completedOn) {
		CompletedOn = completedOn;
	}

	public Integer getLft() {
		return lft;
	}

	public void setLft(Integer lft) {
		this.lft = lft;
	}

	public Integer getRht() {
		return rht;
	}

	public void setRht(Integer rht) {
		this.rht = rht;
	}

	public Integer getLvl() {
		return lvl;
	}

	public void setLvl(Integer lvl) {
		this.lvl = lvl;
	}

	public int getAssignmentsId() {
		return AssignmentsId;
	}

	public void setAssignmentsId(int assignmentsId) {
		AssignmentsId = assignmentsId;
	}
	
	public final void setDao(AssignmentDao dao)
	{
		this.dao = dao;
	} 

	public boolean isSticky() {
		return Sticky;
	}

	public void setSticky(boolean sticky) {
		Sticky = sticky;
	}

	public int getDeleteStatus() {
		return DeleteStatus;
	}

	public void setDeleteStatus(int deleteStatus) {
		DeleteStatus = deleteStatus;
	}

	public int getEntryType() {
		return EntryType;
	}

	public void setEntryType(int entryType) {
		EntryType = entryType;
	}

	public Date getDueDate() {
		return DueDate;
	}

	public void setDueDate(Date dueDate) {
		DueDate = dueDate;
	}
}