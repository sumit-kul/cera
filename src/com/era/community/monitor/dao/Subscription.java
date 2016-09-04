package com.era.community.monitor.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="SUBS" 
 */
public abstract class Subscription extends CecAbstractEntity implements Comparable<Subscription>
{    
	protected int UserId;
	protected int CommunityId;
	protected int Frequency = 0;
	protected int sortOrder;
	
	protected int WebSubscription = 0;
	protected int MailSubscription = 0;
	protected int PageSubscription = 0;

	public int getCommunityId()
	{
		return CommunityId;
	}
	
	public void setCommunityId(int communityId)
	{
		CommunityId = communityId;
	}
	
	public int getUserId()
	{
		return UserId;
	}
	
	public void setUserId(int userId)
	{
		UserId = userId;
	}

	public abstract String getItemName() throws Exception;
	public abstract String getItemUrl() throws Exception;
	public abstract String getItemType() throws Exception;
	public abstract java.util.Date getItemLastUpdateDate() throws Exception;
	public abstract Object getItem() throws Exception;
	public abstract int getSortOrder() throws Exception;

	protected SubscriptionDao dao;

	public SubscriptionDao getDao()
	{
		return dao;
	}

	public void setDao(SubscriptionDao dao)
	{
		this.dao = dao;
	}

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	} 

	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user)
	{
		return true;
	}
	public final int getFrequency()
	{
		return Frequency;
	}
	public final void setFrequency(int frequency)
	{
		Frequency = frequency;
	}

	public int getWebSubscription() {
		return WebSubscription;
	}

	public void setWebSubscription(int webSubscription) {
		WebSubscription = webSubscription;
	}

	public int getMailSubscription() {
		return MailSubscription;
	}

	public void setMailSubscription(int mailSubscription) {
		MailSubscription = mailSubscription;
	}

	public int getPageSubscription() {
		return PageSubscription;
	}

	public void setPageSubscription(int pageSubscription) {
		PageSubscription = pageSubscription;
	}
}