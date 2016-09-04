package com.era.community.doclib.dao; 

import support.community.database.QueryScroller;

interface DocumentCommentLikeDao extends com.era.community.doclib.dao.generated.DocumentCommentLikeDaoBase, DocumentCommentLikeFinder
{
    QueryScroller listCommentLikesForDocument(Document entry) throws Exception;
}