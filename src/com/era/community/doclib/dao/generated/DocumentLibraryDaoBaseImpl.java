package com.era.community.doclib.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.doclib.dao.DocumentLibrary;

public abstract class DocumentLibraryDaoBaseImpl extends AbstractJdbcDaoSupport implements DocumentLibraryDaoBase
{
	/*
	 *
	 */
	public DocumentLibrary getDocumentLibraryForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (DocumentLibrary)getEntity(DocumentLibrary.class, keys);
	}

	/*
	 *
	 */
	public void deleteDocumentLibraryForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(DocumentLibrary o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(DocumentLibrary o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public DocumentLibrary newDocumentLibrary() throws Exception
	{
		return (DocumentLibrary)newEntity(DocumentLibrary.class);
	}

}
