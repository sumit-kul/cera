package com.era.community.assignment.dao; 

import java.util.List;

import com.era.community.assignment.ui.dto.AssignmentHeaderDto;

public interface AssignmentFinder extends com.era.community.assignment.dao.generated.AssignmentFinderBase
{
	public void updateForhierarchy(int parentId, int assignmentId, int newId, int isROOT) throws Exception;
	public int getLastSiblingId(int parentId, int assignmentId, int newId) throws Exception;
	public int getDepthForItem(int entryId) throws Exception;
	public List getEntryBeans(int assignmentId) throws Exception;
	public AssignmentHeaderDto getAssignmentForHeader(int sssignmentId) throws Exception;
}