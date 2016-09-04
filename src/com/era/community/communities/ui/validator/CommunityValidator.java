package com.era.community.communities.ui.validator; 

import support.community.framework.CommandBean;

public class CommunityValidator extends com.era.community.communities.dao.generated.CommunityValidator 
{

    public String validateName(Object value, CommandBean cmd) throws Exception
    {
        if (isNullOrWhitespace(value)) return "Please enter the community name";
        return null;
    }
    
    public String validateWelcomeText(Object value, CommandBean cmd) throws Exception
    {
        
          if (value.toString().length() > 800) {
            return "The introductory text can be a maximum of 800 characters long";
          }
        if (isNullOrWhitespace(value)) return "Please enter the introductory text for the community";
        return null;
    }
    
    public String validateCommunityType(Object value, CommandBean bean) throws Exception
    {
        if (isNullOrWhitespace(value))
            return "Please select the community type";

        else return null;
    }
    /*
    public String validatePollQuestion(Object value, CommandBean bean) throws Exception
    {
        
        if (value.toString()!=null && value.toString().trim().length()> 200)
            return ("Poll question length cannot exceed 200 characters");
        else 
            return null;
    }
    
    public String validatePollOptions(Object value, CommandBean bean) throws Exception
    {
        
        if (value.toString()!=null && value.toString().trim().length()> 350)
            return ("Poll options length cannot exceed 350 characters");
        else
            return null;
    }*/
}
