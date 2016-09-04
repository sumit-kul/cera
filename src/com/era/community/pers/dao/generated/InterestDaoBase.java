package com.era.community.pers.dao.generated; 

import com.era.community.pers.dao.Interest;

public interface InterestDaoBase extends InterestFinderBase
{
  public void store(Interest o) throws Exception;
  public void deleteInterestForId(int id) throws Exception;
  public void delete(Interest o) throws Exception;
}