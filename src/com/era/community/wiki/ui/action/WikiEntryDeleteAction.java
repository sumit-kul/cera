package com.era.community.wiki.ui.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.upload.dao.ImageFinder;
import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryCommentFinder;
import com.era.community.wiki.dao.WikiEntryFinder;
import com.era.community.wiki.dao.WikiEntrySectionFinder;

/**
 * @spring.bean name="/cid/[cec]/wiki/wikiDelete.do"
 */
public class WikiEntryDeleteAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager;
	protected WikiEntryFinder wikiEntryFinder; 
	protected WikiEntrySectionFinder wikiEntrySectionFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected ImageFinder imageFinder;
	protected WikiEntryCommentFinder wikiEntryCommentFinder; 

	public void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder)
	{
		this.wikiEntryFinder = wikiEntryFinder;
	}
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		WikiEntry entry = null;
		try {
			entry = wikiEntryFinder.getLatestWikiEntryForEntryId(cmd.getId());
		} catch (Exception e) {
			return null;
		}
		if ( entry != null && entry.isWriteAllowed( context.getCurrentUserDetails() )) {
			if (cmd.getMode().equals("all")){
				wikiEntryCommentFinder.deleteAllWikiEntryComments(entry.getEntryId());
				imageFinder.deleteImagesForAllWikiEntryVersions(entry.getEntryId());
				//subscriptionFinder.deleteSubscriptionsForWikiEntry(entry.getEntryId()); should be deleted after mail sent
				wikiEntrySectionFinder.deleteWikiEntrySectionsForAllWikiEntryVersions(entry.getEntryId());
				entry.deleteWikiEntryWithAllVersions(cmd.getId());
				return new ModelAndView("redirect:" + context.getCurrentCommunityUrl() + "/wiki/showWikiEntries.do");
			}else{
				imageFinder.deleteImagesForWikiEntry(entry.getId());
				wikiEntrySectionFinder.deleteWikiEntrySectionsForWikiEntryVersion(entry.getId());
				entry.delete();
				
				entry = wikiEntryFinder.getPreviousWikiEntryForEntryId(cmd.getId());
				if (entry != null) {
					entry.setEntrySequence(Integer.MAX_VALUE);
					entry.update();
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("entryId", entry.getEntryId());
					return  new ModelAndView("redirect:"+context.getCurrentCommunityUrl()+"/wiki/wikiDisplay.do", map);
				} else {
					return new ModelAndView("redirect:" + context.getCurrentCommunityUrl() + "/wiki/showWikiEntries.do");
				}
			}
		} else {
			return null;
		}
		
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int id;

		public int getId()
		{
			return id;
		}
		public void setId(int id)
		{
			this.id = id;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
	
	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}
	
	public void setWikiEntrySectionFinder(
			WikiEntrySectionFinder wikiEntrySectionFinder) {
		this.wikiEntrySectionFinder = wikiEntrySectionFinder;
	}
	
	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}
	
	public void setImageFinder(ImageFinder imageFinder) {
		this.imageFinder = imageFinder;
	}
	
	public void setWikiEntryCommentFinder(
			WikiEntryCommentFinder wikiEntryCommentFinder) {
		this.wikiEntryCommentFinder = wikiEntryCommentFinder;
	}
}