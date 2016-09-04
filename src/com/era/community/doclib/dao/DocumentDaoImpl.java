package com.era.community.doclib.dao; 

import java.util.Date;
import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;
import com.era.community.doclib.ui.dto.DocumentDto;
import com.era.community.doclib.ui.dto.DocumentHeaderDto;
import com.era.community.media.ui.dto.MediaInfoDto;
import com.era.community.monitor.ui.dto.SubscriptionDto;
import com.era.community.monitor.ui.dto.SubscriptionItemDto;

public class DocumentDaoImpl extends com.era.community.doclib.dao.generated.DocumentDaoBaseImpl implements DocumentDao
{
	public QueryScroller listAllDocuments() throws Exception
	{        
		String sql = "select D.* from Document D ,DocumentLibrary DL, Community C where " +
		" DL.Id = D.LibraryId and  C.ID = DL.CommunityId and C.SysType != 'PrivateCommunity' "; 
		QueryScroller scroller = getQueryScroller(sql, null);
		return scroller;
	}

	public Date getLatestPostDate(Document doc) throws Exception
	{
		Date date = null;
		String query = "select max(DatePosted) from DocumentComment where DocumentId = ?";
		date =  (Date)getSimpleJdbcTemplate().queryForObject(query,   Date.class , doc.getId());
		if (date==null)
			return doc.getDatePosted();
		else 
			return date;
	}

	public List getItemsSince(Document doc, Date date) throws Exception
	{
		String sQuery = "Select DC.Id as ItemId, DC.Comment as ItemTitle, DC.Created As ItemDate," +
		" DC.PosterId as AuthorId, CONCAT_WS(' ',U.FirstName,U.LastName) as AuthorName " +
		" from DocumentComment DC, User U where U.Id = DC.PosterId and DatePosted > ? and DocumentId = ? ";
		return  getBeanList(sQuery, SubscriptionItemDto.class, date, doc.getId());
	}

	public int getStarRating(Document doc) throws Exception
	{
		String query = "select avg(stars) from DocumentRating  where DocumentId = ?";
		Object o  =  getSimpleJdbcTemplate().queryForObject(query, Integer.class, doc.getId());
		if (o==null) return 0;
		return ((Integer)o).intValue();
	}

	public int getNumberOfRaters(Document doc) throws Exception
	{
		String query = "select count(*) from DocumentRating  where DocumentId = ?";
		Object o  =  getSimpleJdbcTemplate().queryForObject(query, Integer.class, doc.getId()  );

		if (o==null) return 0;
		return ((Integer)o).intValue();
	}

	public int getDocumentCount(int groupId) throws Exception
	{
		String query = "select count(*) from Document";
		String queryForGroup = "select count(*) from Document D, DocumentLibrary P, CommunityGroupLink L where "+
		"L.CommunityId = P.CommunityId and  L.CommunityGroupId=? and D.LibraryId = P.Id ";
		if ( groupId==0  )
			return getJdbcTemplate().queryForInt(query);
		else
		{
			return getSimpleJdbcTemplate().queryForInt(queryForGroup, groupId );
		}
	}

	public int getDocumentCountForCommunity(Community comm) throws Exception
	{
		String query = "select count(*) from DocumentLibrary L, Document D" +
		" where L.Id = D.LibraryId and L.CommunityId = ?";
		return getSimpleJdbcTemplate().queryForInt(query, comm.getId() );
	}

	public QueryScroller getDocumentsForPoster(int posterId, String searchString) throws Exception {
		String searchClause = "";
		if (searchString != null && !"".equals(searchString)) {
			searchClause = " and (D.Title like '%" + searchString + "%' OR D.FileName like '%" + searchString + "%')";
		}

		String sQuery = " select D.* from Document D where D.PosterId = ? " + searchClause;

		QueryScroller scroller = getQueryScroller(sQuery, true, DocumentDto.class, posterId);

		scroller.addScrollKey("STEMP.Title", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}

	public QueryScroller getDocumentsFromOtherCommunity(int posterId, int communityId, String searchString) throws Exception {
		String searchClause = "";
		if (searchString != null && !"".equals(searchString)) {
			searchClause = " and (D.Title like '%" + searchString + "%' OR D.FileName like '%" + searchString + "%')";
		}

		String sQuery = " select D.* from Document D, DocumentLibrary DL, MemberList L, Membership M, Community C where " +
		" DL.Id = D.LibraryId and DL.CommunityId = L.CommunityId and M.MEMBERLISTID = L.ID and " +
		" C.ID = DL.CommunityId and M.UserId = ? and DL.Id <> ? and C.SysType = 'PublicCommunity' " + searchClause;

		QueryScroller scroller = getQueryScroller(sQuery, true, DocumentDto.class, posterId, communityId);

		scroller.addScrollKey("STEMP.Title", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}

	public QueryScroller getDocumentsFromMyConnection(int posterId, String searchString) throws Exception {
		String searchClause = "";
		if (searchString != null && !"".equals(searchString)) {
			searchClause = " and (D.Title like '%" + searchString + "%' OR D.FileName like '%" + searchString + "%')";
		}

		String sQuery = " select * from ( " +
		" select D.* from Contact C, Document D where D.PosterId = C.ContactUserId and C.OwningUserId = ? and " +
		" C.STATUS = 1 and C.DATECONNECTION is not null "  + searchClause +
		" union " +
		" select D.* from Contact C, Document D where D.PosterId = C.OwningUserId and C.ContactUserId = ? and " +
		" C.STATUS = 1 and C.DATECONNECTION is not null "  + searchClause +
		" ) A ";

		QueryScroller scroller = getQueryScroller(sQuery, DocumentDto.class, posterId, posterId);

		scroller.addScrollKey("STEMP.Title", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}

	public List getDocumentForMediaPanel(int libraryId, int max) throws Exception
	{
		String query = "select distinct D.* from Document D, DocumentLibrary DL " 
			+ " where D.LibraryId = DL.Id and DL.CommunityId = ? and D.FileContentType like 'image%' ";

		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		return getBeanList(query, Document.class, libraryId);
	}

	public int getMediaCountForCommunity(Community comm) throws Exception
	{
		String query = "select count(*) from DocumentLibrary L, Document D" +
		" where L.Id = D.LibraryId and L.CommunityId = ? and D.FileContentType like 'image%' ";
		return getSimpleJdbcTemplate().queryForInt(query, comm.getId() );
	}

	public QueryScroller getImagesForLibrary(int communityId, int folderId) throws Exception
	{
		String albIncl = "";
		if (folderId > 0) {
			albIncl = " and D.FolderId = ? ";
		}
		
		String query = "select distinct D.*, CONCAT_WS(' ',U.FirstName,U.LastName) as posterName from Document D, DocumentLibrary DL, User U " 
			+ " where D.LibraryId = DL.Id and U.ID = D.PosterId and DL.CommunityId = ? and D.FileContentType like 'image%' "+albIncl;
		
		QueryScroller scroller = null;
		if (folderId > 0) {
			scroller = getQueryScroller(query, null, communityId, folderId);
		} else {
			scroller = getQueryScroller(query, communityId);
		}

		scroller.addScrollKey("Created", "Created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}
	
	public QueryScroller getFilesForLibraryAndFolder(int communityId, int folderId) throws Exception
	{
		String query = "select distinct D.*,  CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName from Document D, DocumentLibrary DL, User U " 
			+ " where D.LibraryId = DL.Id and U.ID = D.PosterId and DL.CommunityId = ? and D.FolderId = ? ";
		QueryScroller scroller = getQueryScroller(query, null, communityId, folderId);

		scroller.addScrollKey("Created", "Created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public List<Document> listImagesForCommunity(int communityId) throws Exception
	{
		String query = "select distinct D.* from Document D, DocumentLibrary DL " 
			+ " where D.LibraryId = DL.Id and DL.CommunityId = ? and D.FileContentType like 'image%' order by Created desc";
		return getBeanList(query, Document.class, communityId);
	}

	public List<Document> listImagesForFolder(int folderId) throws Exception
	{
		String query = "select * from Document where FolderId = ? and FileContentType like 'image%' order by DatePosted desc";
		return getBeanList(query, Document.class, folderId);
	}

	public MediaInfoDto extraInfoForMedia(int mediaId, int currUserId) throws Exception
	{
		String uld = " , 0 as photoLikeId "; 
    	if (currUserId > 0) {
    		uld = " , (select count(*) from DocumentLike L where L.DocumentId = D.Id and L.PosterId = ? ) as photoLikeId ";
		}
		String query = "select D.Id, D.Title, D.Description, D.DateRevised as Modified, D.DatePosted as Created, D.PosterId as UserId, D.FolderId as AlbumId, D.PrivacyLevel, " +
		" CONCAT_WS(' ',U.FirstName,U.LastName) as OwnerName, U.PhotoPresent " +
		" , (select count(*) from DocumentLike L where L.DocumentId = D.Id) as PhotoLikeCount " + uld + " from Document D,  User U where D.PosterId = U.ID and D.ID = ? and D.FileContentType like 'image%' ";

		if (currUserId > 0) {
			return getBean(query, MediaInfoDto.class, currUserId, mediaId);
		} else {
			return getBean(query, MediaInfoDto.class, mediaId);
		}
	}

	public Document getLatestImageInFolder(int folderId) throws Exception
	{
		String query = "select * from Document where FolderId = ? order by Created desc LIMIT 1";
		return getBean(query, Document.class, folderId);
	}
	
	public void deleteAllPhotosForFolder(int folderId) throws Exception
    {
        String sql="delete from Document where FolderId = ? and FileContentType like 'image%'";
        getSimpleJdbcTemplate().update(sql, folderId);       
    }
	
	public Document getCurrentProfileLogo(int libId) throws Exception
	{
		String query = "select * from Document where CurrentProfile = 1 and LibraryId = ? order by Created desc LIMIT 1";
		return getBean(query, Document.class, libId);
	}
	
	public Document getCurrentProfileBanner(int libId) throws Exception
	{
		String query = "select * from Document where CurrentProfile = 2 and LibraryId = ? order by Created desc LIMIT 1";
		return getBean(query, Document.class, libId);
	}
	
	public DocumentHeaderDto getDocumentForHeader(int docId) throws Exception
	{
		String query = " select d.id docId, d.title, CAST(d.description as CHAR(1000) ) itemDesc, " +
		" d.filename, d.FileContentType, 0 as ImageCount, " +
		" (select SUM(STARS) / COUNT(STARS) from DocumentRating DR where DR.documentid = d.id) avgRating, " +
		" (select COUNT(ID) from DocumentComment DC where DC.documentid = d.id) as commentCount, " +
		" d.DatePosted, d.Downloads, U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
		" (select COUNT(ID) from DocumentLike DL where DL.DocumentId = d.id) likeCount " +
		" from Document d, User U where U.Id = d.PosterId and d.Id = ?";
		return getBean(query, DocumentHeaderDto.class, docId);
	}
	
	public int countPhotoListForHeader(int libraryId, int folderId, int userId, int docGroupNumber) throws Exception
	{
		String query = " select count(Id) from Document where LibraryId = ? and FolderId = ? and PosterId = ? and DocGroupNumber = ? ";
		
		return getSimpleJdbcTemplate().queryForInt(query, libraryId, folderId, userId, docGroupNumber);
	}
	
	public List<DocumentHeaderDto> getPhotoListForHeader(int libraryId, int folderId, int userId, int docGroupNumber) throws Exception
	{
		String query = " select d.id docId, d.title, '' itemDesc, " +
		" d.FileName, d.FileContentType, 0 as ImageCount, " +
		" (select SUM(STARS) / COUNT(STARS) from DocumentRating DR where DR.documentid = d.id) avgRating, " +
		" (select COUNT(ID) from DocumentComment DC where DC.documentid = d.id) as commentCount, " +
		" d.DatePosted, d.Downloads, " +
		" (select COUNT(ID) from DocumentLike DL where DL.DocumentId = d.id) likeCount " +
		" from Document d where d.LibraryId = ? and d.FolderId = ? and d.PosterId = ? and d.DocGroupNumber = ? order by docId desc ";
		return getBeanList(query, DocumentHeaderDto.class, libraryId, folderId, userId, docGroupNumber);
	}
}