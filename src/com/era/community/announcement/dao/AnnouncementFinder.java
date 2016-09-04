package com.era.community.announcement.dao; 

import java.util.*;

import support.community.database.*;

/**
*
*/
public interface AnnouncementFinder extends com.era.community.announcement.dao.generated.AnnouncementFinderBase
{
    public QueryScroller listAnnouncementsByDatePosted() throws Exception;
    public List getLiveAnnouncementList() throws Exception;    
}

