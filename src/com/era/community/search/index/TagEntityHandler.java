package com.era.community.search.index;

import java.util.Date;
import java.util.Map;

import support.community.lucene.index.BinaryDataHandler;
import support.community.lucene.index.EntityHandler;
import support.community.lucene.index.EntityIndex;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.era.community.tagging.dao.Tag;
import com.era.community.wiki.dao.WikiEntry;

/**
 * The search covers the data returned by getText() method
 */
public class TagEntityHandler extends EntityHandler
{
	private CommunityEraContextManager contextManager;

	public boolean supports(Class type) throws Exception
	{
		return Tag.class.isAssignableFrom(type);
	}

	public int getEntityId(Object o) throws Exception
	{
		return ((Tag)o).getId();
	}
	
	public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
	{
		return true;
	}

	public String getContent(Object rowBean, BinaryDataHandler handler, boolean fast) throws Exception
	{
		return "";
	}

	public String getTitle(Object rowBean) throws Exception
	{
		return "";
	}

	public String getDescription(Object rowBean) throws Exception
	{
		return "";
	}

	public Date getDate(Object rowBean) throws Exception
	{
		Tag doc = (Tag)rowBean;
		return doc.getCreated();
	}

	public Date getModified(Object rowBean) throws Exception
	{
		Tag doc = (Tag)rowBean;
		return doc.getModified();
	}

	public Map<String, Object> getDataFields(Object entity) throws Exception
	{
		return null;
	}

	public String getTagText(Object entity) throws Exception 
	{ 
		Tag doc = (Tag)entity;
		return doc.getTagText(); 
	}

	public String getLink(Object entity, String query, EntityIndex index) throws Exception
	{
		Tag doc = (Tag)entity;
		return contextManager.getContext().getContextUrl()+ "/pers/connectionResult.do?id=" +doc.getId()+ "&backlink=ref";
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
}
