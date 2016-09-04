package com.era.community.doclib.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.doclib.dao.DocumentComment;

public abstract class DocumentCommentDaoBaseImpl extends AbstractJdbcDaoSupport implements DocumentCommentDaoBase
{
	/*
	 *
	 */
	public DocumentComment getDocumentCommentForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (DocumentComment)getEntity(DocumentComment.class, keys);
	}

	/*
	 *
	 */
	public void deleteDocumentCommentForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(DocumentComment o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(DocumentComment o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public DocumentComment newDocumentComment() throws Exception
	{
		return (DocumentComment)newEntity(DocumentComment.class);
	}

}
