package com.era.community.base;

import java.util.*;

public interface UserActivityManager
{
    public void addActivity(int userId, String userName, String place) throws Exception ;
    public List getActiveUsersForPlace(String place);
    public void add(int userId, String userName, String place) throws Exception;
    
}
