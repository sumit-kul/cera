package com.era.community.doclib.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="DocGroup"
 */
public class DocGroup extends CecAbstractEntity
{
	private int DocumentId;
	private int FolderId;
	
	private DocGroupDao dao;

	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user)
	{
		if (user != null)
			return true;
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

	public void setDao(DocGroupDao dao) {
		this.dao = dao;
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
}