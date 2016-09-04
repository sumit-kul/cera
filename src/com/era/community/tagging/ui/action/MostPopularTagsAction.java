package com.era.community.tagging.ui.action;

import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/tag/mostPopularTags.ajx"
 */
public class MostPopularTagsAction extends AbstractCommandAction {

	protected CommunityEraContextManager contextManager;
	protected TagFinder tagFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
    {
        CommunityEraContext context = contextManager.getContext();
        HttpServletResponse resp = context.getResponse();
        Command cmd = (Command)data;
        
        List popularTags = null;
        JSONObject json = new JSONObject();
        JSONArray jData = new JSONArray();
        
        if (cmd.getParentType().equalsIgnoreCase("Community")) {
        	popularTags = tagFinder.getPopularCommunityTagsToAdd(cmd.getMaxTags());
        } else {
        	popularTags = tagFinder.getPopularTagsByParentTypeToAdd(cmd.getParentType(), cmd.getMaxTags());
        }
		
        String tags = getTagsString(cmd.getParentType(), cmd.getParentId(), cmd.getCommunityId(), 0);
        
		if (popularTags != null && popularTags.size() > 0) {
    		
	        for (Iterator iterator = popularTags.iterator(); iterator.hasNext();) {
	        	TagDto tag = (TagDto) iterator.next();
	        	JSONObject name = new JSONObject();
	        	name.put("tagText", tag.getTagText());
	        	jData.add(name);
			}
    	}
		json.put("aData", jData);
        json.put("tags", tags);
        String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
    }
	
	private String getTagsString(String parentType, int parentId, int communityID, int maxTag) throws Exception {
		String tags = "";
		if ("Community".equalsIgnoreCase(parentType)) { // For community parentType PraentIs and CommunityId are same.
			parentId = communityID;
		}
		List parentTags = tagFinder.getTagsForParentTypeByPopularity(parentId, communityID, maxTag, parentType);
		for (Iterator iterator = parentTags.iterator(); iterator.hasNext();) {
			TagDto tb = (TagDto) iterator.next();
			String tag = tb.getTagText().trim().toLowerCase();
			tags += tag + " ";
		}
		return tags;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private String parentType;
		private int parentId;
		private int communityId;
		private int maxTags;

		public int getParentId()
		{
			return parentId;
		}

		public void setParentId(int parentId)
		{
			this.parentId = parentId;
		}

		public String getParentType() {
			return parentType;
		}

		public void setParentType(String parentType) {
			this.parentType = parentType;
		}

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}

		public int getMaxTags() {
			return maxTags;
		}

		public void setMaxTags(int maxTags) {
			this.maxTags = maxTags;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}			

}