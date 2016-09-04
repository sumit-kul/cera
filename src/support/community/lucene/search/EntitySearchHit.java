package support.community.lucene.search;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class EntitySearchHit 
{
	private int hitNumber;
	//private Object entity;
	private float score;
	private String summary;
	private String title;
	private String title2;
	private String title3;
	private String link;
	private String date;
	private String modified;
	private String entityTypeName;
	private String userName;
	private String userId;
	private String isPhotoPresent = "false";
	private String isUser = "false";
	private int contactStatus;
	private int contactId;
	private String connectionInfo;
	private String connectionCount;

	/* Community related fields for community search only */
	private String iscommunity = "false";
	private String isprivateCommunity = "false";
	private String communityName;
	private String dateCommunityCreation;
	private int communityId;
	private boolean isMember;
	private boolean isMembershipRequested;
	private String firstName;
	private String lastName;
	private String logoPresent;
	/* Community related fields for community search only */

	private int memberCount;
	private int creatorId;
	private String creatorFullName;

	protected final DateFormat DATE_FORMAT = new SimpleDateFormat("d MMM yyyy");

	public boolean isOdd()
	{
		return hitNumber%2 != 0;
	}

	public boolean isEven()
	{
		return hitNumber%2 == 0;
	}

	/*public final Object getEntity()
    {
        return entity;
    }
    public final void setEntity(Object entity)
    {
        this.entity = entity;
    }*/
	public final float getScore()
	{
		return score;
	}
	public final void setScore(float score)
	{
		this.score = score;
	}
	public final String getSummary()
	{
		return summary;
	}
	public final void setSummary(String summary)
	{
		this.summary = summary;
	}
	public final String getLink()
	{
		return link;
	}
	public final void setLink(String link)
	{
		this.link = link;
	}
	public final String getTitle()
	{
		return title;
	}
	public final void setTitle(String title)
	{
		this.title = title;
	}
	public final String getTitle2()
	{
		return title2;
	}
	public final void setTitle2(String subTitle)
	{
		this.title2 = subTitle;
	}
	public final String getDate()
	{
		return date;
	}
	public final void setDate(Date date)
	{
		this.date = date.toString();
	}

	public final String getModified()
	{
		return modified;
	}
	public final void setModified(Date modified)
	{
		this.modified = modified.toString();
	}
	public final String getTitle3()
	{
		return title3;
	}
	public final void setTitle3(String title3)
	{
		this.title3 = title3;
	}
	public final int getHitNumber()
	{
		return hitNumber;
	}
	public final void setHitNumber(int hitNumber)
	{
		this.hitNumber = hitNumber;
	}

	/**
	 * @return the iscommunity
	 */
	 public String getIscommunity() {
		return iscommunity;
	}

	/**
	 * @param iscommunity the iscommunity to set
	 */
	 public void setIscommunity(String iscommunity) {
		this.iscommunity = iscommunity;
	 }

	 /**
	  * @return the communityName
	  */
	 public String getCommunityName() {
		 return communityName;
	 }

	 /**
	  * @param communityName the communityName to set
	  */
	 public void setCommunityName(String communityName) {
		 this.communityName = communityName;
	 }

	 /**
	  * @return the isMember
	  */
	 public boolean getIsMember() {
		 return isMember;
	 }

	 /**
	  * @param isMember the isMember to set
	  */
	 public void setIsMember(boolean isMember) {
		 this.isMember = isMember;
	 }

	 /**
	  * @return the isMembershipRequested
	  */
	 public boolean getIsMembershipRequested() {
		 return isMembershipRequested;
	 }

	 /**
	  * @param isMembershipRequested the isMembershipRequested to set
	  */
	 public void setIsMembershipRequested(boolean isMembershipRequested) {
		 this.isMembershipRequested = isMembershipRequested;
	 }

	 /**
	  * @return the firstName
	  */
	 public String getFirstName() {
		 return firstName;
	 }

	 /**
	  * @param firstName the firstName to set
	  */
	 public void setFirstName(String firstName) {
		 this.firstName = firstName;
	 }

	 /**
	  * @return the lastName
	  */
	 public String getLastName() {
		 return lastName;
	 }

	 /**
	  * @param lastName the lastName to set
	  */
	 public void setLastName(String lastName) {
		 this.lastName = lastName;
	 }

	 /**
	  * @return the logoPresent
	  */
	 public String getLogoPresent() {
		 return logoPresent;
	 }

	 /**
	  * @param logoPresent the logoPresent to set
	  */
	 public void setLogoPresent(String logoPresent) {
		 this.logoPresent = logoPresent;
	 }

	 /**
	  * @return the communityId
	  */
	 public int getCommunityId() {
		 return communityId;
	 }

	 /**
	  * @param communityId the communityId to set
	  */
	 public void setCommunityId(int communityId) {
		 this.communityId = communityId;
	 }

	 /**
	  * @return the entityTypeName
	  */
	 public String getEntityTypeName() {
		 return entityTypeName;
	 }

	 /**
	  * @param entityTypeName the entityTypeName to set
	  */
	 public void setEntityTypeName(String entityTypeName) {
		 this.entityTypeName = entityTypeName;
	 }

	 /**
	  * @return the userName
	  */
	 public String getUserName() {
		 return userName;
	 }

	 /**
	  * @param userName the userName to set
	  */
	 public void setUserName(String userName) {
		 this.userName = userName;
	 }

	 /**
	  * @return the isPhotoPresent
	  */
	 public String getIsPhotoPresent() {
		 return isPhotoPresent;
	 }

	 /**
	  * @param isPhotoPresent the isPhotoPresent to set
	  */
	 public void setIsPhotoPresent(String isPhotoPresent) {
		 this.isPhotoPresent = isPhotoPresent;
	 }

	 /**
	  * @return the userId
	  */
	 public String getUserId() {
		 return userId;
	 }

	 /**
	  * @param userId the userId to set
	  */
	 public void setUserId(String userId) {
		 this.userId = userId;
	 }

	 /**
	  * @return the isprivateCommunity
	  */
	 public String getIsprivateCommunity() {
		 return isprivateCommunity;
	 }

	 /**
	  * @param isprivateCommunity the isprivateCommunity to set
	  */
	 public void setIsprivateCommunity(String isprivateCommunity) {
		 this.isprivateCommunity = isprivateCommunity;
	 }

	 /**
	  * @return the dateCommunityCreation
	  */
	 public String getDateCommunityCreation() {
		 return dateCommunityCreation;
	 }

	 /**
	  * @param dateCreation the dateCreation to set
	  */
	 public void setDateCommunityCreation(Date dateCreation) {
		 this.dateCommunityCreation = DATE_FORMAT.format(dateCreation);
	 }

	 /**
	  * @return the isUser
	  */
	 public String getIsUser() {
		 return isUser;
	 }

	 /**
	  * @param isUser the isUser to set
	  */
	 public void setIsUser(String isUser) {
		 this.isUser = isUser;
	 }

	 public int getContactStatus() {
		 return contactStatus;
	 }

	 public void setContactStatus(int contactStatus) {
		 this.contactStatus = contactStatus;
	 }

	 public int getContactId() {
		 return contactId;
	 }

	 public void setContactId(int contactId) {
		 this.contactId = contactId;
	 }

	 public String getConnectionInfo() {
		 return connectionInfo;
	 }

	 public void setConnectionInfo(String connectionInfo) {
		 this.connectionInfo = connectionInfo;
	 }

	 public String getConnectionCount() {
		 return connectionCount;
	 }

	 public void setConnectionCount(String connectionCount) {
		 this.connectionCount = connectionCount;
	 }

	 public int getMemberCount() {
		 return memberCount;
	 }

	 public void setMemberCount(int memberCount) {
		 this.memberCount = memberCount;
	 }

	 public int getCreatorId() {
		 return creatorId;
	 }

	 public void setCreatorId(int creatorId) {
		 this.creatorId = creatorId;
	 }

	 public String getCreatorFullName() {
		 return creatorFullName;
	 }

	 public void setCreatorFullName(String creatorFullName) {
		 this.creatorFullName = creatorFullName;
	 }
}