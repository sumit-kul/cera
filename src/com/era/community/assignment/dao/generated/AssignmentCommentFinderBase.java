package com.era.community.assignment.dao.generated; 

import com.era.community.assignment.dao.Assignment;
import com.era.community.assignment.dao.AssignmentComment;

public interface AssignmentCommentFinderBase
{
    public AssignmentComment getAssignmentCommentForId(int id) throws Exception;
    public AssignmentComment newAssignmentComment() throws Exception;
    public void clearAssignmentCommentsForAssignment(Assignment assignment) throws Exception;
}