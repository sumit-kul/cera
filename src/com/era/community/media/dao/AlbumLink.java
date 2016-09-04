package com.era.community.media.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="AlbumLink"
 */
public class AlbumLink extends CecAbstractEntity
{
	private int AlbumId;
	private int ContributorId;
	private int AccessLevel;

	private AlbumLinkDao dao;

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

	public void setDao(AlbumLinkDao dao) {
		this.dao = dao;
	}
	
	public void update() throws Exception
	{
		dao.store(this);
	}
	
	public void delete() throws Exception
	{
		dao.delete(this);
	}

	public int getAlbumId() {
		return AlbumId;
	}

	public void setAlbumId(int albumId) {
		AlbumId = albumId;
	}

	public int getContributorId() {
		return ContributorId;
	}

	public void setContributorId(int contributorId) {
		ContributorId = contributorId;
	}

	public int getAccessLevel() {
		return AccessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		AccessLevel = accessLevel;
	}
}