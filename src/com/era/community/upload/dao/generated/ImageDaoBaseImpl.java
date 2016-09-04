package com.era.community.upload.dao.generated; 

import java.io.InputStream;

import support.community.database.AbstractJdbcDaoSupport;
import support.community.database.BlobData;

import com.era.community.upload.dao.Image;

public abstract class ImageDaoBaseImpl extends AbstractJdbcDaoSupport implements ImageDaoBase
{
	public Image getImageForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Image)getEntity(Image.class, keys);
	}

	public void deleteImageForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(Image o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(Image o) throws Exception
	{
		storeEntity(o);
	}

	public BlobData readFile(Image o) throws Exception
	{
		return  readBlobItem(o, "File");
	} 
	
	public BlobData readFileThumb(Image o) throws Exception
	{
		return  readBlobItem(o, "FileThumb");
	} 
	
	public void storeFile(Image o, java.io.InputStream data, int dataLength, String contentType) throws Exception
	{
		storeBlob(o, "File", data, dataLength, contentType);
	}
	
	public void storeFile(Image o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "File", data);
	}
	
	public void storePhoto(Image o, InputStream imageInputStream) throws Exception
	{
		storeBlob(o, "File", imageInputStream);
	}
	
	public void storePhoto(Image o, java.io.InputStream data, int dataLength, String contentType, int width, int height) throws Exception
	{
		storeBlob(o, "File", data, dataLength, contentType, width, height);
	}
	
	public void storePhotoThumb(Image o, java.io.InputStream data, int dataLength, String contentType, int width, int height) throws Exception
	{
		storeBlob(o, "FileThumb", data, dataLength, contentType, width, height);
	}

	public Image newImage() throws Exception
	{
		return (Image)newEntity(Image.class);
	}
}