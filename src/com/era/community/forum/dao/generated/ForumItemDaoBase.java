package com.era.community.forum.dao.generated; 

import com.era.community.forum.dao.ForumItem;

public interface ForumItemDaoBase extends ForumItemFinderBase
{
	public void store(ForumItem o) throws Exception;
	public void store(ForumItem o, boolean isAllowed) throws Exception;
	public void deleteForumItemForId(int id) throws Exception;
	public void delete(ForumItem o) throws Exception;
	public support.community.database.BlobData readFile1(ForumItem o) throws Exception;
	public void storeFile1(ForumItem o, java.io.InputStream data, int dataLength, String contentType) throws Exception;
	public void storeFile1(ForumItem o, org.springframework.web.multipart.MultipartFile file) throws Exception;
	public void markFile1Relocated(ForumItem o) throws Exception;
	public String getFile1ContentType(ForumItem o) throws Exception;
	public String getFile1SearchText(ForumItem o) throws Exception;
	public void setFile1SearchText(ForumItem o, String s) throws Exception;
	public support.community.database.BlobData readFile2(ForumItem o) throws Exception;
	public void storeFile2(ForumItem o, java.io.InputStream data, int dataLength, String contentType) throws Exception;
	public void storeFile2(ForumItem o, org.springframework.web.multipart.MultipartFile file) throws Exception;
	public void markFile2Relocated(ForumItem o) throws Exception;
	public String getFile2ContentType(ForumItem o) throws Exception;
	public String getFile2SearchText(ForumItem o) throws Exception;
	public void setFile2SearchText(ForumItem o, String s) throws Exception;
	public support.community.database.BlobData readFile3(ForumItem o) throws Exception;
	public void storeFile3(ForumItem o, java.io.InputStream data, int dataLength, String contentType) throws Exception;
	public void storeFile3(ForumItem o, org.springframework.web.multipart.MultipartFile file) throws Exception;
	public void markFile3Relocated(ForumItem o) throws Exception;
	public String getFile3ContentType(ForumItem o) throws Exception;
	public String getFile3SearchText(ForumItem o) throws Exception;
	public void setFile3SearchText(ForumItem o, String s) throws Exception;
}

