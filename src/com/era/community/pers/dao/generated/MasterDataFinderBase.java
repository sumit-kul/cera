package com.era.community.pers.dao.generated; 

import com.era.community.pers.dao.MasterData;

public interface MasterDataFinderBase
{
    public MasterData getMasterDataForId(int id) throws Exception;
    public MasterData newMasterData() throws Exception;
}