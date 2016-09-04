package com.era.community.faqs.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;
import support.community.database.BlobData;

import com.era.community.faqs.dao.Faq;

public abstract class FaqDaoBaseImpl extends AbstractJdbcDaoSupport implements FaqDaoBase
{
	/*
	 *
	 */
	public Faq getFaqForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Faq)getEntity(Faq.class, keys);
	}

	/*
	 *
	 */
	public void deleteFaqForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(Faq o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(Faq o) throws Exception
	{
		storeEntity(o);
	}

	public BlobData readFile(Faq o) throws Exception
	{
		return  readBlobItem(o, "File");
	} 
	public void storeFile(Faq o, java.io.InputStream data, int dataLength, String contentType) throws Exception
	{
		storeBlob(o, "File", data, dataLength, contentType);
	}
	public void storeFile(Faq o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "File", data);
	}
	public String getFileContentType(Faq o) throws Exception
	{
		return (String)getColumn(o, "FileContentType", String.class);
	} 
	public String getFileSearchText(Faq o) throws Exception
	{
		return (String)getColumn(o, "FileSearchText", String.class);
	} 
	public void setFileSearchText(Faq o, String s) throws Exception
	{
		setColumn(o, "FileSearchText", s);
	} 
	public void markFileRelocated(Faq o) throws Exception
	{
		setColumn(o, "File", null);
	}

	/*
	 *
	 */
	public Faq newFaq() throws Exception
	{
		return (Faq)newEntity(Faq.class);
	}

}
