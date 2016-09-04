package com.era.community.events.dao.generated; 

import com.era.community.events.dao.Event;

public interface EventDaoBase extends EventFinderBase
{
	public void store(Event o) throws Exception;
	public void deleteEventForId(int id) throws Exception;
	public void delete(Event o) throws Exception;
	public void storePhoto(Event o, org.springframework.web.multipart.MultipartFile file) throws Exception;
	public support.community.database.BlobData readPhoto(Event o) throws Exception;
	public String getPhotoContentType(Event o) throws Exception;
}

