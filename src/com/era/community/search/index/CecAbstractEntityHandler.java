package com.era.community.search.index;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import support.community.lucene.index.EntityHandler;
import support.community.lucene.index.EntityIndex;

import com.era.community.base.CommunityEraContextManager;

public abstract class CecAbstractEntityHandler extends EntityHandler implements ServletContextAware
{
	private ServletContext servletContext;
	protected CommunityEraContextManager contextManager;

	protected final DateFormat DATE_FORMAT = new SimpleDateFormat("d MMM yyyy");

	public final Map<String, Object> getDataFields(Object rowBean) throws Exception
	{
		Map<String, Object> map = getAdditionalDataFields(rowBean);
		if (map==null) map = new HashMap<String, Object>(1);
		map.put(CecEntityIndex.COMMUNITY_ID_FIELD, new Integer(getCommunityId(rowBean)));
		return map; 
	}

	public final String getLink(Object entity, String query, EntityIndex index) throws Exception
	{
		return contextManager.getContext().getContextUrl()+ "/cid/" + getCommunityId(entity)+"/"+ getCommunityRelativeUrl(entity, query, index)+"&backlink=ref";
	}

	public abstract int getCommunityId(Object rowBean) throws Exception;
	public abstract String getCommunityRelativeUrl(Object rowBean, String query, EntityIndex index) throws Exception;
	public abstract Map<String, Object> getAdditionalDataFields(Object rowBean) throws Exception;

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
	public final ServletContext getServletContext()
	{
		return servletContext;
	}
	public final void setServletContext(ServletContext servletContext)
	{
		this.servletContext = servletContext;
	}
}
