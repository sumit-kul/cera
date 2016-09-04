package com.era.community.doclib.dao.generated; 

import java.io.InputStream;

import support.community.database.AbstractJdbcDaoSupport;
import support.community.database.BlobData;

import com.era.community.doclib.dao.Document;

public abstract class DocumentDaoBaseImpl extends AbstractJdbcDaoSupport implements DocumentDaoBase
{
	/*
	 *
	 */
	public Document getDocumentForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Document)getEntity(Document.class, keys);
	}

	/*
	 *
	 */
	public void deleteDocumentForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(Document o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(Document o) throws Exception
	{
		storeEntity(o);
	}

	public BlobData readFile(Document o) throws Exception
	{
		return  readBlobItem(o, "File");
	} 
	public void storeFile(Document o, java.io.InputStream data, int dataLength, String contentType) throws Exception
	{
		storeBlob(o, "File", data, dataLength, contentType);
	}
	public void storeFile(Document o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "File", data);
	}
	public void storePhoto(Document o, InputStream imageInputStream) throws Exception
	{
		storeBlob(o, "File", imageInputStream);
	}
	
	public void storePhoto(Document o, java.io.InputStream data, int dataLength, String contentType, int width, int height) throws Exception
	{
		storeBlob(o, "File", data, dataLength, contentType, width, height);
	}
	
	public String getFileContentType(Document o) throws Exception
	{
		return (String)getColumn(o, "FileContentType", String.class);
	} 
	public String getFileSearchText(Document o) throws Exception
	{
		return (String)getColumn(o, "FileSearchText", String.class);
	} 
	public void setFileSearchText(Document o, String s) throws Exception
	{
		setColumn(o, "FileSearchText", s);
	} 
	public void markFileRelocated(Document o) throws Exception
	{
		setColumn(o, "File", null);
	}

	/*
	 *
	 */
	public Document newDocument() throws Exception
	{
		return (Document)newEntity(Document.class);
	}

}
