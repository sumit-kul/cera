package com.era.community.assignment.dao;

import java.util.Date;
import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

public class AssignmentTask extends Assignment
{
	public QueryScroller listAssignmentEntries() throws Exception
	{
		return dao.listEntriesForAssignment(this);
	}

	public Date getLastPostDate() throws Exception
	{
		QueryScroller scroller = listAssignmentEntries();
		scroller.addScrollKey("Id", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_INTEGER);
		scroller.setPageSize(1);
		List list = scroller.readPage(1);
		if (list.isEmpty()) return getDatePosted();
		AssignmentEntry ent = (AssignmentEntry)list.get(0);
		return ent.getDatePosted();
	}

	public AssignmentTask getTask() throws Exception
	{
		return this;
	}
}