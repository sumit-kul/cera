package com.era.community.forum.dao; 

import java.util.Date;
import java.util.List;

import support.community.database.QueryScroller;

interface ForumItemDao extends com.era.community.forum.dao.generated.ForumItemDaoBase, ForumItemFinder
{
	public QueryScroller listResponsesForTopic(ForumTopic topic) throws Exception;
	public Forum getForumForItem(ForumItem item) throws Exception;
	public int getThemeIdForItem(ForumItem item) throws Exception;
	public List getItemsSince(ForumItem item, Date date) throws Exception;

	public boolean isFile1Present(ForumItem item) throws Exception;
	public boolean isFile2Present(ForumItem item) throws Exception;
	public boolean isFile3Present(ForumItem item) throws Exception;

	public void clearFile1(ForumItem item) throws Exception;
	public void clearFile2(ForumItem item) throws Exception;
	public void clearFile3(ForumItem item) throws Exception;

	public long getFile1SizeInBytes(ForumItem item) throws Exception;

	public String getFile1IconImage(ForumItem item) throws Exception;
	public String getFile2IconImage(ForumItem item) throws Exception;
	public String getFile3IconImage(ForumItem item) throws Exception;

}

