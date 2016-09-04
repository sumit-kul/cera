package com.era.community.pers.dao.generated; 

import com.era.community.pers.dao.Contact;

public interface ContactDaoBase extends ContactFinderBase
{
  public void store(Contact o) throws Exception;
  public void deleteContactForId(int id) throws Exception;
  public void delete(Contact o) throws Exception;
}

