package com.era.community.wiki.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryFinder;
import com.era.community.wiki.dao.WikiEntrySection;
import com.era.community.wiki.dao.WikiEntrySectionFinder;
import com.era.community.wiki.ui.dto.WikiEntryDto;

/**
 * @spring.bean name="/cid/[cec]/wiki/wikiRestore.do" 
 */
public class WikiRestoreAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected WikiEntryFinder wikiEntryFinder;
	protected UserFinder userFinder;
	protected WikiEntrySectionFinder wikiEntrySectionFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		Community comm = context.getCurrentCommunity();

		WikiEntry currEntry = wikiEntryFinder.getLatestWikiEntryForEntryId(cmd.getEntryId());
		User currEditedBy = userFinder.getUserEntity(currEntry.getPosterId());
		
		WikiEntry entryToRestore = wikiEntryFinder.getPreviousWikiEntryForEntryId(cmd.getEntryId());
		User preEditedBy = userFinder.getUserEntity(entryToRestore.getPosterId());
		
		int nextversion = wikiEntryFinder.getNextSequenceNumberForEntry(currEntry);
		currEntry.setEntrySequence( nextversion );
		currEntry.update();

		WikiEntry newentry = wikiEntryFinder.newWikiEntry(); 

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);

		newentry.setTitle(entryToRestore.getTitle());
		newentry.setWikiId(entryToRestore.getWikiId());
		newentry.setPosterId(context.getCurrentUser().getId());
		newentry.setDatePosted(ts);
		newentry.setEntryId( entryToRestore.getEntryId() ); 
		newentry.setReasonForUpdate("Reverted edits by <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+currEntry.getPosterId()+"&backlink=ref'>"+currEditedBy.getFullName()+
				"</a> to last revision by <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+entryToRestore.getPosterId()+"&backlink=ref'>"+preEditedBy.getFullName()+ "</a>");
		newentry.setVisitors(currEntry.getVisitors()); // visitors for current entry as each visiting has to be counted...
		newentry.update();
		
		newentry.setBody(entryToRestore.getBody());
		newentry.setEntrySequence(Integer.MAX_VALUE);
		newentry.update();   

		comm.setCommunityUpdated(ts);
		comm.update();

		List<WikiEntrySection> secs = wikiEntrySectionFinder.getWikiEntrySectionsForWikiEntryId(entryToRestore.getId());
		// sections restored as well...
		if (secs != null && secs.size() > 0) {
			for (WikiEntrySection section : secs) { 
				WikiEntrySection wikiEntrySection = wikiEntrySectionFinder.newWikiEntrySection();
				wikiEntrySection.setSectionSeq(section.getSectionSeq());
				wikiEntrySection.setSectionTitle(section.getSectionTitle());
				wikiEntrySection.setWikiEntryId(newentry.getId());
				wikiEntrySection.update();
				
				wikiEntrySection.setSectionBody(section.getSectionBody());
				wikiEntrySection.setSectionSeq(section.getSectionSeq());
				wikiEntrySection.update();
			}
		}

		//mailSubscribers(newentry, context);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entryId", currEntry.getEntryId());
		return  new ModelAndView("redirect:"+context.getCurrentCommunityUrl()+"/wiki/wikiDisplay.do", map);
	}

	public static class Command extends WikiEntryDto implements CommandBean
	{        
	} 

	public void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder)
	{
		this.wikiEntryFinder = wikiEntryFinder; 
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setWikiEntrySectionFinder(
			WikiEntrySectionFinder wikiEntrySectionFinder) {
		this.wikiEntrySectionFinder = wikiEntrySectionFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}
}