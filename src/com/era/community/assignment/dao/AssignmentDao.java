package com.era.community.assignment.dao; 

import support.community.database.QueryScroller;

interface AssignmentDao extends com.era.community.assignment.dao.generated.AssignmentDaoBase, AssignmentFinder
{        
	public QueryScroller listEntriesForAssignment(AssignmentTask task) throws Exception;
}