package com.era.community.communities.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 * @entity name="FEED" 
 * @entity.index name="01" unique="no" columns="CommunityId"   
 * 
 * @entity.foreignkey name="01" columns="CommunityId" target="COMM" ondelete="cascade"  
 * @entity.foreignkey name="02" columns="UserId" target="USER" ondelete="restrict"  
 *
 */
public class Feedback extends CecAbstractEntity
{
	/**
	 * @column integer not null
	 */
	private int CommunityId;

	/**
	 * @column integer not null
	 */
	protected int SubmitterId;

	/**
	 * @column varchar(250) not null with default
	 */
	protected String Subject;

	/**
	 * @column long varchar not null with default
	 */
	protected String Body = "";

	/*
	 * Injected  references.
	 */
	protected FeedbackDao dao;
	protected FeedbackFinder feedbackFinder;
	protected CommunityFinder communityFinder;


	/**
	 * Update or insert this entity in the database.
	 */
	public void update() throws Exception
	{
		dao.store(this); 
	}

	public final void setDao(FeedbackDao dao)
	{
		this.dao = dao;
	}  

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		Community comm = getCommunityEraContext().getCurrentCommunity();
		return comm.isMember(user.getId());
	}

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		if (user==null) 
			return false;
		else
			return true;
	}

	public final String getBody()
	{
		return Body;
	}
	
	public final void setBody(String body)
	{
		Body = body;
	}
	public final String getSubject()
	{
		return Subject;
	}
	public final void setSubject(String subject)
	{
		Subject = subject;
	}

	public final int getCommunityId()
	{
		return CommunityId;
	}

	public final void setCommunityId(int communityId)
	{
		CommunityId = communityId;
	}

	public final void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public final void setFeedbackFinder(FeedbackFinder feedbackFinder)
	{
		this.feedbackFinder = feedbackFinder;
	}

	public final int getSubmitterId()
	{
		return SubmitterId;
	}

	public final void setSubmitterId(int submitterId)
	{
		SubmitterId = submitterId;
	}



}
