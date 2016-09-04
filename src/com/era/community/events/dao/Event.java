package com.era.community.events.dao;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import support.community.application.ElementNotFoundException;
import support.community.database.BlobData;

import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;
import com.era.community.tagging.dao.TaggedEntity;

/**
 *
 * @entity name="EVNT" 
 *
 * @entity.index name="01" unique="no" columns="CalendarId"
 * @entity.index name="01" unique="no" columns="StartDate"
 * 
 * @entity.foreignkey name="01" columns="CalendarId" target="EVCL" ondelete="cascade"  
 * @entity.foreignkey name="02" columns="PosterId" target="USER" ondelete="restrict"  
 */
public class Event extends TaggedEntity
{   
	/**
	 * @column varchar(150) not null with default
	 */
	protected String Name;

	/**
	 * @column long varchar not null with default
	 */
	protected String Description;

	/**
	 * @column timestamp not null with default
	 */
	protected Date StartDate;

	/**
	 * @column timestamp not null with default
	 */
	protected Date EndDate;

	/**
	 * @column varchar(150) not null with default
	 */
	protected String Location;

	/**
	 * @column integer
	 */
	protected String Country;
	/**
	 * @column String
	 */
	protected String Cstate;
	/**
	 * @column String
	 */
	protected String City;
	/**
	 * @column String
	 */
	protected String Venue;
	/**
	 * @column String
	 */
	private String Address;
	/**
	 * @column integer
	 */
	protected String PostalCode;
	/**
	 * @column integer not null
	 */
	protected int PosterId;
	/**
	 * @column integer not null
	 */
	protected int Status;
	/**
	 * @column varchar(150) not null with default
	 */
	protected String Weblink;

	/**
	 * @column varchar(60) not null with default
	 */
	protected String ContactName;

	/**
	 * @column varchar(20) not null with default
	 */
	protected String ContactTel;

	/**
	 * @column varchar(60) not null with default
	 */
	protected String ContactEmail;

	/**
	 * @column integer not null
	 */
	protected int CalendarId;
	
	private int PhoneCodeId;
	
	private String Rollup;
	private String Ticket;
	private int EventType;
	private int EventCategory;

	/**
	 * @column char(1) not null with default
	 */
	//protected boolean Rollup = false;

	protected int Invited;
	protected int MayBe;
	protected int Confirmed;
	protected int Declined;
	protected int HostCount;
	protected double Latitude;
	protected double Longitude;
	private int Width;
	private int Height;

	protected EventDao dao;     
	protected EventCalendarFinder calendarFinder;
	protected UserFinder userFinder;
	protected EventLikeFinder eventLikeFinder;
	protected EventInviteeLinkFinder eventInviteeLinkFinder;

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public boolean isAlreadyLike(int userId) throws Exception {
		try {
			EventLike eventLike = eventLikeFinder.getLikeForEventAndUser(this.getId(), userId);
			return true;
		}
		catch (ElementNotFoundException e) {
			return false;
		}
	}
	
	public boolean isInvitee(int userId) throws Exception {
		try {
			eventInviteeLinkFinder.getEventInviteeLinkForEventAndUser(this.getId(), userId);
			return true;
		} catch (ElementNotFoundException e) {
			return false;
		}
	}
	
	public boolean isReplied(int userId) throws Exception {
		try {
			EventInviteeLink link = eventInviteeLinkFinder.getEventInviteeLinkForEventAndUser(this.getId(), userId);
			return (link.getJoiningStatus() == 0)? false : true;
		}
		catch (ElementNotFoundException e) {
			return true;
		}
	}

	public void storePhoto(MultipartFile data) throws Exception
	{
		dao.storePhoto(this, data);
	}

	public BlobData readPhoto() throws Exception
	{
		return dao.readPhoto(this);
	}

	public String getPhotoContentType() throws Exception
	{
		return dao.getPhotoContentType(this);
	}

	public boolean isPhotoPresent() throws Exception
	{
		return dao.isPhotoPresent(this);
	}

	public void clearPhoto() throws Exception
	{
		dao.clearPhoto(this);
	}

	public void delete() throws Exception
	{
		super.delete();
		dao.delete(this);
	} 

	public String getContactEmail()
	{
		return ContactEmail;
	}
	public void setContactEmail(String contactEmail)
	{
		ContactEmail = contactEmail;
	}
	public String getContactName()
	{
		return ContactName;
	}
	public void setContactName(String contactName)
	{
		ContactName = contactName;
	}
	public String getContactTel()
	{
		return ContactTel;
	}
	public void setContactTel(String contactTel)
	{
		ContactTel = contactTel;
	}
	public String getDescription()
	{
		return Description;
	}
	public void setDescription(String description)
	{
		Description = description;
	}
	public Date getEndDate()
	{
		return EndDate;
	}
	public void setEndDate(Date endDate)
	{
		EndDate = endDate;
	}
	public String getLocation()
	{
		return Location;
	}
	public void setLocation(String location)
	{
		Location = location;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String name)
	{
		Name = name;
	}
	public int getPosterId()
	{
		return PosterId;
	}
	public User getPoster() throws Exception
	{
		return userFinder.getUserEntity(getPosterId());
	}
	public void setPosterId(int posterId)
	{
		PosterId = posterId;
	}
	public Date getStartDate()
	{
		return StartDate;
	}
	public void setStartDate(Date startDate)
	{
		StartDate = startDate;
	}
	public String getWeblink()
	{
		return Weblink;
	}
	public void setWeblink(String weblink)
	{
		Weblink = weblink;
	}

	public int getCalendarId()
	{
		return CalendarId;
	}
	public void setCalendarId(int calendarId)
	{
		CalendarId = calendarId;
	} 

	/*public boolean isRollup() {
		return Rollup;
	}

	public void setRollup(boolean rollup) {
		Rollup = rollup;
	}
*/
	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		EventCalendar cal = calendarFinder.getEventCalendarForId(this.getCalendarId());
		return (cal.isReadAllowed(user));
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		if (getPosterId() == user.getId()) return true;
		Community comm = getCommunityEraContext().getCurrentCommunity();
		if (comm != null && comm.isAdminMember(user.getId())) return true;
		/*EventCalendar cal = calendarFinder.getEventCalendarForId(this.getCalendarId());
		return (cal.isWriteAllowed(user));*/
		return false;
	}

	public EventCalendar getEventCalendar() throws Exception
	{
		return calendarFinder.getEventCalendarForId(getCalendarId());
	}

	public void setDao(EventDao dao)
	{
		this.dao = dao;
	}

	public final void setCalendarFinder(EventCalendarFinder calendarFinder)
	{
		this.calendarFinder = calendarFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getCstate() {
		return Cstate;
	}

	public void setCstate(String cstate) {
		Cstate = cstate;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public void setEventLikeFinder(EventLikeFinder eventLikeFinder) {
		this.eventLikeFinder = eventLikeFinder;
	}

	public void setEventInviteeLinkFinder(
			EventInviteeLinkFinder eventInviteeLinkFinder) {
		this.eventInviteeLinkFinder = eventInviteeLinkFinder;
	}

	public int getInvited() {
		return Invited;
	}

	public void setInvited(int invited) {
		Invited = invited;
	}

	public int getMayBe() {
		return MayBe;
	}

	public void setMayBe(int mayBe) {
		MayBe = mayBe;
	}

	public int getConfirmed() {
		return Confirmed;
	}

	public void setConfirmed(int confirmed) {
		Confirmed = confirmed;
	}

	public int getDeclined() {
		return Declined;
	}

	public void setDeclined(int declined) {
		Declined = declined;
	}

	public int getHostCount() {
		return HostCount;
	}

	public void setHostCount(int hostCount) {
		HostCount = hostCount;
	}

	public String getVenue() {
		return Venue;
	}

	public void setVenue(String venue) {
		Venue = venue;
	}

	public String getPostalCode() {
		return PostalCode;
	}

	public void setPostalCode(String postalCode) {
		PostalCode = postalCode;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public int getPhoneCodeId() {
		return PhoneCodeId;
	}

	public void setPhoneCodeId(int phoneCodeId) {
		PhoneCodeId = phoneCodeId;
	}

	public String getRollup() {
		return Rollup;
	}

	public void setRollup(String rollup) {
		Rollup = rollup;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		Latitude = latitude.doubleValue();
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		Longitude = longitude.doubleValue();
	}

	public int getEventType() {
		return EventType;
	}

	public void setEventType(int eventType) {
		EventType = eventType;
	}

	public int getEventCategory() {
		return EventCategory;
	}

	public void setEventCategory(int eventCategory) {
		EventCategory = eventCategory;
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
}