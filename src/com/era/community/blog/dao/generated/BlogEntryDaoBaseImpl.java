package com.era.community.blog.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;
import support.community.database.BlobData;

import com.era.community.blog.dao.BlogEntry;

public abstract class BlogEntryDaoBaseImpl extends AbstractJdbcDaoSupport implements BlogEntryDaoBase
{
	public BlogEntry getBlogEntryForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (BlogEntry)getEntity(BlogEntry.class, keys);
	}

	public void deleteBlogEntryForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(BlogEntry o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(BlogEntry o) throws Exception
	{
		storeEntity(o);
	}

	public void store(BlogEntry o, boolean forCountOnly) throws Exception
	{
		storeEntity(o, forCountOnly);
	}

	public BlobData readFile1(BlogEntry o) throws Exception
	{
		return  readBlobItem(o, "File1");
	} 
	public void storeFile1(BlogEntry o, java.io.InputStream data, int dataLength, String contentType) throws Exception
	{
		storeBlob(o, "File1", data, dataLength, contentType);
	}
	public void storeFile1(BlogEntry o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "File1", data);
	}
	public String getFile1ContentType(BlogEntry o) throws Exception
	{
		return (String)getColumn(o, "File1ContentType", String.class);
	} 
	public String getFile1SearchText(BlogEntry o) throws Exception
	{
		return (String)getColumn(o, "File1SearchText", String.class);
	} 
	public void setFile1SearchText(BlogEntry o, String s) throws Exception
	{
		setColumn(o, "File1SearchText", s);
	} 
	public void markFile1Relocated(BlogEntry o) throws Exception
	{
		setColumn(o, "File1", null);
	}
	public BlobData readFile2(BlogEntry o) throws Exception
	{
		return  readBlobItem(o, "File2");
	} 
	public void storeFile2(BlogEntry o, java.io.InputStream data, int dataLength, String contentType) throws Exception
	{
		storeBlob(o, "File2", data, dataLength, contentType);
	}
	public void storeFile2(BlogEntry o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "File2", data);
	}
	public String getFile2ContentType(BlogEntry o) throws Exception
	{
		return (String)getColumn(o, "File2ContentType", String.class);
	} 
	public String getFile2SearchText(BlogEntry o) throws Exception
	{
		return (String)getColumn(o, "File2SearchText", String.class);
	} 
	public void setFile2SearchText(BlogEntry o, String s) throws Exception
	{
		setColumn(o, "File2SearchText", s);
	} 
	public void markFile2Relocated(BlogEntry o) throws Exception
	{
		setColumn(o, "File2", null);
	}
	public BlobData readFile3(BlogEntry o) throws Exception
	{
		return  readBlobItem(o, "File3");
	} 
	public void storeFile3(BlogEntry o, java.io.InputStream data, int dataLength, String contentType) throws Exception
	{
		storeBlob(o, "File3", data, dataLength, contentType);
	}
	public void storeFile3(BlogEntry o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "File3", data);
	}
	public String getFile3ContentType(BlogEntry o) throws Exception
	{
		return (String)getColumn(o, "File3ContentType", String.class);
	} 
	public String getFile3SearchText(BlogEntry o) throws Exception
	{
		return (String)getColumn(o, "File3SearchText", String.class);
	} 
	public void setFile3SearchText(BlogEntry o, String s) throws Exception
	{
		setColumn(o, "File3SearchText", s);
	} 
	public void markFile3Relocated(BlogEntry o) throws Exception
	{
		setColumn(o, "File3", null);
	}
	public BlogEntry newBlogEntry() throws Exception
	{
		return (BlogEntry)newEntity(BlogEntry.class);
	}
}