package com.era.community.communities.dao.generated; 

import com.era.community.communities.dao.*;

public interface CommunityJoiningRequestDaoBase extends CommunityJoiningRequestFinderBase
{
  public void store(CommunityJoiningRequest o) throws Exception;
  public void deleteCommunityJoiningRequestForId(int id) throws Exception;
  public void delete(CommunityJoiningRequest o) throws Exception;
  public String getOptionalComment(CommunityJoiningRequest o) throws Exception;
  public void setOptionalComment(CommunityJoiningRequest o, String value) throws Exception;
}