package com.era.community.communities.ui.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.CommandValidator;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.ui.dto.CommunityDto;
import com.era.community.communities.ui.validator.CommunityValidator;
import com.era.community.search.dao.SearchSite;
import com.era.community.search.dao.SearchSiteFinder;

/**
 * @spring.bean name="/cid/[cec]/admin/relevantWebsites.do"
 */
public class RelevantSitesAction extends AbstractFormAction
{
	protected CommunityEraContextManager contextManager;
	protected CommunityFinder communityFinder;
	protected SearchSiteFinder searchSiteFinder;

	protected String getView()
	{
		return "community/relevantWebsites";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command)data;        

		CommunityEraContext context = contextManager.getContext();
		Community comm = context.getCurrentCommunity();

		if (!context.isUserCommunityAdmin())
			throw new Exception("Not authorized");

		cmd.setName(comm.getName());

	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		final Command cmd = (Command)data;

		final CommunityEraContext context = contextManager.getContext();
		final Community comm = context.getCurrentCommunity();

		if (!context.isUserCommunityAdmin())
			throw new Exception("Not authorized");

		for (int id : cmd.getSelectedIds()) {
			SearchSite site = searchSiteFinder.getSearchSiteForId(id);
			site.delete();
		}

		return REDIRECT_TO_FORM;
	}

	protected Map referenceData(Object command) throws Exception
	{
		return new HashMap();
	}

	protected CommandValidator createValidator()
	{
		return new Validator(); 
	}

	public class Command extends CommunityDto implements CommandBean
	{       
		private int[] selectedIds;

		private int listSize;

		public int getListSize() throws Exception
		{
			return getSites().size();
		}

		public void setListSize(int listSize)
		{
			this.listSize = listSize;
		}

		public final List<SearchSite> getSites() throws Exception
		{
			return contextManager.getContext().getCurrentCommunity().getSearchSites(); 
		}

		public final int[] getSelectedIds()
		{
			return selectedIds;
		}

		public final void setSelectedIds(int[] selectedIds)
		{
			this.selectedIds = selectedIds;
		}

	}

	public class Validator extends CommunityValidator
	{        
		public String validateHostname (Object value, CommandBeanImpl data) throws Exception
		{
			Command cmd = (Command)data;

			/* Check that the hostname is not already listed */
			try {
				searchSiteFinder.getSearchSiteForCommunityIdAndHostName(cmd.getId(), value.toString());

				return "This host name "+value+" is already listed";
			}
			catch (ElementNotFoundException e) {}

			/* Passed validation, return null' */
			return null;
		}

	}

	public void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}


	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setSearchSiteFinder(SearchSiteFinder searchSiteFinder)
	{
		this.searchSiteFinder = searchSiteFinder;
	}

}
