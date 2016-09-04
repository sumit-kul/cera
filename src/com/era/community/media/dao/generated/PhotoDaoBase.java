package com.era.community.media.dao.generated; 

import java.io.InputStream;

import com.era.community.media.dao.Photo;

public interface PhotoDaoBase extends PhotoFinderBase
{
  public void store(Photo o) throws Exception;
  public void deletePhotoForId(int id) throws Exception;
  public void delete(Photo o) throws Exception;
  public support.community.database.BlobData readPhoto(Photo o) throws Exception;
  public void storePhoto(Photo o, java.io.InputStream data, int dataLength, String contentType) throws Exception;
  public void storePhoto(Photo o, org.springframework.web.multipart.MultipartFile file) throws Exception;
  public void markPhotoRelocated(Photo o) throws Exception;
  public String getPhotoContentType(Photo o) throws Exception;
  public String getPhotoSearchText(Photo o) throws Exception;
  public void setPhotoSearchText(Photo o, String s) throws Exception;
  public void storePhoto(Photo o, InputStream imageInputStream) throws Exception;
}