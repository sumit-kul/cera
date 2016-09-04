package com.era.community.admin.dao; 

import java.util.ArrayList;
import java.util.List;


import support.community.framework.Option;

/**
 *
*/
public class BusinessParamDaoImpl extends com.era.community.admin.dao.generated.BusinessParamDaoBaseImpl implements BusinessParamDao
{

    public List<Option> getCategoryOptionList() throws Exception
    {
        ArrayList<Option> options = new ArrayList<Option>();
        Option category = new Option(BusinessParam.CATEGORY_STATIC_TEXT);
        options.add(category);
        category = new Option(BusinessParam.CATEGORY_HELP);
        options.add(category);
        return options;                        
    }

    public List getParamsForCategory(String category) throws Exception
    {
        final String sql = "select * from BusinessParam where Category = ? Order By Name";
        
        Object[] params = new Object[1];
        params[0] = category.toLowerCase();
       
        List result = getBeanList(sql, params, BusinessParam.class);        
        return result;       
    }

    public BusinessParam getParamForCategoryAndName(String category, String name) throws Exception
    {
        Object[] keys = new Object[] { category.toLowerCase(), name };
        
        return (BusinessParam)getEntity("select * from BusinessParam where Category =? and name=?", keys ) ;
    }        
}