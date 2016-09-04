package com.era.community.forum.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;
import support.community.util.StringHelper;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.ImageManipulation;
import com.era.community.base.LinkBuilderContext;
import com.era.community.forum.dao.ForumFinder;
import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumItemFinder;
import com.era.community.forum.ui.dto.ForumItemDto;
import com.era.community.forum.ui.validator.ForumItemValidator;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.dao.generated.TagEntity;

/**
 * @spring.bean name="/cid/[cec]/forum/editForumitem.do"
 */
public class ForumItemEditAction extends AbstractFormAction
{
	private CommunityEraContextManager contextManager;
	protected TagFinder tagFinder;
	private ForumItemFinder itemFinder;
	
	private static final String HTML_A_TAG_PATTERN = "(?i)<a([^>]+)>(.+?)</a>";
	private static final String HTML_A_HREF_TAG_PATTERN = 
		"\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";
	
	private static Pattern patternTag = Pattern.compile(HTML_A_TAG_PATTERN);
	private static Pattern patternLink = Pattern.compile(HTML_A_HREF_TAG_PATTERN);;
	private Matcher matcherTag, matcherLink;

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	protected String getView()
	{
		return "forum/editForumItem";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command) data;

		ForumItem item = itemFinder.getForumItemForId(cmd.getId());
		cmd.copyPropertiesFrom(item);
		cmd.setResponse(item.isResponse());
		cmd.setKeywords(item.getKeywords());
		cmd.setMetaForDescription(cmd.getBody());
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
		
		ForumItem item = itemFinder.getForumItemForId(cmd.getId());
		item.setSubject(cmd.getSubject());
		item.setBody( ImageManipulation.manageImages(context, cmd.getBody(), cmd.getSubject(), context.getCurrentUser().getId(), item.getId(), "ForumItem") );
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		item.setModified(ts);
		item.update();
		//findLinks(cmd.getBody());

		item.setTags(cmd.getTags());
		
		int itemId = item.isResponse() ? item.getTopic().getId() : item.getId();
		return new ModelAndView("redirect:" + context.getCurrentCommunityUrl() + "/forum/forumThread.do?id=" + itemId);
	}
	
	private Vector<HtmlLink> findLinks(String richtext){
		StringBuffer sb = new StringBuffer();
		Vector<HtmlLink> result = new Vector<HtmlLink>();
		matcherTag = patternTag.matcher(richtext);
		while (matcherTag.find()) {
			String href = matcherTag.group(1); // href
			String linkText = matcherTag.group(2); // link text
			matcherLink = patternLink.matcher(href);
			while (matcherLink.find()) {
				String link = matcherLink.group(1); // link
				HtmlLink obj = new HtmlLink();
				obj.setLink(link);
				obj.setLinkText(linkText);
				result.add(obj);
				matcherTag.appendReplacement(sb, "<a "+href+">"+linkText+"<i class='fa fa-globe'></i></a>");
			}
		}
		return result;
	}
	
	class HtmlLink {

		String link;
		String linkText;

		HtmlLink(){};

		@Override
		public String toString() {
			return new StringBuffer("Link : ").append(this.link)
			.append(" Link Text : ").append(this.linkText).toString();
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = replaceInvalidChar(link);
		}

		public String getLinkText() {
			return linkText;
		}

		public void setLinkText(String linkText) {
			this.linkText = linkText;
		}
		
		private String replaceInvalidChar(String link){
			link = link.replaceAll("'", "");
			link = link.replaceAll("\"", "");
			return link;
		}
	}

	protected Map referenceData(Object command) throws Exception
	{
		Command cmd = (Command) command;

		CommunityEraContext context = contextManager.getContext();
		LinkBuilderContext linkBuilder = context.getLinkBuilder();
		if (context.isUserSysAdmin() || context.isUserCommunityAdmin()) {
			ForumItem item = itemFinder.getForumItemForId(cmd.getId());
			if (item.isResponse()) {
				linkBuilder.addToolLink("Delete this response", context.getCurrentCommunityUrl()
						+ "/forum/item-delete.do?id=" + cmd.getId(), "Delete this response", "cross");
				cmd.setItemType("response");
				cmd.setTopicSubject(item.getTopic().getSubject());
				cmd.setSearchType("Forum");
		        cmd.setQueryText(item.getTopic().getSubject());
			} else if (!item.isResponse()) {
				linkBuilder
				.addToolLink("Delete this entire topic", context.getCurrentCommunityUrl()
						+ "/forum/item-delete.do?id=" + cmd.getId(), "Delete this topic and all its responses",
				"cross");
				cmd.setItemType("topic");
				cmd.setSearchType("Forum");
		        cmd.setQueryText(item.getSubject());
			}
		}
		return new HashMap();
	}

	public class Command extends ForumItemDto implements CommandBean
	{
		private String keywords;
		private String metaForDescription;
		
		public TreeMap getPopularTags()
		{
			return null;
		}
		
		public String getMetaForDescription() {
			return metaForDescription;
		}

		public void setMetaForDescription(String metaForDescription) {
			this.metaForDescription = StringHelper.escapeHTML(metaForDescription);
		}
		
		public String getKeywords(){
			String keywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(this.getId(), 0, 20, "ForumTopic");
				
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagEntity tb = (TagEntity) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					keywords += tag;
					if (iterator.hasNext())keywords += " , ";
				}
			} catch (Exception e) {
				return keywords;
			}
			return keywords;
		}
		
		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}
	}

	public class Validator extends ForumItemValidator
	{
		public String validateSubject(Object value, CommandBean cmd)
		{
			if (value.toString().equals("")) {
				return "You must enter a title for your topic";
			}
			if (value.toString().length() > 100) {
				return "The maximum length of the title is 100 characters, please shorten your title";
			}
			return null;
		}
	}

	public final CommunityEraContextManager getContextHolder()
	{
		return contextManager;
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setItemFinder(ForumItemFinder itemFinder)
	{
		this.itemFinder = itemFinder;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}
}