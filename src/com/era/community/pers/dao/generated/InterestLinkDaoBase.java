package com.era.community.pers.dao.generated; 

import com.era.community.pers.dao.InterestLink;

public interface InterestLinkDaoBase extends InterestLinkFinderBase
{
  public void store(InterestLink o) throws Exception;
  public void deleteInterestLinkForId(int id) throws Exception;
  public void delete(InterestLink o) throws Exception;
}