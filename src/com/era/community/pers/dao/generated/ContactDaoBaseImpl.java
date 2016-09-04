package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.pers.dao.Contact;

public abstract class ContactDaoBaseImpl extends AbstractJdbcDaoSupport implements ContactDaoBase
{
	public Contact getContactForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Contact)getEntity(Contact.class, keys);
	}

	/*
	 *
	 */
	public void deleteContactForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(Contact o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(Contact o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public Contact newContact() throws Exception
	{
		return (Contact)newEntity(Contact.class);
	}
}