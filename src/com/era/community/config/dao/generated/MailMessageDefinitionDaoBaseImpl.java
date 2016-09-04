package com.era.community.config.dao.generated; 

import support.community.database.*;
import com.era.community.config.dao.*;

public abstract class MailMessageDefinitionDaoBaseImpl extends AbstractJdbcDaoSupport implements MailMessageDefinitionDaoBase
{
	/*
	 *
	 */
	public MailMessageDefinition getMailMessageDefinitionForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (MailMessageDefinition)getEntity(MailMessageDefinition.class, keys);
	}

	/*
	 *
	 */
	public void deleteMailMessageDefinitionForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(MailMessageDefinition o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(MailMessageDefinition o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public MailMessageDefinition newMailMessageDefinition() throws Exception
	{
		return (MailMessageDefinition)newEntity(MailMessageDefinition.class);
	}

}
