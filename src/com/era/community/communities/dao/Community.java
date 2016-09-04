package com.era.community.communities.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.multipart.MultipartFile;

import support.community.application.ElementNotFoundException;
import support.community.database.BlobData;
import support.community.database.QueryScroller;

import com.era.community.base.CommunityEraContext;
import com.era.community.connections.communities.dao.MemberInvitationFinder;
import com.era.community.connections.communities.dao.MemberList;
import com.era.community.connections.communities.dao.MemberListFeature;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;
import com.era.community.search.dao.SearchSite;
import com.era.community.search.dao.SearchSiteFinder;
import com.era.community.tagging.dao.Tag;
import com.era.community.tagging.dao.TaggedEntity;
import com.era.community.tagging.dao.generated.TagEntity;
import com.era.community.tagging.ui.dto.TagDto;

/**
 * @entity name="COMM" 
 */
public abstract class Community extends TaggedEntity
{
	/**
	 * @column integer  
	 */
	protected Integer ParentId;

	/**
	 * @column varchar(150) not null with default
	 */
	protected String Name;

	/**
	 * @column varchar(600) not null with default
	 */
	protected String WelcomeText = "Welcome text";

	/**
	 * @column char(1) not null with default
	 */
	protected boolean IncludeParentMembers;

	/**
	 * @column integer not null with default
	 */
	protected int CreatorId;
	
	/**
	 * @cera.column integer not null
	 */
	protected int VisitCount;
	
	protected boolean Featured;

	protected Date CommunityUpdated;

	public static final int STATUS_ACTIVE = 0;
	public static final int STATUS_UNAPPROVED = 1;
	public static final int STATUS_REJECTED = 2;
	public static final int STATUS_INACTIVE = 3;

	/**
	 * @column integer not null with default
	 */
	protected int Status = 0;
	
	private int Width;
	private int Height;

	protected CommunityDao dao;
	protected CommunityMembershipDomainDao domainDao;
	protected MemberListFeature memberListFeature;
	protected UserFinder userFinder;
	protected SearchSiteFinder searchSiteFinder;
	protected CommunityJoiningRequestFinder communityJoiningRequestFinder;
	protected MemberInvitationFinder memberInvitationFinder;

	public abstract String getCommunityType();
	public abstract boolean isPrivate();
	public abstract boolean isProtected();
	public abstract boolean userCanJoinWithoutApproval(User user) throws Exception;

	/**
	 * Update or insert this entity in the database.
	 */
	public void update() throws Exception
	{
		dao.store(this); 
	}

	/** 
	 *  Delete this entity from the database.
	 */
	public void delete() throws Exception
	{
		dao.delete(this);
	}

	public boolean isLogoPresent() throws Exception
	{
		return dao.isLogoPresent(this);
	}
	
	public boolean isBannerPresent() throws Exception
	{
		return dao.isBannerPresent(this);
	}

	public BlobData getCommunityBanner() throws Exception
	{
		return dao.readCommunityBanner(this);
	}
	
	public BlobData getCommunityBannerThumb() throws Exception
	{
		return dao.readCommunityBannerThumb(this);
	}
	
	public BlobData getCommunityLogo() throws Exception
	{
		return dao.readCommunityLogo(this);
	}
	
	public BlobData getCommunityLogoThumb() throws Exception
	{
		return dao.readCommunityLogoThumb(this);
	}
	
	public Community getParentCommunity() throws Exception
	{
		if (getParentId()==null) return null;
		return dao.getCommunityForId(getParentId().intValue());
	}

	public int[] getAncestorIds() throws Exception
	{
		int[] ids = new int[0];
		for (Community c =getParentCommunity(); c!=null; c=c.getParentCommunity())
			ArrayUtils.add(ids, c.getId());
		return ids;
	}   

	public QueryScroller listUsersRequestingMembership(String filterOption, String sortOption) throws Exception
	{
		return dao.listUsersRequestingMembership(this, filterOption, sortOption);
	}

	public QueryScroller listUsersRejectedMembership() throws Exception
	{
		return dao.listUsersRejectedMembership(this);
	}

	public User getCreator() throws Exception
	{
		return userFinder.getUserEntity(getCreatorId());
	}

	public Community[] getAncestors() throws Exception
	{
		List<Community> list = new ArrayList<Community>(8);
		for (Community c =getParentCommunity(); c!=null; c=c.getParentCommunity())
			list.add(c);
		Community[] a = new Community[list.size()];
		for (int n=0; n<a.length; n++) a[n] = (Community)list.get(a.length-(n+1));
		return a;
	}   

	public String[] getAdminMemberEmailAddresses() throws Exception
	{
		MemberList mlist = getMemberList();
		if (mlist != null) {
			List admins = getMemberList().getAdminMembers();
			String[] list = new String[admins.size()];
			for (int i=0; i<list.length; i++) list[i] = ((User)admins.get(i)).getEmailAddress();
			return list;
		}
		return new String[0];
	}

	public String[] getNonAdminMemberEmailAddresses() throws Exception
	{
		MemberList mlist = getMemberList();
		if (mlist != null) {
			List admins = getMemberList().getAdminMembers();
			List nonAdmins = getMemberList().getRecentMembers();

			String[] list = new String[nonAdmins.size()];
			for (int i=0; i<list.length; i++) {
				User member =(User)nonAdmins.get(i);

				if (admins.contains(member.getEmailAddress())==false)

					list[i] = member.getEmailAddress();
			}
			return list;
		}
		return new String[0];
	}

	public void setAdminMemberEmailAddresses(String[] addresses) throws Exception
	{
		MemberList mlist = getMemberList();
		if (mlist != null) {

			List admins = getMemberList().getAdminMembers();
			for (int n=0; n<admins.size(); n++) 
				mlist.setMemberRole((User)admins.get(n), CommunityRoleConstants.MEMBER);
			for (int i=0; i<addresses.length; i++) {
				User user = userFinder.getUserForEmailAddress(addresses[i]);
				if (mlist.isMember(user))
					mlist.setMemberRole(user, CommunityRoleConstants.ADMIN);
				else
					mlist.addMember(user, CommunityRoleConstants.ADMIN);
			}
		}
	}

	public List<SearchSite> getSearchSites() throws Exception
	{
		return searchSiteFinder.getSitesForCommunity(this);
	}

	public String[] getSearchSiteHostnames() throws Exception
	{
		List<String> hosts = new ArrayList<String>(64);
		for (SearchSite site : getSearchSites()) hosts.add(site.getHostname());
		return hosts.toArray(new String[hosts.size()]);
	}

	public boolean isSearchSitePresent() throws Exception
	{
		return !getSearchSites().isEmpty();
	}

	public MemberList getMemberList() throws Exception
	{
		return (MemberList)memberListFeature.getFeatureForCommunity(this);
	}

	/*public List<ContentItemDto> getRecentItems() throws Exception
	{
		return dao.getRecentCommunityItems(this.getId());
	}*/

	public List getRecentMemberList() throws Exception
	{
		MemberList mlist = getMemberList();
		if (mlist != null) {
			return mlist.getRecentMembers();
		}
		return null;
	}

	public List getRecentMemberNames() throws Exception
	{
		MemberList mlist = getMemberList();
		if (mlist != null) {
			return mlist.getRecentMemberNames();
		}
		return null;
	}

	public int getMemberCount() throws Exception
	{
		MemberList mlist = getMemberList();
		if (mlist != null) {
			return mlist.getMemberCount();
		}
		return 0;
	}

	public int getMemberCountAt(Date date) throws Exception
	{
		MemberList mlist = getMemberList();
		if (mlist != null) {
			return mlist.getMemberCountAt(date);
		}
		return 0;
	}

	public int getContributorCount() throws Exception
	{
		return dao.getContributorCount(this);
	}

	public boolean isMember(User user) throws Exception
	{
		//if (user.isSystemAdministrator() || user.isSuperAdministrator()) return true;
		MemberList mlist = getMemberList();
		if (mlist != null) {
			return mlist.isMember(user);
		}
		return false;
	}

	public boolean isAdminMember(User user) throws Exception
	{
		MemberList mlist = getMemberList();
		if (mlist != null) {
			return mlist.isAdminMember(user);
		}
		return false;
	}

	public boolean isCommunityOwner(User user) throws Exception
	{
		MemberList mlist = getMemberList();
		if (mlist != null) {
			return mlist.isCommunityOwner(user.getId());
		}
		return false;
	}

	public boolean isMember(int userId) throws Exception
	{
		MemberList mlist = getMemberList();
		if (mlist != null) {
			return mlist.isMember(userId);
		}
		return false;
	}

	public boolean isMemberShipRequestPending(User user) throws Exception
	{
		try {
			CommunityJoiningRequest joiningRequest = communityJoiningRequestFinder.getRequestForUserAndCommunity(user.getId(), this.getId());
			if (joiningRequest.Status == CommunityJoiningRequest.STATUS_UNAPPROVED)
				return true;
			else 
				return false;
		}
		catch (ElementNotFoundException e) {
			return false;
		}
	}

	public int getMemberShipRequestCount() throws Exception
	{
		int joiningRequestCount = communityJoiningRequestFinder.countPendingJoiningRequestForCommunity(this.getId());
		return joiningRequestCount;
	}

	public int getMemberInvitationCount() throws Exception
	{
		int memberInvitationCount = memberInvitationFinder.countMemberInvitationsForCommunity(this.getId());
		return memberInvitationCount;
	}

	public boolean isAdminMember(int userId) throws Exception
	{
		return getMemberList().isAdminMember(userId);
	}

	public String getDescription() throws Exception
	{
		return dao.getDescription(this);
	}

	public void setDescription(String value) throws Exception
	{
		dao.setDescription(this, value);
	}

	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		return true; 
	}

	public final String getName()
	{
		return Name;
	}

	public final void setName(String name)
	{
		Name = name;
	}

	public final Integer getParentId()
	{
		return ParentId;
	}

	public final void setParentId(Integer parentId)
	{
		ParentId = parentId;
	}

	public final String getWelcomeText()
	{
		return WelcomeText;
	}

	public final void setWelcomeText(String welcomeText)
	{
		WelcomeText = welcomeText;
	}

	public final boolean isIncludeParentMembers()
	{
		return IncludeParentMembers;
	}

	public final void setIncludeParentMembers(boolean includeParentMembers)
	{
		IncludeParentMembers = includeParentMembers;
	}

	public final void setSysType(Object obj, Object value) throws Exception
	{
		dao.setSysType(obj, "SysType", value);
	}

	public final void setDao(CommunityDao dao)
	{
		this.dao = dao;
	}

	public void setMemberListFeature(MemberListFeature memberListFeature)
	{
		this.memberListFeature = memberListFeature;
	}

	public final void setDomainDao(CommunityMembershipDomainDao domainDao)
	{
		this.domainDao = domainDao;
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	} 

	public final int getCreatorId()
	{
		return CreatorId;
	}

	public final void setCreatorId(int creatorId)
	{
		CreatorId = creatorId;
	}

	public final int getStatus()
	{
		return Status;
	}

	public final void setStatus(int status)
	{
		Status = status;
	}

	public String getTagTypeAheadJSArray() throws Exception 
	{
		List tags = tagFinder.getTagsForAllCommunityByPopularity(getId(), 0, "Community");
		StringBuffer tagJSArray = new StringBuffer();

		for (int i=0; i<tags.size(); i++) {
			TagEntity tag = (TagEntity) tags.get(i);
			if (tagJSArray.length() > 0) tagJSArray.append(", ");
			tagJSArray.append("'" + tag.getTagText() + "'");
		}

		String retVal = "tagarray = new Array(" + tagJSArray.toString() + ");";		
		return retVal;
	}
	
	public String getKeywords() throws Exception 
	{
		String keywords = "";
		List tags = tagFinder.getTagsForOnlyCommunityByPopularity(getId(), 0);

		for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
			TagDto tb = (TagDto) iterator.next();
			String tag = tb.getTagText();
			keywords += tag;
			if (iterator.hasNext())keywords += " , ";
		}
		return keywords;
	}

	public List getMostPopularTags(int max) throws Exception
	{
		return tagFinder.getTagsForAllCommunityByPopularity(getId(), max, "Community"); 
	}

	public TreeMap getMostPopularTagsSorted() throws Exception
	{
		TreeMap<String, TagDto> treemap = new TreeMap<String, TagDto>();		

		List tagList = tagFinder.getTagsForAllCommunityByPopularity(getId(), 20, "Community");

		int maxPopularity  = 0;
		int minPopularity = 0;
		int noOfSets = 5;    

		for (int i=0; i<tagList.size(); i++) {
			TagDto tag = (TagDto) tagList.get(i);
			if (tag.getCount() > maxPopularity) {
				maxPopularity = tag.getCount().intValue();
			}
			if (tag.getCount() < minPopularity || minPopularity == 0) {
				minPopularity = tag.getCount().intValue();
			}						
		}

		int setSize = (int)Math.round( (double)(maxPopularity - minPopularity) / noOfSets);
		if (setSize == 0)	{
			setSize = 1;
		}

		for (int i=0; i<tagList.size(); i++) {
			TagDto tag = (TagDto) tagList.get(i);
			int cloudSet = Math.min(noOfSets,((tag.getCount().intValue() / setSize) - minPopularity) + 1);
			tag.setCloudSet(cloudSet);
			treemap.put(tag.getTagText(), tag);
		}		
		return treemap;
	}

	public void setTags(String tagString) throws Exception
	{		
		CommunityEraContext context = getCommunityEraContext();

		clearTagsForUser(context.getCurrentUser().getId());
		StringTokenizer st = new StringTokenizer(tagString, " ");
		while (st.hasMoreTokens()) {
			String tag = st.nextToken().trim().toLowerCase();

			Tag newTag = tagFinder.newTag();
			newTag.setCommunityId(getId());
			newTag.setTagText(tag);			
			newTag.setPosterId(context.getCurrentUser().getId());
			newTag.setParentId(getId());
			newTag.setParentType(this.getClass().getSimpleName());
			newTag.update();	
		}			
	}

	public List getItemsForTag( String tag ) throws Exception 
	{
		return tagFinder.getItemsForTagInCommunity(getId(), tag);
	}

	public List getTagsForOnlyCommunityByPopularity(int max) throws Exception
	{
		return tagFinder.getTagsForOnlyCommunityByPopularity(getId(), max); 
	}

		public void storeCommunityLogo(MultipartFile data) throws Exception
	{
		dao.storeCommunityLogo(this, data);
	}
	
	public void storeCommunityLogo(InputStream data) throws Exception
	{
		dao.storeCommunityLogo(this, data);
	}

	public void clearLogo() throws Exception
	{
		dao.clearLogo(this);
	}
	
	public void storeCommunityBanner(MultipartFile data) throws Exception
	{
		dao.storeCommunityBanner(this, data);
	}
	
	public void storeCommunityBanner(InputStream data) throws Exception
	{
		dao.storeCommunityBanner(this, data);
	}

	public void clearBanner() throws Exception
	{
		dao.clearBanner(this);
	}

	public final void setSearchSiteFinder(SearchSiteFinder searchSiteFinder)
	{
		this.searchSiteFinder = searchSiteFinder;
	}

	public void setCommunityJoiningRequestFinder(CommunityJoiningRequestFinder communityJoiningRequestFinder)
	{
		this.communityJoiningRequestFinder = communityJoiningRequestFinder;
	}
	
	public void setMemberInvitationFinder(
			MemberInvitationFinder memberInvitationFinder) {
		this.memberInvitationFinder = memberInvitationFinder;
	}
	
	public Date getCommunityUpdated() {
		return CommunityUpdated;
	}
	
	public void setCommunityUpdated(Date communityUpdated) {
		CommunityUpdated = communityUpdated;
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
	
	public int getVisitCount() {
		return VisitCount;
	}
	
	public void setVisitCount(int visitCount) {
		VisitCount = visitCount;
	}
	
	public boolean isFeatured()
    {
        return Featured;
    }

    public void setFeatured(boolean featured)
    {
        Featured = featured;
    }   
}