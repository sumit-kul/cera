package com.era.community.doclib.dao; 

public interface DocumentCommentLikeFinder extends com.era.community.doclib.dao.generated.DocumentCommentLikeFinderBase
{
	public int getCommentLikeCount() throws Exception;
	public int getLikeCountForDocumentComment(int mediaId, int commentId) throws Exception;
    public DocumentCommentLike getLikeForDocumentCommentAndUser(int documentId, int commentId, int userId) throws Exception;
}