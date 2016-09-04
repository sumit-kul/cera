package com.era.community.communities.dao.generated; 

import support.community.database.*;
import com.era.community.communities.dao.*;

public abstract class FeedbackDaoBaseImpl extends AbstractJdbcDaoSupport implements FeedbackDaoBase
{
	/*
	 *
	 */
	public Feedback getFeedbackForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Feedback)getEntity(Feedback.class, keys);
	}

	/*
	 *
	 */
	public void deleteFeedbackForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(Feedback o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(Feedback o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public Feedback newFeedback() throws Exception
	{
		return (Feedback)newEntity(Feedback.class);
	}

}
