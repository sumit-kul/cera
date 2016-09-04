package com.era.community.doclib.dao; 

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.doclib.ui.dto.FolderDto;

public interface FolderFinder extends com.era.community.doclib.dao.generated.FolderFinderBase
{
	public List<FolderDto> listAlbumsForLibrary(int libraryId, int folderId) throws Exception;
	public List<FolderDto> listOtherFoldersForLibrary(int libraryId, int folderId) throws Exception;
	public FolderDto extraInfoForFolder(int folderId) throws Exception;
	public Folder getCommunityAlbumForLibrary(int libraryId) throws Exception;
	public Folder getCommunityAlbumForBanner(int libraryId) throws Exception;
	public Folder getCommunityAlbumForBlog(int libraryId) throws Exception;
	public Folder getCommunityAlbumForForum(int libraryId) throws Exception;
	public Folder getCommunityAlbumForWiki(int libraryId) throws Exception;
	
	public int countFoldersForLibrary(int libraryId) throws Exception;
	public QueryScroller listFoldersForLibrary(int libraryId) throws Exception;
}