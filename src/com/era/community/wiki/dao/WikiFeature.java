package com.era.community.wiki.dao;

import support.community.application.ElementNotFoundException;

import com.era.community.base.CommunityFeature;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;

public class WikiFeature implements CommunityFeature
{
	WikiDao dao;

	CommunityEraContextManager contextManager;

	public Object getFeatureForCurrentCommunity() throws Exception
	{
		return getFeatureForCommunity(contextManager.getContext().getCurrentCommunity());
	}

	public void setFeatureEnabledForCommunity(Community comm, boolean status) throws Exception
	{
		Wiki o = (Wiki)getFeatureForCommunity(comm);
		if (o==null && status==false) {
			return;
		}
		else if (o==null && status==true) {
			o = dao.newWiki();
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
		Wiki o = (Wiki)getFeatureForCommunity(comm);
		if (o==null) return false;
		return !o.isInactive();
	}

	public Object getFeatureForCommunity(Community comm) throws Exception
	{
		try {
			return dao.getWikiForCommunity(comm);
		}
		catch (ElementNotFoundException e) {
			return null;
		}
	}

	public String getFeatureName() throws Exception
	{
		return "Wiki";
	}

	public String getFeatureLabel() throws Exception
	{
		return "<i class=\'fa fa-book\' aria-hidden=\'true\' style=\'margin-right: 10px;\'></i>Wiki";
	}

	public String getFeatureUri() throws Exception
	{
		return "/wiki";
	}


	public final void setDao(WikiDao dao)
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
		return "Wiki for this community";
	}
}
