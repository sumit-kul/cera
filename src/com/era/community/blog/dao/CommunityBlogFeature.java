package com.era.community.blog.dao;

import support.community.application.ElementNotFoundException;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.CommunityFeature;
import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;

public class CommunityBlogFeature implements CommunityFeature
{
    CommunityEraContextManager contextManager;
    CommunityBlogDao communityBlogDao;
    BlogAuthorDao blogAuthorDao;
    
    public void setFeatureEnabledForCommunity(Community comm, boolean status)
            throws Exception
    {
    	CommunityBlog comBlog = (CommunityBlog)getFeatureForCommunity(comm);
        if (comBlog == null && status == false) {
            return;
        }
        else if (comBlog == null && status == true) {
        	User user = contextManager.getContext().getCurrentUser();
        	comBlog = communityBlogDao.newCommunityBlog();
        	comBlog.setCommunityId(comm.getId());
        	comBlog.setName(comm.getName());
        	comBlog.setInactive(false);
        	comBlog.update();
            
            if (user != null) {
            	BlogAuthor blogAuthor = blogAuthorDao.newBlogAuthor();
            	blogAuthor.setActive(1);
            	blogAuthor.setBlogId(comBlog.getId());
            	blogAuthor.setCommunity(true);
            	blogAuthor.setRole(1);
            	blogAuthor.setUserId(user.getId());
            	blogAuthor.update();
			}
            return;
        }
        else {
        	comBlog.setInactive(!status);
        	comBlog.update();
        }

    }

    public boolean isFeatureEnabledForCommunity(Community comm)
            throws Exception
    {
    	CommunityBlog comBlog = (CommunityBlog)getFeatureForCommunity(comm);
        if (comBlog == null) return false;
        return !comBlog.isInactive();
    }

    public Object getFeatureForCommunity(Community comm) throws Exception
    {                
        try {
            return communityBlogDao.getCommunityBlogForCommunity(comm);
        }
        catch (ElementNotFoundException e) {
            return null;
        }
    }

    public Object getFeatureForCurrentCommunity() throws Exception
    {
        return getFeatureForCommunity(contextManager.getContext().getCurrentCommunity());
    }

    public String getFeatureName() throws Exception
    {
        return "Blog";
    }

    public String getFeatureLabel() throws Exception
    {
        return "<i class=\'fa fa-quote-left\' aria-hidden=\'true\' style=\'margin-right: 10px;\'></i>Blog";
    }

    public String getFeatureUri() throws Exception
    {
        return "/blog";
    }

    public boolean isFeatureMandatory() throws Exception
    {
        return false;
    }
       
    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public boolean isFeatureAvailableForUser(User user) throws Exception
    {
        return true;
    }

    public String getFeatureTitle() throws Exception
    {
       return "Blog entries posted to this community";
    }

	public void setBlogAuthorDao(BlogAuthorDao blogAuthorDao) {
		this.blogAuthorDao = blogAuthorDao;
	}

	public void setCommunityBlogDao(CommunityBlogDao communityBlogDao) {
		this.communityBlogDao = communityBlogDao;
	}
}