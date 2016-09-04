package com.era.community.forum.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;
import support.community.database.BlobData;

import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumResponse;
import com.era.community.forum.dao.ForumTopic;

public abstract class ForumItemDaoBaseImpl extends AbstractJdbcDaoSupport implements ForumItemDaoBase
{
	/*
	 *
	 */
	public ForumItem getForumItemForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (ForumItem)getEntity(ForumItem.class, keys);
	}

	/*
	 *
	 */
	public void deleteForumItemForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(ForumItem o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(ForumItem o) throws Exception
	{
		storeEntity(o);
	}

	public void store(ForumItem o, boolean forCountOnly) throws Exception
	{
		storeEntity(o, forCountOnly);
	}

	public BlobData readFile1(ForumItem o) throws Exception
	{
		return  readBlobItem(o, "File1");
	} 
	public void storeFile1(ForumItem o, java.io.InputStream data, int dataLength, String contentType) throws Exception
	{
		storeBlob(o, "File1", data, dataLength, contentType);
	}
	public void storeFile1(ForumItem o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "File1", data);
	}
	public String getFile1ContentType(ForumItem o) throws Exception
	{
		return (String)getColumn(o, "File1ContentType", String.class);
	} 
	public String getFile1SearchText(ForumItem o) throws Exception
	{
		return (String)getColumn(o, "File1SearchText", String.class);
	} 
	public void setFile1SearchText(ForumItem o, String s) throws Exception
	{
		setColumn(o, "File1SearchText", s);
	} 
	public void markFile1Relocated(ForumItem o) throws Exception
	{
		setColumn(o, "File1", null);
	}
	public BlobData readFile2(ForumItem o) throws Exception
	{
		return  readBlobItem(o, "File2");
	} 
	public void storeFile2(ForumItem o, java.io.InputStream data, int dataLength, String contentType) throws Exception
	{
		storeBlob(o, "File2", data, dataLength, contentType);
	}
	public void storeFile2(ForumItem o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "File2", data);
	}
	public String getFile2ContentType(ForumItem o) throws Exception
	{
		return (String)getColumn(o, "File2ContentType", String.class);
	} 
	public String getFile2SearchText(ForumItem o) throws Exception
	{
		return (String)getColumn(o, "File2SearchText", String.class);
	} 
	public void setFile2SearchText(ForumItem o, String s) throws Exception
	{
		setColumn(o, "File2SearchText", s);
	} 
	public void markFile2Relocated(ForumItem o) throws Exception
	{
		setColumn(o, "File2", null);
	}
	public BlobData readFile3(ForumItem o) throws Exception
	{
		return  readBlobItem(o, "File3");
	} 
	public void storeFile3(ForumItem o, java.io.InputStream data, int dataLength, String contentType) throws Exception
	{
		storeBlob(o, "File3", data, dataLength, contentType);
	}
	public void storeFile3(ForumItem o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "File3", data);
	}
	public String getFile3ContentType(ForumItem o) throws Exception
	{
		return (String)getColumn(o, "File3ContentType", String.class);
	} 
	public String getFile3SearchText(ForumItem o) throws Exception
	{
		return (String)getColumn(o, "File3SearchText", String.class);
	} 
	public void setFile3SearchText(ForumItem o, String s) throws Exception
	{
		setColumn(o, "File3SearchText", s);
	} 
	public void markFile3Relocated(ForumItem o) throws Exception
	{
		setColumn(o, "File3", null);
	}
	public ForumResponse newForumResponse() throws Exception
	{
		return (ForumResponse)newEntity(ForumResponse.class);
	}
	public ForumTopic newForumTopic() throws Exception
	{
		return (ForumTopic)newEntity(ForumTopic.class);
	}
}