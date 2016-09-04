package com.era.community.events.dao;

import com.era.community.base.CommunityFeature;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;

public class MasterCalendarFeature implements CommunityFeature
{
	CommunityEraContextManager contextManager;

	public Object getFeatureForCommunity(Community comm) throws Exception
	{
		return null;
	}

	public Object getFeatureForCurrentCommunity() throws Exception
	{
		return getFeatureForCommunity(contextManager.getContext().getCurrentCommunity());
	}

	public String getFeatureLabel() throws Exception
	{
		return "MasterCalendar";
	}

	public String getFeatureName() throws Exception
	{
		return "Calendar";
	}

	public String getFeatureUri() throws Exception
	{
		return null;
	}

	public boolean isFeatureEnabledForCommunity(Community comm) throws Exception
	{ 
		// return comm.isMasterCalendarEnabled();
		return true;
	}

	public boolean isFeatureMandatory() throws Exception
	{
		return false;
	}

	public void setFeatureEnabledForCommunity(Community comm, boolean status) throws Exception
	{
		//comm.setMasterCalendarEnabled(true);
		comm.update();
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public boolean isFeatureAvailableForUser(User user) throws Exception
	{
		if (user.isSystemAdministrator()) return true;
		return false;
	}

	public String getFeatureTitle() throws Exception
	{
		return "Event Calendar for all communities";
	}

}
