package com.era.community.upload.dao;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import support.community.database.BlobData;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class Image extends CecAbstractEntity
{
	protected int BlogEntryId;
	protected int WikiEntryId;
	protected int WikiEntrySectionId;
	protected int ForumItemId;
	protected int BlogEntryCommentId;
	protected int WikiEntryCommentId;
	protected int CommunityDescriptionId;
	protected int AboutMeId;
	protected int MessageId;
	protected int AssignmentId;
	protected int MarkDeleted;
	protected String Title;
	protected String FileName;    
	protected int PosterId ;
	protected int Width;
	protected int Height;
	private int FileLength;
	private String FileContentType;
	protected int Thumbnail;

	protected ImageDao dao;

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	} 

	public final void setDao(ImageDao dao)
	{
		this.dao = dao;
	}

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		return true;
	}
	
	public BlobData getFile() throws Exception
	{
		return dao.readFile(this);
	}
	
	public BlobData getFileThumb() throws Exception
	{
		return dao.readFileThumb(this);
	}

	public void storeFile(InputStream data, int dataLength, String contentType) throws Exception
	{
		dao.storeFile(this, data, dataLength, contentType);
	}

	public void storeFile(MultipartFile file) throws Exception
	{
		if (file.getContentType().startsWith("image/")) {
			dao.storePhoto(this, file.getInputStream());
		} else {
			dao.storeFile(this, file);
		}
	}
	
	public void storePhoto(InputStream imageInputStream) throws Exception
	{
		dao.storePhoto(this, imageInputStream);
	}
	
	public void storeBlob(InputStream data, int dataLength, String contentType, int width, int height) throws Exception {
		dao.storePhoto(this, data, dataLength, contentType, width, height);
	}
	
	public void storeBlobThumbnail(InputStream data, int dataLength, String contentType, int width, int height) throws Exception {
		dao.storePhotoThumb(this, data, dataLength, contentType, width, height);
	}

	public int getBlogEntryId() {
		return BlogEntryId;
	}

	public void setBlogEntryId(int blogEntryId) {
		BlogEntryId = blogEntryId;
	}

	public int getWikiEntryId() {
		return WikiEntryId;
	}

	public void setWikiEntryId(int wikiEntryId) {
		WikiEntryId = wikiEntryId;
	}

	public int getForumItemId() {
		return ForumItemId;
	}

	public void setForumItemId(int forumItemId) {
		ForumItemId = forumItemId;
	}

	public int getBlogEntryCommentId() {
		return BlogEntryCommentId;
	}

	public void setBlogEntryCommentId(int blogEntryCommentId) {
		BlogEntryCommentId = blogEntryCommentId;
	}

	public int getWikiEntryCommentId() {
		return WikiEntryCommentId;
	}

	public void setWikiEntryCommentId(int wikiEntryCommentId) {
		WikiEntryCommentId = wikiEntryCommentId;
	}

	public int getCommunityDescriptionId() {
		return CommunityDescriptionId;
	}

	public void setCommunityDescriptionId(int communityDescriptionId) {
		CommunityDescriptionId = communityDescriptionId;
	}

	public int getAboutMeId() {
		return AboutMeId;
	}

	public void setAboutMeId(int aboutMeId) {
		AboutMeId = aboutMeId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public int getPosterId() {
		return PosterId;
	}

	public void setPosterId(int posterId) {
		PosterId = posterId;
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

	public int getFileLength() {
		return FileLength;
	}

	public void setFileLength(int fileLength) {
		FileLength = fileLength;
	}

	public void setFileContentType(String fileContentType) {
		FileContentType = fileContentType;
	}

	public int getMessageId() {
		return MessageId;
	}

	public void setMessageId(int messageId) {
		MessageId = messageId;
	}

	public int getWikiEntrySectionId() {
		return WikiEntrySectionId;
	}

	public void setWikiEntrySectionId(int wikiEntrySectionId) {
		WikiEntrySectionId = wikiEntrySectionId;
	}

	public int getMarkDeleted() {
		return MarkDeleted;
	}

	public void setMarkDeleted(int markDeleted) {
		MarkDeleted = markDeleted;
	}

	public int getThumbnail() {
		return Thumbnail;
	}

	public void setThumbnail(int thumbnail) {
		Thumbnail = thumbnail;
	}

	public int getAssignmentId() {
		return AssignmentId;
	}

	public void setAssignmentId(int assignmentId) {
		AssignmentId = assignmentId;
	}
}