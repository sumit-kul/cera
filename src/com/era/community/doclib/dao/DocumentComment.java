package com.era.community.doclib.dao;

import java.util.Date;
import java.util.StringTokenizer;

import support.community.application.ElementNotFoundException;

import com.era.community.base.CommunityEraContext;
import com.era.community.pers.dao.generated.UserEntity;
import com.era.community.tagging.dao.Tag;
import com.era.community.tagging.dao.TaggedEntity;

/**
 *
 * @entity name="DCOM"
 * 
 *  @entity.index name="01" unique="no" columns="DocumentId"   
 *  
 * @entity.foreignkey name="01" columns="DocumentId" target="DCMT" ondelete="cascade"  
 * 
 */
public class DocumentComment extends TaggedEntity
{
	/**
	 * @column integer not null
	 */
	protected int DocumentId;

	/**
	 * @column long varchar not null with default
	 */
	protected String Comment = "";

	/**
	 * @column timestamp not null with default
	 */
	protected Date DatePosted;

	/**
	 * @column integer not null
	 */
	protected int PosterId ;

	/*
	 * Injected references.
	 */
	protected DocumentCommentDao dao;
	private DocumentFinder documentFinder;
	private DocumentCommentLikeFinder documentCommentLikeFinder;


	// For a document comment, the tags will apply to the parent
	public void setTags(String tagString) throws Exception
	{       

		CommunityEraContext context = getCommunityEraContext();

		clearTagsForUser(context.getCurrentUser().getId());            // Clear the tags for this entity and user

		/* Parse tag string */
		StringTokenizer st = new StringTokenizer(tagString, " ");

		while (st.hasMoreTokens()) {
			/* Create new tag */
			String tag = st.nextToken().trim().toLowerCase();
			Tag newTag = tagFinder.newTag();
			newTag.setCommunityId(context.getCurrentCommunity().getId());
			newTag.setTagText(tag);         
			newTag.setPosterId(context.getCurrentUser().getId());
			newTag.setParentId(this.getParentDocument().getId());
			newTag.setParentType(this.getParentDocument().getClass().getSimpleName());  // e.g. WikiEntry, Document, BlogEntry
			newTag.update();            
		}           

	}


	public String getComment()
	{
		return Comment;
	}

	public void setComment(String comment)
	{
		Comment = comment;
	}

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
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		if (getPosterId()==user.getId()) return true;
		return false;
	}

	public boolean isAlreadyLike(int commentId, int userId) throws Exception {
		try {
			DocumentCommentLike documentCommentLike = documentCommentLikeFinder.getLikeForDocumentCommentAndUser(this.getId(), commentId, userId);
			return true;
		}
		catch (ElementNotFoundException e) {
			return false;
		}
	}


	public Document getParentDocument() throws Exception
	{
		return documentFinder.getDocumentForId(getDocumentId());
	}

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

	public final void setDao(DocumentCommentDao dao)
	{
		this.dao = dao;
	}

	public final DocumentCommentDao getDao()
	{
		return dao;
	}

	public final void setDocumentFinder(DocumentFinder documentFinder)
	{
		this.documentFinder = documentFinder;
	}


	public void setDocumentCommentLikeFinder(
			DocumentCommentLikeFinder documentCommentLikeFinder) {
		this.documentCommentLikeFinder = documentCommentLikeFinder;
	}   

}
