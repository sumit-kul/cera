package com.era.community.doclib.dao; 

interface FolderDao extends com.era.community.doclib.dao.generated.FolderDaoBase, FolderFinder
{
	public boolean isPhotoPresent(Folder folder) throws Exception;
}