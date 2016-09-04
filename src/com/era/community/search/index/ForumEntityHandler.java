package com.era.community.search.index;

import java.util.*;

import com.era.community.forum.dao.*;

public abstract class ForumEntityHandler extends CecAbstractEntityHandler 
{  
	public int getEntityId(Object o) throws Exception
	{
		return ((ForumItem)o).getId();
	}

	public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
	{
		return true;
	}

	public String getTitle(Object rowBean) throws Exception
	{
		ForumItem doc = (ForumItem)rowBean;
		return doc.getSubject();
	}

	public Date getDate(Object rowBean) throws Exception
	{
		ForumItem doc = (ForumItem)rowBean;
		return doc.getDatePosted();
	}

	public int getCommunityId(Object rowBean) throws Exception
	{
		ForumItem doc = (ForumItem)rowBean;
		return doc.getForum().getCommunityId();
	}
}