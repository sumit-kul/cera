package com.era.community.doclib.dao; 

import support.community.database.QueryScroller;

interface DocumentLikeDao extends com.era.community.doclib.dao.generated.DocumentLikeDaoBase, DocumentLikeFinder
{
	QueryScroller listLikesForDocument(Document entry) throws Exception;
}

