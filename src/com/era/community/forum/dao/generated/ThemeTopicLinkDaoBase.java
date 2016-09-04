package com.era.community.forum.dao.generated; 

import com.era.community.forum.dao.ThemeTopicLink;

public interface ThemeTopicLinkDaoBase extends ThemeTopicLinkFinderBase
{
	public void store(ThemeTopicLink o) throws Exception;
	public void deleteThemeTopicLinkForId(int id) throws Exception;
	public void delete(ThemeTopicLink o) throws Exception;
}

