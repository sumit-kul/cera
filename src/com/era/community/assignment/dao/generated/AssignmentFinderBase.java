package com.era.community.assignment.dao.generated; 

import com.era.community.assignment.dao.Assignment;
import com.era.community.assignment.dao.AssignmentEntry;
import com.era.community.assignment.dao.AssignmentTask;

public interface AssignmentFinderBase
{
	public Assignment getAssignmentForId(int id) throws Exception;
	public AssignmentTask newAssignmentTask() throws Exception;
	public AssignmentEntry newAssignmentEntry() throws Exception;
}