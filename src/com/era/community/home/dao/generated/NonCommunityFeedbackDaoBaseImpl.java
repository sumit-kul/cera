package com.era.community.home.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.home.dao.NonCommunityFeedback;

public abstract class NonCommunityFeedbackDaoBaseImpl extends AbstractJdbcDaoSupport implements NonCommunityFeedbackDaoBase
{
	/*
	 *
	 */
	public NonCommunityFeedback getNonCommunityFeedbackForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (NonCommunityFeedback)getEntity(NonCommunityFeedback.class, keys);
	}

	/*
	 *
	 */
	public void deleteNonCommunityFeedbackForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(NonCommunityFeedback o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(NonCommunityFeedback o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public NonCommunityFeedback newNonCommunityFeedback() throws Exception
	{
		return (NonCommunityFeedback)newEntity(NonCommunityFeedback.class);
	}

}
