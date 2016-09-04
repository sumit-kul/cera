package com.era.community.pers.dao.generated; 

import com.era.community.pers.dao.User;

public interface UserDaoBase extends UserFinderBase
{
  public void store(User o) throws Exception;
  public void deleteUserForId(int id) throws Exception;
  public void delete(User o) throws Exception;
  public support.community.database.BlobData readPhoto(User o) throws Exception;
  public support.community.database.BlobData readCover(User o) throws Exception;
  public support.community.database.BlobData readPhotoThumb(User o) throws Exception;
  public support.community.database.BlobData readCoverThumb(User o) throws Exception;
  public void storePhoto(User o, java.io.InputStream data) throws Exception;
  public void storePhoto(User o, org.springframework.web.multipart.MultipartFile file) throws Exception;
  public void storeCover(User o, java.io.InputStream data) throws Exception;
  public void storeCover(User o, org.springframework.web.multipart.MultipartFile file) throws Exception;
  public void markPhotoRelocated(User o) throws Exception;
  public String getPhotoContentType(User o) throws Exception;
  public String getPhotoSearchText(User o) throws Exception;
  public void setPhotoSearchText(User o, String s) throws Exception;
  public String getCoverContentType(User o) throws Exception;
  public String getCoverSearchText(User o) throws Exception;
  public void setCoverSearchText(User o, String s) throws Exception;
  public String getAbout(User o) throws Exception;
  public void setAbout(User o, String value) throws Exception;
}

