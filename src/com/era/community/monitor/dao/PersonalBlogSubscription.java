package com.era.community.monitor.dao;

import java.util.Date;

import com.era.community.blog.dao.PersonalBlogFinder;

public class PersonalBlogSubscription extends Subscription
{
	protected Integer PersonalBlogId  ;
	protected PersonalBlogFinder personalBlogFinder;

	@Override
	public String getItemName() throws Exception
	{
		return "BlogPersonal";
	}

	@Override
	public String getItemUrl() throws Exception
	{
		return  "blog/viewBlog.do?bid="+this.getPersonalBlogId();
	}

	@Override
	public String getItemType() throws Exception
	{
		return "BlogPersonal";
	}

	@Override
	public Date getItemLastUpdateDate() throws Exception
	{
		return personalBlogFinder.getPersonalBlogForId(this.getPersonalBlogId()).getLatestPostDate();
	}

	@Override
	public Object getItem() throws Exception
	{
		return null;
	}

	@Override
	public int getSortOrder() throws Exception
	{
		return 1;
	}

	public int compareTo(Subscription o) 
	{
		return this.sortOrder-o.sortOrder;
	}

	public Integer getPersonalBlogId() {
		return PersonalBlogId;
	}

	public void setPersonalBlogId(Integer PersonalBlogId) {
		PersonalBlogId = PersonalBlogId;
	}

	public void setPersonalBlogFinder(PersonalBlogFinder personalBlogFinder) {
		this.personalBlogFinder = personalBlogFinder;
	}
}