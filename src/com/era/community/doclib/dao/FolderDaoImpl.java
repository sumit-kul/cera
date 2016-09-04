package com.era.community.doclib.dao;

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.doclib.ui.dto.FolderDto;

public class FolderDaoImpl extends com.era.community.doclib.dao.generated.FolderDaoBaseImpl implements FolderDao
{
	public boolean isPhotoPresent(Folder folder) throws Exception
	{
		//Integer v = (Integer) getValue(album, "case when Photo is null then 0 else 1 end", Integer.class);
		//return v.intValue() == 1;
		return false;
	}

	public List<FolderDto> listAlbumsForLibrary(int libraryId, int folderId) throws Exception
	{
		String albNtIncl = "";
		if (folderId > 0) {
			albNtIncl = " and F.ID != ? and F.ProfileFolder != 1 and F.BannerFolder != 1 ";
		}
		String query = "select F.*, (select COUNT(D.ID) from Document D where D.FolderId = F.ID) photoCount " +
		" from Folder F where F.LibraryId = ? "+albNtIncl+" order by Created desc ";

		if (folderId > 0) {
			return getBeanList(query, FolderDto.class, libraryId, folderId);
		}

		return getBeanList(query, FolderDto.class, libraryId);
	}
	
	public QueryScroller listFoldersForLibrary(int libraryId) throws Exception
	{
		String query = "select F.*, (select COUNT(D.ID) from Document D where D.FolderId = F.ID) photoCount, U.FirstName, U.LastName" +
		" from Folder F, User U where F.OwnerId = U.Id and F.LibraryId = ? ";
		QueryScroller scroller = getQueryScroller(query, libraryId);
		return scroller;
	}

	public List<FolderDto> listOtherFoldersForLibrary(int libraryId, int folderId) throws Exception
	{
		String albNtIncl = "";
		if (folderId > 0) {
			albNtIncl = " and F.ID != ? ";
		}
		String query = "select F.*, (select COUNT(D.ID) from Document D where D.FolderId = F.ID) photoCount " +
		" from Folder F where F.LibraryId = ? "+albNtIncl+" order by Created desc ";

		if (folderId > 0) {
			return getBeanList(query, FolderDto.class, libraryId, folderId);
		}

		return getBeanList(query, FolderDto.class, libraryId);
	}

	public FolderDto extraInfoForFolder(int folderId) throws Exception
	{
		String query = "select F.*, (select COUNT(D.ID) from Document D where D.FolderId = F.ID) photoCount, U.FirstName, U.LastName,  " +
		" (select max(DatePosted) from Document where FolderId = ?) as updatedOn " +
		" from Folder F, User U where U.ID = F.OwnerId and F.Id = ? ";

		return getBean(query, FolderDto.class, folderId, folderId);
	}

	public Folder getCommunityAlbumForLibrary(int libraryId) throws Exception
	{
		String query = "select * from Folder where LibraryId = ? and ProfileFolder = 1 ";
		return getBean(query, Folder.class, libraryId);
	}

	public Folder getCommunityAlbumForBanner(int libraryId) throws Exception
	{
		String query = "select * from Folder where LibraryId = ? and BannerFolder = 1 ";
		return getBean(query, Folder.class, libraryId);
	}

	public Folder getCommunityAlbumForBlog(int libraryId) throws Exception
	{
		String query = "select * from Folder where LibraryId = ? and BlogFolder = 1 ";
		return getBean(query, Folder.class, libraryId);
	}

	public Folder getCommunityAlbumForForum(int libraryId) throws Exception
	{
		String query = "select * from Folder where LibraryId = ? and ForumFolder = 1 ";
		return getBean(query, Folder.class, libraryId);
	}

	public Folder getCommunityAlbumForWiki(int libraryId) throws Exception
	{
		String query = "select * from Folder where LibraryId = ? and WikiFolder = 1 ";
		return getBean(query, Folder.class, libraryId);
	}
	
	public int countFoldersForLibrary(int libraryId) throws Exception
	{
		String query = "select count(*) from Folder F where F.LibraryId =  ? ";
		return getSimpleJdbcTemplate().queryForInt(query, libraryId );
	}
}