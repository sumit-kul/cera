package com.era.community.pers.dao; 

import java.util.List;

import support.community.framework.Option;

import com.era.community.pers.ui.dto.SocialLinkDto;

public class MasterDataDaoImpl extends com.era.community.pers.dao.generated.MasterDataDaoBaseImpl implements MasterDataDao
{
	public List listAllCategory() throws Exception
	{
		String query = "select MD.* from MasterData MD where MD.Category = 'InterestType' and MD.Active = 1 ";
		return getBeanList(query, MasterData.class);
	}
	
	public List getEventCategoryList() throws Exception
	{             
		String sql="select label, id as value from MasterData where Category = 'EventType' and Active = 1 ";        
		List optionList=getBeanList(sql, Option.class);        
		return optionList;        
	}
	
	public List getFeedbackCategoryList() throws Exception
	{             
		String sql="select label, id as value from MasterData where Category = 'FeedbackType' and Active = 1 ";        
		List optionList=getBeanList(sql, Option.class);        
		return optionList;        
	}
	
	public List getInterestTypeList() throws Exception
	{             
		String sql="select label, id as value from MasterData where Category = 'InterestType' and Active = 1 ";        
		List optionList=getBeanList(sql, Option.class);        
		return optionList;        
	}
	
	public List getOccupationList() throws Exception
	{             
		String sql="select label, id as value from MasterData where Category = 'Occupation' and Active = 1 ";        
		List optionList=getBeanList(sql, Option.class);        
		return optionList;        
	}
	
	public List getGenderTypeList() throws Exception
	{             
		String sql="select label, id as value from MasterData where Category = 'Gender' and Active = 1 ";        
		List optionList=getBeanList(sql, Option.class);        
		return optionList;        
	}
	
	public List getRelationTypeList() throws Exception
	{             
		String sql="select label, id as value from MasterData where Category = 'RelationType' and Active = 1 ";        
		List optionList=getBeanList(sql, Option.class);        
		return optionList;        
	}
	
	public List<SocialLinkDto> getAllSocialLink(int userId) throws Exception
	{             
		String sql="select label as Name, MD.id, coalesce (SL.Id, 0) Selected from MasterData MD" +
				" left join SocialLink SL on MD.label = SL.Name and SL.UserId = ? where Category = 'SocialLink' and Active = 1 ";        
		List optionList=getBeanList(sql, SocialLinkDto.class, userId);        
		return optionList;        
	}
}