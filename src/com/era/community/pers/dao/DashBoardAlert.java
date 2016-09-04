package com.era.community.pers.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @cera.entity name="TBDBALT"
 */
public class DashBoardAlert extends CecAbstractEntity
{
	/**
	 * @cera.column integer not null
	 */
	protected int UserId;

	/**
	 * @cera.column integer not null
	 */
	protected int MessageCount;
	
	/**
	 * @cera.column integer not null
	 */
	protected int NotificationCount;

	/**
	 * @cera.column integer not null
	 */
	protected int LikeCount;

	/**
	 * @cera.column integer not null
	 */
	protected int ProfileVisitCount;

	/**
	 * @cera.column integer not null
	 */
	protected int ConnectionReceivedCount;

	/**
	 * @cera.column integer not null
	 */
	protected int ConnectionApprovedCount;

	/*
	 * Injected references.
	 */
	protected DashBoardAlertDao dao;
	protected UserFinder userFinder;

	/**
	 * @return the messageCount
	 */
	public int getMessageCount() {
		return MessageCount;
	}

	/**
	 * @param messageCount the messageCount to set
	 */
	public void setMessageCount(int messageCount) {
		MessageCount = messageCount;
	}

	/**
	 * @return the likeCount
	 */
	public int getLikeCount() {
		return LikeCount;
	}

	/**
	 * @param likeCount the likeCount to set
	 */
	public void setLikeCount(int likeCount) {
		LikeCount = likeCount;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		return true;
	}    

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public int getProfileVisitCount() {
		return ProfileVisitCount;
	}

	public void setProfileVisitCount(int profileVisitCount) {
		ProfileVisitCount = profileVisitCount;
	}

	public int getConnectionReceivedCount() {
		return ConnectionReceivedCount;
	}

	public void setConnectionReceivedCount(int connectionReceivedCount) {
		ConnectionReceivedCount = connectionReceivedCount;
	}

	public int getConnectionApprovedCount() {
		return ConnectionApprovedCount;
	}

	public void setConnectionApprovedCount(int connectionApprovedCount) {
		ConnectionApprovedCount = connectionApprovedCount;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public void setDao(DashBoardAlertDao dao) {
		this.dao = dao;
	}

	public int getNotificationCount() {
		return NotificationCount;
	}

	public void setNotificationCount(int notificationCount) {
		NotificationCount = notificationCount;
	}
}