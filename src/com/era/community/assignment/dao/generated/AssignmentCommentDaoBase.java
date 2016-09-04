package com.era.community.assignment.dao.generated; 

import com.era.community.assignment.dao.AssignmentComment;

public interface AssignmentCommentDaoBase extends AssignmentCommentFinderBase
{
  public void store(AssignmentComment o) throws Exception;
  public void deleteAssignmentCommentForId(int id) throws Exception;
  public void delete(AssignmentComment o) throws Exception;
}