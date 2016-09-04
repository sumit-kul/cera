package com.era.community.wiki.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.era.community.wiki.dao.Wiki;
import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryFinder;
import com.era.community.wiki.dao.WikiFeature;
import com.era.community.wiki.dao.generated.WikiEntryEntity;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/cid/[cec]/wiki/updateWikiTitle.ajx"
 */
public class UpdateWikiTitleAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager; 
	protected WikiFeature wikiFeature;
	protected WikiEntryFinder wikiEntryFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = contextManager.getContext().getResponse();
		User currUser = context.getCurrentUser();
		JSONObject json = new JSONObject();
		boolean iserror = false;
		if (currUser != null) {
			if (cmd.getTitle().length() == 0) {
				iserror = true;
				json.put("titleError", "<p class='availableFail'>Title for an article is must. Please give a title.</p>");
			} else if (cmd.getTitle().length() > 100) {
				iserror = true;
				json.put("titleError", "<p class='availableFail'>Maximum length of the title is 100 characters, please shorten your title.</p>");
			}
			Wiki wiki = (Wiki) wikiFeature.getFeatureForCurrentCommunity();
			if (wiki.entryExists(cmd.getTitle(), cmd.getEntryId())) {
				iserror = true;
				json.put("titleError", "<p class='availableFail'>An article with the same title already exists in this wiki. Either change title or edit the existing article.</p>");
			}
			if (!iserror) {
				if (cmd.getEntryId() > 0) {
					WikiEntry entry = wikiEntryFinder.getLatestWikiEntryForEntryId(cmd.getEntryId());
					entry.setTitle(cmd.getTitle());
					entry.update();
				} else {
					WikiEntry entry = wikiEntryFinder.newWikiEntry();
					cmd.copyNonNullPropertiesTo(entry);
					entry.setWikiId(wiki.getId());
					entry.setReasonForUpdate("Creating new article");
					entry.setPosterId(context.getCurrentUser().getId());

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date now = new Date();
					String dt = sdf.format(now);
					Timestamp ts = Timestamp.valueOf(dt);
					entry.setDatePosted(ts);
					entry.setVisitors(0);
					entry.setLastVisitorsTime(ts);
					entry.setEntryId(entry.getId());
					entry.setEntrySequence(Integer.MAX_VALUE);
					entry.update();
					json.put("entryId", entry.getEntryId());
				}
			} 
			json.put("iserror", iserror);
		}
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	public class Command extends WikiEntryEntity implements CommandBean
	{
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setWikiFeature(WikiFeature wikiFeature) {
		this.wikiFeature = wikiFeature;
	}

	public void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder) {
		this.wikiEntryFinder = wikiEntryFinder;
	}
}