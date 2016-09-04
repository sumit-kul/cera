package com.era.community.pers.dao.generated; 

import com.era.community.pers.dao.MasterData;

public interface MasterDataDaoBase extends MasterDataFinderBase
{
  public void store(MasterData o) throws Exception;
  public void deleteMasterDataForId(int id) throws Exception;
  public void delete(MasterData o) throws Exception;
}