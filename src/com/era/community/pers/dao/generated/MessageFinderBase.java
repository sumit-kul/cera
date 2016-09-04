package com.era.community.pers.dao.generated; 
import com.era.community.pers.dao.Message;
import com.era.community.pers.dao.ReceivedMessage;
import com.era.community.pers.dao.SentMessage;

public interface MessageFinderBase
{
    public Message getMessageForId(int id) throws Exception;
    public ReceivedMessage newReceivedMessage() throws Exception;
    public SentMessage newSentMessage() throws Exception;
}

