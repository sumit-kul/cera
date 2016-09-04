package com.era.community.assignment.dao; 

import java.util.List;

public interface AssignmentAssigneeLinkFinder extends com.era.community.assignment.dao.generated.AssignmentAssigneeLinkFinderBase
{      
	public AssignmentAssigneeLink getAssignmentAssigneeLinkForAssignmentAndUser(int assignmentId, int userId) throws Exception;
	public int countAssigneesForAssignment(int assignmentId) throws Exception;
	public List getAssigneesForAssignment(int assignmentId, int max) throws Exception;
}