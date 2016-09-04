package com.era.community.pers.ui.dto; 

import java.util.Date;

public class UserDto extends com.era.community.pers.dao.generated.UserEntity implements Comparable
{
    private String About = "";
    
    private String RequestDate;
    
    public final String getRequestDate()
    {
        return RequestDate;
    }
    public final void setRequestDate(Date requestDate)
    {
        RequestDate = requestDate.toString();
    }
    public final String getAbout()
    {
    	if (About == null) return "";
        return About.replace( "<br />", "\n");
    }
    public final void setAbout(String about)
    {
        About = about;
    }
    public boolean isValidated() throws Exception
    {
        return getValidated();
    }
    
    public String getFullName()
    {
        return getFirstName().trim()+" "+getLastName().trim();
    }
    
	public int compareTo(Object o) {
		UserDto u = (UserDto) o; 
		return getLastName().compareTo( u.getLastName() );
	}
}
