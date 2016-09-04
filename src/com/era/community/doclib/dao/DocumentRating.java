package com.era.community.doclib.dao;

import java.util.Date;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 *
 * @entity name="DOCR"
 * 
 *  @entity.index name="01" unique="no" columns="DocumentId"   
 *  
 * @entity.foreignkey name="01" columns="DocumentId" target="DCMT" ondelete="cascade"  
 * @entity.foreignkey name="02" columns="PosterId" target="USER" ondelete="cascade"  
 * 
 */
public class DocumentRating extends CecAbstractEntity
{
	/**
	 * @column integer not null
	 */
	protected int DocumentId;

	/**
	 * @column integer not null
	 */
	protected int PosterId ;

	/**
	 * @column timestamp not null with default
	 */
	protected Date DatePosted;


	/**
	 * @column integer not null
	 */
	protected int Stars ;


	/*
	 * Injected references.
	 */
	protected DocumentRatingDao dao;
	private DocumentFinder documentFinder;



	public Date getDatePosted()
	{
		return DatePosted;
	}

	public void setDatePosted(Date datePosted)
	{
		DatePosted = datePosted;
	}

	public int getDocumentId()
	{
		return DocumentId;
	}

	public final int getStars()
	{
		return Stars;
	}

	public final void setStars(int stars)
	{
		Stars = stars;
	}

	public void setDocumentId(int documentId)
	{
		DocumentId = documentId;
	}

	public int getPosterId()
	{
		return PosterId;
	}

	public void setPosterId(int posterId)
	{
		PosterId = posterId;
	}

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		CommunityEraContext context = getCommunityEraContext();
		return context.getCurrentCommunity().isMember(user.getId());
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		CommunityEraContext context = getCommunityEraContext();
		return context.getCurrentCommunity().isMember(user.getId());
	}

	public Document getParentDocument() throws Exception
	{
		return documentFinder.getDocumentForId(getDocumentId());
	}

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	} 



	public final DocumentRatingDao getDao()
	{
		return dao;
	}

	public final void setDao(DocumentRatingDao dao)
	{
		this.dao = dao;
	}

	public final void setDocumentFinder(DocumentFinder documentFinder)
	{
		this.documentFinder = documentFinder;
	}



}
