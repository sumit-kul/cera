package com.era.community.communities.dao.generated; 

import support.community.database.*;
import com.era.community.communities.dao.*;

public abstract class DeletedCommunityDaoBaseImpl extends AbstractJdbcDaoSupport implements DeletedCommunityDaoBase
{
	/*
	 *
	 */
	public DeletedCommunity getDeletedCommunityForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (DeletedCommunity)getEntity(DeletedCommunity.class, keys);
	}

	/*
	 *
	 */
	public void deleteDeletedCommunityForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(DeletedCommunity o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(DeletedCommunity o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public DeletedCommunity newDeletedCommunity() throws Exception
	{
		return (DeletedCommunity)newEntity(DeletedCommunity.class);
	}

}
