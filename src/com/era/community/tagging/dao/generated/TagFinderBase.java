package com.era.community.tagging.dao.generated; 
import com.era.community.tagging.dao.Tag;

public interface TagFinderBase
{
	public Tag getTagForId(int id) throws Exception;
	public Tag newTag() throws Exception;
}

