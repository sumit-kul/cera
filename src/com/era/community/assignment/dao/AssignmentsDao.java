package com.era.community.assignment.dao; 

import java.util.Date;

import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;

interface AssignmentsDao extends com.era.community.assignment.dao.generated.AssignmentsDaoBase, AssignmentsFinder
{
	public Assignments getAssignmentsForCommunity(Community comm) throws Exception;
	public Date getLatestPostDate(Assignments assignments) throws Exception;
	public QueryScroller listAssignmentsForCommunity(Assignments assignments, String sortBy) throws Exception;
}