package com.era.community.forum.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.base.CecAbstractEntity;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.monitor.ui.dto.SubscriptionItemDto;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 * @entity name="FRUM" 
 *
 * @entity.index name="01" unique="no" columns="CommunityId"   
 * 
 * @entity.foreignkey name="01" columns="CommunityId" target="COMM" ondelete="cascade"  
 */
public class Forum extends CecAbstractEntity
{
	/**
	 * @column integer not null
	 */
	protected int CommunityId;

	/**
	 * @column varchar(150) not null with default
	 */
	protected String Name;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Inactive = false;


	public ForumTopic newTopic() throws Exception
	{
		if(getCommunityEraContext().getCurrentUser() == null) return null;
		ForumTopic topic = forumItemDao.newForumTopic();
		topic.setForumId(this.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		topic.setDatePosted(ts);
		topic.setAuthorId(getCommunityEraContext().getCurrentUser().getId());
		return topic;
	}

	public ForumResponse newResponse() throws Exception
	{
		if(getCommunityEraContext().getCurrentUser() == null) return null;
		ForumResponse response = forumItemDao.newForumResponse();
		response.setForumId(this.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		response.setDatePosted(ts);
		response.setAuthorId(getCommunityEraContext().getCurrentUser().getId());
		return response;
	}

	protected ForumDao dao;     
	protected ForumItemDao forumItemDao;
	protected ForumFinder forumFinder;
	protected CommunityFinder communityFinder;

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public final void setDao(ForumDao dao)
	{
		this.dao = dao;
	} 

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (!getCommunity().isPrivate()) return true;
		if (user==null) return false;
		return getCommunity().isMember(user.getId());
	}    

	public Community getCommunity() throws Exception
	{
		return communityFinder.getCommunityForId(getCommunityId());
	}

	public Date getLatestPostDate(int themeId) throws Exception
	{
		return dao.getLatestPostDate(this, themeId);
	}

	public final int getCommunityId()
	{
		return CommunityId;
	}
	public final void setCommunityId(int communityId)
	{
		CommunityId = communityId;
	}
	public final boolean isInactive()
	{
		return Inactive;
	}
	public final void setInactive(boolean inactive)
	{
		Inactive = inactive;
	}
	public final String getName()
	{
		return Name;
	}
	public final void setName(String name)
	{
		Name = name;
	}
	public void setForumItemDao(ForumItemDao forumItemDao)
	{
		this.forumItemDao = forumItemDao;
	}

	public QueryScroller listTopicsByMostRecentResponse(int themeId, String sortBy) throws Exception
	{
		return dao.listTopicsByMostRecentResponse(this, themeId, sortBy);
	}

	public QueryScroller listItemsByDate(int themeId) throws Exception
	{
		return dao.listItemsByDate(this, themeId);
	}

	public List<SubscriptionItemDto> getItemsSince(Date date, int themeId) throws Exception
	{
		return dao.getItemsSince(this, date, themeId);
	}

	public final ForumDao getDao()
	{
		return dao;
	}

	public final void setForumFinder(ForumFinder forumFinder)
	{
		this.forumFinder = forumFinder;
	}

	public final void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}
}