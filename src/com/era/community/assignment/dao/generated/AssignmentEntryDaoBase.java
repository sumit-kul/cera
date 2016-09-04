package com.era.community.assignment.dao.generated; 

import com.era.community.assignment.dao.AssignmentEntry;

public interface AssignmentEntryDaoBase extends AssignmentEntryFinderBase
{
	public void store(AssignmentEntry o) throws Exception;
	public void store(AssignmentEntry o, boolean isAllowed) throws Exception;
	public void deleteAssignmentEntryForId(int id) throws Exception;
	public void delete(AssignmentEntry o) throws Exception;
	public String getBody(AssignmentEntry o) throws Exception;
	public void setBody(AssignmentEntry o, String value) throws Exception;
}