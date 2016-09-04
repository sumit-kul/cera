package com.era.community.communities.dao.generated; 

import support.community.database.*;
import com.era.community.communities.dao.*;

public abstract class CommunityActivityDaoBaseImpl extends AbstractJdbcDaoSupport implements CommunityActivityDaoBase
{
	public CommunityActivity getCommunityActivityForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (CommunityActivity)getEntity(CommunityActivity.class, keys);
	}

	public void deleteCommunityActivityForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(CommunityActivity o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(CommunityActivity o) throws Exception
	{
		storeEntity(o);
	}

	public CommunityActivity newCommunityActivity() throws Exception
	{
		return (CommunityActivity)newEntity(CommunityActivity.class);
	}
}