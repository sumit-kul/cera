package com.era.community.events.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;
import support.community.database.BlobData;

import com.era.community.events.dao.Event;

public abstract class EventDaoBaseImpl extends AbstractJdbcDaoSupport implements EventDaoBase
{
	public Event getEventForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Event)getEntity(Event.class, keys);
	}

	public void deleteEventForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(Event o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(Event o) throws Exception
	{
		storeEntity(o);
	}

	public Event newEvent() throws Exception
	{
		return (Event)newEntity(Event.class);
	}
	
	public void storePhoto(Event o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "Photo", data);
	}

	public BlobData readPhoto(Event o) throws Exception
	{
		return  readBlobItem(o, "Photo");
	} 
	
	public String getPhotoContentType(Event o) throws Exception
	{
		return (String)getColumn(o, "PhotoContentType", String.class);
	}
}
