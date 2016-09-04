package com.era.community.config.dao; 

public class MailMessageDefinitionDaoImpl extends com.era.community.config.dao.generated.MailMessageDefinitionDaoBaseImpl implements MailMessageDefinitionDao
{
    public MailMessageDefinition getMailMessageForName(String name) throws Exception
    {
        return (MailMessageDefinition) getEntityWhere("Name = ?", name);
    }
}

