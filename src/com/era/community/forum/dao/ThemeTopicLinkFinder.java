package com.era.community.forum.dao; 

public interface ThemeTopicLinkFinder extends com.era.community.forum.dao.generated.ThemeTopicLinkFinderBase
{
	public void clearTopicLinksForTheme(int themeId) throws Exception;
	public void clearThemesForTopic(int topicId) throws Exception;
	public void clearThemeTopicLink(int topicId, int themeId) throws Exception;
}