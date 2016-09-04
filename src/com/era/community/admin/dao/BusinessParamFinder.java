package com.era.community.admin.dao; 

import java.util.List;

import support.community.framework.*;

/**
*
*/
public interface BusinessParamFinder extends com.era.community.admin.dao.generated.BusinessParamFinderBase
{
    List<Option> getCategoryOptionList() throws Exception;
    List getParamsForCategory(String category) throws Exception;
    BusinessParam getParamForCategoryAndName(String category, String name) throws Exception;
}