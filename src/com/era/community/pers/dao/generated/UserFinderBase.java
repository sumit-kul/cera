package com.era.community.pers.dao.generated;

import com.era.community.pers.dao.User;

public interface UserFinderBase
{
    public User getUserForId(int id) throws Exception;
    public User getUserEntity(int id) throws Exception;
    public User getUnvalidatedUserForId(int id) throws Exception;
    public User newUser() throws Exception;
}

