package com.era.community.search.index;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import support.community.application.ElementNotFoundException;
import support.community.lucene.index.BinaryDataHandler;
import support.community.lucene.index.EntityHandler;
import support.community.lucene.index.EntityIndex;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogEntry;

public class BlogEntityHandler extends EntityHandler implements ServletContextAware
{
	private ServletContext servletContext;

	protected final DateFormat DATE_FORMAT = new SimpleDateFormat("d MMM yyyy");

	protected CommunityEraContextManager contextManager;

	public boolean supports(Class type) throws Exception
	{
		return BlogEntry.class.isAssignableFrom(type);
	}

	public int getEntityId(Object o) throws Exception
	{
		return ((BlogEntry)o).getId();
	}

	public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
	{
		return true;
	}

	public String getContent(Object rowBean, BinaryDataHandler handler, boolean fast) throws Exception
	{
		BlogEntry doc = (BlogEntry)rowBean;
		final StringBuffer buf = new StringBuffer(4096);

		return buf.toString();
	}

	public String getTitle(Object rowBean) throws Exception
	{
		BlogEntry doc = (BlogEntry)rowBean;
		return doc.getTitle();
	}

	public String getDescription(Object rowBean) throws Exception
	{
		BlogEntry doc = (BlogEntry)rowBean;
		return htmlToText(doc.getBody());
	}

	public Date getDate(Object rowBean) throws Exception
	{
		BlogEntry doc = (BlogEntry)rowBean;
		return doc.getDatePosted();
	}

	public Date getModified(Object rowBean) throws Exception
	{
		BlogEntry doc = (BlogEntry)rowBean;
		return doc.getDatePosted();
	}

	public Map<String, Object> getDataFields(Object entity) throws Exception
	{
		Map<String, Object> map = new HashMap<String, Object>(1);
		BlogEntry entry = (BlogEntry)entity;
		map.put(CecEntityIndex.USER_ID_FIELD, new Integer(entry.getPosterId()));
		return map; 
	}

	public String getTitle3(Object entity, String query, EntityIndex index) throws Exception
	{
		BlogEntry doc = (BlogEntry)entity;
		return DATE_FORMAT.format(doc.getDatePosted());
	}

	public final String getLink(Object entity, String query, EntityIndex index) throws Exception
	{
		BlogEntry entry = (BlogEntry)entity;
		try {
			if (entry.getCommunityBlog() != null && entry.getCommunityBlog().getCommunity() != null) {
				return contextManager.getContext().getContextUrl()+ "/cid/"+entry.getCommunityBlog().getCommunity().getId()+"/blog/blogEntry.do?id=" + entry.getId();
			} else {
				return contextManager.getContext().getContextUrl()+ "/blog/blogEntry.do?id=" + entry.getId();
			}
		} catch (ElementNotFoundException e) {
		}
		return "";
	}

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