package com.era.community.upload.dao.generated; 

import java.io.InputStream;

import support.community.database.BlobData;

import com.era.community.upload.dao.Image;

public interface ImageDaoBase extends ImageFinderBase
{
  public void store(Image o) throws Exception;
  public void deleteImageForId(int id) throws Exception;
  public void delete(Image o) throws Exception;
  public BlobData readFile(Image o) throws Exception;
  public BlobData readFileThumb(Image o) throws Exception;
  public void storeFile(Image o, java.io.InputStream data, int dataLength, String contentType) throws Exception;
  public void storeFile(Image o, org.springframework.web.multipart.MultipartFile file) throws Exception;
  public void storePhoto(Image o, InputStream imageInputStream) throws Exception;
  public void storePhoto(Image o, java.io.InputStream data, int dataLength, String contentType, int width, int height) throws Exception;
  public void storePhotoThumb(Image o, java.io.InputStream data, int dataLength, String contentType, int width, int height) throws Exception;
}