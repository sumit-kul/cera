package com.era.community.forum.dao;

import support.community.application.ElementNotFoundException;

import com.era.community.base.CommunityFeature;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;

public class ForumFeature implements CommunityFeature
{
	ForumDao dao;

	CommunityEraContextManager contextManager;

	public Object getFeatureForCurrentCommunity() throws Exception
	{
		return getFeatureForCommunity(contextManager.getContext().getCurrentCommunity());
	}

	public void setFeatureEnabledForCommunity(Community comm, boolean status) throws Exception
	{
		Forum o = (Forum)getFeatureForCommunity(comm);
		if (o==null && status==false) {
			return;
		}
		else if (o==null && status==true) {
			o = dao.newForum();
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
		Forum o = (Forum)getFeatureForCommunity(comm);
		if (o==null) return false;
		return !o.isInactive();
	}

	public Object getFeatureForCommunity(Community comm) throws Exception
	{
		try {
			return dao.getForumForCommunity(comm);
		}
		catch (ElementNotFoundException e) {
			return null;
		}
	}

	public String getFeatureName() throws Exception
	{
		return "Forum";
	}

	public String getFeatureLabel() throws Exception
	{
		return "<i class=\'fa fa-comments-o\' aria-hidden=\'true\' style=\'margin-right: 10px;\'></i>Forum";
	}

	/*
	 * If there are no themes for this forum, the uri should be "/forum"
	 * If there are themes, it should be /forums/by-theme.do
	 * 
	 */
	public String getFeatureUri() throws Exception
	{
		return "/forum";
	}


	public final void setDao(ForumDao dao)
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
		return "Community Discussion Forum";
	}
}
