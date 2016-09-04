package com.era.community.wiki.ui.dto; 

import support.community.util.*;

public class WikiEntryCommentDto extends com.era.community.wiki.dao.generated.WikiEntryCommentEntity 
{
    public String getFormattedComment() throws Exception
    {
        return StringHelper.formatForDisplay(getComment());
    }
}