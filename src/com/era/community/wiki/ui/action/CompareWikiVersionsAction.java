package com.era.community.wiki.ui.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.outerj.daisy.diff.HtmlCleaner;
import org.outerj.daisy.diff.XslFilter;
import org.outerj.daisy.diff.html.HTMLDiffer;
import org.outerj.daisy.diff.html.HtmlSaxDiffOutput;
import org.outerj.daisy.diff.html.TextNodeComparator;
import org.outerj.daisy.diff.html.dom.DomTreeBuilder;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.CommandValidator;
import support.community.util.StringHelper;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryFinder;
import com.era.community.wiki.dao.WikiEntrySection;
import com.era.community.wiki.dao.WikiEntrySectionFinder;
import com.era.community.wiki.ui.dto.WikiEntryDto;

/**
 * @spring.bean name="/cid/[cec]/wiki/compareWikiVersions.do"
 */
public class CompareWikiVersionsAction extends AbstractFormAction
{
	protected CommunityEraContextManager contextManager;
	protected UserFinder userFinder;
	protected WikiEntryFinder wikiEntryFinder;
	protected WikiEntrySectionFinder wikiEntrySectionFinder;

	protected String getView()
	{
		return "wiki/compareWikiVersions";
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		WikiEntry pre, latest;
		StringBuffer preBody, laterBody;
		if (cmd.getFirst() == 0 || cmd.getFirst() == Integer.MAX_VALUE) {
			latest = wikiEntryFinder.getLatestWikiEntryForEntryId(cmd.getEntryId());
		} else {
			latest = wikiEntryFinder.getWikiEntryForEntryIdAndSequence(cmd.getEntryId(), cmd.getFirst());
		}
		pre = wikiEntryFinder.getWikiEntryForEntryIdAndSequence(cmd.getEntryId(), cmd.getSecond());

		StringBuffer sInt1 = new StringBuffer("<h2 class='secHeader'><span>Introduction</span></h2>");
		StringBuffer sInt2 = new StringBuffer("<h2 class='secHeader'><span>Introduction</span></h2>");
		// First compare introduction section...
		preBody = sInt1.append(pre.getBody());
		laterBody = sInt2.append(latest.getBody());
		cmd.setFirst(latest.getEntrySequence());
		cmd.setSecond(pre.getEntrySequence());
		cmd.setCompared(true);
		cmd.setTitle(latest.getTitle());
		User laterUser = userFinder.getUserEntity(latest.getPosterId());
		cmd.setLaterUser(laterUser);
		cmd.setLaterDate(latest.getDatePosted());
		cmd.setLaterReason(latest.getReasonForUpdate());
		User previousUser = userFinder.getUserEntity(pre.getPosterId());
		cmd.setPreviousUser(previousUser);
		cmd.setPreviousDate(pre.getDatePosted());
		cmd.setPreviousReason(pre.getReasonForUpdate());

		// Now compare remaining sections one by one...
		List<WikiEntrySection> latestSections = wikiEntrySectionFinder.getWikiEntrySectionsForWikiEntryId(latest.getId());
		if (latestSections != null && latestSections.size() > 0) {
			for (WikiEntrySection wikiEntrySection : latestSections) {
				String lBody = wikiEntrySection.getSectionBody() != null ? wikiEntrySection.getSectionBody() : "";
				String lHeader = wikiEntrySection.getSectionTitle() != null ? "<h2 class='secHeader'><span>"+wikiEntrySection.getSectionTitle()+"</span></h2>" : "";
				laterBody = laterBody.append(lHeader).append(lBody);
			}
		}
		
		List<WikiEntrySection> preSections = wikiEntrySectionFinder.getWikiEntrySectionsForWikiEntryId(pre.getId());
		if (preSections != null && preSections.size() > 0) {
			for (WikiEntrySection wikiEntrySection : preSections) {
				String pBody = wikiEntrySection.getSectionBody() != null ? wikiEntrySection.getSectionBody() : "";
				String pHeader = wikiEntrySection.getSectionTitle() != null ? "<h2 class='secHeader'><span>"+wikiEntrySection.getSectionTitle()+"</span></h2>" : "";
				preBody = preBody.append(pHeader).append(pBody);
			}
		}
		
		String[] args = { preBody.toString(), laterBody.toString() };
		String result = compare(args);
		cmd.setComparisonText(result);
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		return new ModelAndView("redirect:/cid/" + context.getCurrentCommunity().getId()
				+ "/wiki/compareWikiVersions.do?entryId=" + cmd.getEntryId() + "&first=" + cmd.getFirst() + "&second="
				+ cmd.second);
	}

	public class Validator extends CommandValidator
	{
		public String validateFirst(Object value, CommandBeanImpl cmd) throws Exception
		{
			Command command = (Command) cmd;
			if (isNullOrWhitespace(value)) return "Please select version from";
			if (command.getFirst() == command.getSecond()) return "You have selected same versions";
			return null;
		}

		public String validateSecond(Object value, CommandBeanImpl cmd) throws Exception
		{
			if (isNullOrWhitespace(value)) return "Please select version to";
			return null;
		}

	}

	private String compare(String[] args) throws Exception
	{
		String[] css = new String[] { "diff.css" };

		SAXTransformerFactory tf = (SAXTransformerFactory) TransformerFactory.newInstance();
		TransformerHandler result = tf.newTransformerHandler();

		// This class implements an output stream in which the data is written into a byte array. 
		// The buffer automatically grows as data is written to it. The data can be retrieved using toByteArray() and toString().
		ByteArrayOutputStream out = new ByteArrayOutputStream(40960);
		result.setResult(new StreamResult(out));

		InputStream oldStream, newStream;
		oldStream = new ByteArrayInputStream(args[0].getBytes());
		newStream = new ByteArrayInputStream(args[1].getBytes());

		XslFilter filter = new XslFilter();
		ContentHandler postProcess = filter.xsl(result, "support/community/tag/htmlheader.xsl");
		Locale locale = Locale.ENGLISH;
		String prefix = "diff";
		HtmlCleaner cleaner = new HtmlCleaner();

		InputSource oldSource = new InputSource(oldStream);
		InputSource newSource = new InputSource(newStream);

		DomTreeBuilder oldHandler = new DomTreeBuilder();
		cleaner.cleanAndParse(oldSource, oldHandler);
		TextNodeComparator leftComparator = new TextNodeComparator(oldHandler, locale);

		DomTreeBuilder newHandler = new DomTreeBuilder();
		cleaner.cleanAndParse(newSource, newHandler);
		TextNodeComparator rightComparator = new TextNodeComparator(newHandler, locale);

		postProcess.startDocument();
		postProcess.startElement("", "diffreport", "diffreport", new AttributesImpl());
		doCSS(css, postProcess);
		postProcess.startElement("", "diff", "diff", new AttributesImpl());
		HtmlSaxDiffOutput output = new HtmlSaxDiffOutput(postProcess, prefix);
		try{
			HTMLDiffer differ = new HTMLDiffer(output);
			differ.diff(leftComparator, rightComparator);
		}catch (Throwable t){

		}
		postProcess.endElement("", "diff", "diff");
		postProcess.endElement("", "diffreport", "diffreport");
		postProcess.endDocument();

		return  new String( out.toByteArray(), "UTF-8");
	}

	private static void doCSS(String[] css, ContentHandler handler) throws SAXException
	{
		handler.startElement("", "css", "css", new AttributesImpl());
		for (String cssLink : css) {
			AttributesImpl attr = new AttributesImpl();
			attr.addAttribute("", "href", "href", "CDATA", cssLink);
			attr.addAttribute("", "type", "type", "CDATA", "text/css");
			attr.addAttribute("", "rel", "rel", "CDATA", "stylesheet");
			handler.startElement("", "link", "link", attr);
			handler.endElement("", "link", "link");
		}
		handler.endElement("", "css", "css");
	}

	public class Command extends WikiEntryDto implements CommandBean
	{
		private int first;
		private int second;
		private String comparisonText;
		private boolean compared;
		private List compareSections = new ArrayList();
		private User laterUser;
		private User previousUser;
		private Date laterDate;
		private Date previousDate;
		private String laterReason;
		private String previousReason;

		public boolean getCompared()
		{
			return compared;
		}

		public void setCompared(boolean compared)
		{
			this.compared = compared;
		}
		
		public String getFirstSequence() {
			if (this.getFirst() == Integer.MAX_VALUE) {
				return "Latest Version";
			} else {
				return Integer.toString(this.getFirst());
			}
		}

		public String getDisplayBody()
		{
			return StringHelper.formatForDisplay(getBody());
		}

		public boolean isFirstWikiEntry() throws Exception
		{

			WikiEntry wikiEntry = null;
			try {
				wikiEntry = wikiEntryFinder.getFirstWikiEntry(this.getWikiId());
				if (wikiEntry.getEntryId() == this.getEntryId()) {
					return true;
				}
				return false;
			} catch (ElementNotFoundException x) {
				return false;
			}
		}

		public List getVersionOptionList() throws Exception
		{
			return wikiEntryFinder.getWikiVersionOptionList(this.getEntryId());
		}

		public final int getFirst()
		{
			return first;
		}

		public final void setFirst(int first)
		{
			this.first = first;
		}

		public final int getSecond()
		{
			return second;
		}

		public final void setSecond(int second)
		{
			this.second = second;
		}

		public final String getComparisonText()
		{
			return comparisonText;
		}

		public final void setComparisonText(String comparisonText)
		{
			this.comparisonText = comparisonText;
		}

		public List getCompareSections() {
			return compareSections;
		}

		public void setCompareSections(List compareSections) {
			this.compareSections = compareSections;
		}

		public User getLaterUser() {
			return laterUser;
		}

		public void setLaterUser(User laterUser) {
			this.laterUser = laterUser;
		}

		public User getPreviousUser() {
			return previousUser;
		}

		public void setPreviousUser(User previousUser) {
			this.previousUser = previousUser;
		}

		public Date getLaterDate() {
			return laterDate;
		}

		public void setLaterDate(Date laterDate) {
			this.laterDate = laterDate;
		}

		public Date getPreviousDate() {
			return previousDate;
		}

		public void setPreviousDate(Date previousDate) {
			this.previousDate = previousDate;
		}

		public String getLaterReason() {
			return laterReason;
		}

		public void setLaterReason(String laterReason) {
			this.laterReason = laterReason;
		}

		public String getPreviousReason() {
			return previousReason;
		}

		public void setPreviousReason(String previousReason) {
			this.previousReason = previousReason;
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

	public void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder)
	{
		this.wikiEntryFinder = wikiEntryFinder;
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setWikiEntrySectionFinder(
			WikiEntrySectionFinder wikiEntrySectionFinder) {
		this.wikiEntrySectionFinder = wikiEntrySectionFinder;
	}
}