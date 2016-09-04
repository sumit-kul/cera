package com.era.community.pers.ui.action;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.events.ui.dto.EventPannelDto;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/pers/eventPannel.ajx"
 */
public class EventPannelAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = context.getRequest().getParameter("start");
		String enddate = context.getRequest().getParameter("end");
		
		
		Date start = formatter.parse(startDate);
		Date end = formatter.parse(enddate);

		HttpServletResponse resp = context.getResponse();
		User user = context.getCurrentUser();

		JSONArray json = new JSONArray();
		
		List<EventPannelDto> events = user.listEventsForUser(start, end);
		toJsonStringForUsers(json, events, context);

		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	private void toJsonStringForUsers(JSONArray json, List<EventPannelDto> events, CommunityEraContext context) throws Exception
	{
		for (EventPannelDto evnt : events) {
			JSONObject row = new JSONObject();
			row.put("id", evnt.getEntryId());
			row.put("title", evnt.getName());
			row.put("start", evnt.getStartDate());
			row.put("end", evnt.getEndDate());
			if ("Event".equalsIgnoreCase(evnt.getType())) {
				row.put("url", context.getContextUrl()+"/cid/"+evnt.getCommunityID()+"/event/showEventEntry.do?id="+evnt.getEntryId());
			} else {
				row.put("url", context.getContextUrl()+"/cid/"+evnt.getCommunityID()+"/assignment/showAssignment.do?id="+evnt.getEntryId());
			}
			json.add(row);
		}
	}
	
	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int blogId;
		private int persBlogId;
		private int communityId;
		private int profileId;
		private int pymkId;
		private String memRequest = "N";
		private String memInvt = "N";
		private String profVisitors = "N";

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}

		public String getMemRequest() {
			return memRequest;
		}

		public void setMemRequest(String memRequest) {
			this.memRequest = memRequest;
		}

		public String getMemInvt() {
			return memInvt;
		}

		public void setMemInvt(String memInvt) {
			this.memInvt = memInvt;
		}

		public int getProfileId() {
			return profileId;
		}

		public void setProfileId(int profileId) {
			this.profileId = profileId;
		}

		public int getPersBlogId() {
			return persBlogId;
		}

		public void setPersBlogId(int persBlogId) {
			this.persBlogId = persBlogId;
		}

		public int getBlogId() {
			return blogId;
		}

		public void setBlogId(int blogId) {
			this.blogId = blogId;
		}

		public String getProfVisitors() {
			return profVisitors;
		}

		public void setProfVisitors(String profVisitors) {
			this.profVisitors = profVisitors;
		}

		public int getPymkId() {
			return pymkId;
		}

		public void setPymkId(int pymkId) {
			this.pymkId = pymkId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
}