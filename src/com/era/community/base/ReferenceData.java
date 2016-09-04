package com.era.community.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import support.community.framework.Option;

import com.era.community.pers.dao.MasterDataFinder;
import com.era.community.pers.dao.UserExpertiseFinder;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;

public class ReferenceData
{
    protected Log logger = LogFactory.getLog(getClass());
    
    private UserFinder userFinder;
    private UserExpertiseFinder userExpertiseFinder;
    private MasterDataFinder masterDataFinder;
    
    public List getSearchOptions() throws Exception
    {
    	return stringToOptionList("All Content, Blog, Community, Event, File, Forum, People, Wiki");
    }

    public List getUserTitleOptions() throws Exception
    {
        return stringToOptionList("Mr, Miss, Mrs, Ms, Dr");
    }
    
    public List getEventCategoryOptions() throws Exception
    {
        return masterDataFinder.getEventCategoryList();
    }
    
    public List getFeedbackCategoryOptions() throws Exception
    {
        return masterDataFinder.getFeedbackCategoryList();
    }
    
    public List getInterestTypeOptions() throws Exception
    {
        return masterDataFinder.getInterestTypeList();
    }
    
    public List getGenderTypeOptions() throws Exception
    {
        return masterDataFinder.getGenderTypeList();
    }
    
    public List getRelationTypeOptions() throws Exception
    {
        return masterDataFinder.getRelationTypeList();
    }

    public List getUserExpertiseOptions() throws Exception
    {
        return userExpertiseFinder.getExpertiseOptionList();
    }

    public List getRegionOptions() throws Exception
    {
        return stringToOptionList("");
    }
    
    private List stringToOptionList(String data)
    {
        String[] items = StringUtils.tokenizeToStringArray(data, ",");
        List<Option> options = new ArrayList<Option>(items.length);
        for (int n=0; n<items.length; n++) {
            options.add(stringToOption(items[n]));
        }
        return options;
    }
    
    private Option stringToOption(String s)
    {
        Option opt = new Option();
        int i = s.indexOf('|');
        if (i<0) { 
            opt.setValue(s);
        }
        else {
            opt.setLabel(s.substring(0, i));
            opt.setValue(s.substring(i+1));
        }
        return opt;
    }
    
    public String getOrganisationTypeAheadJSArray(String org) throws Exception
    {
        List organisations = userFinder.getOrganisationsFromAllUsers(org);
        StringBuffer organisationJSArray = new StringBuffer();

        for (int i = 0; i < organisations.size(); i++) {
            UserDto organisation = (UserDto)organisations.get(i);
            if (organisationJSArray.length() > 0)
                organisationJSArray.append(", ");
            //organisationJSArray.append("'" + organisation.getOrganisation() + "'");
        }

        String retVal = "organisationarray = new Array(" + organisationJSArray.toString() + ");";
        return retVal;
    }
    
    public void setUserExpertiseFinder(UserExpertiseFinder userExpertiseFinder)
    {
        this.userExpertiseFinder = userExpertiseFinder;
    }
    
    public final void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }

	public void setMasterDataFinder(MasterDataFinder masterDataFinder) {
		this.masterDataFinder = masterDataFinder;
	}
}