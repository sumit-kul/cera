package com.era.community.communities.dao.generated; 

import com.era.community.communities.dao.*;

public interface FeedbackDaoBase extends FeedbackFinderBase
{
  public void store(Feedback o) throws Exception;
  public void deleteFeedbackForId(int id) throws Exception;
  public void delete(Feedback o) throws Exception;
}