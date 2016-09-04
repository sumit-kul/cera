package com.era.community.monitor.dao;

import java.util.Date;

import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;

public class CommunitySubscription extends Subscription
{
	protected CommunityFinder communityFinder;
	
	@Override
	public Object getItem() throws Exception {
		return communityFinder.getCommunityForId(CommunityId);
	}

	@Override
	public Date getItemLastUpdateDate() throws Exception {
		Community comm = communityFinder.getCommunityForId(CommunityId);
		return comm.getModified();
	}

	@Override
	public String getItemName() throws Exception {
		Community comm = communityFinder.getCommunityForId(CommunityId);
		return comm.getName();
	}

	@Override
	public String getItemType() throws Exception {
		return "Community";
	}

	@Override
	public String getItemUrl() throws Exception {
		return "cid/" + this.getCommunityId() + "/home.do?backlink=ref";
	}

	@Override
	public int getSortOrder() throws Exception {
		return 0;
	}

	@Override
	public int compareTo(Subscription o)
    {
        return this.sortOrder - o.sortOrder;
    }

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}
}