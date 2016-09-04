package com.era.community.wiki.ui.action;

import java.util.Map;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.AbstractIndexAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.LinkBuilderContext;
import com.era.community.pers.dao.UserFinder;
import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryFinder;
import com.era.community.wiki.ui.dto.WikiEntryDto;

/**
 *  @spring.bean name="/cid/[cec]/wiki/wikiVersions.do" 
 */
public class WikiVersionsIndexAction extends AbstractIndexAction
{
	protected WikiEntryFinder wikiEntryFinder;
	protected UserFinder userFinder;
	protected CommunityEraContextManager contextManager;

	protected String getView(IndexCommandBean bean) throws Exception
	{
		return "wiki/wikiVersions";
	}

	protected QueryPaginator getScroller(IndexCommandBean bean) throws Exception
	{
		Command cmd = (Command) bean;        
		WikiEntry entry = wikiEntryFinder.getLatestWikiEntryForEntryId( cmd.getEntryId() );       
		cmd.setTitle( entry.getTitle() );
		QueryScroller scroller = entry.listHistoryByEditDate(false);               
		scroller.setBeanClass(RowBean.class);
		return scroller;
	}

	protected Map referenceData(Object data) throws Exception
	{
		Command cmd = (Command) data;        
		CommunityEraContext context = contextManager.getContext();
		LinkBuilderContext linkBuilder = context.getLinkBuilder();
		linkBuilder.addToolLink("Edit this page", context.getCurrentCommunityUrl() + 
				"/wiki/editWiki.do?entryId=" + cmd.getEntryId(), "Edit page");
		linkBuilder.addToolLink("Add new wiki page", context.getCurrentCommunityUrl() + 
				"/wiki/addWiki.do","Add New");

		linkBuilder.addToolLink("Compare wiki page versions", context.getCurrentCommunityUrl() + 
				"/wiki/compareWikiVersions.do?entryId=" + cmd.getEntryId(),"Compare wiki versions for differences");   
		return super.referenceData(data);
	}

	public static class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{ 
		private int entryId;
		private String title;

		public int getEntryId()
		{
			return entryId;
		}

		public void setEntryId(int entryId)
		{
			this.entryId = entryId;
		}

		public String getTitle()
		{
			return title;
		}

		public void setTitle(String title)
		{
			this.title = title;
		}
	} 

	public static class RowBean extends WikiEntryDto
	{  
		private int resultSetIndex;        
		private String editedBy;
		private boolean editedByPhotoPresent;
		private String sequence;

		public int getResultSetIndex()
		{
			return resultSetIndex; 
		}

		public void setResultSetIndex(int resultSetIndex) 
		{
			this.resultSetIndex = resultSetIndex;
		}

		public boolean isLatest()
		{
			return this.getEntrySequence() == Integer.MAX_VALUE;
		}

		public boolean isFirst()
		{
			return this.getEntrySequence() == 1;
		}

		public String getEditedBy()
		{
			return editedBy;
		}
		public void setEditedBy(String editedBy)
		{
			this.editedBy = editedBy; 
		}

		public boolean isEditedByPhotoPresent() {
			return editedByPhotoPresent;
		}

		public void setEditedByPhotoPresent(boolean editedByPhotoPresent) {
			this.editedByPhotoPresent = editedByPhotoPresent;
		}

		public String getSequence() {
			if (this.getEntrySequence() == Integer.MAX_VALUE) {
				return "Latest";
			} else {
				return Integer.toString(this.getEntrySequence());
			}
		}

		public void setSequence(String sequence) {
			this.sequence = sequence;
		}

	}

	/* Used by Spring to inject reference */
	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	/* Used by Spring to inject reference */    
	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	/* Used by Spring to inject reference */        
	public void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder)
	{
		this.wikiEntryFinder = wikiEntryFinder;
	}
}
