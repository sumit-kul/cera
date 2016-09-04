package com.era.community.connections.communities.dao;

import support.community.application.ElementNotFoundException;

import com.era.community.base.CommunityFeature;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;

public class MemberListFeature implements CommunityFeature
{
    MemberListDao dao;
    
    CommunityEraContextManager contextManager;

    public Object getFeatureForCurrentCommunity() throws Exception
    {
        return getFeatureForCommunity(contextManager.getContext().getCurrentCommunity());
    }

    public void setFeatureEnabledForCommunity(Community comm, boolean status) throws Exception
    {
        MemberList o = (MemberList)getFeatureForCommunity(comm);
        if (o==null && status==false) {
            return;
        }
        else if (o==null && status==true) {
            o = dao.newMemberList();
            o.setCommunityId(comm.getId());
            o.setName(comm.getName());
            o.setInactive(false);
            o.update();
            return;
        }
        else {
            o.setInactive(!status);
            o.update();
        }
    }

    public boolean isFeatureEnabledForCommunity(Community comm) throws Exception
    {
        MemberList o = (MemberList)getFeatureForCommunity(comm);
        if (o==null) return false;
        return !o.isInactive();
    }

    public Object getFeatureForCommunity(Community comm) throws Exception
    {
        try {
            return dao.getMemberListForCommunity(comm);
        }
        catch (ElementNotFoundException e) {
            return null;
        }
    }

    public String getFeatureName() throws Exception
    {
        return "Members";
    }

    public String getFeatureLabel() throws Exception
    {
        return "<i class=\'fa fa-user-plus\' aria-hidden=\'true\' style=\'margin-right: 10px;\'></i>Members";
    }

    public String getFeatureUri() throws Exception
    {
        return "/members";
    }

    
    public final void setDao(MemberListDao dao)
    {
        this.dao = dao;
    }

    public boolean isFeatureMandatory() throws Exception
    {
        return true;
    }
    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public boolean isFeatureAvailableForUser(User user) throws Exception
    {
        return true;
    }

    public String getFeatureTitle() throws Exception
    {
            return "Member List for this community";
    }
}
