package com.era.community.media.dao;

import java.util.List;

import com.era.community.media.ui.dto.AlbumLinkDto;

public class AlbumLinkDaoImpl extends com.era.community.media.dao.generated.AlbumLinkDaoBaseImpl implements AlbumLinkDao
{
	public AlbumLink getAlbumLinkForContributor(int albumId, int contId) throws Exception
	{
		String query = "select * from AlbumLink where AlbumId = ? and ContributorId = ? ";
		return getBean(query, AlbumLink.class, albumId, contId);
	}
	
	public List<AlbumLinkDto> getAlbumLinksForAlbum(int albumId) throws Exception
	{
		String query = "select L.*, U.PhotoPresent, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName from AlbumLink L, User U where L.ContributorId = U.Id and L.AlbumId = ? ";
		return getBeanList(query, AlbumLinkDto.class, albumId);
	}
	
	public void deleteAllLinksForAlbum(int albumId) throws Exception
    {
        String sql="delete from AlbumLink where AlbumId = ?";
        getSimpleJdbcTemplate().update(sql, albumId);       
    }
	
	public void deleteAlbumLinkForId(int linkId) throws Exception
    {
        String sql="delete from AlbumLink where Id = ?";
        getSimpleJdbcTemplate().update(sql, linkId);       
    }
}