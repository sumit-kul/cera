package com.era.community.wiki.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryComment;

public abstract class WikiEntryCommentDaoBaseImpl extends AbstractJdbcDaoSupport implements WikiEntryCommentDaoBase
{
	public WikiEntryComment getWikiEntryCommentForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (WikiEntryComment)getEntity(WikiEntryComment.class, keys);
	}

	public void deleteWikiEntryCommentForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}
	
	public void clearWikiEntryCommentsForWikiEntry(WikiEntry entry) throws Exception
	{
		String sql="delete from " + getTableName() + " where  WikiEntryId = ?";

		getSimpleJdbcTemplate().update(sql, entry.getId());     

	}

	public void delete(WikiEntryComment o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(WikiEntryComment o) throws Exception
	{
		storeEntity(o);
	}

	public WikiEntryComment newWikiEntryComment() throws Exception
	{
		return (WikiEntryComment)newEntity(WikiEntryComment.class);
	}
}