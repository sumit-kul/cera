package com.era.community.admin.dao.generated; 

import support.community.database.*;
import com.era.community.admin.dao.*;

public abstract class BusinessParamDaoBaseImpl extends AbstractJdbcDaoSupport implements BusinessParamDaoBase
{
   /*
   *
  */
  public BusinessParam getBusinessParamForId(int id) throws Exception
  {
      Object[] keys = new Object[] { new Integer(id) };
      return (BusinessParam)getEntity(BusinessParam.class, keys);
  }

    /*
     *
    */
    public void deleteBusinessParamForId(int id) throws Exception
    {
        Object[] keys = new Object[] { new Integer(id) };
        deleteEntity(keys);
    }

    /*
     *
    */
    public void delete(BusinessParam o) throws Exception
    {
        deleteEntity(o);
    }

    /*
    *
   */
   public void store(BusinessParam o) throws Exception
   {
       storeEntity(o);
   }

    /*
    *
   */
   public BusinessParam newBusinessParam() throws Exception
   {
       return (BusinessParam)newEntity(BusinessParam.class);
   }

}
