package com.era.community.assignment.dao; 

import java.util.List;

import com.era.community.assignment.ui.dto.AssignmentDto;


public class AssignmentEntryDaoImpl extends com.era.community.assignment.dao.generated.AssignmentEntryDaoBaseImpl implements AssignmentEntryDao
{
	public void updateForhierarchy(int parentId, int assignmentId, int newId, int isROOT) throws Exception
	{
	 String sql ="call Assignment_Entry_Add_Node(" +parentId+ ", " + assignmentId + ", " + newId + ", " + isROOT + ")";
	 getJdbcTemplate().update(sql);
	}
	
	public int getLastSiblingId(int parentId, int assignmentId, int newId) throws Exception
	{
	 String sql ="SELECT max(ID) FROM AssignmentEntry WHERE ParentId = ? AND AssignmentId = ? AND id <> ?";
	 return getSimpleJdbcTemplate().queryForInt(sql, parentId, assignmentId, newId);
	}
	
	public int getDepthForItem(int entryId) throws Exception
	{
	 String sql ="SELECT (COUNT(parent.ID)+1) AS depth  FROM AssignmentEntry AS node, AssignmentEntry AS parent " +
	 "WHERE (node.lft BETWEEN parent.lft AND parent.rht and (node.AssignmentId = parent.AssignmentId and node.ParentId = parent.ID)) and node.id =  ?";
	 return getSimpleJdbcTemplate().queryForInt(sql, entryId);
	}
	
	public List getEntryBeans(int assignmentId) throws Exception
	{
		final String sQuery = "select (SELECT (COUNT(parent.ID)+1) AS depth " +
				" FROM AssignmentEntry AS node, AssignmentEntry AS parent WHERE (node.lft BETWEEN parent.lft AND parent.rht and (node.AssignmentId = parent.AssignmentId and node.ParentId = parent.ID)) and node.id = AE.ID) as depth, " +
				" AE.Id ,AE.AssignmentsId ,AE.Title, AE.CreatorId ,AE.DatePosted ,AE.Completed ,AE.CompletedById ,AE.CompletedOn , " +
				" AE.Modified ,AE.Created ,AE.AssignmentId ,AE.SysType , AE.ParentId,  " +
				" CONCAT_WS(' ',U.FirstName,U.LastName) as CreatorName, U.PhotoPresent as PhotoPresent " +
				" from AssignmentEntry AE, Assignment ASS, User U " +
				" where AE.CreatorId= U.Id and AE.AssignmentId = ASS.Id and AE.AssignmentId = ? " +
				" ORDER BY AE.lft ";
				
		return getBeanList(sQuery, AssignmentDto.class, assignmentId);
	}
}