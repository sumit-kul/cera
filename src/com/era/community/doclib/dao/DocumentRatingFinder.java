package com.era.community.doclib.dao; 

public interface DocumentRatingFinder extends com.era.community.doclib.dao.generated.DocumentRatingFinderBase
{
	public DocumentRating getRatingForUserAndDocument(int userId, int docId) throws Exception;
}

