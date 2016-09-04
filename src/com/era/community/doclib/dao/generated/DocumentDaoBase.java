package com.era.community.doclib.dao.generated; 

import java.io.InputStream;

import com.era.community.doclib.dao.Document;

public interface DocumentDaoBase extends DocumentFinderBase
{
  public void store(Document o) throws Exception;
  public void deleteDocumentForId(int id) throws Exception;
  public void delete(Document o) throws Exception;
  public support.community.database.BlobData readFile(Document o) throws Exception;
  public void storeFile(Document o, java.io.InputStream data, int dataLength, String contentType) throws Exception;
  public void storeFile(Document o, org.springframework.web.multipart.MultipartFile file) throws Exception;
  public void markFileRelocated(Document o) throws Exception;
  public String getFileContentType(Document o) throws Exception;
  public String getFileSearchText(Document o) throws Exception;
  public void setFileSearchText(Document o, String s) throws Exception;
  public void storePhoto(Document o, InputStream imageInputStream) throws Exception;
  public void storePhoto(Document o, java.io.InputStream data, int dataLength, String contentType, int width, int height) throws Exception;
}