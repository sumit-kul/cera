package com.era.community.assignment.dao; 

import com.era.community.communities.dao.Community;

public interface AssignmentsFinder extends com.era.community.assignment.dao.generated.AssignmentsFinderBase
{
	public Assignments getAssignmentsForCommunity(Community comm) throws Exception;
	public Assignments getAssignmentsForCommunityId(int commId) throws Exception;
}