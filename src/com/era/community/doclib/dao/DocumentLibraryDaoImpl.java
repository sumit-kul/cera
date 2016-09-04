package com.era.community.doclib.dao;

import java.util.Date;
import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.Option;

import com.era.community.communities.dao.Community;
import com.era.community.doclib.ui.dto.DocumentRatingBean;
import com.era.community.monitor.ui.dto.SubscriptionItemDto;
import com.era.community.pers.dao.User;

public class DocumentLibraryDaoImpl extends com.era.community.doclib.dao.generated.DocumentLibraryDaoBaseImpl implements DocumentLibraryDao {


	/* Constants with SQL queries */
	private static final String BASE_SQL = "select D.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent "
		+ ",coalesce ( (select avg(stars) from DocumentRating  R where R.DocumentId = D.Id), 0) as StarRating "
		+",coalesce ( (select count(posterid) from DocumentRating  R where R.DocumentId = D.Id), 0) as CountUser "
		+ " from Document D, User U"
		+ " where D.LibraryId = ? and D.PosterId = U.Id";

	public static final String THEMECLAUSE = " and exists (select * from ThemeDocumentLink L where L.ThemeId = ? and L.DocumentId = D.id)" ;

	public static final String NOTHEMECLAUSE = " and not exists (select * from ThemeDocumentLink L where L.DocumentId = D.id)" ;

	public DocumentLibrary getDocumentLibraryForCommunity(Community comm) throws Exception
	{
		return (DocumentLibrary) getEntityWhere("CommunityId=?", comm.getId());
	}
	
	public DocumentLibrary getDocumentLibraryForCommunityId(int commId) throws Exception
	{
		return (DocumentLibrary) getEntityWhere("CommunityId=?", commId);
	}

	public QueryScroller listDocumentsByDate(DocumentLibrary lib, Class beanClass, int themeId, boolean ignoreThemes) throws Exception
	{

		String sThemeClause = ignoreThemes ? "" :   ( themeId>0 ? THEMECLAUSE : NOTHEMECLAUSE);
		String query = BASE_SQL  +sThemeClause;

		QueryScroller scroller;
		if (themeId> 0 && !ignoreThemes)
			scroller = getQueryScroller(query,  beanClass,   lib.getId(), themeId);
		else
			scroller = getQueryScroller(query,  beanClass,   lib.getId());

		scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public QueryScroller listDocumentsByTitle(DocumentLibrary lib, Class beanClass, int themeId, boolean ignoreThemes) throws Exception
	{
		String sThemeClause = ignoreThemes ? "" :   ( themeId>0 ? THEMECLAUSE : NOTHEMECLAUSE);
		String query = BASE_SQL  +sThemeClause;

		QueryScroller scroller;
		if (themeId> 0 && !ignoreThemes)
			scroller = getQueryScroller(query,  beanClass,   lib.getId(), themeId);
		else
			scroller = getQueryScroller(query,  beanClass,   lib.getId());

		scroller.addScrollKey("STEMP.Title", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);

		return scroller;
	}

	public QueryScroller listDocumentsByAuthor(DocumentLibrary lib, Class beanClass, int themeId, boolean ignoreThemes) throws Exception
	{
		String sThemeClause = ignoreThemes ? "" :   ( themeId>0 ? THEMECLAUSE : NOTHEMECLAUSE);
		String query = BASE_SQL  +sThemeClause;

		QueryScroller scroller;
		if (themeId> 0 && !ignoreThemes)
			scroller = getQueryScroller(query,  beanClass,   lib.getId(), themeId);
		else
			scroller = getQueryScroller(query,  beanClass,   lib.getId());
		scroller.addScrollKey("STEMP.LastName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		scroller.addScrollKey("STEMP.FirstName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}

	public QueryScroller listDocumentsForUser(DocumentLibrary lib, Class beanClass, User user, int themeId, boolean ignoreThemes) throws Exception
	{
		String sThemeClause = ignoreThemes ? "" :   ( themeId>0 ? THEMECLAUSE : NOTHEMECLAUSE);
		String query = BASE_SQL  + " and D.PosterId = ? " + sThemeClause;
		QueryScroller scroller;
		if (themeId> 0 && !ignoreThemes)
			scroller = getQueryScroller(query, beanClass, user.getId(), lib.getId(), themeId);
		else
			scroller = getQueryScroller(query, beanClass, user.getId(), lib.getId());

		scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public QueryScroller listDocumentsByRating(DocumentLibrary lib, Class beanClass, int themeId, boolean ignoreThemes) throws Exception
	{
		String sThemeClause = ignoreThemes ? "" :   ( themeId>0 ? THEMECLAUSE : NOTHEMECLAUSE);
		String query = BASE_SQL  +sThemeClause;

		QueryScroller scroller;
		if (themeId> 0 && !ignoreThemes)
			scroller = getQueryScroller(query, DocumentRatingBean.class, lib.getId(), themeId);
		else
			scroller = getQueryScroller(query, DocumentRatingBean.class, lib.getId());

		scroller.addScrollKey("coalesce ( (select avg(stars) from DocumentRating  R where R.DocumentId = D.Id), 0)", "StarRating", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_INTEGER);
		return scroller;
	}

	public Community getCommunityForDoclib(DocumentLibrary doclib) throws Exception
	{
		return (Community) getEntityWhere( "Id = ?", doclib.getCommunityId());
	}

	public Date getLatestPostDate(DocumentLibrary doclib) throws Exception
	{
		String query = "select max(DatePosted) from Document where LibraryId = ?";
		return (Date)getSimpleJdbcTemplate().queryForObject(query, Date.class , doclib.getId());
	}

	public List getItemsSince(DocumentLibrary lib, Date date) throws Exception
	{
		String sQuery = "Select D.Id as ItemId, D.Title as ItemTitle, D.Created As ItemDate, " +
		" D.PosterId AS AuthorId, CONCAT_WS(' ',U.FirstName,U.LastName) AS AuthorName, C.ID AS CommunityId, C.Name AS CommunityName " +
		" from Document D, User U, DocumentLibrary DL, Community C " +
		" where U.ID = D.PosterId and D.LibraryId = DL.ID and DL.CommunityId = C.ID and D.Created > ? and D.Libraryid=? ";
		return  getBeanList(sQuery, SubscriptionItemDto.class, date, lib.getId());
	}

	public int getUnThemedDocumentCount(DocumentLibrary documentLibrary) throws Exception
	{
		String query = "select count(*) as DocumentCount from Document D where " +
		"D.LibraryId = ? " +
		"and not exists (select * from ThemeDocumentLink L where L.DocumentId = D.id)" ;
		return getSimpleJdbcTemplate().queryForInt(query, documentLibrary.getId());
	}

	@SuppressWarnings("unchecked")
	public List getLibraryThemeOptionList(int communityId) throws Exception
	{             
		String sql="select Name as label, Id as value, sequence from Theme where CommunityId = ? and Library = 'Y' order by Sequence ";        
		return getBeanList(sql,Option.class, communityId);
	}
}