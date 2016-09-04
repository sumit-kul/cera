package com.era.community.config.dao.generated; 

import com.era.community.config.dao.*;

public interface MailMessageDefinitionDaoBase extends MailMessageDefinitionFinderBase
{
  public void store(MailMessageDefinition o) throws Exception;
  public void deleteMailMessageDefinitionForId(int id) throws Exception;
  public void delete(MailMessageDefinition o) throws Exception;
}