package com.era.community.assignment.ui.dto; 

import support.community.util.StringHelper;

public class AssignmentCommentDto extends com.era.community.assignment.dao.generated.AssignmentCommentEntity 
{
    public String getFormattedComment() throws Exception
    {
        return StringHelper.formatForDisplay(getComment());
    }
}