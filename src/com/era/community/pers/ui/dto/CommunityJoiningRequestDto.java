package com.era.community.pers.ui.dto; 

import java.util.Date;

public class CommunityJoiningRequestDto extends com.era.community.communities.dao.generated.CommunityEntity 
{
	private String RequestDate;
	private String Requester;
	private int RequestId;
	private int RequestorId;
	private String OptionalComment;
	private String CreatedBy;
	
	public String getRequestDate() {
		return RequestDate;
	}
	public void setRequestDate(Date requestDate) {
		RequestDate = requestDate.toString();
	}
	public String getRequester() {
		return Requester;
	}
	public void setRequester(String requester) {
		Requester = requester;
	}
	public int getRequestId() {
		return RequestId;
	}
	public void setRequestId(int requestId) {
		RequestId = requestId;
	}
	public int getRequestorId() {
		return RequestorId;
	}
	public void setRequestorId(int requestorId) {
		RequestorId = requestorId;
	}
	public String getOptionalComment() {
		return OptionalComment;
	}
	public void setOptionalComment(String optionalComment) {
		OptionalComment = optionalComment;
	}
	public String getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}
	
}