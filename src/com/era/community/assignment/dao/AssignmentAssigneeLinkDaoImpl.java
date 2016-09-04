package com.era.community.assignment.dao; 

import java.util.List;

import com.era.community.assignment.ui.dto.AssignmentAssigneeDto;

public class AssignmentAssigneeLinkDaoImpl extends com.era.community.assignment.dao.generated.AssignmentAssigneeLinkDaoBaseImpl implements AssignmentAssigneeLinkDao
{
	public AssignmentAssigneeLink getAssignmentAssigneeLinkForAssignmentAndUser(int assignmentId, int userId) throws Exception
	{
		return (AssignmentAssigneeLink) getEntityWhere("AssignmentId = ? and UserId = ?", assignmentId, userId);
	}
	
	public int countAssigneesForAssignment(int assignmentId) throws Exception
	{
		String query = "select count(ID) from AssignmentAssigneeLink where AssignmentId = ?" ;

		return getSimpleJdbcTemplate().queryForInt(query, assignmentId); 
	}
	
	public List getAssigneesForAssignment(int assignmentId, int max) throws Exception
	{
		String query = "select U.Id as UserId, U.FirstName, U.LastName, U.PhotoPresent from User U, AssignmentAssigneeLink link " 
			+ " where U.ID = link.UserId and U.Inactive = 'N' and link.AssignmentId = ? " ;

		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		return getBeanList(query, AssignmentAssigneeDto.class, assignmentId);
	}
}