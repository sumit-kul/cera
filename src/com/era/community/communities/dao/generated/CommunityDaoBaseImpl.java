package com.era.community.communities.dao.generated; 

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import support.community.database.AbstractJdbcDaoSupport;
import support.community.database.BlobData;

import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.PrivateCommunity;
import com.era.community.communities.dao.ProtectedCommunity;
import com.era.community.communities.dao.PublicCommunity;

public abstract class CommunityDaoBaseImpl extends AbstractJdbcDaoSupport implements CommunityDaoBase
{
	public Community getCommunityForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Community)getEntity(Community.class, keys);
	}

	public void deleteCommunityForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(Community o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(Community o) throws Exception
	{
		storeEntity(o);
	}

	public BlobData readCommunityLogo(Community o) throws Exception
	{
		return  readBlobItem(o, "CommunityLogo");
	} 
	
	public BlobData readCommunityLogoThumb(Community o) throws Exception
	{
		return  readBlobItem(o, "CommunityLogoThumb");
	}

	public void storeCommunityLogo(Community o, InputStream data) throws Exception
	{
		storeBlob(o, "CommunityLogo", data);
	}

	public void storeCommunityLogo(Community o, MultipartFile data) throws Exception
	{
		storeBlob(o, "CommunityLogo", data);
	}
	
	public BlobData readCommunityBanner(Community o) throws Exception
	{
		return  readBlobItem(o, "CommunityBanner");
	}
	
	public BlobData readCommunityBannerThumb(Community o) throws Exception
	{
		return  readBlobItem(o, "CommunityBannerThumb");
	}

	public void storeCommunityBanner(Community o, InputStream data) throws Exception
	{
		storeBlob(o, "CommunityBanner", data);
	}
	
	public void storeCommunityBanner(Community o, MultipartFile data) throws Exception
	{
		storeBlob(o, "CommunityBanner", data);
	}

	public String getCommunityLogoContentType(Community o) throws Exception
	{
		return (String)getColumn(o, "CommunityLogoContentType", String.class);
	} 

	public String getCommunityLogoSearchText(Community o) throws Exception
	{
		return (String)getColumn(o, "CommunityLogoSearchText", String.class);
	} 

	public void setCommunityLogoSearchText(Community o, String s) throws Exception
	{
		setColumn(o, "CommunityLogoSearchText", s);
	} 

	public void markCommunityLogoRelocated(Community o) throws Exception
	{
		setColumn(o, "CommunityLogo", null);
	}

	public String getDescription(Community o) throws Exception
	{
		return (String)getColumn(o, "Description", String.class);
	}

	public void setDescription(Community o, String data) throws Exception
	{
		setColumn(o, "Description", data);
	}

	public PrivateCommunity newPrivateCommunity() throws Exception
	{
		return (PrivateCommunity)newEntity(PrivateCommunity.class);
	}

	public PublicCommunity newPublicCommunity() throws Exception
	{
		return (PublicCommunity)newEntity(PublicCommunity.class);
	}

	public ProtectedCommunity newProtectedCommunity() throws Exception
	{
		return (ProtectedCommunity)newEntity(ProtectedCommunity.class);
	}
}