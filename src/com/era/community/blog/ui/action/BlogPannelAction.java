package com.era.community.blog.ui.action;

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
import com.era.community.blog.dao.BlogEntryFinder;
import com.era.community.blog.ui.dto.BlogEntryPannelDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/blog/blogPannel.ajx"
 */
public class BlogPannelAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected BlogEntryFinder blogEntryFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		HttpServletResponse resp = context.getResponse();

		JSONObject json = new JSONObject();
		JSONArray aData = new JSONArray();
		List<BlogEntryPannelDto> stories = new ArrayList<BlogEntryPannelDto>();
		if ("top".equalsIgnoreCase(cmd.getType())){
			stories = blogEntryFinder.listAllTopStories(8);
		} else {
			stories = blogEntryFinder.listAllLatestPosts(8);
		}

		for (BlogEntryPannelDto dto : stories) {
			aData.add(toJsonStringForMedia(dto));
		}

		json.put("aData", aData);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	private JSONObject toJsonStringForMedia(BlogEntryPannelDto dto) throws Exception
	{
		JSONObject row = new JSONObject();
		row.put("entryId", dto.getEntryId());
		row.put("title", dto.getTitle());
		row.put("editedTitle", dto.getEditedTitle());
		row.put("userId", dto.getUserId());
		row.put("fullName", dto.getFullName());
		row.put("photoPresent", dto.getPhotoPresent());
		row.put("communityId", dto.getCommunityId());
		row.put("imageCount", dto.getImageCount());
		row.put("commentCount", dto.getCommentCount());
		row.put("likeCount", dto.getLikeCount());
		row.put("visitors", dto.getVisitors());
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

	public void setBlogEntryFinder(BlogEntryFinder blogEntryFinder) {
		this.blogEntryFinder = blogEntryFinder;
	}
}