package com.era.community.doclib.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.doclib.dao.DocumentRating;

public abstract class DocumentRatingDaoBaseImpl extends AbstractJdbcDaoSupport implements DocumentRatingDaoBase
{
	/*
	 *
	 */
	public DocumentRating getDocumentRatingForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (DocumentRating)getEntity(DocumentRating.class, keys);
	}

	/*
	 *
	 */
	public void deleteDocumentRatingForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(DocumentRating o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(DocumentRating o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public DocumentRating newDocumentRating() throws Exception
	{
		return (DocumentRating)newEntity(DocumentRating.class);
	}

}
