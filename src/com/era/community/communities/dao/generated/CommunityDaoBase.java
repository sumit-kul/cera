package com.era.community.communities.dao.generated; 

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import support.community.database.BlobData;

import com.era.community.communities.dao.Community;

public interface CommunityDaoBase extends CommunityFinderBase
{
  public void store(Community o) throws Exception;
  public void deleteCommunityForId(int id) throws Exception;
  public void delete(Community o) throws Exception;
  
  public BlobData readCommunityLogo(Community o) throws Exception;
  public BlobData readCommunityLogoThumb(Community o) throws Exception;
  public void storeCommunityLogo(Community o, InputStream data) throws Exception;
  public void storeCommunityLogo(Community o, MultipartFile file) throws Exception;
  
  public BlobData readCommunityBanner(Community o) throws Exception;
  public BlobData readCommunityBannerThumb(Community o) throws Exception;
  public void storeCommunityBanner(Community o, InputStream data) throws Exception;
  public void storeCommunityBanner(Community o, MultipartFile file) throws Exception;
  
  public void markCommunityLogoRelocated(Community o) throws Exception;
  public String getCommunityLogoContentType(Community o) throws Exception;
  public String getCommunityLogoSearchText(Community o) throws Exception;
  public void setCommunityLogoSearchText(Community o, String s) throws Exception;
  public String getDescription(Community o) throws Exception;
  public void setDescription(Community o, String value) throws Exception;
}