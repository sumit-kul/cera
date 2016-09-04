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
import com.era.community.doclib.dao.Document;
import com.era.community.pers.dao.UserFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/common/similarProfiles.ajx"
 */
public class SimilarProfileAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected UserFinder userFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		HttpServletResponse resp = context.getResponse();
		//User user = context.getCurrentUser();

		JSONObject json = new JSONObject();
		JSONArray aData = new JSONArray();
		List medias = new ArrayList();
		if (cmd.getProfileId() != 0){
			medias = userFinder.searchSimilarProfiles(cmd.getProfileId(), 10);
			for (Iterator iterator = medias.iterator(); iterator.hasNext();) {
				Document document = (Document) iterator.next();
				aData.add(toJsonStringForMedia(document.getFileName(), document.getId()));
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

	private JSONObject toJsonStringForMedia(String name, int mediaId) throws Exception
	{
		JSONObject row = new JSONObject();
		row.put("name", name);
		row.put("mediaId", mediaId);
		return row;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int communityId;
		private int profileId;

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}

		public int getProfileId() {
			return profileId;
		}

		public void setProfileId(int profileId) {
			this.profileId = profileId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}
}