package com.era.community.forum.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * The Theme-Topic Thin Entity Link Table - links Forum Topic rows to Theme rows
 *
 * @entity name="THIT"
 * 
 * @entity.index name="01" unique="yes" columns="ItemId, ThemeId"  cluster="yes"
 * 
 * If either the Forum Item or the Theme gets deleted, the link item that refers to either should get deleted as well.
 * 
 * @entity.foreignkey name="01" columns="ItemId" target="FMIT" ondelete="cascade"
 * @entity.foreignkey name="02" columns="ThemeId" target="THEM" ondelete="cascade"
 * 
 */
public class ThemeTopicLink extends CecBaseEntity
{
	/**
	 * @column int 
	 */
	private int ItemId;

	/**
	 * @column int 
	 */    
	private int ThemeId;

	protected ThemeTopicLinkDao dao;


	/**
	 * Update or insert this entity in the database.
	 */
	public void update() throws Exception
	{
		dao.store(this); 
	}

	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user)
	{
		return true;        
	}

	public final int getItemId()
	{
		return ItemId;
	}


	public final void setItemId(int itemId)
	{
		ItemId = itemId;
	}


	public final int getThemeId()
	{
		return ThemeId;
	}


	public final void setThemeId(int themeId)
	{
		ThemeId = themeId;
	}


	public final ThemeTopicLinkDao getDao()
	{
		return dao;
	}


	public final void setDao(ThemeTopicLinkDao dao)
	{
		this.dao = dao;
	}




}
