package com.era.community.assignment.dao; 

import java.util.List;

public interface AssignmentEntryFinder extends com.era.community.assignment.dao.generated.AssignmentEntryFinderBase
{
	public void updateForhierarchy(int parentId, int assignmentId, int newId, int isROOT) throws Exception;
	public int getLastSiblingId(int parentId, int assignmentId, int newId) throws Exception;
	public int getDepthForItem(int entryId) throws Exception;
	public List getEntryBeans(int assignmentId) throws Exception;
}