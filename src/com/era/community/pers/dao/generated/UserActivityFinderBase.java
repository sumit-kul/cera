package com.era.community.pers.dao.generated; 
import com.era.community.pers.dao.UserActivity;

public interface UserActivityFinderBase
{
    public UserActivity getUserActivityForId(int id) throws Exception;
    public UserActivity newUserActivity() throws Exception;
}