package com.era.community.doclib.dao; 

public class DocumentRatingDaoImpl extends com.era.community.doclib.dao.generated.DocumentRatingDaoBaseImpl implements DocumentRatingDao
{
	public DocumentRating getRatingForUserAndDocument(int userId, int docId) throws Exception
	{
		return (DocumentRating)getEntityWhere(" posterId = ? and documentid = ? ",  userId, docId) ;
	}
}

