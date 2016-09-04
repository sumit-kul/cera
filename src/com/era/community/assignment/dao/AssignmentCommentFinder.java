package com.era.community.assignment.dao; 

import support.community.database.QueryScroller;

public interface AssignmentCommentFinder extends com.era.community.assignment.dao.generated.AssignmentCommentFinderBase
{
	public QueryScroller listCommentsForAssignment(int assignmentId) throws Exception;
	
    public int getCommentCountForAssignment(int assignmentId) throws Exception;
    
    public void deleteAllAssignmentComments(int assignmentId) throws Exception;
}