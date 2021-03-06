package com.era.community.communities.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.search.dao.SearchSite;
import com.era.community.search.dao.SearchSiteFinder;
import com.era.community.search.ui.dto.SearchSiteDto;
import com.era.community.search.ui.validator.SearchSiteValidator;


/**
 * @spring.bean name="/cid/[cec]/relevantWebsiteCreate.do"
 */
public class RelevantSiteCreateAction extends AbstractFormAction
{
    protected CommunityEraContextManager contextManager;
    protected SearchSiteFinder searchSiteFinder;
    
    protected String getView()
    {
        return "community/relevantWebsite";
    }

    protected void onDisplay(Object data) throws Exception
    {
        Command cmd = (Command)data;        
        
        CommunityEraContext context = contextManager.getContext();
        Community comm = context.getCurrentCommunity();
        
         cmd.setSequence(1);
        
         Integer highest = searchSiteFinder.getHighestSequenceSiteForCommunity(comm.getId());
         
        if (highest!=null) {
            cmd.setSequence(highest.intValue()+1);
        }
        
        if (!context.isUserCommunityAdmin())

            throw new Exception("Not authorized");
 
    }

    protected ModelAndView onSubmit(Object data) throws Exception
    {
        Command cmd = (Command)data;
        
        CommunityEraContext context = contextManager.getContext();
        Community comm = context.getCurrentCommunity();

        if (!context.isUserCommunityAdmin())     
 
            throw new Exception("Not authorized");
               
        SearchSite site = searchSiteFinder.newSearchSite();
        
        cmd.copyNonNullPropertiesTo(site);
        
        site.setCommunityId(comm.getId());
        site.update();
        
        return REDIRECT_TO_BACKLINK;
    }

    protected CommandValidator createValidator()
    {
        return new Validator(); 
    }
    
    public static class Command extends SearchSiteDto implements CommandBean
    {       
 
    }
    
    public class Validator extends SearchSiteValidator
    {
        
         
    }

    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public final void setSearchSiteFinder(SearchSiteFinder searchSiteFinder)
    {
        this.searchSiteFinder = searchSiteFinder;
    }



}
