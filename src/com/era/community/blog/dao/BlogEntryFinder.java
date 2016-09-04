package com.era.community.blog.dao; 

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.blog.ui.dto.BlogEntryHeaderDto;
import com.era.community.blog.ui.dto.BlogEntryPannelDto;

public interface BlogEntryFinder extends com.era.community.blog.dao.generated.BlogEntryFinderBase
{
    public List getCommBlogEntryAppearsIn(int entryId) throws Exception;
    public QueryScroller listAllEntries() throws Exception; 
    public int getEntryCount() throws Exception;
    public int getEntryCount(String sRegion) throws Exception;
    public List getCommunitiesForBlogEntry(int beid) throws Exception;
    public List<BlogEntryPannelDto> listAllTopStories(int max) throws Exception;
    public List<BlogEntryPannelDto> listAllLatestPosts(int max) throws Exception;
    public BlogEntryHeaderDto getBlogEntryForHeader(int entryId) throws Exception;
}