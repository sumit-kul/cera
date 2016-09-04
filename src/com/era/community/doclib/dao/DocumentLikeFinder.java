package com.era.community.doclib.dao; 

public interface DocumentLikeFinder extends com.era.community.doclib.dao.generated.DocumentLikeFinderBase
{
    public int getLikeCount() throws Exception;
    public int getLikeCountForDocument(int documentId) throws Exception;
    public DocumentLike getLikeForDocumentAndUser(int docId, int userId) throws Exception;
}