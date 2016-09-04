package com.era.community.forum.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.forum.dao.ForumItemFinder;
import com.era.community.forum.ui.dto.ForumTopicPannelDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/forum/forumPannel.ajx"
 */
public class ForumPannelAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected ForumItemFinder forumItemFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		HttpServletResponse resp = context.getResponse();

		JSONObject json = new JSONObject();
		JSONArray aData = new JSONArray();
		List<ForumTopicPannelDto> topics = new ArrayList<ForumTopicPannelDto>();
		if ("hot".equalsIgnoreCase(cmd.getType())){
			topics = forumItemFinder.listHotTopics(20);
		} else {
			topics = forumItemFinder.listHotTopics(20);
		}

		for (ForumTopicPannelDto dto : topics) {
			aData.add(toJsonStringForTopics(dto));
		}

		json.put("aData", aData);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	private JSONObject toJsonStringForTopics(ForumTopicPannelDto dto) throws Exception
	{
		JSONObject row = new JSONObject();
		row.put("topicId", dto.getTopicId());
		row.put("subject", dto.getSubject());
		row.put("editedSubject", dto.getEditedSubject());
		row.put("communityId", dto.getCommunityId());
		row.put("communityName", dto.getCommunityName());
		row.put("logoPresent", dto.getLogoPresent());
		return row;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private String type;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setForumItemFinder(ForumItemFinder forumItemFinder) {
		this.forumItemFinder = forumItemFinder;
	}
}