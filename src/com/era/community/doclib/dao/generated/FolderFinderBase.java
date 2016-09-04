package com.era.community.doclib.dao.generated;

import com.era.community.doclib.dao.Folder;

public interface FolderFinderBase
{
	public Folder getFolderForId(int id) throws Exception;
	public Folder newFolder() throws Exception;
}