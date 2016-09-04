package com.era.community.base;

import com.era.community.communities.dao.*;
import com.era.community.pers.dao.*;

public interface CommunityFeature 
{
    public void setFeatureEnabledForCommunity(Community comm, boolean status) throws Exception;
    public boolean isFeatureEnabledForCommunity(Community comm) throws Exception;
    public Object getFeatureForCommunity(Community comm) throws Exception;
    public Object getFeatureForCurrentCommunity() throws Exception;
    public String getFeatureName() throws Exception;
    public String getFeatureLabel() throws Exception;
    public String getFeatureTitle() throws Exception;
    public String getFeatureUri() throws Exception;
    public boolean isFeatureMandatory() throws Exception;
    public boolean isFeatureAvailableForUser(User user) throws Exception; 
}
