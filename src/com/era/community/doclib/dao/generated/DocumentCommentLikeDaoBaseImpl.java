package com.era.community.doclib.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.doclib.dao.DocumentCommentLike;

public abstract class DocumentCommentLikeDaoBaseImpl extends AbstractJdbcDaoSupport implements DocumentCommentLikeDaoBase
{
	public DocumentCommentLike getDocumentCommentLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (DocumentCommentLike)getEntity(DocumentCommentLike.class, keys);
	}

	public void deleteDocumentCommentLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(DocumentCommentLike o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(DocumentCommentLike o) throws Exception
	{
		storeEntity(o);
	}

	public DocumentCommentLike newDocumentCommentLike() throws Exception
	{
		return (DocumentCommentLike)newEntity(DocumentCommentLike.class);
	}
}