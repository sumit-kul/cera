package com.era.community.blog.ui.dto; 

import support.community.util.*;

public class BlogEntryCommentDto extends com.era.community.blog.dao.generated.BlogEntryCommentEntity 
{
    public String getFormattedComment() throws Exception
    {
        return StringHelper.formatForDisplay(getComment());
    }
}
