package com.era.community.common.dao.generated; 

import com.era.community.common.dao.Insight;

public interface InsightDaoBase extends InsightFinderBase
{
  public void store(Insight o) throws Exception;
  public void deleteInsightForId(int id) throws Exception;
  public void delete(Insight o) throws Exception;
}