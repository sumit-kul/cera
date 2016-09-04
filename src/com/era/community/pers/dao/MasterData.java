package com.era.community.pers.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.MasterDataEntity;

public class MasterData extends CecBaseEntity
{
	private String Label;
	private String Category;
	private int Active;

	protected MasterDataDao dao;

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	}

	public boolean isReadAllowed(MasterDataEntity msdt)
	{
		return true;
	}

	public boolean isWriteAllowed(MasterData msdt)
	{
		if (msdt == null) return false;
		return true;
	}

	public void setDao(MasterDataDao dao)
	{
		this.dao = dao;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public int getActive() {
		return Active;
	}

	public void setActive(int active) {
		Active = active;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}
}