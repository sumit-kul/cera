package com.era.community.assignment.dao.generated; 

import com.era.community.assignment.dao.Assignment;

public interface AssignmentDaoBase extends AssignmentFinderBase
{
	public void store(Assignment o) throws Exception;
	public void store(Assignment o, boolean isAllowed) throws Exception;
	public void deleteAssignmentForId(int id) throws Exception;
	public void delete(Assignment o) throws Exception;
	public String getBody(Assignment o) throws Exception;
	public void setBody(Assignment o, String value) throws Exception;
}