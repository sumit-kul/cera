package com.era.community.forum.dao; 

public class ThemeTopicLinkDaoImpl extends com.era.community.forum.dao.generated.ThemeTopicLinkDaoBaseImpl implements ThemeTopicLinkDao
{
	public void clearTopicLinksForTheme(int themeId) throws Exception
	{
		String sql="delete from ThemeTopicLink where themeId=?";

		getSimpleJdbcTemplate().update(sql, themeId);

	}

	public void clearThemesForTopic(int topicId) throws Exception
	{
		String sql="delete from ThemeTopicLink where ItemId=?";

		getSimpleJdbcTemplate().update(sql,topicId);

	}

	public void clearThemeTopicLink(int topicId, int themeId) throws Exception
	{
		String sql="delete from ThemeTopicLink where ItemId=? and themeId=?";

		getSimpleJdbcTemplate().update(sql, topicId, themeId);

	}
}

