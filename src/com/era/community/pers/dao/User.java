package com.era.community.pers.dao;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import support.community.application.ElementNotFoundException;
import support.community.database.BlobData;
import support.community.database.QueryScroller;

import com.era.community.base.CecAbstractEntity;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.events.dao.EventFinder;
import com.era.community.events.ui.dto.EventPannelDto;
import com.era.community.media.dao.Photo;
import com.era.community.media.dao.PhotoFinder;
import com.era.community.pers.dao.generated.UserEntity;
import com.era.community.profile.dao.ProfileVisitFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;

/**
 * 
 * @entity name="USER"
 */
public class User extends CecAbstractEntity
{
	/**
	 * @column varchar(80) not null with default
	 */
	protected String FirstName;

	/**
	 * @column varchar(80) not null with default
	 */
	protected String LastName;

	/**
	 * @column varchar(120) not null with default
	 */
	protected String EmailAddress;

	/**
	 * @column varchar(80) not null with default
	 */
	protected String Password;

	/**
	 * @column timestamp not null with default
	 */
	protected Timestamp DateRegistered;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean SystemAdministrator = false;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Inactive = false;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Validated = false;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean MsgAlert = false;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean SuperAdministrator = false; // This role is for Application admin for supporting purpose

	/**
	 * @column timestamp not null with default
	 */
	protected Timestamp DateLastVisit;

	/**
	 * @column integer not null with default 
	 */
	protected int ConnectionCount ;

	/**
	 * @cera.column timestamp not null with default
	 */
	protected Timestamp DateDeactivated;

	/**
	 * @column varchar(80) not null with default
	 */
	protected String ProfileName;

	protected String FirstKey;
	
	protected String ChangeKey;
	
	private int Width;
	
	private int Height;
	

	/*
	 * Injected dao references.
	 */
	protected UserDao dao;
	protected MessageDao messageDao;
	protected UserExpertiseLinkDao userExpertiseLinkDao;
	protected ContactFinder contactFinder;
	protected CommunityFinder communityFinder;
	protected UserFinder userFinder;
	protected TagFinder tagFinder;
	protected ProfileVisitFinder profileVisitFinder;
	protected PhotoFinder photoFinder;
	protected EventFinder eventFinder;
	
	public List<Photo> getlistAllPhotosForUser() throws Exception
	{
		return photoFinder.listPhotosForUser(this.getId());
	}
	
	public QueryScroller listRecentUpdatedActiveCommunitiesForMember() throws Exception
	{
		return userFinder.listRecentUpdatedActiveCommunitiesForMember(this);
	}
	
	public QueryScroller listCommunitesByDateJoined() throws Exception
	{
		return communityFinder.listActiveCommunitiesForMemberByDateJoined(this, null);
	}

	public QueryScroller listCommunitesByDateVisited() throws Exception
	{
		return communityFinder.listActiveCommunitiesForMemberByDateVisited(this, null);
	}

	public QueryScroller listContacts() throws Exception
	{
		return dao.listContacts(this);
	}

	public QueryScroller listContactsByJob() throws Exception
	{
		return dao.listContactsByJob(this);
	}

	public QueryScroller listContactsByRegion() throws Exception
	{
		return dao.listContactsByRegion(this);
	}

	public QueryScroller listContactsByOrg() throws Exception
	{
		return dao.listContactsByOrg(this);
	}

	public List listAllContacts() throws Exception
	{
		return dao.listAllContacts(this);
	}
	
	public List<EventPannelDto> listEventsForUser(Date start, Date end) throws Exception
	{
		return eventFinder.listEventsForUserForCurrentMonth(this.getId(), start, end);
	}

	public int countAllMyContacts() throws Exception
	{
		return dao.countAllMyContacts(this.getId());
	}

	public QueryScroller listUserCommunitesByName(String type, String role, String sortBy) throws Exception
	{
		return communityFinder.listActiveUserCommunitiesForMemberByName(this, type, role, sortBy);
	}

	public QueryScroller listUserSubscriptions() throws Exception
	{
		return dao.listUserSubscriptions(this);
	}

	public List listSubscriptions() throws Exception
	{
		return dao.listSubscriptionsForUser(this);
	}

	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user)
	{
		if (user == null)
			return true;
		if (user.getId() == getId())
			return true;
		try {
			if (dao.isConnectedWith(this, user.getId()))
				return true;
			if (this.isSystemAdministrator())
				return dao.getUserEntity(user.getId()).isSystemAdministrator();
			if (dao.isAdminFor(this, user.getId()))
				return true;
		} catch (Exception x) {
			logger.error("", x);
			throw new RuntimeException(x);
		}

		return false;
	}

	/**
	 * Update or insert the object in the database.
	 * 
	 * @throws Exception
	 */
	public void update() throws Exception
	{
		dao.store(this);
	}

	/**
	 * Delete this in the database
	 */
	public void delete() throws Exception
	{
		dao.delete(this);
	}

	/**
	 * Perform a soft delete of this user, setting the inactive flag and neutralising the email address.
	 */
	public void softDelete() throws Exception
	{
		if (isInactive())
			return;
		dao.removeFromMemberLists(this);
		dao.removeFromContactLists(this);
		setInactive(true);
		setEmailAddress(getEmailAddress() + "_deleted_" + getId());
		update();
	}

	public int[] getExpertiseIds() throws Exception
	{
		return userExpertiseLinkDao.getExpertiseIdsForUserId(getId());
	}

	public List getExpertiseBeans() throws Exception
	{
		return userExpertiseLinkDao.getExpertiseBeansForUserId(getId());
	}

	public void clearExpertise() throws Exception
	{
		userExpertiseLinkDao.clearExpertise(getId());
	}

	/**
	 * The user expertise values are stored in a separate link table, outside the main user record
	 * Sets a list of expertises. Clears any existing values in the process
	 */
	public void setExpertise(int[] ids) throws Exception
	{
		clearExpertise();

		if (ids == null)
			return;
		for (int n = 0; n < ids.length; n++)
			addExpertise(ids[n]);
	}

	/**
	 * Add an individual expertise value for a user
	 */
	private void addExpertise(int id) throws Exception
	{

		UserExpertiseLink link = userExpertiseLinkDao.newUserExpertiseLink();
		link.setUserExpertiseId(id);
		link.setUserId(getId());
		link.update();
	}

	public boolean isPhotoPresent() throws Exception
	{
		return dao.isPhotoPresent(this);
	}
	
	public boolean isCoverPresent() throws Exception
	{
		return dao.isCoverPresent(this);
	}

	public boolean isNewMailAvailable(String order) throws Exception
	{
		if (listUnreadMessages(order).readRowCount() > 0) {
			return true;
		} else {
			return false;
		}
	}


	public int getUnreadMessagesCount(String order) throws Exception
	{
		return (listUnreadMessages(order).readRowCount());

	}

	public int getBlogCommentCountSinceLastLogin() throws Exception
	{
		return dao.getBlogCommentCountSinceLastLogin(this);
	}

	public int getMembershipCount() throws Exception
	{
		return dao.getMembershipCount(this);
	}

	public String getTagTypeAheadJSArray() throws Exception
	{
		List tags = tagFinder.getTagsForUserId(getId(), 0);
		StringBuffer tagJSArray = new StringBuffer();

		for (int i = 0; i < tags.size(); i++) {
			TagDto tag = (TagDto) tags.get(i);
			if (tagJSArray.length() > 0)
				tagJSArray.append(", ");
			tagJSArray.append("'" + tag.getTagText() + "'");
		}

		String retVal = "tagarray = new Array(" + tagJSArray.toString() + ");";
		return retVal;
	}

	public String getOrganisationTypeAheadJSArray(String org) throws Exception
	{/*
        List organisations = userFinder.getOrganisationsFromAllUsers(org);
        StringBuffer organisationJSArray = new StringBuffer();

        for (int i = 0; i < organisations.size(); i++) {
            UserBean organisation = (UserBean)organisations.get(i);
            if (organisationJSArray.length() > 0)
                organisationJSArray.append(", ");
            organisationJSArray.append("'" + organisation.getOrganisation() + "'");
        }

        String retVal = "organisationarray = new Array(" + organisationJSArray.toString() + ");";
        return retVal;*/
		return "";
	}

	public List getOrganisationsFromAllUsers(String org) throws Exception
	{
		return userFinder.getOrganisationsFromAllUsers(org);
	}

	public List getMostPopularTagsForCommunities(int max) throws Exception
	{
		return tagFinder.getTagsForAllCommunityByPopularity(0, max, "Community");
	}

	public List getMostPopularTagsForUser(int max) throws Exception
	{
		return tagFinder.getTagsForUserId(getId(), max);
	}

	public List getMostPopularTags(int max) throws Exception
	{
		return tagFinder.getTagsForUserId(getId(), max);
	}

	public List getMostPopularTagsForParent(String parentType, int parentId, int max) throws Exception
	{
		int count = communityFinder.countCommunitiesForBlogEntry(parentId);
		if (count > 1) {
			return tagFinder.getTagsForcommunitiesForParentByPopularity(parentId, max);
		} else {
			return tagFinder.getTagsForUserId(getId(), max);
		}
	}

	public BlobData readPhoto() throws Exception
	{
		return dao.readPhoto(this);
	}
	
	public BlobData readCover() throws Exception
	{
		return dao.readCover(this);
	}
	
	public BlobData readPhotoThumb() throws Exception
	{
		return dao.readPhotoThumb(this);
	}
	
	public BlobData readCoverThumb() throws Exception
	{
		return dao.readCoverThumb(this);
	}

	public boolean isContactFor(User user) throws Exception
	{
		try {
			contactFinder.getContact(getId(), user.getId());
			return true;
		} catch (ElementNotFoundException x) {
			return false;
		}
	}

	public String getFullName()
	{
		return getFirstName() + " " + getLastName();
	}

	public void storePhoto(InputStream data) throws Exception
	{
		dao.storePhoto(this, data);
	}

	public void storePhoto(MultipartFile data) throws Exception
	{
		dao.storePhoto(this, data);
	}

	public String getPhotoContentType() throws Exception
	{
		return dao.getPhotoContentType(this);
	}

	public void clearPhoto() throws Exception
	{
		dao.clearPhoto(this);
	}
	
	public void storeCover(InputStream data) throws Exception
	{
		dao.storeCover(this, data);
	}

	public void storeCover(MultipartFile data) throws Exception
	{
		dao.storeCover(this, data);
	}

	public String getCoverContentType() throws Exception
	{
		return dao.getCoverContentType(this);
	}

	public void clearCover() throws Exception
	{
		dao.clearCover(this);
	}


	public final void setDao(UserDao dao)
	{
		this.dao = dao;
	}

	public QueryScroller listMessages(String order) throws Exception
	{
		return messageDao.listMessagesForUserByDate(this,order);
	}

	public QueryScroller listReceivedMessages(String order) throws Exception
	{
		return messageDao.listReceivedMessagesForUserByDate(this,order);
	}
	public QueryScroller listUnreadMessages(String order) throws Exception
	{
		return messageDao.listUnreadMessagesForUserByDate(this,order);
	}
	public int getMessageCount(String msgType) throws Exception
	{
		return messageDao.getMessageCount(this,msgType);
	}
	public QueryScroller getListOfMessageIds(String msgType) throws Exception
	{
		return messageDao.getListOfMessageIds(this,msgType);
	}
	public void deleteAllMessage() throws Exception
	{
		messageDao.deleteAllMessage(this.getId());
	}
	public void archiveAllMessage() throws Exception
	{
		List<Message> list =  messageDao.listMessagesToArchive(this.getId());
		for (Message msg : list) {
			msg.setDeleteFlag(com.era.community.common.Constants.FLAG_1); // For Archive Message
			msg.setAlreadyRead(false);
			msg.update();
		}
	}
	
	public void restoreAllMessage() throws Exception
	{
		List<Message> list =  messageDao.listMessagesToRestore(this.getId());
		for (Message msg : list) {
			msg.setDeleteFlag(com.era.community.common.Constants.FLAG_0); // For Archive Message
			msg.setAlreadyRead(false);
			msg.update();
		}
	}
	
	public List<Message> listAllMessagesToReadUnread() throws Exception
	{
		return messageDao.listAllMessagesToReadUnread(this.getId());
	}
	
	public QueryScroller listSentMessages(String order) throws Exception
	{
		return messageDao.listSentMessagesForUserByDate(this,order);
	}
	public QueryScroller listArchivedMessages(String order) throws Exception
	{
		return messageDao.listArchivedMessagesForUserByDate(this,order);
	}
	public int getNewMessageCountAfterLogin() throws Exception
	{
		return messageDao.getNewMessageCountAfterLogin(this);
	}
	public QueryScroller getMyProfileVisitorList(String filterBy, String sortBy) throws Exception
	{
		return profileVisitFinder.getUnseenProfileVisitorListForCurrentUser(this.getId(), filterBy, sortBy);
	}
	public final String getEmailAddress()
	{
		return EmailAddress;
	}

	public final void setEmailAddress(String emailAddress)
	{
		EmailAddress = emailAddress;
	}

	public final String getFirstName()
	{
		return FirstName;
	}

	public final void setFirstName(String firstName)
	{
		FirstName = firstName;
	}

	public final String getLastName()
	{
		return LastName;
	}

	public final void setLastName(String lastName)
	{
		LastName = lastName;
	}

	public final boolean isInactive()
	{
		return Inactive;
	}

	public final void setInactive(boolean inactive)
	{
		Inactive = inactive;
	}

	public final void setPassword(String password)
	{
		Password = password;
	}

	public final Timestamp getDateRegistered()
	{
		return DateRegistered;
	}

	public final void setDateRegistered(Timestamp dateRegistered)
	{
		DateRegistered = dateRegistered;
	}

	public final String getPassword()
	{
		return Password;
	}

	public void setUserExpertiseLinkDao(UserExpertiseLinkDao userExpertiseLinkDao)
	{
		this.userExpertiseLinkDao = userExpertiseLinkDao;
	}

	public boolean isSystemAdministrator()
	{
		return SystemAdministrator;
	}

	public void setSystemAdministrator(boolean systemAdministrator)
	{
		SystemAdministrator = systemAdministrator;
	}

	public final boolean isValidated()
	{
		return Validated;
	}

	public final void setValidated(boolean validated)
	{
		Validated = validated;
	}

	public boolean isMsgAlert()
	{
		return MsgAlert;
	}

	public void setMsgAlert(boolean msgAlert)
	{
		MsgAlert = msgAlert;
	}

	public final void setContactFinder(ContactFinder contactFinder)
	{
		this.contactFinder = contactFinder;
	}

	public final void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setTagFinder(TagFinder tagFinder)
	{
		this.tagFinder = tagFinder;
	}

	public boolean isSuperAdministrator()
	{
		return SuperAdministrator;
	}

	public void setSuperAdministrator(boolean superAdministrator)
	{
		SuperAdministrator = superAdministrator;
	}

	public Timestamp getDateLastVisit()
	{
		return DateLastVisit;
	}

	public void setDateLastVisit(Timestamp dateLastVisit)
	{
		DateLastVisit = dateLastVisit;
	}

	public Contact getContact(int contactOwnerId, int contactId) throws Exception
	{
		return contactFinder.getContact(contactOwnerId, contactId);
	}

	public void setMessageDao(MessageDao messageDao)
	{
		this.messageDao = messageDao;
	}

	public int getConnectionCount() {
		return ConnectionCount;
	}

	public void setConnectionCount(int connectionCount) {
		ConnectionCount = connectionCount;
	}

	public Timestamp getDateDeactivated() {
		return DateDeactivated;
	}

	public void setDateDeactivated(Timestamp dateDeactivated) {
		DateDeactivated = dateDeactivated;
	}

	public String getProfileName() {
		return ProfileName;
	}

	public void setProfileName(String profileName) {
		ProfileName = profileName;
	}

	public void setProfileVisitFinder(ProfileVisitFinder profileVisitFinder) {
		this.profileVisitFinder = profileVisitFinder;
	}

	public String getFirstKey() {
		return FirstKey;
	}

	public void setFirstKey(String firstKey) {
		FirstKey = firstKey;
	}

	public String getChangeKey() {
		return ChangeKey;
	}

	public void setChangeKey(String changeKey) {
		ChangeKey = changeKey;
	}

	public void setPhotoFinder(PhotoFinder photoFinder) {
		this.photoFinder = photoFinder;
	}

	public int getWidth() {
		return Width;
	}

	public void setWidth(int width) {
		Width = width;
	}

	public int getHeight() {
		return Height;
	}

	public void setHeight(int height) {
		Height = height;
	}

	public void setEventFinder(EventFinder eventFinder) {
		this.eventFinder = eventFinder;
	}
}