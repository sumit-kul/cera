package com.era.community.wiki.dao; 

import java.util.Date;
import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.pers.dao.User;

interface WikiDao extends com.era.community.wiki.dao.generated.WikiDaoBase, WikiFinder
{
	public QueryScroller listEntriesByTitle(Wiki wiki) throws Exception;   
	public QueryScroller listEntriesByUpdateDate(Wiki wiki) throws Exception;    
	public QueryScroller listEntriesForUser(Wiki wiki, User user) throws Exception;
	public boolean entryExists(Wiki wiki, String title,Integer entryId) throws Exception;

	public Date getLatestPostDate(Wiki wiki) throws Exception;
	public List getItemsSince(Wiki wiki, Date date) throws Exception;
}