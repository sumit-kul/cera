package com.era.community.doclib.dao; 

import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;

public interface DocumentCommentFinder extends com.era.community.doclib.dao.generated.DocumentCommentFinderBase
{
	public QueryScroller listCommentsForDocumentId(int docId, int userId) throws Exception;
	public QueryScroller listCommentsForDocumentId(int docId, int userId, Class beanClass) throws Exception;
	public int getDocumentCommentCount(int groupId) throws Exception;
	public int getDocumentCommentCountForCommunity(Community comm) throws Exception;
	public int getDocumentCommentCountForUserAndDocument(int userId, int docId) throws Exception;
	public int getDocumentCommentCountForDocument(int docId) throws Exception;
}

