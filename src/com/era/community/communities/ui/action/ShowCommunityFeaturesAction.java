package com.era.community.communities.ui.action;

import java.io.Writer;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.EntityWrapper;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.CommunityFeature;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.faqs.dao.generated.HelpEntryEntity;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/communities/showCommunityFeatures.ajx" 
 */
public class ShowCommunityFeaturesAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private CommunityFinder communityFinder; 

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data; 
		HttpServletResponse resp = context.getResponse();
		
		Community currCommunity = communityFinder.getCommunityForId(cmd.getCommunityId());
		
		Iterator i = context.getLinksForCurrentCommunity().iterator();
		JSONObject json = new JSONObject();
        JSONArray jData = new JSONArray();
		while (i.hasNext()) {
			CommunityFeature f = (CommunityFeature)i.next();
			JSONObject name = new JSONObject();
        	name.put("title", f.getFeatureTitle());
        	name.put("label", f.getFeatureLabel());
        	name.put("anabled", f.isFeatureEnabledForCommunity(currCommunity));
        	name.put("mandatory", f.isFeatureMandatory());
        	name.put("name", f.getFeatureName());
        	name.put("communityId", currCommunity.getId());
        	jData.add(name);
		}
		json.put("aData", jData);
        String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int communityId;

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
	}
	
	public class RowBean extends HelpEntryEntity implements EntityWrapper
	{
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}
}