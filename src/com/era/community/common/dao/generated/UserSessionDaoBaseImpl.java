package com.era.community.common.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.common.dao.UserSession;

public abstract class UserSessionDaoBaseImpl extends AbstractJdbcDaoSupport implements UserSessionDaoBase
{
  public UserSession getUserSessionForId(int id) throws Exception
  {
      Object[] keys = new Object[] { new Integer(id) };
      return (UserSession)getEntity(UserSession.class, keys);
  }

    public void deleteUserSessionForId(int id) throws Exception
    {
        Object[] keys = new Object[] { new Integer(id) };
        deleteEntity(keys);
    }

    public void delete(UserSession o) throws Exception
    {
        deleteEntity(o);
    }

   public void store(UserSession o) throws Exception
   {
       storeEntity(o);
   }

   public UserSession newUserSession() throws Exception
   {
       return (UserSession)newEntity(UserSession.class);
   }

}
