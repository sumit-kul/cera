package com.era.community.forum.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.framework.IndexCommandBean;
import support.community.util.StringFormat;
import support.community.util.StringHelper;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.LinkBuilderContext;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.base.Taggable;
import com.era.community.communities.dao.Community;
import com.era.community.forum.dao.Forum;
import com.era.community.forum.dao.ForumFeature;
import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumItemFinder;
import com.era.community.forum.dao.ForumItemLikeFinder;
import com.era.community.forum.dao.ForumResponse;
import com.era.community.forum.dao.ForumTopic;
import com.era.community.forum.dao.generated.ForumItemDaoBase;
import com.era.community.forum.ui.dto.ForumItemDto;
import com.era.community.forum.ui.validator.ForumItemValidator;
import com.era.community.monitor.dao.Subscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.Tag;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.dao.generated.TagEntity;
import com.era.community.tagging.ui.dto.TagDto;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/cid/[cec]/forum/forumThread.do"
 */
public class ForumThreadAction extends AbstractFormAction
{
	public static final String REQUIRES_AUTHENTICATION = "";

	protected ForumFeature forumFeature;
	protected ForumItemFinder forumItemFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected UserFinder userFinder;
	protected TagFinder tagFinder;
	protected ForumItemDaoBase dao;
	protected ForumItemLikeFinder forumItemLikeFinder;
	protected RunAsAsyncThread taskExecutor;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;

	protected CommunityEraContextManager contextManager;

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	protected Map referenceData(Object data) throws Exception
	{
		Command cmd = (Command) data;
		ForumItem topic = forumItemFinder.getForumItemForId(cmd.getId());
		User user = userFinder.getUserEntity(topic.getAuthorId());
		cmd.setPhotoPresent(user.isPhotoPresent()? "Y" : "N");
		cmd.setAuthorName(user.getFullName());
		processData(topic, cmd); 
		getThreadItems(cmd, topic);
		if (cmd.getResponseCount() > 0) {
			ForumResponse latestPost = forumItemFinder.getLatestPost(topic);
			if (latestPost != null) {
				User latestPoster = userFinder.getUserEntity(latestPost.getAuthorId());
				cmd.setLastPosterId(latestPoster.getId());
				cmd.setLastPosterName(latestPoster.getFullName());
				cmd.setLastPostPhotoPresent(latestPoster.isPhotoPresent()? "Y" : "N");
				cmd.setLatestPostDate(latestPost.getDatePosted());
			}
		}
		cmd.setMetaForDescription(cmd.getBody());
		cmd.setSearchType("Forum");
		cmd.setQueryText(topic.getSubject());
		return new HashMap();
	}

	private void processData(ForumItem topic, Command cmd) throws Exception {
		CommunityEraContext context = contextManager.getContext();
		cmd.setTopicId(topic.getId());
		cmd.setTopicSubject(topic.getSubject());
		cmd.copyPropertiesFrom(topic);
		cmd.setAuthorName(topic.getAuthor().getFullName());
		cmd.setDatePosted(topic.getDatePosted());


		if (context.getCurrentUser() == null || context.getCurrentUser().getId() != topic.getAuthorId()) {
			HttpServletRequest request = contextManager.getContext().getRequest(); 
			Object sessionId = request.getSession().getAttribute("ForumTopic-"+topic.getId());
			if (sessionId == null) {
				request.getSession().setAttribute("ForumTopic-"+topic.getId(), topic.getId());
				int visitors = topic.getVisitors()+1;
				topic.setVisitors(visitors);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String dt = sdf.format(now);
				Timestamp ts = Timestamp.valueOf(dt);
				topic.setLastVisitorsTime(ts);
				dao.store(topic, true);
			}
		}

		int likeCount = forumItemLikeFinder.getLikeCountForForumItem(topic.getId());
		cmd.setLikeCount(likeCount);
		cmd.setAlreadyLike(isAlreadyLike(topic));
		cmd.setLikeAllowed(isLikeAllowed(topic));
		cmd.setVisitCount(topic.getVisitors());
		cmd.setLastVisitTime(topic.getLastVisitorsTime());

		String copurl = context.getCurrentCommunityUrl() + "/forum/";
		LinkBuilderContext linkBuilder = context.getLinkBuilder();

		if (context.getCurrentUser() != null && (context.getCurrentUser().getId() == topic.getAuthorId() || context.isUserCommunityAdmin())) {
			if (!topic.isSticky() && !topic.isClosed()) {

				linkBuilder.addOptionsLink("<i class='fa fa-thumb-tack' style='margin: 0px 4px; font-size: 14px;'></i>Pin this topic", copurl+"topicPinned.do?backlink=ref&amp;id="+ topic.getId(),  "Pinned topics always appear first");
			}
			if (topic.isSticky()) {
				linkBuilder.addOptionsLink("<i class='fa fa-thumb-tack rotateCss' style='margin: 0px 4px; font-size: 14px;'></i>Unpin this topic", copurl+"topicUnpinned.do?backlink=ref&amp;id="+ topic.getId(), "Unpin this topic");
			}
			if (!topic.isClosed()) {
				linkBuilder.addOptionsLink("<i class='fa fa-lock' style='margin: 0px 4px; font-size: 14px;'></i>Close this topic", copurl+"topicClose.do?backlink=ref&amp;id="+ topic.getId(), "Close this topic for new replies");
			}

			if (topic.isClosed()) {
				linkBuilder.addOptionsLink("<i class='fa fa-unlock' style='margin: 0px 4px; font-size: 14px;'></i>Re-open this topic", copurl + "topicReopen.do?backlink=ref&amp;id="+ topic.getId(), "Re-Open this topic for replies");
			}
		}
	}

	private boolean isAlreadyLike(ForumItem entry) throws Exception {
		if (contextManager.getContext().getCurrentUser() == null ) return false;
		if (contextManager.getContext().isUserAuthenticated() == false) return false;
		int currentUserId = contextManager.getContext().getCurrentUser().getId();
		return entry.isAlreadyLike(currentUserId);
	}

	private boolean isLikeAllowed(ForumItem entry) throws Exception {
		if (contextManager.getContext().getCurrentUser() == null ) return true;
		if (contextManager.getContext().isUserAuthenticated() == false) return true;
		int currentUserId = contextManager.getContext().getCurrentUser().getId();
		return entry.getAuthorId() != currentUserId;
	}

	protected String getView()
	{
		return "forum/forumThread";
	}

	protected void onDisplay(Object data) throws Exception
	{
	}

	private void getThreadItems(Command bean, ForumItem item) throws Exception
	{
		List items = forumItemFinder.getItemBeans(bean.getId());
		bean.setItems(items);
		bean.setResponseCount(Long.valueOf(bean.getItems().size()));
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
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()), "command", cmd);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);

		ForumResponse response = null;
		try {
			Forum forum = (Forum) forumFeature.getFeatureForCurrentCommunity();
			response = forum.newResponse();
			response.setSubject(cmd.getSubject());
			response.setBody(cmd.getBody());
			response.setTopicId(cmd.getTopicId());
			response.setParentId(cmd.getParentId());
			response.setDatePosted(ts);
			response.update();
		} catch (Exception e){
			e.printStackTrace();
		}

		Community comm = context.getCurrentCommunity();
		comm.setCommunityUpdated(ts);
		comm.update();

		if (!(cmd.getParentId() == null || cmd.getTopicId() == null)) {
			forumItemFinder.updateForhierarchy(cmd.getParentId(), cmd.getTopicId(), response.getId(), cmd.getParentId() == cmd.getTopicId() ? 1 : 0);
		} 

		if (cmd.getTags() != null) {

			if (!"".equals(cmd.getTags()))
				tagFinder.clearTagsForParentIdUserId(cmd.getTopicId(), context.getCurrentUser().getId());    

			StringTokenizer st = new StringTokenizer(cmd.getTags(), " ");
			while (st.hasMoreTokens()) {
				String tag = st.nextToken().trim().toLowerCase();
				Tag newTag = tagFinder.newTag();
				newTag.setCommunityId(context.getCurrentCommunity().getId());
				newTag.setTagText(tag);         
				newTag.setPosterId(context.getCurrentUser().getId());
				newTag.setParentId(cmd.getTopicId());
				newTag.setParentType("ForumItem");
				newTag.update();            
			} 
		}

		int returnId = forumItemFinder.getLastSiblingId(cmd.getParentId(), cmd.getTopicId(), response.getId());
		if (returnId == 0) {
			returnId = cmd.getParentId();
		} 

		int depth = forumItemFinder.getDepthForItem(response.getId());
		ForumItem parent = forumItemFinder.getForumItemForId(cmd.getParentId());

		runInBackground(response, context.getCurrentUser(), context);

		HttpServletResponse resp = contextManager.getContext().getResponse();
		JSONObject json = null;
		json = new JSONObject();
		json.put("lastSiblingId", returnId);
		json.put("photoPresent", context.getCurrentUser().isPhotoPresent());
		json.put("authorId", response.getAuthorId());
		json.put("responseId", response.getId());
		json.put("subject", response.getSubject());
		json.put("authorName", context.getCurrentUser().getFullName());
		json.put("postedOn", getPostedOn(response.getDatePosted().toString()));
		json.put("postedOnHover", getPostedOnHover(response.getDatePosted().toString()));
		json.put("likeCount", response.getLikeCountForForumItem());
		json.put("likeAllowed", isLikeAllowed(response));
		json.put("alreadyLike", isAlreadyLike(response));
		json.put("parentId", response.getParentId());
		json.put("parentAuthorId", parent.getAuthorId());
		json.put("parentAuthorPhotoPresent", parent.getAuthor().isPhotoPresent());
		json.put("parentAuthorName", parent.getAuthor().getFullName());
		json.put("parentPostDateOnHover", getPostedOnHover(parent.getDatePosted().toString()));
		json.put("parentPostDateON", getPostedOn(parent.getDatePosted().toString()));
		json.put("body", response.getBody());
		json.put("depth", depth);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	private void runInBackground(final ForumResponse response, final User currentUser, final CommunityEraContext context){
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					ForumTopic topic = response.getTopic();
					Iterator it = subscriptionFinder.getSubscriptionsForThread(response.getTopicId().intValue()).iterator();
					while (it.hasNext()) {
						Subscription sub = (Subscription) it.next();
						if (sub.getFrequency() == 0) { //Only immediate alerts
							mailSubscriber(sub, response, topic, currentUser, context);
						}
					}

					it = subscriptionFinder.getSubscriptionsForForum(response.getForumId()).iterator();

					while (it.hasNext()) {

						Subscription sub = (Subscription) it.next();
						if (sub.getFrequency() == 0) { //Only immediate alerts 
							mailSubscriber(sub, response, topic, currentUser, context);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void mailSubscriber(Subscription sub, ForumResponse response, ForumTopic topic, User currentUser, CommunityEraContext context)
	throws Exception
	{
		User user = userFinder.getUserEntity(sub.getUserId());

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(user.getEmailAddress());
		helper.setFrom(new InternetAddress("support@jhapak.com ") );
		helper.setSubject(context.getCurrentCommunity().getName()+" - New thread response ("+topic.getSubject()+")");
		helper.setSentDate(new Date());

		String sLink = context.getCurrentCommunityUrl() + "/forum/forumThread.do?id="+ response.getTopicId();

		Map model = new HashMap();                
		model.put("topicSubject", topic.getSubject());
		model.put("threadLink", sLink);
		model.put("communityName", context.getCurrentCommunity().getName());
		model.put("userName", user.getFullName());

		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, "main/resources/velocity/ThreadResponseAlert.vm", "UTF-8", model);
		helper.setText(text, true);
		try {
			if (user.getId() != currentUser.getId() && !user.isSuperAdministrator()) mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getPostedOn(String datePosted) throws Exception
	{
		SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
		SimpleDateFormat fmt2 = new SimpleDateFormat("dd MMM, yy");
		Date today = new Date();
		String sToday = fmter.format(today);

		try {

			Date date = formatter.parse(datePosted);
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);

		} catch (ParseException e) {
			return datePosted;
		}
	}

	public String getPostedOnHover(String datePosted) throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
		try {
			Date date = formatter.parse(datePosted);
			return fmt2.format(date);
		} catch (ParseException e) {
			return datePosted;
		}
	}

	public class Command extends ForumItemDto implements CommandBean, Taggable
	{
		private String tags;        
		private List m_items;
		private int likeCount;
		private int visitCount;
		private String lastVisitTime;
		private boolean alreadyLike;
		private boolean likeAllowed;
		private ForumItemDto fTopic;
		private int isRoot;
		
		private String keywords;
		private String metaForDescription;
		
		public String getDisplayBody()
		{
			if ( (this.getBody()==null) || (this.getBody().length()==0)) return "";

			String sBody = this.getBody();
			//sBody = Jsoup.parse(sBody).text();
			//sBody = StringHelper.escapeHTML(sBody);
			return sBody;
		}

		public String getTaggedKeywords(){
			String taggedKeywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(this.getId(), 0, 20, "ForumTopic");
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='"+contextManager.getContext().getCurrentCommunityUrl()+"/search/searchByTagInCommunity.do?filterTag="+tag+"' class='euInfoSelect normalTip' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += ", ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

		public String getLastPostDate() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			try {

				Date date = formatter.parse(getLatestPostDate());
				return fmt2.format(date);

			} catch (Exception e) {
				return getLatestPostDate();
			}
		}

		public boolean isCurrentUserSubscribed() throws Exception
		{
			try {
				if (contextManager.getContext().getCurrentUser() == null) return false;
				subscriptionFinder.getThreadSubscriptionForUser(this.getTopicId(), contextManager.getContext().getCurrentUser().getId());
				return true;
			}
			catch (ElementNotFoundException x) {
				return false;
			}
		}

		public String getTags()
		{
			return tags;
		}

		public void setTags(String tags)
		{
			this.tags = tags;
		}

		public TreeMap getPopularTags()
		{
			return null;
		}

		public List getItems()
		{
			return m_items;
		}

		public void setItems(List items)
		{
			m_items = items;
		}

		public int getLikeCount() {
			return likeCount;
		}

		public void setLikeCount(int likeCount) {
			this.likeCount = likeCount;
		}

		public int getVisitCount() {
			return visitCount;
		}

		public void setVisitCount(int visitCount) {
			this.visitCount = visitCount;
		}

		public String getLastVisitTime() {
			return lastVisitTime;
		}

		public String getPostedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yy");
			Date today = new Date();
			String sToday = fmter.format(today);
			try {
				Date date = formatter.parse(getDatePosted());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);
			} catch (Exception e) {
				return getCreated();
			}
		}

		public String getPostedOnHover() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			try {
				
				Date date = formatter.parse(getDatePosted());
				return fmt2.format(date);
			} catch (Exception e) {
				return getCreated();
			}
		}

		public void setLastVisitTime(Date lastVisitorsTime) throws Exception {
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			Date date = lastVisitorsTime != null ? lastVisitorsTime : fmter.parse(getLastVisitTime());
			if (fmter.format(date).equals(sToday)) {
				lastVisitTime =  "Today " + fmt.format(date);
			}
			lastVisitTime =  fmt2.format(date);
		}

		public boolean isAlreadyLike() {
			return alreadyLike;
		}

		public void setAlreadyLike(boolean alreadyLike) {
			this.alreadyLike = alreadyLike;
		}

		public boolean isLikeAllowed() {
			return likeAllowed;
		}

		public void setLikeAllowed(boolean likeAllowed) {
			this.likeAllowed = likeAllowed;
		}

		public ForumItemDto getFTopic() {
			return fTopic;
		}

		public void setFTopic(ForumItemDto topic) {
			fTopic = topic;
		}

		public int getIsRoot() {
			return isRoot;
		}

		public void setIsRoot(int isRoot) {
			this.isRoot = isRoot;
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

		public String getMetaForDescription() {
			return metaForDescription;
		}

		public void setMetaForDescription(String metaForDescription) {
			this.metaForDescription = StringHelper.escapeHTML(metaForDescription);
		}
	}

	public class Validator extends ForumItemValidator
	{
		public String validateSubject(Object value, CommandBean cmd)
		{
			if (value.toString().length() > 200) {
				return "The maximum length of the title is 200 characters, please shorten your title";
			}
			return null;
		}
	}

	public void setFeature(ForumFeature feature)
	{
		this.forumFeature = feature;
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setForumFeature(ForumFeature forumFeature)
	{
		this.forumFeature = forumFeature;
	}

	protected String getView(IndexCommandBean bean) throws Exception
	{
		return null;
	}

	public void setForumItemFinder(ForumItemFinder forumItemFinder)
	{
		this.forumItemFinder = forumItemFinder;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setTagFinder(TagFinder tagFinder)
	{
		this.tagFinder = tagFinder;
	}

	public void setDao(ForumItemDaoBase dao) {
		this.dao = dao;
	}

	public void setForumItemLikeFinder(ForumItemLikeFinder forumItemLikeFinder) {
		this.forumItemLikeFinder = forumItemLikeFinder;
	}

	public void setTaskExecutor(RunAsAsyncThread taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}
}