package com.era.community.wiki.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryCommentLike;

public abstract class WikiEntryCommentLikeDaoBaseImpl extends AbstractJdbcDaoSupport implements WikiEntryCommentLikeDaoBase
{
	public WikiEntryCommentLike getWikiEntryCommentLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (WikiEntryCommentLike)getEntity(WikiEntryCommentLike.class, keys);
	}

	public void deleteWikiEntryCommentLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}
	
	public void clearWikiEntryCommentLikesForWikiEntry(WikiEntry entry) throws Exception
	{
		String sql="delete from " + getTableName() + " where  WikiEntryId = ?";

		getSimpleJdbcTemplate().update(sql, entry.getId());     

	}

	public void delete(WikiEntryCommentLike o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(WikiEntryCommentLike o) throws Exception
	{
		storeEntity(o);
	}

	public WikiEntryCommentLike newWikiEntryCommentLike() throws Exception
	{
		return (WikiEntryCommentLike)newEntity(WikiEntryCommentLike.class);
	}
}