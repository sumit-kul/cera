package com.era.community.connections.communities.ui.dto; 

import java.util.Date;

public class MemberInvitationDto extends com.era.community.pers.dao.generated.UserEntity implements Comparable
{
	private String RequestDate;
	private String InvitorName;
	private String fullName;

	public final String getRequestDate()
    {
        return RequestDate;
    }
    public final void setRequestDate(Date requestDate)
    {
        RequestDate = requestDate.toString();
    }

	public String getInvitorName() {
		return InvitorName;
	}

	public void setInvitorName(String invitorName) {
		InvitorName = invitorName;
	}
	
	public int compareTo(Object o) {
		MemberInvitationDto u = (MemberInvitationDto) o; 
		return getLastName().compareTo( u.getLastName() );
	}
	
	public String getFullName() {
		return this.getFirstName() + " "+ this.getLastName();
	}
}
