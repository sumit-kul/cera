package com.era.community.pers.dao.generated; 

import com.era.community.pers.dao.Message;

public interface MessageDaoBase extends MessageFinderBase
{
	public void store(Message o) throws Exception;
	  public void deleteMessageForId(int id) throws Exception;
	  public void delete(Message o) throws Exception;
	  public support.community.database.BlobData readAttachment(Message o) throws Exception;
	  public void storeAttachment(Message o, java.io.InputStream data, int dataLength, String contentType) throws Exception;
	  public void storeAttachment(Message o, org.springframework.web.multipart.MultipartFile file) throws Exception;
	  public void markAttachmentRelocated(Message o) throws Exception;
	  public String getAttachmentContentType(Message o) throws Exception;
	  public String getAttachmentSearchText(Message o) throws Exception;
	  public void setAttachmentSearchText(Message o, String s) throws Exception;
	  public String getBigDescription(Message o) throws Exception;
	  public void setBigDescription(Message o, String value) throws Exception;
	  public String getAddressLabel(Message o) throws Exception;
	  public void setAddressLabel(Message o, String value) throws Exception;
}