package com.era.community.faqs.dao;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import support.community.database.BlobData;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * Frequently asked questions
 * 
 * @entity name="FRAQ" 
 * 
 * @entity.blob name="File"
 *
 */
public class Faq extends CecAbstractEntity
{

	/**
	 * @column varchar(250) not null with default
	 */
	protected String Subject;

	/**
	 * @column long varchar not null with default
	 */
	protected String Body = "";

	/**
	 * @column varchar(150)
	 */
	protected String FileName; 

	/**
	 * @column integer not null with default
	 */
	protected int Sequence = 0;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Inactive = false;

	/*
	 * Injected references.
	 */
	protected FaqDao dao;     

	/**
	 * Update or insert this entity in the database.
	 */
	public void update() throws Exception
	{
		dao.store(this); 
	}

	/** 
	 *  Delete this entity from the database.
	 */
	public void delete() throws Exception
	{
		dao.delete(this);
	} 

	public void clearFile() throws Exception
	{
		dao.clearFile(this);
	}

	public final void setDao(FaqDao dao)
	{
		this.dao = dao;
	}

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		return true;
	}

	public final boolean isInactive()
	{
		return Inactive;
	}

	public final void setInactive(boolean inactive)
	{
		Inactive = inactive;
	}

	public final String getBody()
	{
		return Body;
	}

	public final void setBody(String body)
	{
		Body = body;
	}

	public final String getSubject()
	{
		return Subject;
	}

	public final void setSubject(String subject)
	{
		Subject = subject;
	}

	public final int getSequence()
	{
		return Sequence;
	}

	public final void setSequence(int sequence)
	{
		Sequence = sequence;
	}

	public boolean isFilePresent() throws Exception
	{
		return dao.isFilePresent(this);
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
		dao.storeFile(this, file);
	}

	public String getFileContentType() throws Exception
	{
		return dao.getFileContentType(this);
	}

	public String getFileName()
	{
		return FileName;
	}

	public void setFileName(String fileName)
	{
		FileName = fileName;
	}
}