package com.era.community.doclib.dao;

import java.util.Date;
import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.base.CecAbstractEntity;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 * @entity name="DLIB" 
 *
 * @entity.index name="01" unique="no" columns="CommunityId"   
 * 
 * @entity.foreignkey name="01" columns="CommunityId" target="COMM" ondelete="cascade"  
 */
public class DocumentLibrary extends CecAbstractEntity
{
	/**
	 * @column integer not null
	 */
	private int CommunityId;

	/**
	 * @column varchar(150) not null with default
	 */
	protected String Name;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Inactive = false;

	/*
	 * Injected  references.
	 */
	protected DocumentLibraryDao dao; 
	protected DocumentLibraryFinder documentLibraryFinder;
	protected CommunityFinder communityFinder;

	public List getItemsSince(Date date) throws Exception
	{
		return dao.getItemsSince(this, date);
	}

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public Date getLatestPostDate() throws Exception
	{
		return dao.getLatestPostDate(this);
	}

	public Community getCommunity() throws Exception
	{
		return communityFinder.getCommunityForId(getCommunityId());
	}

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		if ("Private".equals(getCommunity().getCommunityType())) {
			return getCommunity().isMember(user.getId());
		} else {
			return true;
		}
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		if ("Private".equals(getCommunity().getCommunityType())) {
			return getCommunity().isMember(user.getId());
		} else {
			return true;
		} 
	}

	public QueryScroller listDocumentsByDate(int themeId, Class beanClass, boolean ignoreThemes) throws Exception
	{
		return dao.listDocumentsByDate(this, beanClass, themeId, ignoreThemes);
	}

	public QueryScroller listDocumentsByTitle(int themeId, Class beanClass, boolean ignoreThemes) throws Exception
	{
		return dao.listDocumentsByTitle(this,  beanClass, themeId, ignoreThemes);
	}

	public QueryScroller listDocumentsByAuthor(int themeId, Class beanClass, boolean ignoreThemes) throws Exception
	{
		return dao.listDocumentsByAuthor(this, beanClass, themeId, ignoreThemes);
	}

	public QueryScroller listDocumentsByRating(int themeId, Class beanClass,  boolean ignoreThemes) throws Exception
	{
		return dao.listDocumentsByRating(this, beanClass,  themeId, ignoreThemes);
	}

	public QueryScroller listDocumentsForCurrentUser(User user, Class beanClass, int themeId,  boolean ignoreThemes) throws Exception
	{
		return dao.listDocumentsForUser(this, beanClass, user, themeId, ignoreThemes);
	}

	public int getUnThemedDocumentCount() throws Exception
	{
		Community comm = communityFinder.getCommunityForId(this.getCommunityId());
		DocumentLibrary documentLibrary = documentLibraryFinder.getDocumentLibraryForCommunity(comm);

		return dao.getUnThemedDocumentCount(documentLibrary);
	}

	public final void setDao(DocumentLibraryDao dao)
	{
		this.dao = dao;
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

	public final void setDocumentLibraryFinder(DocumentLibraryFinder documentLibraryFinder)
	{
		this.documentLibraryFinder = documentLibraryFinder;
	}

	public final void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}
}