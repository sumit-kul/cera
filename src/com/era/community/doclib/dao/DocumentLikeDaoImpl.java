package com.era.community.doclib.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.doclib.ui.dto.DocumentCommentDto;

public class DocumentLikeDaoImpl extends com.era.community.doclib.dao.generated.DocumentLikeDaoBaseImpl implements DocumentLikeDao
{
	public QueryScroller listLikesForDocument(Document entry) throws Exception
	{
		final String SQL = "select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent " + 
		"from DocumentLike C, User U " +
		"where C.DocumentId = ? " +
		"and C.PosterId = U.Id";

		QueryScroller scroller = getQueryScroller(SQL, DocumentCommentDto.class, entry.getId());
		scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public int getLikeCount() throws Exception
	{
		String query = "select count(*) from DocumentLike";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getLikeCountForDocument(int documentId) throws Exception
	{
		String query = "select count(*) from DocumentLike where DocumentLike.DocumentId = ? ";
		return getSimpleJdbcTemplate().queryForInt(query, documentId);
	}
	
	public DocumentLike getLikeForDocumentAndUser(int documentId, int userId) throws Exception
    {
    	return (DocumentLike)getEntityWhere("DocumentId = ? and PosterId = ? ", documentId, userId);
    }
}