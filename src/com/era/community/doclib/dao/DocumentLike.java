package com.era.community.doclib.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class DocumentLike extends CecBaseEntity
{
	/**
	 * @column integer not null
	 */
	protected int DocumentId;

	/**
	 * @column integer not null
	 */
	protected int PosterId ;

	/*
	 * Injected references.
	 */
	protected DocumentLikeDao dao;

	public int getDocumentId()
	{
		return DocumentId;
	}

	public void setDocumentId(int documentId)
	{
		DocumentId = documentId;
	}

	public int getPosterId()
	{
		return PosterId;
	}

	public void setPosterId(int posterId)
	{
		PosterId = posterId;
	}

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		return false;
	}

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	} 

	public final void setDao(DocumentLikeDao dao)
	{
		this.dao = dao;
	}

	public final DocumentLikeDao getDao()
	{
		return dao;
	}
}