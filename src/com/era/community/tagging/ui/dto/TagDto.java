package com.era.community.tagging.ui.dto; 

public class TagDto extends com.era.community.tagging.dao.generated.TagEntity 
{
	private int cloudSet;   
	private Long count;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public int getCloudSet() {
		return cloudSet;
	}

	public void setCloudSet(int cloudSet) {
		this.cloudSet = cloudSet;
	}
}