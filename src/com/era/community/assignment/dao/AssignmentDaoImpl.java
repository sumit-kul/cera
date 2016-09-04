package com.era.community.assignment.dao; 

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.assignment.ui.dto.AssignmentDto;
import com.era.community.assignment.ui.dto.AssignmentHeaderDto;

public class AssignmentDaoImpl extends com.era.community.assignment.dao.generated.AssignmentDaoBaseImpl implements AssignmentDao
{
	public QueryScroller listEntriesForAssignment(AssignmentTask task) throws Exception
	{
		QueryScroller scroller = getQueryScroller("select * from AssignmentEntry where TaskId = ? ", null, task.getId());
		return scroller;
	}
	
	public void updateForhierarchy(int parentId, int assignmentId, int newId, int isROOT) throws Exception
	{
	 String sql ="call Assignment_Entry_Add_Node(" +parentId+ ", " + assignmentId + ", " + newId + ", " + isROOT + ")";
	 getJdbcTemplate().update(sql);
	}
	
	public int getLastSiblingId(int parentId, int taskId, int newId) throws Exception
	{
	 String sql ="SELECT max(ID) FROM AssignmentEntry WHERE ParentId = ? AND TaskId = ? AND id <> ?";
	 return getSimpleJdbcTemplate().queryForInt(sql, parentId, taskId, newId);
	}
	
	public int getDepthForItem(int entryId) throws Exception
	{
	 String sql ="SELECT (COUNT(parent.ID)-1) AS depth  FROM AssignmentEntry AS node, AssignmentEntry AS parent " +
	 "WHERE (node.lft BETWEEN parent.lft AND parent.rht and (node.TaskId = parent.TaskId or node.TaskId = parent.ID)) and node.id =  ?";
	 return getSimpleJdbcTemplate().queryForInt(sql, entryId);
	}
	
	public List getEntryBeans(int assignmentId) throws Exception
	{
		final String sQuery = "select (SELECT (COUNT(parent.ID)-1) AS depth " +
				" FROM Assignment AS node, Assignment AS parent WHERE (node.lft BETWEEN parent.lft AND parent.rht and (node.TaskId = parent.TaskId or node.TaskId = parent.ID)) and node.id = A.ID) as depth, " +
				" A.Id ,A.AssignmentsId ,A.Title, A.Body, A.CreatorId ,A.DatePosted ,A.Completed ,A.CompletedById ,A.CompletedOn, A.lvl, A.DueDate, " +
				" A.Modified ,A.Created ,A.TaskId ,A.EntryType , A.ParentId,  " +
				" CONCAT_WS(' ',U.FirstName,U.LastName) as CreatorName, U.PhotoPresent as PhotoPresent " +
				" from Assignment A, AssignmentTask AT, User U " +
				" where A.CreatorId= U.Id and A.TaskId = AT.Id and A.TaskId = ? " +
				" and A.lft >= AT.lft ORDER BY A.lft ";
				
		return getBeanList(sQuery, AssignmentDto.class, assignmentId);
	}
	
	public AssignmentHeaderDto getAssignmentForHeader(int sssignmentId) throws Exception
	{
		String query = " select ass.id, ass.Title, CAST(ass.BODY as CHAR(1000) ) itemDesc, " +
			" (select COUNT(ID) from AssignmentComment ac where ac.AssignmentId = ass.id) as commentCount, ass.DatePosted datePosted, " +
			" U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
			" (select COUNT(ID) from Image I where I.AssignmentId = ass.Id) ImageCount " +
			" from Assignment ass, User U where U.Id = ass.CreatorId and ass.Id = ? " ;
		return getBean(query, AssignmentHeaderDto.class, sssignmentId);
	}
}