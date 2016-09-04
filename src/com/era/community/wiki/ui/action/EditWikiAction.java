package com.era.community.wiki.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.ImageManipulation;
import com.era.community.base.Taggable;
import com.era.community.communities.dao.Community;
import com.era.community.config.dao.MailMessageConfig;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.monitor.dao.WikiEntrySubscription;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.wiki.dao.Wiki;
import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryFinder;
import com.era.community.wiki.dao.WikiEntrySection;
import com.era.community.wiki.dao.WikiEntrySectionFinder;
import com.era.community.wiki.dao.WikiFeature;
import com.era.community.wiki.ui.dto.Section;
import com.era.community.wiki.ui.dto.WikiEntryDto;
import com.era.community.wiki.ui.validator.WikiEntryValidator;

/**
 * @spring.bean name="/cid/[cec]/wiki/editWiki.do" 
 */
public class EditWikiAction extends AbstractFormAction
{
	protected CommunityEraContextManager contextManager;
	protected WikiFeature wikiFeature;
	protected WikiEntryFinder wikiEntryFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected WikiEntrySectionFinder wikiEntrySectionFinder;
	protected UserFinder userFinder;
	protected MailSender mailSender;
	protected MailMessageConfig mailMessageConfig;

	protected String getView()
	{
		return "wiki/editWiki";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command) data;
		Wiki wiki = (Wiki) wikiFeature.getFeatureForCurrentCommunity();
		cmd.setWikiEntryFinder(wikiEntryFinder);
		cmd.setReasonForUpdate("");
		cmd.setSearchType("Wiki");
		cmd.setQueryText(wiki.getName());

		WikiEntry entry = wikiEntryFinder.getLatestWikiEntryForEntryId(cmd.getEntryId());
		cmd.copyPropertiesFrom(entry);    
		List<Section> secs = wikiEntrySectionFinder.getWikiEntrySectionDtosForWikiEntryId(entry.getId());
		cmd.getSectionsToDisplay().clear();
		cmd.setSectionsToDisplay(secs);
		cmd.setSecCount(secs.size());
		List<Section> temp = new ArrayList<Section>();
		for (int i = 0; i < (10 - secs.size()); i++) {
			temp.add(new Section());
		}
		cmd.setSections(temp);
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		if (context.getCurrentUser() == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

		Community comm = context.getCurrentCommunity();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);

		WikiEntry oldentry = null;
		WikiEntry newentry = null;
		if (cmd.minorEdit) {
			oldentry = wikiEntryFinder.getLatestWikiEntryForEntryId(cmd.getEntryId());
			//cmd.copyNonNullPropertiesTo(entry);
			//entry.setTitle(cmd.getTitle());
			oldentry.setPosterId(context.getCurrentUser().getId());
			oldentry.setDatePosted(new Date());
			Wiki wiki = (Wiki) wikiFeature.getFeatureForCommunity(comm);
			oldentry.setWikiId(wiki.getId());
			oldentry.setModified(ts);
			oldentry.update();   

			oldentry.setBody( ImageManipulation.manageImages(contextManager.getContext(), cmd.getBody(), oldentry.getTitle(), context.getCurrentUser().getId(), oldentry.getId(), "WikiEntry"));
			newentry = oldentry;
		} else {
			oldentry = wikiEntryFinder.getLatestWikiEntryForEntryId( cmd.getEntryId() );
			int nextversion = wikiEntryFinder.getNextSequenceNumberForEntry(oldentry);
			oldentry.setEntrySequence( nextversion );
			oldentry.setModified(ts);
			oldentry.update();
			
			Wiki wiki = (Wiki) wikiFeature.getFeatureForCommunity(comm);

			newentry = wikiEntryFinder.newWikiEntry();   
			cmd.copyNonNullPropertiesTo(newentry);
			newentry.setTitle(oldentry.getTitle());
			newentry.setWikiId(wiki.getId());
			newentry.setPosterId(context.getCurrentUser().getId());
			newentry.setDatePosted(new Date());
			newentry.setEntryId( oldentry.getEntryId() ); 
			newentry.setVisitors(oldentry.getVisitors());
			
			
			newentry.setModified(ts);
			newentry.setEntrySequence(Integer.MAX_VALUE);
			newentry.update(); 

			newentry.setBody( ImageManipulation.manageImages(context, cmd.getBody(), cmd.getTitle(), context.getCurrentUser().getId(), newentry.getId(), "WikiEntry") );
		}

		comm.setCommunityUpdated(ts);
		comm.update();

		if (cmd.minorEdit) {
			// already added section if exists
			if (cmd.getSectionsToDisplay() != null && cmd.getSectionsToDisplay().size() > 0) {
				for (Section section : cmd.getSectionsToDisplay()) { // from UI
					if ((section.getHeader() != null && !"".equals(section.getHeader().trim())) || (section.getBody() != null && !"".equals(section.getBody().trim()))) {
						try {
							WikiEntrySection wikiEntrySection = wikiEntrySectionFinder.getWikiEntrySectionForId(section.getSectionId());
							wikiEntrySection.setSectionSeq(section.getSectionSeq());
							wikiEntrySection.setSectionTitle(section.getHeader());
							wikiEntrySection.setWikiEntryId(newentry.getId());
							wikiEntrySection.update();

							wikiEntrySection.setSectionBody( ImageManipulation.manageImages(context, section.getBody(), cmd.getTitle(), context.getCurrentUser().getId(), wikiEntrySection.getId(), "WikiEntrySection") );
							wikiEntrySection.setSectionSeq(section.getSectionSeq());
							wikiEntrySection.update();
						} catch (ElementNotFoundException e) {
						}
					} // else info has been deleted from UI and no need to create a new version for the section...
				}
			}

			// new sections if added
			if (cmd.getSections() != null && cmd.getSections().size() > 0) {
				for (Section section : cmd.getSections()) { // from UI
					if ((section.getHeader() != null && !"".equals(section.getHeader().trim())) || (section.getBody() != null && !"".equals(section.getBody().trim()))) {
						WikiEntrySection wikiEntrySection = wikiEntrySectionFinder.newWikiEntrySection();
						wikiEntrySection.setSectionSeq(section.getSectionSeq());
						wikiEntrySection.setSectionTitle(section.getHeader());
						wikiEntrySection.setWikiEntryId(newentry.getId());
						wikiEntrySection.update();

						wikiEntrySection.setSectionBody( ImageManipulation.manageImages(context, section.getBody(), cmd.getTitle(), context.getCurrentUser().getId(), wikiEntrySection.getId(), "WikiEntrySection") );
						wikiEntrySection.setSectionSeq(section.getSectionSeq());
						wikiEntrySection.update();
					} // else info has been deleted from UI and no need to create a new version for the section...
				}
			}
		} else {
			// already added section if exists
			if (cmd.getSectionsToDisplay() != null && cmd.getSectionsToDisplay().size() > 0) {
				for (Section section : cmd.getSectionsToDisplay()) { // from UI
					if ((section.getHeader() != null && !"".equals(section.getHeader())) || (section.getBody() != null && !"".equals(section.getBody()))) {
						WikiEntrySection wikiEntrySection = wikiEntrySectionFinder.newWikiEntrySection();
						wikiEntrySection.setSectionSeq(section.getSectionSeq());
						wikiEntrySection.setSectionTitle(section.getHeader());
						wikiEntrySection.setWikiEntryId(newentry.getId());
						wikiEntrySection.update();

						wikiEntrySection.setSectionBody( ImageManipulation.manageImages(context, section.getBody(), cmd.getTitle(), context.getCurrentUser().getId(), wikiEntrySection.getId(), "WikiEntrySection") );
						wikiEntrySection.setSectionSeq(section.getSectionSeq());
						wikiEntrySection.update();
					} // else info has been deleted from UI and no need to create a new version for the section...
				}
			}

			// new sections if added
			if (cmd.getSections() != null && cmd.getSections().size() > 0) {
				for (Section section : cmd.getSections()) { // from UI
					if ((section.getHeader() != null && !"".equals(section.getHeader())) || (section.getBody() != null && !"".equals(section.getBody()))) {
						WikiEntrySection wikiEntrySection = wikiEntrySectionFinder.newWikiEntrySection();
						wikiEntrySection.setSectionSeq(section.getSectionSeq());
						wikiEntrySection.setSectionTitle(section.getHeader());
						wikiEntrySection.setWikiEntryId(newentry.getId());
						wikiEntrySection.update();

						wikiEntrySection.setSectionBody( ImageManipulation.manageImages(context, section.getBody(), cmd.getTitle(), context.getCurrentUser().getId(), wikiEntrySection.getId(), "WikiEntrySection") );
						wikiEntrySection.setSectionSeq(section.getSectionSeq());
						wikiEntrySection.update();
					} // else info has been deleted from UI and no need to create a new version for the section...
				}
			}
			mailSubscribers(newentry, context);
		}
		return new ModelAndView("redirect:" + context.getCurrentCommunityUrl() + "/wiki/wikiDisplay.do?entryId="+newentry.getEntryId());
	}

	public static class Command extends WikiEntryDto implements CommandBean, Taggable
	{         
		private String tags;
		private boolean minorEdit;
		private List<Section> sections;
		private List<Section> sectionsToDisplay;
		private int secCount;

		public Command() {
			sections = new ArrayList<Section>();
			for (int i = 0; i < 10; i++) {
				sections.add(new Section());
			}

			sectionsToDisplay = new ArrayList<Section>();
			for (int i = 0; i < 10; i++) {
				sectionsToDisplay.add(new Section());
			}
		}

		private final String WIKI_ACTION = "Edit";

		public String getWikiAction() {
			return WIKI_ACTION;
		}

		public Date getToday() 
		{
			return new Date();
		}

		public String getTags() {
			return tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public Map getPopularTags()
		{
			return null;
		}

		public boolean getMinorEdit()
		{
			return minorEdit;
		}

		public void setMinorEdit(boolean minorEdit)
		{
			this.minorEdit = minorEdit;
		}

		public List<Section> getSections() {
			return sections;
		}

		public void setSections(List<Section> sections) {
			this.sections = sections;
		}

		public int getSecCount() {
			return secCount;
		}

		public void setSecCount(int secCount) {
			this.secCount = secCount;
		}

		public List<Section> getSectionsToDisplay() {
			return sectionsToDisplay;
		}

		public void setSectionsToDisplay(List<Section> sectionsToDisplay) {
			this.sectionsToDisplay = sectionsToDisplay;
		}
	}

	public class Validator extends WikiEntryValidator
	{ 
	}

	private void mailSubscribers(WikiEntry entry, CommunityEraContext context) throws Exception
	{

		List subs = subscriptionFinder.getSubscriptionsForWikiEntry(entry.getEntryId());


		Iterator it = subs.iterator();

		/* Loop thru the list of Wiki Entry Subscribers */
		while (it.hasNext()) {
			WikiEntrySubscription sub = (WikiEntrySubscription) it.next();

			if (sub.getFrequency() == 0) { // Only email the subscribers who want immediate alerts
				/*
				 * Email the wiki entry subscribers to alert that a new version of the wiki entry has been posted
				 */
				int uid = sub.getUserId();
				if (uid==context.getCurrentUser().getId()) continue;
				User subscriber = userFinder.getUserEntity(uid);
				if (!subscriber.isSuperAdministrator()) {

					/*
					 * Parameters to substitute into the body text of the Email.
					 */
					Map<String, String> params = new HashMap<String, String>(11);

					String sLink = context.getCurrentCommunityUrl()+"/wiki/wikiDisplay.do?id="+entry.getId();

					params.put("#communityName#", context.getCurrentCommunity().getName());
					params.put("#entryLink#", sLink);
					params.put("#entryTitle#", entry.getTitle());

					/*
					 * Create and send the mail message.
					 */
					SimpleMailMessage msg = mailMessageConfig.createMailMessage("wiki-page-update-alert", params);
					msg.setTo(subscriber.getEmailAddress());
					try {
						mailSender.send(msg);
					} catch (RuntimeException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/* Used by Spring to inject reference */
	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	/* Used by Spring to inject reference */    
	public void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder)
	{
		this.wikiEntryFinder = wikiEntryFinder;
	}

	/* Used by Spring to inject reference */    
	public void setWikiFeature(WikiFeature wikiFeature)
	{
		this.wikiFeature = wikiFeature;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}
	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setMailSender(MailSender mailSender)
	{
		this.mailSender = mailSender;
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	public final void setMailMessageConfig(MailMessageConfig mailMessageConfig)
	{
		this.mailMessageConfig = mailMessageConfig;
	}

	public void setWikiEntrySectionFinder(
			WikiEntrySectionFinder wikiEntrySectionFinder) {
		this.wikiEntrySectionFinder = wikiEntrySectionFinder;
	}
}