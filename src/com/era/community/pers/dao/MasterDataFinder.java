package com.era.community.pers.dao; 

import java.util.List;

import com.era.community.pers.ui.dto.SocialLinkDto;

public interface MasterDataFinder extends com.era.community.pers.dao.generated.MasterDataFinderBase
{
	public List listAllCategory() throws Exception;
	public List getEventCategoryList() throws Exception;
	public List getFeedbackCategoryList() throws Exception;
	public List getInterestTypeList() throws Exception;
	public List getGenderTypeList() throws Exception;
	public List getRelationTypeList() throws Exception;
	public List getOccupationList() throws Exception;
	public List<SocialLinkDto> getAllSocialLink(int userId) throws Exception;
}