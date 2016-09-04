package com.era.community.communities.dao.generated; 

import support.community.database.*;
import com.era.community.communities.dao.*;

public abstract class CommunityJoiningRequestDaoBaseImpl extends AbstractJdbcDaoSupport implements CommunityJoiningRequestDaoBase
{
   /*
   *
  */
  public CommunityJoiningRequest getCommunityJoiningRequestForId(int id) throws Exception
  {
      Object[] keys = new Object[] { new Integer(id) };
      return (CommunityJoiningRequest)getEntity(CommunityJoiningRequest.class, keys);
  }

    /*
     *
    */
    public void deleteCommunityJoiningRequestForId(int id) throws Exception
    {
        Object[] keys = new Object[] { new Integer(id) };
        deleteEntity(keys);
    }

    /*
     *
    */
    public void delete(CommunityJoiningRequest o) throws Exception
    {
        deleteEntity(o);
    }

    /*
    *
   */
   public void store(CommunityJoiningRequest o) throws Exception
   {
       storeEntity(o);
   }

  public String getOptionalComment(CommunityJoiningRequest o) throws Exception
  {
      return (String)getColumn(o, "OptionalComment", String.class);
  }
  public void setOptionalComment(CommunityJoiningRequest o, String data) throws Exception
  {
      setColumn(o, "OptionalComment", data);
  }

    /*
    *
   */
   public CommunityJoiningRequest newCommunityJoiningRequest() throws Exception
   {
       return (CommunityJoiningRequest)newEntity(CommunityJoiningRequest.class);
   }

}
