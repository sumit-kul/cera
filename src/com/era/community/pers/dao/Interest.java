package com.era.community.pers.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class Interest extends CecAbstractEntity
{
	private String Interest;
	private int CategoryId;
	private int CreatorId;
	private int Active;

	protected InterestDao dao;

	/**
	 * Update or insert the object in the database.
	 * 
	 * @throws Exception
	 */
	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	}

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user == null) return false;
		return true;
	}

	public String getInterest()
	{
		return Interest;
	}

	public void setInterest(String interest)
	{
		Interest = interest;
	}

	public void setDao(InterestDao dao)
	{
		this.dao = dao;
	}

	public int getCategoryId() {
		return CategoryId;
	}

	public void setCategoryId(int categoryId) {
		CategoryId = categoryId;
	}

	public int getCreatorId() {
		return CreatorId;
	}

	public void setCreatorId(int creatorId) {
		CreatorId = creatorId;
	}

	public int getActive() {
		return Active;
	}

	public void setActive(int active) {
		Active = active;
	}
}