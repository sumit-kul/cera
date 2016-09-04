package com.era.community.forum.dao; 

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.Option;

import com.era.community.communities.dao.Community;
import com.era.community.forum.ui.dto.ForumItemDto;
import com.era.community.monitor.ui.dto.SubscriptionItemDto;

public class ForumDaoImpl extends com.era.community.forum.dao.generated.ForumDaoBaseImpl implements ForumDao
{

	public static final String RESPONSE_COUNT =   
		"coalesce ( (select count(*)  from ForumResponse R where "+
		"R.TopicId = T.Id and R.DeleteStatus<>9 and T.DeleteStatus<>9), 0) as ResponseCount ";

	public static final String LAST_UPDATE_NAME = " CONCAT_WS(' ',U.FirstName,U.LastName) as LastUpdateName ";
	public static final String LAST_UPDATE_ID = " U.ID as LastUpdateId ";

	public static final String EXCLUDE_DELETED = " R.DeleteStatus<>9 and T.DeleteStatus<>9  ";

	public static final String LAST_POST_ID =
		"coalesce ( (select max(R.Id) from ForumResponse R where R.TopicId = T.Id and "+
		EXCLUDE_DELETED + "), T.Id) As LastPostId , ";

	public static final String WHERECLAUSE = " where T.ForumId = ?  and T.DeleteStatus<>9 ";

	//public static final String THEMECLAUSE = " and exists (select * from ThemeTopicLink L where L.ThemeId = ? and L.ItemId = T.id)" ;

	//public static final String NOTHEME = " and not exists (select * from ThemeTopicLink L where  L.ItemId = T.id)" ;

	public static final String NOTHEMECLAUSE = " " ;

	public Forum getForumForCommunity(Community comm) throws Exception
	{
		return (Forum)getEntityWhere("CommunityId=?", comm.getId() );
	}
	
	public Forum getForumForCommunityId(int commId) throws Exception
	{
		return (Forum)getEntityWhere("CommunityId=?", commId );
	}

	public QueryScroller listTopicsByMostRecentResponse(Forum forum, int themeId, String sortBy) throws Exception
	{
		String sQuery = "select A.Id, A.Sticky, A.Closed, A.Subject, A.Body, A.AuthorId, A.DatePosted, " +
		" CONCAT_WS(' ',U1.FirstName,U1.LastName) as AuthorName, U2.ID as LastPosterId, CONCAT_WS(' ',U2.FirstName,U2.LastName) as LastPosterName, I.DatePosted as LatestPostDate, U1.PhotoPresent, U2.PhotoPresent as LastPostPhotoPresent, " +
		" coalesce (I.VISITORS, 0 ) as visitors, I.LASTVISITORSTIME , A.ResponseCount  from  ( " +
		" select T.Id, T.Sticky, T.Closed, T.ForumId, coalesce(T.Subject,' ') as Subject, T.Body as Body, T.AuthorId, T.DatePosted, " +
		" coalesce ( (select max(R.Id) from ForumResponse R where R.TopicId = T.Id and  R.DeleteStatus<>9 and T.DeleteStatus<>9  ), T.Id) As LastPostId , " +
		" coalesce ( (select count(*)  from ForumResponse R where R.TopicId = T.Id and R.DeleteStatus<>9 and T.DeleteStatus<>9), 0) as ResponseCount " +
		" from ForumTopic T  where T.ForumId = ?  and T.DeleteStatus<>9  ) A, User U1, User U2, ForumItem I " +
		" where A.Id is not null and U1.Id = A.AuthorId and I.Id = A.LastPostId  and U2.Id = I.AuthorId and I.DeleteStatus<>9 ";

		QueryScroller scroller;
		if (themeId> 0)
			scroller = getQueryScroller(sQuery, true, ForumItemDto.class,   forum.getId(), themeId);
		else
			scroller = getQueryScroller(sQuery, true,  ForumItemDto.class,   forum.getId());

		scroller.addScrollKey("STEMP.Sticky", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT); 
		if (sortBy != null && sortBy.equalsIgnoreCase("Subject")) {
			scroller.addScrollKey("STEMP.Subject", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else if (sortBy != null && sortBy.equalsIgnoreCase("Author")) {
			scroller.addScrollKey("STEMP.AuthorName", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		} else {
			scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}

	public QueryScroller listAllUnThemedTopicsByMostRecentResponse(String sortBy, String filterTagList) throws Exception
	{
		String filterTag = "";
		int count = 0;
		if(filterTagList != null && !"".equals(filterTagList)) {
			String filterTagPart = "";
			if (filterTagList.contains(",")) {
				StringTokenizer st = new StringTokenizer(filterTagList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					filterTagPart += " T.TagText = \'"+tag+"\' ";
					if (st.hasMoreTokens()) filterTagPart += " or ";
					count++;
				}
			} else {
				filterTagPart = " T.TagText = \'"+filterTagList+"\' ";
				count = 1;
			}
			filterTag = " and exists (select ParentId from (select count(ID), ParentId  from Tag T where PARENTTYPE = 'ForumTopic' ";
			filterTag = filterTag + " and " +filterTagPart+ " group by ParentId having count(ID) >= "+count+" ) as T where T.ParentId = T.Id) " ;
		}
		
		String sQuery = "select A.Id, A.Sticky, A.Closed, A.Subject, A.Body, A.AuthorId, A.DatePosted, " +
		" CONCAT_WS(' ',U1.FirstName,U1.LastName) as AuthorName, U2.ID as LastPosterId, CONCAT_WS(' ',U2.FirstName,U2.LastName) as LastPosterName, I.DatePosted as LatestPostDate, U1.PhotoPresent, U2.PhotoPresent as LastPostPhotoPresent, " +
		" coalesce (A.VISITORS, 0 ) as visitors, A.LASTVISITORSTIME , A.ResponseCount, A.topicLike, A.imageCount, A.responseLike, C.ID as communityId, C.name as communityName, C.SysType as CommunitySysType, C.CommunityLogoPresent as logoPresent  from  ( " +
		" select T.Id, T.Sticky, T.Closed, T.ForumId, T.Subject as Subject, T.Body as Body, T.AuthorId, T.DatePosted, T.VISITORS, T.LASTVISITORSTIME, " +
		" coalesce ( (select max(R.Id) from ForumResponse R where R.TopicId = T.Id and  R.DeleteStatus<>9 and T.DeleteStatus<>9  ), T.Id) As LastPostId , " +
		" coalesce ( (select count(*)  from ForumResponse R where R.TopicId = T.Id and R.DeleteStatus<>9 and T.DeleteStatus<>9), 0) as ResponseCount, " +
		" coalesce ( (select count(*) from ForumItemLike L where L.FORUMITEMID = T.Id), 0) As topicLike , " +
		" coalesce ( (select count(*) from Image I where I.ForumItemId = T.Id), 0) As imageCount , " +
		" coalesce ( (select count(*) from ForumItemLike L, ForumResponse R where R.Id = L.FORUMITEMID and R.TopicId = T.Id), 0) As responseLike " +
		" from ForumTopic T  where T.DeleteStatus<>9 "+ filterTag +" ) A, User U1, User U2, ForumItem I, Forum F , Community C " +
		" where A.Id is not null and U1.Id = A.AuthorId and I.Id = A.LastPostId  and U2.Id = I.AuthorId and I.DeleteStatus<>9 and A.FORUMID = F.Id and C.ID = F.COMMUNITYID ";
		QueryScroller scroller = getQueryScroller(sQuery, true, ForumItem.class);
		
		//scroller.addScrollKey("STEMP.Sticky", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT); 
		if (sortBy != null && sortBy.equalsIgnoreCase("Topic")) {
			scroller.addScrollKey("STEMP.Subject", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else if (sortBy != null && sortBy.equalsIgnoreCase("Author")) {
			scroller.addScrollKey("STEMP.AuthorName", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		} else {
			scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}

	public Date getLatestPostDate(Forum forum, int themeId) throws Exception
	{
		String query = "select max(DatePosted) from ForumItem where ForumId = ?";
		return getSimpleJdbcTemplate().queryForObject(query, Date.class, forum.getId() );
	}

	public QueryScroller listItemsByDate(Forum forum, int themeId) throws Exception
	{
		String sQuery = "select T.*  from ForumItem T " + WHERECLAUSE ;
		//+ ( themeId>0 ? THEMECLAUSE : NOTHEMECLAUSE) ;

		QueryScroller scroller = getQueryScroller(sQuery, ForumItem.class, forum.getId());
		scroller.addScrollKey("STEMP.Sticky", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return  scroller;
	}

	public List<SubscriptionItemDto> getItemsSince(Forum forum, Date date, int themeId) throws Exception
	{
		String sQuery = "Select T.Id as ItemId, T.Subject as ItemTitle, T.Created as ItemDate, " +
				"T.AuthorId as AuthorId, CONCAT_WS(' ',U.FirstName,U.LastName) as AuthorName from ForumTopic T, User U "+ WHERECLAUSE 
		+ "and U.Id = T.AuthorId and T.Created > ?" ;

		return  getBeanList(sQuery, SubscriptionItemDto.class, date, forum.getId());
	}

	public List getForumThemeOptionList(int communityId) throws Exception
	{             
		String sql="select Name as label, Id as value from Theme where CommunityId = ? and Forum = 'Y' order by sequence ";        
		return getBeanList(sql,Option.class, communityId);

	}
}