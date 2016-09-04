package com.era.community.doclib.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.doclib.dao.DocumentLike;

public abstract class DocumentLikeDaoBaseImpl extends AbstractJdbcDaoSupport implements DocumentLikeDaoBase
{
	public DocumentLike getDocumentLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (DocumentLike)getEntity(DocumentLike.class, keys);
	}

	public void deleteDocumentLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(DocumentLike o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(DocumentLike o) throws Exception
	{
		storeEntity(o);
	}

	public DocumentLike newDocumentLike() throws Exception
	{
		return (DocumentLike)newEntity(DocumentLike.class);
	}
}