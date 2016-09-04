package com.era.community.doclib.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.doclib.dao.generated.DocumentCommentLikeEntity;

public class DocumentCommentLikeDaoImpl extends com.era.community.doclib.dao.generated.DocumentCommentLikeDaoBaseImpl implements DocumentCommentLikeDao
{
	public QueryScroller listCommentLikesForDocument(Document document) throws Exception
	{
		final String SQL = "select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent " + 
		"from DocumentCommentLike C, User U " +
		"where C.DocumentId = ? and C.DocumentCommentId = ?" +
		"and C.PosterId = U.Id";

		QueryScroller scroller = getQueryScroller(SQL, DocumentCommentLikeEntity.class, document.getId());
		scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public int getCommentLikeCount() throws Exception
	{
		String query = "select count(*) from DocumentCommentLike";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getLikeCountForDocumentComment(int documentId, int commentId) throws Exception
    {
        String query = "select count(*) from DocumentCommentLike where DocumentCommentLike.DocumentId = ? and DocumentCommentLike.DocumentCommentId = ? ";
        return getSimpleJdbcTemplate().queryForInt(query, documentId, commentId);
    }
	
	public DocumentCommentLike getLikeForDocumentCommentAndUser(int documentId, int commentId, int userId) throws Exception
    {
    	return (DocumentCommentLike)getEntityWhere("DocumentId = ? and DocumentCommentId = ? and PosterId = ?", documentId, commentId, userId);
    }
}