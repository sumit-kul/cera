package com.era.community.config.dao.generated; 

import support.community.database.*;
import com.era.community.config.dao.*;

public abstract class SystemParameterDaoBaseImpl extends AbstractJdbcDaoSupport implements SystemParameterDaoBase
{
	/*
	 *
	 */
	public SystemParameter getSystemParameterForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (SystemParameter)getEntity(SystemParameter.class, keys);
	}

	/*
	 *
	 */
	public void deleteSystemParameterForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(SystemParameter o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(SystemParameter o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public SystemParameter newSystemParameter() throws Exception
	{
		return (SystemParameter)newEntity(SystemParameter.class);
	}

}
