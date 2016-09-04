package com.era.community.pers.ui.dto; 

import java.util.Date;

public class MemberDto extends com.era.community.pers.dao.generated.UserEntity
{
    private String DateJoined;
    private int memId;
    private String photoAvailable;
    
    public final String getDateJoined()
    {
        return DateJoined;
    }
    public final void setDateJoined(Date dateJoined)
    {
    	DateJoined = dateJoined.toString();
    }
	public int getMemId() {
		return getId();
	}
	public void setMemId(int memId) {
		this.memId = memId;
	}
	public String getPhotoAvailable() {
		return String.valueOf(getPhotoPresent());
	}
}