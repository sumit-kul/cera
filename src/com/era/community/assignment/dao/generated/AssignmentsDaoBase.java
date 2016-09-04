package com.era.community.assignment.dao.generated; 

import com.era.community.assignment.dao.Assignments;

public interface AssignmentsDaoBase extends AssignmentsFinderBase
{
	public void store(Assignments o) throws Exception;
	public void deleteAssignmentsForId(int id) throws Exception;
	public void delete(Assignments o) throws Exception;
}