package com.era.community.blog.dao.generated; 

import support.community.database.*;
import com.era.community.blog.dao.*;

public abstract class PersonalBlogDaoBaseImpl extends AbstractJdbcDaoSupport implements PersonalBlogDaoBase
{
	public PersonalBlog getPersonalBlogForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (PersonalBlog)getEntity(PersonalBlog.class, keys);
	}

	public void deletePersonalBlogForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(PersonalBlog o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(PersonalBlog o) throws Exception
	{
		storeEntity(o);
	}

	public PersonalBlog newPersonalBlog() throws Exception
	{
		return (PersonalBlog)newEntity(PersonalBlog.class);
	}
}