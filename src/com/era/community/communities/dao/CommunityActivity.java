package com.era.community.communities.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 *
 * @entity name="CACT"
 * 
 */
public class CommunityActivity extends CecAbstractEntity
{
    protected int CommunityId;
    protected int DocumentId ;
    protected int FolderId ;
    protected int DocGroupNumber ;
    protected int BlogEntryId ;
    protected int ForumItemId ;
    protected int WikiEntryId ;
    protected int EventId ;
    protected int ItemStatus;
    protected int AssignmentId ;
    protected int UserId ;

    protected CommunityActivityDao dao;

    public boolean isReadAllowed(UserEntity user) throws Exception
    {
        if (user==null) return false;
        return true;
   }
    
    public boolean isWriteAllowed(UserEntity user) throws Exception
    {
        return true;
    }
    
    public void update() throws Exception
    {
       dao.store(this); 
    }

    public void delete() throws Exception
    {
        dao.delete(this);
    } 

    public final void setDao(CommunityActivityDao dao)
    {
        this.dao = dao;
    }
        
    public final CommunityActivityDao getDao()
    {
        return dao;
    }

	public int getCommunityId() {
		return CommunityId;
	}

	public void setCommunityId(int communityId) {
		CommunityId = communityId;
	}

	public int getDocumentId() {
		return DocumentId;
	}

	public void setDocumentId(int documentId) {
		DocumentId = documentId;
	}

	public int getFolderId() {
		return FolderId;
	}

	public void setFolderId(int folderId) {
		FolderId = folderId;
	}

	public int getDocGroupNumber() {
		return DocGroupNumber;
	}

	public void setDocGroupNumber(int docGroupNumber) {
		DocGroupNumber = docGroupNumber;
	}

	public int getBlogEntryId() {
		return BlogEntryId;
	}

	public void setBlogEntryId(int blogEntryId) {
		BlogEntryId = blogEntryId;
	}

	public int getForumItemId() {
		return ForumItemId;
	}

	public void setForumItemId(int forumItemId) {
		ForumItemId = forumItemId;
	}

	public int getWikiEntryId() {
		return WikiEntryId;
	}

	public void setWikiEntryId(int wikiEntryId) {
		WikiEntryId = wikiEntryId;
	}

	public int getEventId() {
		return EventId;
	}

	public void setEventId(int eventId) {
		EventId = eventId;
	}

	public int getAssignmentId() {
		return AssignmentId;
	}

	public void setAssignmentId(int assignmentId) {
		AssignmentId = assignmentId;
	}

	public int getItemStatus() {
		return ItemStatus;
	}

	public void setItemStatus(int itemStatus) {
		ItemStatus = itemStatus;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}
    
}