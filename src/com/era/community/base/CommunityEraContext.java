package com.era.community.base;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.util.UrlPathHelper;

import support.community.application.BacklinkStack;
import support.community.application.ElementNotFoundException;
import support.community.framework.AppRequestContext;
import support.community.util.StringFormat;

import com.era.community.admin.dao.BusinessParam;
import com.era.community.admin.dao.BusinessParamFinder;
import com.era.community.blog.ui.dto.BlogEntryPannelDto;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.ui.dto.CommunityEntryPannelDto;
import com.era.community.events.ui.dto.EventPannelDto;
import com.era.community.forum.ui.dto.ForumTopicPannelDto;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;
import com.era.community.wiki.ui.dto.WikiEntryPannelDto;

public class CommunityEraContext
{
	protected Log logger = LogFactory.getLog(getClass());

	private AppRequestContext requestContext; 
	protected UserFinder userFinder;    
	protected CommunityFinder communityFinder;
	protected BusinessParamFinder businessParamFinder;

	private LinkBuilderContext linkBuilder;
	private Community currentCommunity;
	private SortedMap<String, CommunityFeature> featureSet;
	private List<CommunityEntryPannelDto>  mostActiveCommunities;
	private List<CommunityEntryPannelDto>  mostViewedCommunities;
	private List<CommunityEntryPannelDto>  mostRecentCommunities;
	private List<BlogEntryPannelDto>  topBlogEntries;
	private List<ForumTopicPannelDto>  topForumTopics;
	private List<WikiEntryPannelDto>  topWikiEntries;
	private List<EventPannelDto>  topUpcomingEvents;	
	private int communityCountSoFar;
	private int userCountSoFar;
	private long mostActiveRefreshTime;
	private long mostViewedRefreshTime;
	private long mostRecentRefreshTime;
	private long topBlogRefreshTime;
	private long topForumRefreshTime;
	private long topWikiRefreshTime;
	private long topEventsRefreshTime;
	private long communityCountRefreshTime;
	private long userCountRefreshTime;
	private String h2Header;
	private DashBoardAlert alert;
	private boolean production;

	public String getH2Header()
	{
		return h2Header;
	}

	public void setH2Header(String header)
	{
		h2Header = header;
	}

	public void setSessionCookie(String name, String value)
	{
		String path = "/"; 
		String domain = getDefaultCookieDomain(); 
		Cookie ck = new Cookie(name, value);
		ck.setDomain(domain);
		ck.setPath(path);
		ck.setMaxAge(-1); 
		getResponse().addCookie(ck);
	}

	public String getCookieValue(String name) throws Exception
	{
		Cookie[] cookies = getRequest().getCookies();
		if (cookies == null)  return null;
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(name))
				return StringFormat.unescape(cookies[i].getValue());
		}
		return null;
	}

	public void clearCookie(String name) 
	{
		String path = "/"; 
		String domain = getDefaultCookieDomain(); 
		Cookie cookie = new Cookie(name, null);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setMaxAge(0);
		getResponse().addCookie(cookie);
	}

	private String getDefaultCookieDomain()
	{
		String[] a = getRequest().getServerName().split("\\.");
		int x = a.length;
		if (a[x-1].length()==2 && x>2) 
			return a[x-3]+"."+a[x-2]+"."+a[x-1];
		return a[x-2]+"."+a[x-1];
	}   

	@SuppressWarnings("unchecked")
	CommunityEraContext(AppRequestContext requestContext, ApplicationContext springContext, CommunityFinder communityFinder, UserFinder finder) throws Exception
	{
		this.userFinder = finder;
		this.communityFinder = communityFinder;
		this.requestContext = requestContext;
		this.linkBuilder = new LinkBuilderContext();
		featureSet = new TreeMap<String, CommunityFeature>();
		featureSet.putAll(springContext.getBeansOfType(CommunityFeature.class));
	}

	public String getTextParameter(String name) throws Exception
	{
		try {
			BusinessParam p= businessParamFinder.getParamForCategoryAndName(BusinessParam.CATEGORY_STATIC_TEXT, name);
			return p.getValue();
		}
		catch (ElementNotFoundException x) {
			return null;
		}
	}

	public final void setBusinessParamFinder(BusinessParamFinder businessParamFinder)
	{
		this.businessParamFinder = businessParamFinder;
	}

	void init(HttpServletRequest req) throws Exception
	{    
		UrlPathHelper uph = new UrlPathHelper();
		logger.debug(uph.getPathWithinApplication(req));
		try {

			String p = uph.getPathWithinApplication(req);
			if (p!=null && p.length()>1 && (p.startsWith("/cid/"))) {
				String copId = "";
				int id = 0;
				boolean selected = false;
				copId = p.substring(5, p.indexOf('/', 5));
				id = Integer.parseInt(copId);
				selected = p.endsWith("cid/"+copId+"/home.do");
				// Get the community 
				currentCommunity= communityFinder.getCommunityForId(id);
				if(currentCommunity != null){
					Iterator i = getFeatures().iterator();
					while (i.hasNext()) {
						CommunityFeature f = (CommunityFeature)i.next();              
						if (!f.isFeatureEnabledForCommunity(currentCommunity)) continue;
						String uri = f.getFeatureUri();
						if (uri != null ) {
							selected = p.indexOf(uri) > -1;

							if ( this.currentCommunity.isProtected() &&  uri.equals("/members") )    
								continue;
							String t = f.getFeatureTitle();
							String l = f.getFeatureLabel();
						}
					}

					if (this.isUserCommunityAdmin()) {
						selected = p.contains("cid/"+copId+ "/admin") ;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized List getMostActiveCommunities() throws Exception
	{
		if (mostActiveCommunities == null || System.currentTimeMillis()- mostActiveRefreshTime > 1000*60*60) {
			mostActiveCommunities = communityFinder.getMostActiveCommunities(10);
			mostActiveRefreshTime = System.currentTimeMillis();
		}
		return mostActiveCommunities;
	}
	
	public synchronized List getMostViewedCommunities() throws Exception
	{
		if (mostViewedCommunities == null || System.currentTimeMillis()- mostViewedRefreshTime > 1000*60*60) {
			mostViewedCommunities = communityFinder.getMostViewedCommunities(10);
			mostViewedRefreshTime = System.currentTimeMillis();
		}
		return mostViewedCommunities;
	}
	
	public synchronized List getMostRecentCommunities() throws Exception
	{
		if (mostRecentCommunities == null || System.currentTimeMillis()- mostRecentRefreshTime > 1000*60*60) {
			mostRecentCommunities = communityFinder.getMostRecentCommunities(10);
			mostRecentRefreshTime = System.currentTimeMillis();
		}
		return mostRecentCommunities;
	}
	
	public synchronized List getTopBlogPosts() throws Exception
	{
		if (topBlogEntries == null || System.currentTimeMillis()- topBlogRefreshTime > 1000*60*60) {
			topBlogEntries = communityFinder.listAllTopStories(10);
			topBlogRefreshTime = System.currentTimeMillis();
		}
		return topBlogEntries;
	}
	
	public synchronized List getTopForumTopic() throws Exception
	{
		if (topForumTopics == null || System.currentTimeMillis()- topForumRefreshTime > 1000*60*60) {
			topForumTopics = communityFinder.listAllTopTopics(4);
			topForumRefreshTime = System.currentTimeMillis();
		}
		return topForumTopics;
	}
	
	public synchronized List getTopWikiEntries() throws Exception
	{
		if (topWikiEntries == null || System.currentTimeMillis()- topWikiRefreshTime > 1000*60*60) {
			topWikiEntries = communityFinder.listAllWikiEntries(10);
			topWikiRefreshTime = System.currentTimeMillis();
		}
		return topWikiEntries;
	}
	
	public synchronized List getTopGlobalEvents() throws Exception
	{
		if (topUpcomingEvents == null || System.currentTimeMillis()- topEventsRefreshTime > 1000*60*60) {
			topUpcomingEvents = communityFinder.getUpcomingEvents(10);
			topEventsRefreshTime = System.currentTimeMillis();
		}
		return topUpcomingEvents;
	}
	
	public synchronized int getCommunityCountSoFar() throws Exception
	{
		if (communityCountSoFar == 0 || System.currentTimeMillis()- communityCountRefreshTime > 1000*60*10) {
			communityCountSoFar = communityFinder.getCommunityCountSoFar();
			communityCountRefreshTime = System.currentTimeMillis();
		}
		return communityCountSoFar;
	}
	
	public synchronized int getUserCountSoFar() throws Exception
	{
		if (userCountSoFar == 0 || System.currentTimeMillis()- userCountRefreshTime > 1000*60*10) {
			userCountSoFar = userFinder.getUserValidatedCount();
			userCountRefreshTime = System.currentTimeMillis();
		}
		return userCountSoFar;
	}

	public boolean isUserCommunityAdmin() throws Exception
	{
		User user = getCurrentUser();
		if (user==null) return false;
		if (requestContext.isCurrentUserInRole(UserRoleConstants.ROLE_SUPER_ADMIN)) return true; 
		Community comm = getCurrentCommunity();
		if (comm==null) return false;
		return comm.isAdminMember(user); 
	}

	public boolean isCommunityOwner() throws Exception
	{
		User user = getCurrentUser();
		if (user==null) return false;
		if (requestContext.isCurrentUserInRole(UserRoleConstants.ROLE_SUPER_ADMIN)) return true; 
		Community comm = getCurrentCommunity();
		if (comm==null) return false;
		return comm.isCommunityOwner(user);
	}

	public Collection getFeatures()
	{
		return featureSet.values();
	}

	public String[] getEnabledFeatureNamesForCurrentCommunity() throws Exception
	{
		Object[] list = new String[0];
		Community comm = getCurrentCommunity();
		Iterator i = featureSet.values().iterator();
		while (i.hasNext()) {
			CommunityFeature f = (CommunityFeature)i.next();
			if (f.isFeatureEnabledForCommunity(comm)) list = ArrayUtils.add(list, f.getFeatureName());
		}       
		return (String[])list;
	}

	public String[] getEnabledFeatureNamesForCommunity(Community comm) throws Exception
	{
		Object[] list = new String[0];

		Iterator i = featureSet.values().iterator();
		while (i.hasNext()) {
			CommunityFeature f = (CommunityFeature)i.next();
			if (f.isFeatureEnabledForCommunity(comm)) list = ArrayUtils.add(list, f.getFeatureName());
		}       
		return (String[])list;
	}

	public Collection getLinksForCurrentCommunity()
	{
		return featureSet.values();
	}

	public User getCurrentUser() throws Exception
	{
		UserDetails user = requestContext.getCurrentUserDetails();
		if (!(user instanceof UserEntity)) return null;
		return userFinder.getUserEntity(((UserEntity)user).getId());
	}    

	public LinkBuilderContext getLinkBuilder()
	{
		return linkBuilder;
	}

	public final Community getCurrentCommunity()
	{
		return currentCommunity;
	}

	public String getCurrentCommunityUrl() 
	{
		return  requestContext.getContextUrl()+ "/cid/" + currentCommunity.Id;
	}

	public String getCurrentCommunityHomeUrl()
	{
		return getCurrentCommunityUrl()+"/home.do";
	}

	public String getCurrentCommunityName() 
	{
		return  getCurrentCommunity().getName();
	}

	public String getContextPath()
	{
		return requestContext.getContextPath();
	}
	
	public String getContextUrl()
	{
		return requestContext.getContextUrl();
	}
	
	public String getCurrentUrl() throws Exception
	{
		return requestContext.getFullRequestUrl();
	}
	
	public HttpServletRequest getRequest()
	{
		return requestContext.getRequest();
	}
	
	public String getRequestUri()
	{
		return requestContext.getRequestUri();
	}
	
	public String getRequestUrl() throws JspException
	{
		return requestContext.getFullRequestUrl();
	}
	
	public HttpServletResponse getResponse()
	{
		return requestContext.getResponse();
	}
	
	public String getRootUrl()
	{
		return requestContext.getRootUrl();
	}
	
	public boolean isCurrentUserInRole(String role)
	{
		return requestContext.isCurrentUserInRole(role);
	}
	
	public boolean isUserAuthenticated() throws Exception
	{
		return requestContext.isUserAuthenticated();
	}
	public boolean isUserSysAdmin() throws Exception
	{
		return requestContext.isUserSysAdmin();
	}

	public boolean isUserSuperAdmin() throws Exception
	{
		return requestContext.isUserSuperAdmin();
	}
	
	public UserDetails getCurrentUserDetails() throws Exception
	{
		return requestContext.getCurrentUserDetails();
	}
	
	public void exposeInView(String name, Object object)
	{
		requestContext.exposeInView(name, object);
	}
	
	public String getBacklink() throws Exception
	{
		return requestContext.getBacklink();
	}
	
	public BacklinkStack getBacklinkStack()
	{
		return requestContext.getBacklinkStack();
	}
	
	public void setBacklinkStack(BacklinkStack backlinks)
	{
		requestContext.setBacklinkStack(backlinks);
	}

	public DashBoardAlert getAlert() {
		return alert;
	}

	public void setAlert(DashBoardAlert alert) {
		this.alert = alert;
	}

	public boolean isProduction() {
		return production;
	}

	public void setProduction(boolean production) {
		this.production = production;
	}
}