package com.era.community.tagging.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.tagging.dao.Tag;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.ibm.json.java.JSONObject;

/**
 * Saves tags for a specific item and user
 * 
 * @spring.bean name="/cid/[cec]/addTag.do"
 * @spring.bean name="/cid/[cec]/addTag.ajx"
 * @spring.bean name="/tag/addTag.ajx"
 */
public class AddTagAction extends AbstractCommandAction
{

	protected TagFinder tagFinder;
	protected CommunityEraContextManager contextManager;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Community currentCommunity = context.getCurrentCommunity();
		int communityId = currentCommunity != null ? currentCommunity.getId() : 0;
		HttpServletResponse resp = context.getResponse();
		if (context.getCurrentUser() == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}
		Command cmd = (Command) data;                
		List fromDB = new ArrayList();
		String pType = cmd.getParentType();
		if ("Community".equalsIgnoreCase(cmd.getParentType())) { 
			fromDB = tagFinder.getAllTagTextForParentID(communityId, communityId, cmd.getParentType());
			pType = currentCommunity.getCommunityType()+cmd.getParentType();
		} else {
			fromDB = tagFinder.getAllTagTextForParentID(cmd.getParentId(), communityId, cmd.getParentType());
		}

		List fromGUI = new ArrayList();
		if (cmd.getTags() != null && !"".equals(cmd.getTags())) {
			StringTokenizer st = new StringTokenizer(cmd.getTags(), " ");
			while (st.hasMoreTokens()) {
				String tag = st.nextToken().trim().toLowerCase();
				if (!fromGUI.contains(tag)) {
					fromGUI.add(tag);
				}
			} 
		}

		for (Iterator iteratorGui = fromGUI.iterator(); iteratorGui.hasNext();) {
			String tagFfromGUI = (String) iteratorGui.next();
			boolean keep = false;
			for (Iterator iteratorDB = fromDB.iterator(); iteratorDB.hasNext();) {
				TagDto tagFromDB = (TagDto) iteratorDB.next();
				if (tagFfromGUI.equalsIgnoreCase(tagFromDB.getTagText())) {
					keep = true;
					break;
				}
			}
			if (!keep)addNewTag(communityId, cmd.getParentId(), context.getCurrentUser().getId(), pType, tagFfromGUI);
		}

		for (Iterator iteratorDB = fromDB.iterator(); iteratorDB.hasNext();) {
			TagDto tagFromDB = (TagDto) iteratorDB.next();
			boolean keep = false;
			for (Iterator iteratorGui = fromGUI.iterator(); iteratorGui.hasNext();) {
				String tagFfromGUI = (String) iteratorGui.next();
				if (tagFfromGUI.equalsIgnoreCase(tagFromDB.getTagText())) {
					keep = true;
					break;
				}
			}
			if (!keep)clearExistingTag(communityId, cmd.getParentId(), context.getCurrentUser().getId(), pType, tagFromDB.getTagText());
		}
		List tags = getLatestTagList(context.getCurrentCommunity(), cmd.getParentType(), cmd.getParentId());
		String taggedKeyWords = getTaggedKeywords(tags);
		JSONObject json = new JSONObject();
		json.put("taggedKeyWords", taggedKeyWords);
		json.put("tagCount", tags.size());
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	private void addNewTag(int communityId, int parentId, int userId, String parentType, String tag) throws Exception {
		Tag newTag = tagFinder.newTag();
		newTag.setCommunityId(communityId);
		newTag.setTagText(tag);         
		newTag.setPosterId(userId);
		newTag.setParentId(parentId);
		newTag.setParentType(parentType);
		newTag.update(); 
	}
	
	private void clearExistingTag(int communityId, int parentId, int userId, String parentType, String tag) throws Exception {
		tagFinder.clearExistingTag(parentId, communityId, parentType, tag);
	}
	
	private List getLatestTagList(Community community, String parentType, int parentId) throws Exception{
		List tags = new ArrayList();
		if (parentType.equalsIgnoreCase("Community")) {
			try {
				tags = community.getTagsForOnlyCommunityByPopularity(20);
			} catch (Exception e) {
			}
		} else {
			tags = tagFinder.getTagsForParentTypeByPopularity(parentId, 0, 20, parentType);
		}
		return tags;
	}

	private String getTaggedKeywords(List tags){
		String taggedKeywords = "Tags: ";
		try {
			for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
				TagDto tb = (TagDto) iterator.next();
				String tag = tb.getTagText().trim().toLowerCase();
				taggedKeywords += "<a href='community/showCommunities.do?filterTag="+tag+" ' class='normalTip euTagSelect' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
				if (iterator.hasNext())taggedKeywords += " , ";
			}
		} catch (Exception e) {
			return taggedKeywords;
		}
		return taggedKeywords;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int parentId;
		private int communityId;
		private String parentType;
		private String tags;  
		private String errorMsg;

		public String getParentType()
		{
			return parentType;
		}
		public void setParentType(String parentType)
		{
			this.parentType = parentType;
		}
		public int getParentId()
		{
			return parentId;
		}
		public void setParentId(int parentId)
		{
			this.parentId = parentId;
		}
		public String getTags()
		{
			return tags;
		}
		public void setTags(String tags)
		{
			this.tags = tags;
		}
		public String getErrorMsg() {
			return errorMsg;
		}
		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
		public int getCommunityId() {
			return communityId;
		}
		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}               

	}

	public void setTagFinder(TagFinder tagFinder)
	{
		this.tagFinder = tagFinder;
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
}