package com.era.community.common.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.common.dao.Insight;

public abstract class InsightDaoBaseImpl extends AbstractJdbcDaoSupport implements InsightDaoBase
{
  public Insight getInsightForId(int id) throws Exception
  {
      Object[] keys = new Object[] { new Integer(id) };
      return (Insight)getEntity(Insight.class, keys);
  }

    public void deleteInsightForId(int id) throws Exception
    {
        Object[] keys = new Object[] { new Integer(id) };
        deleteEntity(keys);
    }

    public void delete(Insight o) throws Exception
    {
        deleteEntity(o);
    }

   public void store(Insight o) throws Exception
   {
       storeEntity(o);
   }

   public Insight newInsight() throws Exception
   {
       return (Insight)newEntity(Insight.class);
   }

}
