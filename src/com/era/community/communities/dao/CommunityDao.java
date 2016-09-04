package com.era.community.communities.dao; 

import support.community.database.QueryScroller;

/**
 	Interface with package level access to hold methods private to the package.
 */
interface CommunityDao extends com.era.community.communities.dao.generated.CommunityDaoBase, CommunityFinder
{
    public QueryScroller listUsersRequestingMembership(Community comm, String filterOption, String sortOption) throws Exception;
    public QueryScroller listUsersRejectedMembership(Community comm) throws Exception;
    public void setSysType(Object obj, String column, Object value) throws Exception;
    public boolean isLogoPresent(Community comm) throws Exception;
    public void clearLogo(Community comm) throws Exception;
    public boolean isBannerPresent(Community comm) throws Exception;
    public void clearBanner(Community comm) throws Exception;
}