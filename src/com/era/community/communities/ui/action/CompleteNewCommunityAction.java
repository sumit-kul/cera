package com.era.community.communities.ui.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.CommunityFeature;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.ui.dto.CommunityFeatureDto;

/**
 * @spring.bean name="/community/completeNewCommunity.do"
 */
public class CompleteNewCommunityAction extends AbstractCommandAction
{
    protected CommunityEraContextManager contextManager;
    protected CommunityFinder communityFinder;
    
    protected ModelAndView handle(Object data) throws Exception
    {
    	CommunityEraContext context = contextManager.getContext();
        Command cmd = (Command)data;
        Community comm = null;
        try {
        	comm = communityFinder.getCommunityForId(cmd.getCommunityId());
            cmd.setCommunity(comm);
            cmd.setSearchType("Community");
            cmd.setQueryText(comm.getName());
		} catch (ElementNotFoundException e) {
			return new ModelAndView("/pageNotFound");
		}
		
		List<CommunityFeatureDto> featureList = new ArrayList<CommunityFeatureDto>();
		Iterator i = context.getLinksForCurrentCommunity().iterator();
		while (i.hasNext()) {
			CommunityFeature f = (CommunityFeature)i.next();
			CommunityFeatureDto feature = new CommunityFeatureDto();
			feature.setTitle(f.getFeatureTitle());
			feature.setName(f.getFeatureName());
			feature.setLabel(f.getFeatureLabel());
			feature.setAnabled(f.isFeatureEnabledForCommunity(comm));
			feature.setMandatory(f.isFeatureMandatory());
			featureList.add(feature);
		}
		
		cmd.setFeatures(featureList);
		
        return new ModelAndView("community/confirmCommunity");
    }

    public static class Command extends IndexCommandBeanImpl implements CommandBean
    {
    	private int communityId;
        private Community community = null;
        private List<CommunityFeatureDto> features;

		public Community getCommunity() {
			return community;
		}

		public void setCommunity(Community community) {
			this.community = community;
		}

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}

		public List<CommunityFeatureDto> getFeatures() {
			return features;
		}

		public void setFeatures(List<CommunityFeatureDto> features) {
			this.features = features;
		}
    }

    public void setCommunityFinder(CommunityFinder communityFinder)
    {
        this.communityFinder = communityFinder;
    }

    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
}