package com.era.community.announcement.dao.generated; 

import com.era.community.announcement.dao.*;

public interface AnnouncementDaoBase extends AnnouncementFinderBase
{
  public void store(Announcement o) throws Exception;
  public void deleteAnnouncementForId(int id) throws Exception;
  public void delete(Announcement o) throws Exception;
  public support.community.database.BlobData readFile1(Announcement o) throws Exception;
  public void storeFile1(Announcement o, java.io.InputStream data, int dataLength, String contentType) throws Exception;
  public void storeFile1(Announcement o, org.springframework.web.multipart.MultipartFile file) throws Exception;
  public void markFile1Relocated(Announcement o) throws Exception;
  public String getFile1ContentType(Announcement o) throws Exception;
  public String getFile1SearchText(Announcement o) throws Exception;
  public void setFile1SearchText(Announcement o, String s) throws Exception;
  public support.community.database.BlobData readFile2(Announcement o) throws Exception;
  public void storeFile2(Announcement o, java.io.InputStream data, int dataLength, String contentType) throws Exception;
  public void storeFile2(Announcement o, org.springframework.web.multipart.MultipartFile file) throws Exception;
  public void markFile2Relocated(Announcement o) throws Exception;
  public String getFile2ContentType(Announcement o) throws Exception;
  public String getFile2SearchText(Announcement o) throws Exception;
  public void setFile2SearchText(Announcement o, String s) throws Exception;
  public support.community.database.BlobData readFile3(Announcement o) throws Exception;
  public void storeFile3(Announcement o, java.io.InputStream data, int dataLength, String contentType) throws Exception;
  public void storeFile3(Announcement o, org.springframework.web.multipart.MultipartFile file) throws Exception;
  public void markFile3Relocated(Announcement o) throws Exception;
  public String getFile3ContentType(Announcement o) throws Exception;
  public String getFile3SearchText(Announcement o) throws Exception;
  public void setFile3SearchText(Announcement o, String s) throws Exception;
}