package com.era.community.doclib.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;
import com.era.community.doclib.ui.dto.DocumentCommentDto;
import com.era.community.media.dao.PhotoComment;

public class DocumentCommentDaoImpl extends com.era.community.doclib.dao.generated.DocumentCommentDaoBaseImpl implements DocumentCommentDao
{

	public QueryScroller listCommentsForDocumentId(int docId, int userId) throws Exception
	{
		return listCommentsForDocumentId(docId, userId, null);
	}

	public QueryScroller listCommentsForDocumentId(int docId, int userId, Class beanClass) throws Exception
	{        
		String uld = " , 0 as UserLikeId ";
    	if (userId > 0) {
    		uld = " , (select count(*) from DocumentCommentLike L where L.DocumentId = C.DocumentId and L.DocumentCommentId = C.Id and L.PosterId = ? ) as UserLikeId ";
		}
    	
		String sql = "select T.* from ( select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent, " + 
		"(select count(*) from DocumentCommentLike L where L.DocumentId = C.DocumentId and L.DocumentCommentId = C.Id) As LikeCommentCount " +
		uld + " from DocumentComment C, User U" +
		" where C.DocumentId = ? and U.Id = C.PosterId ) as T";   
		
		QueryScroller scroller;
		if (userId > 0) {
			scroller = getQueryScroller(sql, true, beanClass, userId, docId);
        } else {
        	scroller = getQueryScroller(sql, true, beanClass, docId);
        }
		scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}


	public int getDocumentCommentCount(int groupId) throws Exception
	{
		String query = "select count(*) from DocumentComment";
		;

		// Use 'P' to denote the Parent library
		String queryForGroup = "select count(*) from DocumentComment M, Document D, DocumentLibrary P, CommunityGroupLink L where "+
		"L.CommunityId = P.CommunityId and  L.CommunityGroupId=? and D.LibraryId = P.Id and M.DocumentId=D.Id";

		if ( groupId==0  )
			return getJdbcTemplate().queryForInt(query);
		else
		{
			return getSimpleJdbcTemplate().queryForInt(queryForGroup,  groupId );
		}

	}

	public int getDocumentCommentCountForCommunity(Community comm) throws Exception
	{
		String query = "select count(*) from DocumentLibrary L, Document D, DocumentComment C" +
		" where D.Id = C.DocumentId and L.Id = D.LibraryId and L.CommunityId = ? ";
		return getSimpleJdbcTemplate().queryForInt(query,  comm.getId() );
	}

	public int getDocumentCommentCountForUserAndDocument(int userId, int docId) throws Exception
	{
		String query = "select count(*) from DocumentComment C where C.DocumentId  = ? and C.UserId= ?";
		return getSimpleJdbcTemplate().queryForInt(query,  userId, docId);
	}
	public int getDocumentCommentCountForDocument(int docId) throws Exception
	{
		String query = "select count(*) from DocumentComment C where C.DocumentId  = ?";
		return getSimpleJdbcTemplate().queryForInt(query, docId);
	}
}

