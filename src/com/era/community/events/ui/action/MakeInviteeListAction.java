package com.era.community.events.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/event/makeInviteeList.ajx"
 */
public class MakeInviteeListAction extends AbstractCommandAction
{

	public static final String REQUIRES_AUTHENTICATION = "";

	protected CommunityEraContextManager contextManager;
	protected CommunityFinder communityFinder; 
	protected UserFinder userFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
        CommunityEraContext context = contextManager.getContext();
        
        HttpServletResponse resp = context.getResponse();
        User user = context.getCurrentUser();
        
        JSONObject json = new JSONObject();
        JSONArray aData = new JSONArray();
        JSONArray bData = new JSONArray();
		Community comm = communityFinder.getCommunityForId(cmd.getCommunityId());
		
		aData.add(toJsonStringForCommunities(comm.getName(), comm.getId(), comm.isLogoPresent()? 1 : 0, user.getId(), 1));
		
		if (comm != null && !"Private".equals(comm.getCommunityType()) && user != null) {
			List cList = communityFinder.getActiveOtherCommunitiesForMember(user, comm.getId());
			for (Iterator iterator = cList.iterator(); iterator.hasNext();) {
				Community community = (Community) iterator.next();
				aData.add(toJsonStringForCommunities(community.getName(), community.getId(), community.isLogoPresent()? 1 : 0, user.getId(), 0));
			}
		}

		List invities = new ArrayList();;
		if (comm != null) {
			invities = userFinder.getInviteeListForEvent(comm.getId(), user.getId(), cmd.getEventId(), 0);
		}

		for (Iterator iterator = invities.iterator(); iterator.hasNext();) {
			User inv = (User) iterator.next();
			int phPresent = 0;
			if (inv.isPhotoPresent()) {
				phPresent = 1;
			}
			bData.add(toJsonStringForUsers(inv.getFullName(), phPresent, inv.getId(), "", 1));
		}
		
		json.put("aData", aData);
		json.put("bData", bData);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}
	
	private JSONObject toJsonStringForCommunities(String commName, int commId, int photoPresant, int userId, int selected) throws Exception
    {
    	JSONObject row = new JSONObject();
    	row.put("commName", commName);
    	row.put("commId", commId);
    	row.put("photoPresant", photoPresant);
    	row.put("userId", userId);
    	row.put("selected", selected);
    	return row;
    }
	
	private JSONObject toJsonStringForUsers(String name, int photoPresant, int userId, String location, int selected) throws Exception
    {
    	JSONObject row = new JSONObject();
    	row.put("name", name);
    	row.put("photoPresant", photoPresant);
    	row.put("userId", userId);
    	row.put("location", location);
    	row.put("selected", selected);
    	return row;
    }

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int userId;
		private int communityId;
		private int eventId;

		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public int getCommunityId() {
			return communityId;
		}
		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
		public int getEventId() {
			return eventId;
		}
		public void setEventId(int eventId) {
			this.eventId = eventId;
		}
	}

	public void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}
}