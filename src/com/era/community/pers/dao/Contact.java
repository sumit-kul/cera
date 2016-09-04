package com.era.community.pers.dao;

import java.util.Date;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="CONT"
 *   
 * @entity.index name="01" unique="no" columns="OwningUserId"   
 * @entity.index name="02" unique="no" columns="ContactUserId"   
 * 
 * @entity.foreignkey name="01" columns="OwningUserId" target="USER" ondelete="cascade"  
 * @entity.foreignkey name="02" columns="ContactUserId" target="USER" ondelete="cascade"  
 */
public class Contact extends CecAbstractEntity
{
	public static final int STATUS_UNAPPROVED = 0;
	public static final int STATUS_APPROVED = 1;
	public static final int STATUS_HIDE = 2;
	public static final int STATUS_SPAMMED = 3;
	public static final int STATUS_SPAMMED_CANCELLED = 4; //user has spammed and owner then cancel request

	/**
	 * @column integer not null
	 */
	protected int OwningUserId;

	/**
	 * @column integer not null
	 */
	protected int ContactUserId;

	/**
	 * @column integer not null with default
	 */
	protected int Status = 0;

	/**
	 * @DateConnection
	 */
	protected Date DateConnection;

	/**
	 * @column integer not null with default
	 */
	protected int FollowOwner = 0;

	/**
	 * @column integer not null with default
	 */
	protected int FollowContact = 0;

	protected UserFinder userFinder;
	protected ContactDao dao;

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
		//if (user==null) return false;
		return true;
	}  

	public Contact getContact(int contactOwnerId, int contactUserId) throws Exception
	{
		return dao.getContact(contactOwnerId, contactUserId);
	}

	public int getContactUserId()
	{
		return ContactUserId;
	}

	public void setContactUserId(int contactUserId)
	{
		ContactUserId = contactUserId;
	}

	public int getOwningUserId()
	{
		return OwningUserId;
	}

	public void setOwningUserId(int owningUserId)
	{
		OwningUserId = owningUserId;
	}

	public UserFinder getUserFinder()
	{
		return userFinder;
	} 

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	}

	public void setDao(ContactDao dao)
	{
		this.dao = dao;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public Date getDateConnection() {
		return DateConnection;
	}

	public void setDateConnection(Date dateConnection) {
		DateConnection = dateConnection;
	}

	public int getFollowOwner() {
		return FollowOwner;
	}

	public void setFollowOwner(int followOwner) {
		FollowOwner = followOwner;
	}

	public int getFollowContact() {
		return FollowContact;
	}

	public void setFollowContact(int followContact) {
		FollowContact = followContact;
	} 
}