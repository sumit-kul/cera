package com.era.community.forum.dao;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import support.community.application.ElementNotFoundException;
import support.community.database.BlobData;

import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;
import com.era.community.tagging.dao.TaggedEntity;

/**
 * 
 * @entity name="FMIT" 
 * 
 *  @entity.blob name="File1"
 *  @entity.blob name="File2"
 *  @entity.blob name="File3"
 *
 * @entity.index name="01" unique="no" columns="ForumId"   
 * @entity.index name="02" unique="no" columns="AuthorId"   
 * @entity.index name="03" unique="no" columns="TopicId"     
 *  
 * @entity.foreignkey name="01" columns="ForumId" target="FRUM" ondelete="cascade"  
 * @entity.foreignkey name="02" columns="AuthorId" target="USER" ondelete="restrict"  
 * @entity.foreignkey name="03" columns="TopicId" target="FMIT" ondelete="cascade"
 * 
 *   // Non essential foreign key - can be null
 * @entity.foreignkey name="04" columns="ClosedById" target="USER" ondelete="restrict"  
 */
public abstract class ForumItem extends TaggedEntity
{
	/**
	 * @column integer not null
	 */
	protected int ForumId; 

	/**
	 * @column varchar(200) not null with default
	 */
	protected String Subject ;

	/**
	 * @column long varchar 
	 */
	protected String Body ;

	/**
	 * @column integer not null 
	 */
	protected int AuthorId ;

	/**
	 * @column timestamp not null with default 
	 */
	protected Date DatePosted ;

	/**
	 * @column varchar(150)
	 */
	protected String FileName1;    

	/**
	 * @column varchar(150)
	 */
	protected String FileName2;  

	/**
	 * @column varchar(150)
	 */
	protected String FileName3;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Closed= false;

	/**
	 * @column integer 
	 */
	protected Integer ClosedById = null; 

	/**
	 * @column timestamp not null with default
	 */
	protected Date ClosedOn;

	protected int Visitors;

	protected Date LastVisitorsTime;
	
	protected Integer lft;
	protected Integer rht;
	protected Integer lvl;
	protected Integer ParentId;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Sticky = false;

	public static final int STATUS_LIVE = 0;
	public static final int STATUS_DELETED = 9;
	protected int DeleteStatus = 0;

	protected ForumItemDao dao;
	protected ForumFinder forumFinder;
	protected UserFinder userFinder;
	protected ForumItemLikeFinder forumItemLikeFinder;

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public final void setDao(ForumItemDao dao)
	{
		this.dao = dao;
	}  

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		Forum forum = forumFinder.getForumForId(this.getForumId());
		return (forum.isReadAllowed(user));
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		if (getAuthorId()==user.getId()) return true;
		Community comm = getCommunityEraContext().getCurrentCommunity();
		return comm.isAdminMember(user.getId());
	}

	public List getItemsSince(Date date) throws Exception
	{
		return dao.getItemsSince(this, date);
	}

	public boolean isResponse() throws Exception
	{
		if (this instanceof ForumTopic)
			return false;
		else
			return true;
	}

	public boolean isTopic() throws Exception
	{
		if (this instanceof ForumTopic)
			return true;
		else
			return false;
	}

	public abstract ForumTopic getTopic() throws Exception;

	public User getAuthor() throws Exception
	{
		return userFinder.getUserEntity(getAuthorId());
	}

	public Forum getForum() throws Exception
	{
		return forumFinder.getForumForId(getForumId());
	}

	public int getAuthorId()
	{
		return AuthorId;
	}
	public void setAuthorId(int authorId)
	{
		AuthorId = authorId;
	}
	public String getBody()
	{
		return Body;
	}
	public void setBody(String body)
	{
		Body = body;
	}

	public int getForumId()
	{
		return ForumId;
	}
	public void setForumId(int forumId)
	{
		ForumId = forumId;
	}
	public String getSubject()
	{
		return Subject;
	}
	public void setSubject(String subject)
	{
		Subject = subject;
	}
	public Date getDatePosted()
	{
		return DatePosted;
	}
	public void setDatePosted(Date datePosted)
	{
		DatePosted = datePosted;
	}

	public Date getClosedOn()
	{
		return ClosedOn;
	}
	public void setClosedOn(Date closedOn)
	{
		ClosedOn = closedOn;
	}

	public final boolean isSticky()
	{
		return Sticky;
	}

	public void setSticky(boolean sticky)
	{
		Sticky = sticky;
	}

	public final ForumItemDao getDao()
	{
		return dao;
	}

	public BlobData getFile1() throws Exception
	{
		return dao.readFile1(this);
	}

	public void clearFile1() throws Exception
	{
		dao.clearFile1(this);
	}

	public BlobData getFile2() throws Exception
	{
		return dao.readFile2(this);
	}

	public void clearFile2() throws Exception
	{
		dao.clearFile2(this);
	}

	public void clearFile3() throws Exception
	{
		dao.clearFile3(this);
	}

	public BlobData getFile3() throws Exception
	{
		return dao.readFile3(this);
	}

	public final String getFileName1()
	{
		return FileName1;
	}
	public final void setFileName1(String fileName1)
	{
		FileName1 = fileName1;
	}
	public final String getFileName2()
	{
		return FileName2;
	}
	public final void setFileName2(String fileName2)
	{
		FileName2 = fileName2;
	}
	public final String getFileName3()
	{
		return FileName3;
	}
	public final void setFileName3(String fileName3)
	{
		FileName3 = fileName3;
	}

	public boolean isAlreadyLike(int userId) throws Exception {
		try {
			ForumItemLike forumItemLike = forumItemLikeFinder.getLikeForForumItemAndUser(this.getId(), userId);
			return true;
		}
		catch (ElementNotFoundException e) {
			return false;
		}
	}

	public int getLikeCountForForumItem() throws Exception {
		return forumItemLikeFinder.getLikeCountForForumItem(this.getId());
	}

	public final String getFile1ContentType() throws Exception
	{
		return dao.getFile1ContentType(this);
	}

	public final String getFile2ContentType() throws Exception
	{
		return dao.getFile2ContentType(this);
	}

	public final String getFile3ContentType() throws Exception
	{
		return dao.getFile3ContentType(this);
	}

	public void storeFile1(MultipartFile data) throws Exception
	{
		dao.storeFile1(this, data);
	}

	public void storeFile2(MultipartFile data) throws Exception
	{
		dao.storeFile2(this, data);
	}

	public void storeFile3(MultipartFile data) throws Exception
	{
		dao.storeFile3(this, data);
	}

	public void storeFile1(InputStream data, int length, String contentType) throws Exception
	{
		dao.storeFile1(this, data, length, contentType);
	}

	public void storeFile2(InputStream data, int length, String contentType) throws Exception
	{
		dao.storeFile2(this, data, length, contentType);
	}

	public void storeFile3(InputStream data, int length, String contentType) throws Exception
	{
		dao.storeFile3(this, data, length, contentType);
	}

	public boolean isFile1Present() throws Exception
	{
		return dao.isFile1Present(this);
	}

	public boolean isFile2Present() throws Exception
	{
		return dao.isFile2Present(this);
	}

	public boolean isFile3Present() throws Exception
	{
		return dao.isFile3Present(this);
	}

	public final void setForumFinder(ForumFinder forumFinder)
	{
		this.forumFinder = forumFinder;
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	/** 
	 *  Delete this entity from the database.
	 */
	public void delete() throws Exception
	{
		dao.delete(this);
	}

	public int getDeleteStatus()
	{
		return DeleteStatus;
	}

	public void setDeleteStatus(int deleteStatus)
	{
		DeleteStatus = deleteStatus;
	}

	public final Integer getClosedById()
	{
		return ClosedById;
	}

	public final void setClosedById(Integer closedById)
	{
		ClosedById = closedById;
	}

	public final boolean isClosed()
	{
		return Closed;
	}

	public final void setClosed(boolean closed)
	{
		Closed = closed;
	} 

	public int getVisitors() {
		return Visitors;
	}

	public void setVisitors(int visitors) {
		Visitors = visitors;
	}

	public Date getLastVisitorsTime() {
		return LastVisitorsTime;
	}

	public void setLastVisitorsTime(Date lastVisitorsTime) {
		LastVisitorsTime = lastVisitorsTime;
	}

	public void setForumItemLikeFinder(ForumItemLikeFinder forumItemLikeFinder) {
		this.forumItemLikeFinder = forumItemLikeFinder;
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

	public Integer getParentId() {
		return ParentId;
	}

	public void setParentId(Integer parentId) {
		ParentId = parentId;
	}
}