package com.era.community.common.dao.generated; 
import com.era.community.common.dao.UserSession;

public interface UserSessionFinderBase
{
    public UserSession getUserSessionForId(int id) throws Exception;
    public UserSession newUserSession() throws Exception;
}