package com.era.community.doclib.dao;

import support.community.application.ElementNotFoundException;

import com.era.community.base.CommunityFeature;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;

public class DocumentLibraryFeature implements CommunityFeature
{
	CommunityEraContextManager contextManager;
	DocumentLibraryDao doclibDao;


	public void setFeatureEnabledForCommunity(Community comm, boolean status) throws Exception
	{
		DocumentLibrary doclib = (DocumentLibrary)getFeatureForCommunity(comm);
		if (doclib==null && status==false) {
			return;
		}
		else if (doclib==null && status==true) {
			doclib = doclibDao.newDocumentLibrary();
			doclib.setCommunityId(comm.getId());
			doclib.setName(comm.getName());
			doclib.setInactive(false);
			doclib.update();
			return;
		}
		else {
			doclib.setInactive(!status);
			doclib.update();
		}
	}

	public boolean isFeatureEnabledForCommunity(Community comm) throws Exception
	{
		DocumentLibrary doclib = (DocumentLibrary)getFeatureForCommunity(comm);
		if (doclib==null) return false;
		return !doclib.isInactive();
	}

	public Object getFeatureForCommunity(Community comm) throws Exception
	{
		try {
			return doclibDao.getDocumentLibraryForCommunity(comm);
		}
		catch (ElementNotFoundException e) {
			return null;
		}
	}
	public Object getFeatureForCurrentCommunity() throws Exception
	{
		return getFeatureForCommunity(contextManager.getContext().getCurrentCommunity());
	}

	public String getFeatureName() throws Exception
	{
		return "Library";
	}

	public String getFeatureLabel() throws Exception
	{
		return "<i class=\'fa fa-folder-open\' aria-hidden=\'true\' style=\'margin-right: 10px;\'></i>Library";
	}

	public String getFeatureUri() throws Exception
	{
		return "/doclib";
	}


	public final void setDoclibDao(DocumentLibraryDao doclibDao)
	{
		this.doclibDao = doclibDao;
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
		return "Library for this Community";
	}
}
