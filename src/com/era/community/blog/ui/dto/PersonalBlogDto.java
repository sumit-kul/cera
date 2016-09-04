package com.era.community.blog.ui.dto; 

public class PersonalBlogDto extends com.era.community.blog.dao.generated.PersonalBlogEntity 
{
	private String tags;

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}