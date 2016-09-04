package com.era.community.upload.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;
import support.community.database.BlobData;

import com.era.community.upload.dao.Upload;

public abstract class UploadDaoBaseImpl extends AbstractJdbcDaoSupport implements UploadDaoBase
{
	/*
	 *
	 */
	public Upload getUploadForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Upload)getEntity(Upload.class, keys);
	}

	/*
	 *
	 */
	public void deleteUploadForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(Upload o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(Upload o) throws Exception
	{
		storeEntity(o);
	}

	public BlobData readData(Upload o) throws Exception
	{
		return  readBlobItem(o, "Data");
	} 
	public void storeData(Upload o, java.io.InputStream data, int dataLength, String contentType) throws Exception
	{
		storeBlob(o, "Data", data, dataLength, contentType);
	}
	public void storeData(Upload o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "Data", data);
	}
	public String getDataContentType(Upload o) throws Exception
	{
		return (String)getColumn(o, "DataContentType", String.class);
	} 
	public String getDataSearchText(Upload o) throws Exception
	{
		return (String)getColumn(o, "DataSearchText", String.class);
	} 
	public void setDataSearchText(Upload o, String s) throws Exception
	{
		setColumn(o, "DataSearchText", s);
	} 
	public void markDataRelocated(Upload o) throws Exception
	{
		setColumn(o, "Data", null);
	}

	/*
	 *
	 */
	public Upload newUpload() throws Exception
	{
		return (Upload)newEntity(Upload.class);
	}

}
