package com.era.community.wiki.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.UserFinder;
import com.era.community.wiki.dao.Wiki;
import com.era.community.wiki.dao.WikiFeature;
import com.era.community.wiki.ui.dto.WikiEntryDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/cid/[cec]/wiki/showWikiEntries.do" 
 */
public class ShowWikiEntriesAction extends AbstractCommandAction
{
	protected WikiFeature wikiFeature;
	protected UserFinder userFinder;
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		Wiki wiki = (Wiki) wikiFeature.getFeatureForCurrentCommunity();
		QueryScroller scroller = null;

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		if (context.getRequest().getParameter("sortByOption") != null 
				&& !"".equals(context.getRequest().getParameter("sortByOption"))) {
			cmd.setSortByOption(context.getRequest().getParameter("sortByOption"));
		}  else {
			cmd.setSortByOption("Most Recent");
		}

		if (cmd.getSortByOption() != null && cmd.getSortByOption().equals("Title")) {
			scroller = wiki.listEntriesByTitle();
		}
		/*else if (cmd. getOrder().equals("mywiki")) {
            CommunityEraContext context = contextManager.getContext();            
            scroller = wiki.listEntriesForUser( context.getCurrentUser() );
        }*/
		else {
			scroller = wiki.listEntriesByUpdateDate();                
		}

		scroller.setBeanClass(RowBean.class, this);
		scroller.setPageSize(cmd.getPageSize());
		//pagination
		cmd.setPageCount(scroller.readPageCount());

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(cmd.getPageSize());
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = page.toJsonString(pNumber);
			} else {
				json = new JSONObject();
				json.put("pageNumber", pNumber);
				JSONArray jData = new JSONArray();
				json.put("aData", jData);
			}
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
			return null;
		} else {
			scroller.setPageSize(cmd.getPageSize());
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			cmd.setSearchType("Wiki");
	        cmd.setQueryText(wiki.getName());
			return new ModelAndView("wiki/showWikiEntries");
		}
	} 

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{           
		private int resultSetIndex;
		private String sortByOption;

		public boolean isMember() throws Exception
		{
			if (contextManager.getContext().getCurrentUser() == null ) return false;
			if (contextManager.getContext().isUserAuthenticated() == false) return false;
			Community community = contextManager.getContext().getCurrentCommunity();
			if (community == null) return false;
			int currentUser = contextManager.getContext().getCurrentUser().getId();
			return community.isMember(currentUser);
		}

		public List getSortByOptionOptions() throws Exception
		{
			List sortByOptionList = new ArrayList();
			sortByOptionList.add("Most Recent");
			sortByOptionList.add("Title");
			return sortByOptionList;
		}


		public final int getResultSetIndex()
		{
			return resultSetIndex;
		}
		public final void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}


		public Wiki getWiki() throws Exception
		{
			return (Wiki)wikiFeature.getFeatureForCurrentCommunity();
		}

		public boolean isCurrentUserSubscribed() throws Exception
		{
			Wiki wiki = this.getWiki();
			if (contextManager.getContext().getCurrentUser() == null) return false;
			int userId = contextManager.getContext().getCurrentUser().getId();
			try {
				subscriptionFinder.getWikiSubscriptionForUser(wiki.getId(), userId);
				return true;
			}
			catch (ElementNotFoundException x) {
				return false;
			}
		}

		/**
		 * @return the sortByOption
		 */
		 public String getSortByOption() {
			return sortByOption;
		}


		/**
		 * @param sortByOption the sortByOption to set
		 */
		 public void setSortByOption(String sortByOption) {
			 this.sortByOption = sortByOption;
		 }
	} 

	public class RowBean extends WikiEntryDto
	{  
		private int resultSetIndex;    
		private boolean userCommunityAdmin;
		private boolean userSysAdmin;
		
		public boolean isAdsPosition()
		{
			return resultSetIndex%6==0;
		}

		public String getDisplayBody()
		{
			if ( (this.getBody()==null) || (this.getBody().length()==0)) return "";

			String sBody = this.getBody();

			sBody = sBody.replaceAll("<p>","");
			sBody = sBody.replaceAll("</p>"," ");
			sBody = sBody.replaceAll("   ", "");

			if (sBody.contains("<")) {
    			sBody = sBody.substring(0, sBody.indexOf("<"));
    			if(sBody.length() >= 300)sBody.substring(0, 300);
    				sBody.concat("...");
    		} else if (sBody.length() >= 300) {
    			sBody = sBody.substring(0, 300).concat("...");
    		}
    		return sBody;
		}

		public int getResultSetIndex()
		{
			return resultSetIndex; 
		}

		public void setResultSetIndex(int resultSetIndex) 
		{
			this.resultSetIndex = resultSetIndex;
		}

		public boolean isEvenRow()
		{
			return resultSetIndex%2==0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex%2==1;
		}

		public boolean isUserCommunityAdmin() throws Exception
		{
			if (contextManager.getContext().getCurrentUser() == null ) return false;
			return contextManager.getContext().isUserCommunityAdmin();
		}

		public boolean isUserSysAdmin() throws Exception
		{
			if (contextManager.getContext().getCurrentUser() == null ) return false;
			return contextManager.getContext().isUserSysAdmin();
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setWikiFeature(WikiFeature wikiFeature)
	{
		this.wikiFeature = wikiFeature;
	}

	public SubscriptionFinder getSubscriptionFinder()
	{
		return subscriptionFinder;
	}
	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}
}