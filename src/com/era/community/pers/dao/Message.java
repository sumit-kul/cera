package com.era.community.pers.dao;

import java.util.Date;

import support.community.database.BlobData;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @cera.entity name="UMSG"
 * @cera.entity.blob name="Attachment"
 * @cera.entity.longtext name="BigDescription"
 * @cera.entity.longtext name="AddressLabel"
 *   
 * @cera.entity.index name="01" unique="no" columns="ToUserId"   
 * @cera.entity.index name="02" unique="no" columns="FromUserId"   
 * 
 * @cera.entity.foreignkey name="01" columns="ToUserId" target="USER" ondelete="cascade"  
 * @cera.entity.foreignkey name="02" columns="FromUserId" target="USER" ondelete="cascade"  
 */
public abstract class Message extends CecAbstractEntity
{
	/**
	 * @cera.column integer not null
	 */
	protected int ToUserId;

	/**
	 * @cera.column integer not null
	 */
	protected int FromUserId;

	/**
	 * @cera.column varchar(120) not null with default
	 */
	protected String Subject ;

	/**
	 * @cera.column long varchar not null with default
	 */
	protected String Body ;

	/**
	 * @cera.column timestamp not null with default
	 */
	protected Date DateSent ;

	/**
	 * @cera.column char(1) not null with default
	 */
	protected boolean AlreadyRead ;

	/**
	 * @cera.column char(1) not null with default
	 */
	protected String SysType ;

	/**
	 * @cera.column integer not null
	 */
	protected int DeleteFlag;
	
	/**
	 * @cera.column integer not null
	 */
	protected int ReadFlag;

	protected MessageDao dao;
	protected UserFinder userFinder;

	public String getFromUserName() throws Exception
	{
		User user = userFinder.getUserEntity(getFromUserId());
		return user.getFirstName()+" "+user.getLastName();       
	}

	public BlobData readAttachment() throws Exception
	{
		return dao.readAttachment(this);
	}

	public String getBigDescription() throws Exception
	{
		return dao.getBigDescription(this);
	}

	public void setBigDescription(String value) throws Exception
	{
		dao.setBigDescription(this, value);
	}    
	public String getBody()
	{
		return Body;
	}
	public void setBody(String body)
	{
		Body = body;
	}
	public Date getDateSent()
	{
		return DateSent;
	}
	public void setDateSent(Date dateSent)
	{
		DateSent = dateSent;
	}
	public int getFromUserId()
	{
		return FromUserId;
	}
	public void setFromUserId(int fromUserId)
	{
		FromUserId = fromUserId;
	}
	public String getSubject()
	{
		return Subject;
	}
	public void setSubject(String subject)
	{
		Subject = subject;
	}
	public int getToUserId()
	{
		return ToUserId;
	}
	public void setToUserId(int toUserId)
	{
		ToUserId = toUserId;
	}
	public void setDao(MessageDao dao)
	{
		this.dao = dao;
	}
	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public String getAddressLabel() throws Exception {
		return dao.getAddressLabel(this);
	}

	public void setAddressLabel(String addressLabel) throws Exception {
		dao.setAddressLabel(this, addressLabel);
	}

	public String getSysType() {
		return SysType;
	}

	public void setSysType(String SysType) {
		this.SysType = SysType;
	}

	public int getDeleteFlag() {
		return DeleteFlag;
	}

	public void setDeleteFlag(int DeleteFlag) {
		this.DeleteFlag = DeleteFlag;
	}

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;        
		if (user.getId() == getFromUserId()) return true;
		if (user.getId() == getToUserId()) return true;
		return false;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		if (user.getId() == getFromUserId()) return true;
		if (user.getId() == getToUserId()) return true;        
		return false;
	}    

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	}

	public boolean isAlreadyRead() {
		return AlreadyRead;
	}

	public void setAlreadyRead(boolean alreadyRead) {
		AlreadyRead = alreadyRead;
	}

	public int getReadFlag() {
		return ReadFlag;
	}

	public void setReadFlag(int readFlag) {
		ReadFlag = readFlag;
	} 
}