package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;
import support.community.database.BlobData;

import com.era.community.pers.dao.User;

public abstract class UserDaoBaseImpl extends AbstractJdbcDaoSupport implements UserDaoBase
{
	public User getUserForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (User)getEntity("select * from User where Inactive = 'N' and Validated = 'Y' and ID = ? ", keys);
	}
	
	public User getUserEntity(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (User)getEntity(User.class, keys);
	}
	
	public User getUnvalidatedUserForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (User)getEntity("select * from User where Validated = 'N' and ID = ? ", keys);
	}

	public void deleteUserForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(User o) throws Exception
	{
		deleteEntity(o);
	}
	
	public void store(User o) throws Exception
	{
		storeEntity(o);
	}
	
	public BlobData readPhoto(User o) throws Exception
	{
		return  readBlobItem(o, "Photo");
	} 
	
	public BlobData readCover(User o) throws Exception
	{
		return  readBlobItem(o, "Cover");
	} 
	
	public BlobData readPhotoThumb(User o) throws Exception
	{
		return  readBlobItem(o, "PhotoThumb");
	} 
	
	public BlobData readCoverThumb(User o) throws Exception
	{
		return  readBlobItem(o, "CoverThumb");
	} 
	
	public void storePhoto(User o, java.io.InputStream data) throws Exception
	{
		storeBlob(o, "Photo", data);
	}
	
	public void storePhoto(User o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "Photo", data);
	}
	
	public String getPhotoContentType(User o) throws Exception
	{
		return (String)getColumn(o, "PhotoContentType", String.class);
	} 
	
	public String getPhotoSearchText(User o) throws Exception
	{
		return (String)getColumn(o, "PhotoSearchText", String.class);
	} 
	
	public void setPhotoSearchText(User o, String s) throws Exception
	{
		setColumn(o, "PhotoSearchText", s);
	} 
	
	public void storeCover(User o, java.io.InputStream data) throws Exception
	{
		storeBlob(o, "Cover", data);
	}
	
	public void storeCover(User o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "Cover", data);
	}
	
	public String getCoverContentType(User o) throws Exception
	{
		return (String)getColumn(o, "CoverContentType", String.class);
	} 
	
	public String getCoverSearchText(User o) throws Exception
	{
		return (String)getColumn(o, "CoverSearchText", String.class);
	} 
	
	public void setCoverSearchText(User o, String s) throws Exception
	{
		setColumn(o, "CoverSearchText", s);
	} 
	
	public void markPhotoRelocated(User o) throws Exception
	{
		setColumn(o, "Photo", null);
	}

	public String getAbout(User o) throws Exception
	{
		return (String)getColumn(o, "About", String.class);
	}
	
	public void setAbout(User o, String data) throws Exception
	{
		setColumn(o, "About", data);
	}
	
	public User newUser() throws Exception
	{
		return (User)newEntity(User.class);
	}
}
