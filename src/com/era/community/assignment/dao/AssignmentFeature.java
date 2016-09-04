package com.era.community.assignment.dao;

import support.community.application.ElementNotFoundException;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.CommunityFeature;
import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;

public class AssignmentFeature implements CommunityFeature
{
	AssignmentsDao dao;

	CommunityEraContextManager contextManager;

	public Object getFeatureForCurrentCommunity() throws Exception
	{
		return getFeatureForCommunity(contextManager.getContext().getCurrentCommunity());
	}

	public void setFeatureEnabledForCommunity(Community comm, boolean status) throws Exception
	{
		Assignments o = (Assignments)getFeatureForCommunity(comm);
		if (o==null && status==false) {
			return;
		}
		else if (o==null && status==true) {
			o = dao.newAssignments();
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
		Assignments o = (Assignments)getFeatureForCommunity(comm);
		if (o==null) return false;
		return !o.isInactive();
	}

	public Object getFeatureForCommunity(Community comm) throws Exception
	{
		try {
			return dao.getAssignmentsForCommunity(comm);
		}
		catch (ElementNotFoundException e) {
			return null;
		}
	}

	public String getFeatureName() throws Exception
	{
		return "Assignments";
	}

	public String getFeatureLabel() throws Exception
	{
		return "<i class=\'fa fa-briefcase\' aria-hidden=\'true\' style=\'margin-right: 10px;\'></i>Assignments";
	}

	/*
	 * If there are no themes for this forum, the uri should be "/forum"
	 * If there are themes, it should be /forums/by-theme.do
	 * 
	 */
	public String getFeatureUri() throws Exception
	{
		return "/assignments";
	}


	public final void setDao(AssignmentsDao dao)
	{
		this.dao = dao;
	}

	public boolean isFeatureMandatory() throws Exception
	{
		return false;
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
		return "Community Assignments";
	}
}
