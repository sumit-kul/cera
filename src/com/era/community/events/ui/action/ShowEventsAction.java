package com.era.community.events.ui.action;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.events.dao.Event;
import com.era.community.events.dao.EventCalendar;
import com.era.community.events.dao.EventCalendarFeature;
import com.era.community.events.dao.EventCalendarFinder;
import com.era.community.events.dao.EventFinder;
import com.era.community.events.ui.dto.EventDto;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/cid/[cec]/event/showEvents.do"
 */
public class ShowEventsAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	protected EventCalendarFinder eventCalFinder;
	protected EventFinder eventFinder;
	protected EventCalendarFeature eventCalFeature;
	protected SubscriptionFinder subscriptionFinder;

	protected ModelAndView handle(Object data) throws Exception
	{ 
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		int currentUserId = context.getCurrentUser() != null ? context.getCurrentUser().getId() : 0;

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		if (context.getRequest().getParameter("sortByOption") != null 
				&& !"".equals(context.getRequest().getParameter("sortByOption"))) {
			cmd.setSortByOption(context.getRequest().getParameter("sortByOption"));
		} else {
			cmd.setSortByOption("Upcoming events");
		}
		Community comm = contextManager.getContext().getCurrentCommunity();
		EventCalendar eventCal = (EventCalendar)eventCalFeature.getFeatureForCommunity(comm);
		cmd.setEventCalendarId(eventCal.getId());

		QueryScroller scroller = null;
		if (cmd.getSortByOption() == null || "".equals(cmd.getSortByOption()) || cmd.getSortByOption().equalsIgnoreCase("Upcoming events")) {
			scroller = eventCal.listFutureEvents(currentUserId);
		} else if (context.getCurrentUser() != null && cmd.getSortByOption().equalsIgnoreCase("My events")) {
			scroller = eventCal.listEventsForUserId( context.getCurrentUser().getId() );
		} else if (cmd.getSortByOption().equalsIgnoreCase("Past events")) {
			scroller = eventCal.listPastEvents(currentUserId);
		}  

		scroller.setBeanClass(RowBean.class);
		scroller.setPageSize(cmd.getPageSize());
		//pagination
		cmd.setPageCount(scroller.readPageCount());

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				int userId = context.getCurrentUser() == null ? 0 : context.getCurrentUser().getId();
				scroller.setPageSize(cmd.getPageSize());
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = page.toJsonString(pNumber, userId);
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
			cmd.setSearchType("Event");
			cmd.setQueryText(comm.getName());

			return new ModelAndView("event/showEvents");
		}
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{
		private String sortBy;
		private int[] selectedIds;
		private String sortByOption;
		private int eventCalendarId;

		public boolean isMember() throws Exception
		{
			if (contextManager.getContext().getCurrentUser() == null ) return false;
			if (contextManager.getContext().isUserAuthenticated() == false) return false;
			Community community = contextManager.getContext().getCurrentCommunity();
			if (community == null) return false;
			int currentUser = contextManager.getContext().getCurrentUser().getId();
			return community.isMember(currentUser);
		}

		public List getSortOptions() throws Exception
		{
			List sortOptionList = new ArrayList();
			sortOptionList.add("Upcoming events");
			sortOptionList.add("Past events");
			sortOptionList.add("My events");
			return sortOptionList;
		}

		public int[] getSelectedIds() {
			return selectedIds;
		}

		public void setSelectedIds(int[] selectedIds) {
			this.selectedIds = selectedIds;
		}

		public EventCalendar getEventCalendar() throws Exception
		{
			return (EventCalendar)eventCalFeature.getFeatureForCommunity(contextManager.getContext().getCurrentCommunity());
		}

		public boolean isCurrentUserSubscribed() throws Exception
		{
			try {
				if (contextManager.getContext().getCurrentUser() == null) return false;
				subscriptionFinder.getEventCalendarSubscriptionForUser(getEventCalendar().getId(), contextManager.getContext().getCurrentUser().getId());
				return true;
			}
			catch (ElementNotFoundException x) {
				return false;
			}
		}

		public String getSortBy() {
			return sortBy;
		}

		public void setSortBy(String sortBy) {
			this.sortBy = sortBy;
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

		public int getEventCalendarId() {
			return eventCalendarId;
		}

		public void setEventCalendarId(int eventCalendarId) {
			this.eventCalendarId = eventCalendarId;
		} 
	}

	public static class RowBean extends EventDto implements EntityWrapper
	{  
		private Event event;
		private int likeCount;
		private boolean alreadyLike;
		private boolean likeAllowed;
		private boolean host;
		private boolean invitee;

		public String getDateStarted() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd");   
			Date today = new Date();
			String sToday = fmter.format(today);
			try {
				Date date = fmter.parse(getStartDate());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date) + " at " + fmt.format(date);
			} catch (Exception e) {
				return "";
			}
		}

		public String getDateEnded() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd");      
			Date today = new Date();
			String sToday = fmter.format(today);
			try {
				Date date = fmter.parse(getEndDate());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date) + " at " + fmt.format(date);
			} catch (Exception e) {
				return "";
			}
		}

		public boolean isHost(int userID) throws Exception {
			if (userID == 0) return false;
			return this.getPosterId() == userID;
		}

		public boolean isInvitee(int userID) throws Exception {
			if (userID == 0) return false;
			return event.isInvitee(userID);
		}

		public boolean isReplied(int userID) throws Exception {
			if (userID == 0) return true;
			return event.isReplied(userID);
		}

		public boolean isAlreadyLike(int userID) throws Exception {
			if (userID == 0) return false;
			return event.isAlreadyLike(userID);
		}

		public boolean isLikeAllowed(int userID) throws Exception {
			if (userID == 0) return true;
			return event.getPosterId() != userID;
		}

		public int getLikeCount() {
			return likeCount;
		}

		public void setLikeCount(java.lang.Long likeCount) {
			this.likeCount = likeCount.intValue();
		}

		public void setEvent(Event event) {
			this.event = event;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
	public void setEventCalFeature(EventCalendarFeature eventCalFeature)
	{
		this.eventCalFeature = eventCalFeature;
	}
	public void setEventCalFinder(EventCalendarFinder eventCalFinder)
	{
		this.eventCalFinder = eventCalFinder;
	}
	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}
	public void setEventFinder(EventFinder eventFinder) {
		this.eventFinder = eventFinder;
	}

}
