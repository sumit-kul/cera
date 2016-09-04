package com.era.community.pers.dao; 

public interface MessageFinder extends com.era.community.pers.dao.generated.MessageFinderBase
{
    public int getMessageCount(String sRegion) throws Exception;
    public int getMessageSenderCount(String sRegion) throws Exception;
    public int getMessageReceiverCount(String sRegion) throws Exception;
    
    public int getMessageCount() throws Exception;
    public int getMessageSenderCount() throws Exception;
    public int getMessageReceiverCount() throws Exception;
}

