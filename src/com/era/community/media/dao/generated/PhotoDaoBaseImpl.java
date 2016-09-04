package com.era.community.media.dao.generated; 

import java.io.InputStream;

import support.community.database.AbstractJdbcDaoSupport;
import support.community.database.BlobData;

import com.era.community.media.dao.Photo;

public abstract class PhotoDaoBaseImpl extends AbstractJdbcDaoSupport implements PhotoDaoBase
{
	public Photo getPhotoForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Photo)getEntity(Photo.class, keys);
	}
	
	public void delete(Photo o) throws Exception
	{
		deleteEntity(o);
	}
	
	public void deletePhotoForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}
	
	public void store(Photo o) throws Exception
	{
		storeEntity(o);
	}
	public BlobData readPhoto(Photo o) throws Exception
	{
		return  readBlobItem(o, "Photo");
	} 

	public void storePhoto(Photo o, java.io.InputStream data, int dataLength, String contentType) throws Exception
	{
		storeBlob(o, "Photo", data, dataLength, contentType);
	}

	public void storePhoto(Photo o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "Photo", data);
	}
	
	public void storePhoto(Photo o, InputStream imageInputStream) throws Exception
	{
		storeBlob(o, "Photo", imageInputStream);
	}

	public String getPhotoContentType(Photo o) throws Exception
	{
		return (String)getColumn(o, "PhotoContentType", String.class);
	} 

	public String getPhotoSearchText(Photo o) throws Exception
	{
		return (String)getColumn(o, "PhotoSearchText", String.class);
	} 

	public void setPhotoSearchText(Photo o, String s) throws Exception
	{
		setColumn(o, "PhotoSearchText", s);
	} 

	public void markPhotoRelocated(Photo o) throws Exception
	{
		setColumn(o, "Photo", null);
	}

	public Photo newPhoto() throws Exception
	{
		return (Photo)newEntity(Photo.class);
	}
}