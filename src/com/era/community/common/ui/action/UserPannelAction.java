package com.era.community.common.ui.action;

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
import com.era.community.blog.dao.BlogAuthorFinder;
import com.era.community.communities.dao.CommunityJoiningRequestFinder;
import com.era.community.connections.communities.dao.MemberInvitationFinder;
import com.era.community.connections.communities.dao.MemberListFinder;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.ui.dto.UserDto;
import com.era.community.profile.dao.ProfileVisitFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/common/userPannel.ajx"
 */
public class UserPannelAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected BlogAuthorFinder blogAuthorFinder;
	protected MemberListFinder memberListFinder;
	protected CommunityJoiningRequestFinder joiningRequestFinder;
	protected MemberInvitationFinder memberInvitationFinder;
	protected ContactFinder contactFinder;
	protected ProfileVisitFinder profileVisitFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		HttpServletResponse resp = context.getResponse();
		//User user = context.getCurrentUser();

		JSONObject json = new JSONObject();
		JSONArray aData = new JSONArray();
		List profiles = new ArrayList();
		if (cmd.getProfileId() != 0 && "N".equals(cmd.getProfVisitors())) {
			profiles = contactFinder.listAllMyContacts(cmd.getProfileId(), 10);
		} else if (cmd.getProfileId() != 0 && "Y".equals(cmd.getProfVisitors()) && context.getCurrentUser() != null) {
			profiles = profileVisitFinder.getProfileVisitorsForUser(cmd.getProfileId(), context.getCurrentUser().getId(), 10);
		} else if (cmd.getBlogId() != 0) {
			profiles = blogAuthorFinder.getBlogAuthorsListForBlog(cmd.getBlogId(), 10);
		}  else if (cmd.getPersBlogId() != 0) {
			profiles = blogAuthorFinder.getPersonalBlogAuthorsListForBlog(cmd.getPersBlogId(), 10);
		} else if (cmd.getCommunityId() != 0 && "Y".equals(cmd.getMemRequest())){
			profiles = joiningRequestFinder.getPendingJoiningRequestForCommunity(cmd.getCommunityId(), 10);
		} else if (cmd.getCommunityId() != 0 && "Y".equals(cmd.getMemInvt())){
			profiles = memberInvitationFinder.getMemberInvitationsForCommunity(cmd.getCommunityId(), 10);
		} else if (cmd.getCommunityId() != 0){
			profiles = memberListFinder.getMemberListForCommunity(cmd.getCommunityId(), 10);
		} 

		if (cmd.getPymkId() > 0){
			List<UserDto> pymks = contactFinder.listPeopleYouMayKnowForHeader(cmd.getPymkId(), 5);
			for (UserDto usr : pymks) {
				aData.add(toJsonStringForPYMKs(usr.getFullName(), usr.getPhotoPresent(), usr.getId()));
			}
		} else {
			for (Iterator iterator = profiles.iterator(); iterator.hasNext();) {
				User user = (User) iterator.next();
				int phPresent = 0;
				if (user.isPhotoPresent()) {
					phPresent = 1;
				}
				aData.add(toJsonStringForUsers(user.getFullName(), phPresent, user.getId()));
			}
		}

		json.put("aData", aData);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	private JSONObject toJsonStringForUsers(String name, int photoPresant, int userId) throws Exception
	{
		JSONObject row = new JSONObject();
		row.put("name", name);
		row.put("photoPresant", photoPresant);
		row.put("userId", userId);
		return row;
	}
	
	private JSONObject toJsonStringForPYMKs(String name, boolean photoPresant, int userId) throws Exception
	{
		JSONObject row = new JSONObject();
		row.put("name", name);
		row.put("photoPresant", photoPresant);
		row.put("userId", userId);
		return row;
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

	public void setBlogAuthorFinder(BlogAuthorFinder blogAuthorFinder) {
		this.blogAuthorFinder = blogAuthorFinder;
	}

	public void setMemberListFinder(MemberListFinder memberListFinder) {
		this.memberListFinder = memberListFinder;
	}

	public void setJoiningRequestFinder(
			CommunityJoiningRequestFinder joiningRequestFinder) {
		this.joiningRequestFinder = joiningRequestFinder;
	}

	public void setMemberInvitationFinder(
			MemberInvitationFinder memberInvitationFinder) {
		this.memberInvitationFinder = memberInvitationFinder;
	}

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}

	public void setProfileVisitFinder(ProfileVisitFinder profileVisitFinder) {
		this.profileVisitFinder = profileVisitFinder;
	}
}