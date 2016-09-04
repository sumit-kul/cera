package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;
import support.community.database.BlobData;

import com.era.community.pers.dao.Message;
import com.era.community.pers.dao.ReceivedMessage;
import com.era.community.pers.dao.SentMessage;

public abstract class MessageDaoBaseImpl extends AbstractJdbcDaoSupport implements MessageDaoBase
{
	public Message getMessageForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Message)getEntity(Message.class, keys);
	}

	public void deleteMessageForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void deleteMessageBasedOnIds(int[] ids) throws Exception
	{
		if(ids != null && ids.length > 0){
			for(int i:ids){
				deleteMessageForId(i);
			}
		}
	}

	public void delete(Message o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(Message o) throws Exception
	{
		storeEntity(o);
	}

	public BlobData readAttachment(Message o) throws Exception
	{
		return  readBlobItem(o, "Attachment");
	} 

	public void storeAttachment(Message o, java.io.InputStream data, int dataLength, String contentType) throws Exception
	{
		storeBlob(o, "Attachment", data, dataLength, contentType);
	}

	public void storeAttachment(Message o, org.springframework.web.multipart.MultipartFile data) throws Exception
	{
		storeBlob(o, "Attachment", data);
	}

	public String getAttachmentContentType(Message o) throws Exception
	{
		return (String)getColumn(o, "AttachmentContentType", String.class);
	} 

	public String getAttachmentSearchText(Message o) throws Exception
	{
		return (String)getColumn(o, "AttachmentSearchText", String.class);
	} 

	public void setAttachmentSearchText(Message o, String s) throws Exception
	{
		setColumn(o, "AttachmentSearchText", s);
	} 

	public void markAttachmentRelocated(Message o) throws Exception
	{
		setColumn(o, "Attachment", null);
	}

	public String getBigDescription(Message o) throws Exception
	{
		return (String)getColumn(o, "BigDescription", String.class);
	}

	public void setBigDescription(Message o, String data) throws Exception
	{
		setColumn(o, "BigDescription", data);
	}

	public String getAddressLabel(Message o) throws Exception
	{
		return (String)getColumn(o, "AddressLabel", String.class);
	}

	public void setAddressLabel(Message o, String data) throws Exception
	{
		setColumn(o, "AddressLabel", data);
	}

	public ReceivedMessage newReceivedMessage() throws Exception
	{
		return (ReceivedMessage)newEntity(ReceivedMessage.class);
	}

	public SentMessage newSentMessage() throws Exception
	{
		return (SentMessage)newEntity(SentMessage.class);
	}
}