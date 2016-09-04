package com.era.community.pers.ui.dto; 

import java.util.Date;

public class MembershipInvitationDto extends com.era.community.communities.dao.generated.CommunityEntity 
{
	private String RequestDate;
	private String FirstName;
	private String LastName;
	private int InvitationId;
	private int InvitorId;
	private String CreatedBy;
	
	public String getRequestDate() {
		return RequestDate;
	}
	public void setRequestDate(Date requestDate) {
		RequestDate = requestDate.toString();
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public int getInvitationId() {
		return InvitationId;
	}
	public void setInvitationId(int invitationId) {
		InvitationId = invitationId;
	}
	public int getInvitorId() {
		return InvitorId;
	}
	public void setInvitorId(int invitorId) {
		InvitorId = invitorId;
	}
	public String getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}
}