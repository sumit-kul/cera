package com.era.community.doclib.dao;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import support.community.database.BlobData;

import com.era.community.accesslog.dao.AccessLog;
import com.era.community.accesslog.dao.AccessLogFinder;
import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;
import com.era.community.tagging.dao.TaggedEntity;

/**
 * 
 * @entity name="DCMT" 
 *
 * @entity.blob name="File"
 * 
 * @entity.index name="01" unique="no" columns="LibraryId"   
 * @entity.index name="02" unique="no" columns="PosterId"   
 * 
 * @entity.foreignkey name="01" columns="LibraryId" target="DLIB" ondelete="cascade"  
 * @entity.foreignkey name="02" columns="PosterId" target="USER" ondelete="restrict"  
 */
public class Document extends TaggedEntity
{
	/**
	 * @column integer not null
	 */
	protected int LibraryId;
	
	/**
	 * @column integer not null
	 */
	protected int FolderId;

	/**
	 * @column varchar(150) not null with default
	 */
	protected String Title;

	/**
	 * @column varchar(150)
	 */
	protected String FileName;    

	/**
	 * @column long varchar not null with default
	 */
	protected String Description = "";

	/**
	 * @column timestamp not null with default
	 */
	protected Date DatePosted;

	/**
	 * @column integer not null
	 */
	protected int PosterId ;

	/**
	 * @column integer not null with default
	 */
	protected int RevisionNumber ;

	/**
	 * @column timestamp not null with default
	 */
	protected Date DateRevised;
	
	protected int DocGroupNumber;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Inactive = false;
	
	private int CurrentProfile;

	protected int Downloads;

	protected Date LastDownloadTime;
	
	protected int Width;
	protected int Height;
	
	private int PrivacyLevel;
	
	private int FileLength;
	private String FileContentType;

	/*
	 * Injected references.
	 */
	protected DocumentDao dao;
	protected DocumentLibraryFinder libraryFinder;
	protected UserFinder userFinder;
	protected AccessLogFinder accessLogFinder;

	public List getItemsSince(Date date) throws Exception
	{
		return dao.getItemsSince(this, date);
	}

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void recordAccessForItem(int userId, int communityId) throws Exception
	{
		AccessLog aLog = accessLogFinder.newAccessLog();
		aLog.setItemId(this.getId());
		aLog.setItemType(this.getClass().getSimpleName());
		aLog.setUserId(userId);
		aLog.setCommunityId(communityId);
		aLog.update();
	}

	public void delete() throws Exception
	{
		super.delete();
		dao.delete(this);
	} 

	public final void setDao(DocumentDao dao)
	{
		this.dao = dao;
	}

	public Date getLatestPostDate() throws Exception
	{
		return dao.getLatestPostDate(this);
	}

	public DocumentLibrary getLibrary() throws Exception
	{
		return libraryFinder.getDocumentLibraryForId(getLibraryId());
	}

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return this.getLibrary().isReadAllowed(user);
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		if (getPosterId()==user.getId()) return true;
		Community comm = getCommunityEraContext().getCurrentCommunity();
		return comm.isAdminMember(user.getId());
	}

	public User getPoster() throws Exception
	{
		return userFinder.getUserEntity(PosterId);
	}

	public int  getStarRating() throws Exception
	{
		return dao.getStarRating(this);
	}

	public int  getNumberOfRaters() throws Exception
	{
		return dao.getNumberOfRaters(this);
	}

	public BlobData getFile() throws Exception
	{
		return dao.readFile(this);
	}

	public void storeFile(InputStream data, int dataLength, String contentType) throws Exception
	{
		dao.storeFile(this, data, dataLength, contentType);
	}

	public void storeFile(MultipartFile file) throws Exception
	{
		if (file.getContentType().startsWith("image/")) {
			//dao.storePhoto(this, file.getInputStream());
			dao.storeFile(this, file);
		} else {
			dao.storeFile(this, file.getInputStream(), (int)file.getSize(), file.getContentType());
		}
	}
	
	public void storeBlob(InputStream data, int dataLength, String contentType, int width, int height) throws Exception {
		dao.storePhoto(this, data, dataLength, contentType, width, height);
	}

	public String getFileContentType() throws Exception
	{
		return dao.getFileContentType(this);
	}

	public final Date getDatePosted()
	{
		return DatePosted;
	}
	public final void setDatePosted(Date datePosted)
	{
		DatePosted = datePosted;
	}
	public final Date getDateRevised()
	{
		return DateRevised;
	}
	public final void setDateRevised(Date dateRevised)
	{
		DateRevised = dateRevised;
	}
	public final String getDescription()
	{
		return Description;
	}
	public final void setDescription(String description)
	{
		Description = description;
	}
	public final boolean isInactive()
	{
		return Inactive;
	}
	public final void setInactive(boolean inactive)
	{
		Inactive = inactive;
	}
	public final int getLibraryId()
	{
		return LibraryId;
	}
	public final void setLibraryId(int libraryId)
	{
		LibraryId = libraryId;
	}
	public final int getPosterId()
	{
		return PosterId;
	}
	public final void setPosterId(int posterId)
	{
		PosterId = posterId;
	}
	public final int getRevisionNumber()
	{
		return RevisionNumber;
	}
	public final void setRevisionNumber(int revisionNumber)
	{
		RevisionNumber = revisionNumber;
	}
	public final String getTitle()
	{
		return Title;
	}
	public final void setTitle(String title)
	{
		Title = title;
	}
	public final DocumentDao getDao()
	{
		return dao;
	}

	/**
	 * @return Returns the fileName.
	 */
	public String getFileName()
	{
		return FileName;
	}
	/**
	 * @param fileName The fileName to set.
	 */
	public void setFileName(String fileName)
	{
		FileName = fileName;
	}


	public final void setLibraryFinder(DocumentLibraryFinder libraryFinder)
	{
		this.libraryFinder = libraryFinder;
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setAccessLogFinder(AccessLogFinder accessLogFinder)
	{
		this.accessLogFinder = accessLogFinder;
	}

	public int getDownloads() {
		return Downloads;
	}

	public void setDownloads(int downloads) {
		Downloads = downloads;
	}

	public Date getLastDownloadTime() {
		return LastDownloadTime;
	}

	public void setLastDownloadTime(Date lastDownloadTime) {
		LastDownloadTime = lastDownloadTime;
	}

	public int getFolderId() {
		return FolderId;
	}

	public void setFolderId(int folderId) {
		FolderId = folderId;
	}

	public int getWidth() {
		return Width;
	}

	public void setWidth(int width) {
		Width = width;
	}

	public int getHeight() {
		return Height;
	}

	public void setHeight(int height) {
		Height = height;
	}

	public int getPrivacyLevel() {
		return PrivacyLevel;
	}

	public void setPrivacyLevel(int privacyLevel) {
		PrivacyLevel = privacyLevel;
	}

	public int getFileLength() {
		return FileLength;
	}

	public void setFileLength(int fileLength) {
		FileLength = fileLength;
	}

	public void setFileContentType(String fileContentType) {
		FileContentType = fileContentType;
	}

	public int getCurrentProfile() {
		return CurrentProfile;
	}

	public void setCurrentProfile(int currentProfile) {
		CurrentProfile = currentProfile;
	}

	public int getDocGroupNumber() {
		return DocGroupNumber;
	}

	public void setDocGroupNumber(int docGroupNumber) {
		DocGroupNumber = docGroupNumber;
	}
}