package com.era.community.tagging.dao.generated; 

import com.era.community.tagging.dao.Tag;

public interface TagDaoBase extends TagFinderBase
{
	public void store(Tag o) throws Exception;
	public void deleteTagForId(int id) throws Exception;
	public void delete(Tag o) throws Exception;
}

