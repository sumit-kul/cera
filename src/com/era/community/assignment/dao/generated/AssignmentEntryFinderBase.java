package com.era.community.assignment.dao.generated; 

import com.era.community.assignment.dao.AssignmentEntry;

public interface AssignmentEntryFinderBase
{
	public AssignmentEntry getAssignmentEntryForId(int id) throws Exception;
	public AssignmentEntry newAssignmentEntry() throws Exception;
}