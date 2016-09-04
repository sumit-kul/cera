package com.era.community.doclib.ui.dto; 

import support.community.util.StringHelper;

public class DocumentCommentDto extends com.era.community.doclib.dao.generated.DocumentCommentEntity 
{
	private String DisplayName;
	private String PhotoPresent;
	
	public String getFormattedComment() throws Exception
	{
		return StringHelper.formatForDisplay(getComment());
	}
	
	public String getDisplayName() {
		return DisplayName;
	}
	
	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}
	
	public String getPhotoPresent() {
		return PhotoPresent;
	}
	
	public void setPhotoPresent(String photoPresent) {
		PhotoPresent = photoPresent;
	}
}
