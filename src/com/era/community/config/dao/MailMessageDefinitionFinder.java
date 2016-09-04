package com.era.community.config.dao; 

public interface MailMessageDefinitionFinder extends com.era.community.config.dao.generated.MailMessageDefinitionFinderBase
{
    public MailMessageDefinition getMailMessageForName(String name) throws Exception;
}

