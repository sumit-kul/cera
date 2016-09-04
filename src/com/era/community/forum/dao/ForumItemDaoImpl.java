package com.era.community.forum.dao;

import java.util.Date;
import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;
import com.era.community.forum.ui.dto.ForumItemDto;
import com.era.community.forum.ui.dto.ForumItemHeaderDto;
import com.era.community.forum.ui.dto.ForumTopicPannelDto;
import com.era.community.monitor.ui.dto.SubscriptionItemDto;

public class ForumItemDaoImpl extends com.era.community.forum.dao.generated.ForumItemDaoBaseImpl implements ForumItemDao
{
	public List getItemBeans(int topicId) throws Exception
	{
		final String sQuery = "select (SELECT (COUNT(parent.ID)-1) AS depth " +
				" FROM ForumItem AS node, ForumItem AS parent WHERE (node.lft BETWEEN parent.lft AND parent.rht and (node.TopicId = parent.TopicId or node.TopicId = parent.ID)) and node.id = I.ID) as depth, " +
				" I.Id ,I.ForumId ,I.Subject, I.AuthorId ,I.DatePosted ,I.Closed ,I.ClosedById ,I.ClosedOn ,I.Sticky ,I.DeleteStatus , " +
				" coalesce (I.VISITORS, 0 ) as visitors ,I.LastVisitorsTime ,I.Modified ,I.Created ,I.TopicId ,I.SysType , I.ParentId , I.Body,  " +
				" coalesce ( (select count(*) from ForumItemLike L where L.FORUMITEMID = I.Id), 0) As ItemLike, " +
				" coalesce ( (select count(*) from ForumItemLike L where L.FORUMITEMID = I.Id and L.PosterId = U.Id), 0) as AlreadyLike, " +
				" CONCAT_WS(' ',U.FirstName,U.LastName) as AuthorName, U.PhotoPresent as PhotoPresent, IP.AuthorId as parentAuthorId, " +
				" UP.PhotoPresent as parentAuthorPhotoPresent, CONCAT_WS(' ',UP.FirstName,UP.LastName) as parentAuthorName, IP.DatePosted as parentPostDate" +
				" from ForumItem I, ForumTopic T, User U, ForumItem IP, User UP " +
				" where I.AuthorId= U.Id and I.TopicId = T.Id and I.TopicId = ? and I.ParentId = IP.ID and IP.AuthorId = UP.ID and I.DeleteStatus <> 9 " +
				" and I.lft >= T.lft ORDER BY I.lft ";
				
		return getBeanList(sQuery, ForumItemDto.class, topicId);
	}
	
	public void updateForhierarchy(int parentId, int topicId, int newId, int isROOT) throws Exception
	{
	 String sql ="call Forum_Response_Add_Node(" +parentId+ ", " + topicId + ", " + newId + ", " + isROOT + ")";
	 getJdbcTemplate().update(sql);
	}
	
	public int getLastSiblingId(int parentId, int topicId, int newId) throws Exception
	{
	 String sql ="SELECT max(ID) FROM ForumItem  WHERE ParentId = ? AND TopicId = ? AND Id <> ?";
	 return getSimpleJdbcTemplate().queryForInt(sql, parentId, topicId, newId);
	}
	
	public int getDepthForItem(int itemId) throws Exception
	{
	 String sql ="SELECT (COUNT(parent.ID)-1) AS depth  FROM ForumItem AS node, ForumItem AS parent " +
	 "WHERE (node.lft BETWEEN parent.lft AND parent.rht and (node.TopicId = parent.TopicId or node.TopicId = parent.ID)) and node.id =  ?";
	 return getSimpleJdbcTemplate().queryForInt(sql, itemId);
	}


	public boolean isFile1Present(ForumItem item) throws Exception
	{
		Integer v = (Integer) getValue(item, "case when File1 is null then 0 else 1 end ", Integer.class);
		return v.intValue() == 1;
	}

	public boolean isFile2Present(ForumItem item) throws Exception
	{
		Integer v = (Integer) getValue(item, "case when File2 is null then 0 else 1 end ", Integer.class);
		return v.intValue() == 1;
	}

	public boolean isFile3Present(ForumItem item) throws Exception
	{
		Integer v = (Integer) getValue(item, "case when File3 is null then 0 else 1 end ", Integer.class);
		return v.intValue() == 1;
	}

	public void clearFile1(ForumItem item) throws Exception
	{
		setColumn(item, "File1", null);
		setColumn(item, "File1ContentType", null);
		setColumn(item, "FileName1", null);
		setColumn(item, "File1Length", 0);
	}

	public void clearFile2(ForumItem item) throws Exception
	{
		setColumn(item, "File2", null);
		setColumn(item, "File2ContentType", null);
		setColumn(item, "FileName21", null);
		setColumn(item, "File2Length", 0);
	}

	public void clearFile3(ForumItem item) throws Exception
	{
		setColumn(item, "File3", null);
		setColumn(item, "File3ContentType", null);
		setColumn(item, "FileName3", null);
		setColumn(item, "File3Length", 0);
	}

	public long getFile1SizeInBytes(ForumItem item) throws Exception
	{
		return this.getFile1SizeInBytes(item);
	}

	public String getFile1IconImage(ForumItem item) throws Exception
	{
		return this.getFile1IconImage(item);
	}

	public String getFile2IconImage(ForumItem item) throws Exception
	{
		return this.getFile2IconImage(item);
	}

	public String getFile3IconImage(ForumItem item) throws Exception
	{
		return this.getFile3IconImage(item);
	}

	public QueryScroller listAllItems() throws Exception
	{
		String sql = "select I.* from ForumItem I ,Forum F, Community C where " +
		" F.Id = I.ForumId and C.ID = F.CommunityId and C.SysType != 'PrivateCommunity' and I.DeleteStatus <> " + ForumItem.STATUS_DELETED;
		return getQueryScroller(sql, null);
	}

	public QueryScroller listAllTopics() throws Exception
	{
		QueryScroller scroller = getQueryScroller("select I.* from ForumTopic I where I.DeleteStatus <> 9", null);
		return scroller;
	}

	public QueryScroller listResponsesForTopic(ForumTopic topic) throws Exception
	{
		QueryScroller scroller = getQueryScroller("select R.* from ForumResponse R where R.TopicId = ? and R.DeleteStatus <> 9", null, topic.getId());
		return scroller;
	}
	
	public QueryScroller listForumResponsesForDsiplay(int topicId) throws Exception
	{
		String query = "select * from (select R.* from ForumResponse R where R.TopicId = ? and R.DeleteStatus <> 9 LIMIT 3 ) as res";
		QueryScroller scroller =  getQueryScroller(query, null, topicId);
		scroller.addScrollKey("DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public Forum getForumForItem(ForumItem item) throws Exception
	{
		return (Forum) getEntityWhere("Id = ?", item.getForumId());
	}

	public int getThemeIdForItem(ForumItem item) throws Exception
	{
		ThemeTopicLink link =  (ThemeTopicLink) getEntityWhere("L.ItemId = ?", item.getId());
		return link.getThemeId();
	}


	public List getItemsSince(ForumItem item, Date date) throws Exception
	{
		String sQuery = "Select Id As ItemId, Subject as ItemTitle , Created as ItemDate," +
				" R.AuthorId as AuthorId, CONCAT_WS(' ',U.FirstName,U.LastName) as AuthorName from "+
		"ForumResponse R, User U where U.Id = R.AuthorId and (R.Created > ? and R.TopicId=? and R.DeleteStatus <> 9)";

		return getBeanList(sQuery, SubscriptionItemDto.class, date, item.getId());
	}

	public Date getLatestPostDate(ForumItem item) throws Exception
	{
		String query = "select max(DatePosted) from ForumResponse R where TopicId = ? and R.DeleteStatus <> 9";
		return (Date) getSimpleJdbcTemplate().queryForObject(query,  Date.class, item.getId());
	}
	
	public ForumResponse getLatestPost(ForumItem item) throws Exception
	{
		String query = "select R.* from ForumResponse R where TopicId = ? and R.DeleteStatus <> 9 order by R.ID desc LIMIT 1";
		List postLst = getBeanList(query, ForumResponse.class, item.getId());
		if (postLst != null && postLst.size() > 0) {
			return (ForumResponse)postLst.get(0);
		} else {
			return null;
		}
	}

	public int getResponseCount(int groupId) throws Exception
	{
		String query = groupId==0 ?  "select count(*) from ForumResponse R where R.DeleteStatus <> 9" :
			"select count(*) from ForumResponse R, Forum F, CommunityGroupLink L where "+
			"L.CommunityId = F.CommunityId and  L.CommunityGroupId=? and R.ForumId = F.Id  and R.DeleteStatus <> 9 ";

		if (groupId==0)
			return getSimpleJdbcTemplate().queryForInt(query);
		else
			return 
			getSimpleJdbcTemplate().queryForInt(query,  groupId ) ;
	}

	public int getTopicCount(int groupId) throws Exception
	{
		String queryNoGroup = "select count(*) from ForumTopic T where T.DeleteStatus <> 9";

		String queryForGroup = "select count(*) from ForumTopic T, Forum F, CommunityGroupLink L where "+
		"L.CommunityId = F.CommunityId and  L.CommunityGroupId=? and T.ForumId = F.Id  and T.DeleteStatus <> 9 ";

		if ( groupId==0  )
			return getJdbcTemplate().queryForInt(queryNoGroup);
		else
		{
			return getSimpleJdbcTemplate().queryForInt(queryForGroup, groupId );
		}

	}

	public int getParticipantCount(int groupId) throws Exception
	{
		if ( groupId==0  ) {
			String query = "select count(*) from User U where exists (select * from ForumItem I where I.AuthorId = U.Id)";
			return getJdbcTemplate().queryForInt(query);
		}
		else
		{
			String query = "select count(*) from User U where " +
			"exists (select * from Forum F, ForumItem I, CommunityGroupLink L where I.AuthorId = U.Id and F.Id = I.ForumId and F.CommunityId = L.CommunityId and L.CommunityGroupId=? )";
			return getSimpleJdbcTemplate().queryForInt(query, groupId);
		}
	}

	public int getThreadCount(int groupId) throws Exception
	{
		String queryNoGroup  = "select count(*) " +
		"from ForumTopic T " +
		"where T.DeleteStatus <> 9 and exists (select * from ForumResponse R where  R.TopicId = T.Id and R.DeleteStatus <> 9)";

		String queryForGroup = "select count(*) " +
		"from ForumTopic T, Forum F, CommunityGroupLink L  " +
		"where exists (select * from ForumResponse R where R.TopicId = T.Id and R.DeleteStatus <> 9) " +
		"and T.DeleteStatus <> 9 and T.ForumId = F.Id "+
		"and L.CommunityId = F.CommunityId "+
		" and L.CommunityGroupId = ? ";

		if ( groupId==0  )
			return getJdbcTemplate().queryForInt(queryNoGroup);
		else
		{
			return getSimpleJdbcTemplate().queryForInt(queryForGroup, groupId );
		}
	}

	public int getResponseCountForCommunity(Community comm) throws Exception
	{
		String query = "select count(*) from Forum F, ForumResponse R" +
		" where F.Id = R.ForumId and F.CommunityId = ? and R.DeleteStatus <> 9";
		return getSimpleJdbcTemplate().queryForInt(query, comm.getId());
	}

	public int getTopicCountForCommunity(Community comm) throws Exception
	{
		String query = "select count(*) from Forum F, ForumTopic T"+
		" where F.Id = T.ForumId and F.CommunityId = ? and T.DeleteStatus <> 9";
		return getSimpleJdbcTemplate().queryForInt(query, comm.getId() );
	}

	public int getParticipantCountForCommunity(Community comm) throws Exception
	{
		String query = "select count(*) from User U where " +
		"exists (select * from Forum F, ForumItem I where I.AuthorId = U.Id and F.Id = I.ForumId and F.CommunityId = ?)";
		return getSimpleJdbcTemplate().queryForInt(query, comm.getId() );
	}

	public int getThreadCountForCommunity(Community comm) throws Exception
	{
		String query = "select count(*) from Forum F, ForumTopic T where exists "+
		"(select * from ForumResponse R where R.TopicId = T.Id and R.DeleteStatus <> 9)"+
		" and F.Id = T.ForumId and T.DeleteStatus <> 9 and F.CommunityId = ?";
		return getSimpleJdbcTemplate().queryForInt(query, comm.getId() );
	}

	public int getStickyTopicCount(int forumId) throws Exception
	{
		return 0;
	}
	
	public List<ForumTopicPannelDto> listHotTopics(int max) throws Exception 
	{              
		String sql = " select T.Id as topicId, coalesce(T.Subject,' ') as Subject, C.Id as CommunityId, C.Name as CommunityName, C.CommunityLogoPresent LogoPresent, " +
		//" coalesce ( (select max(R.Id) from ForumResponse R where R.TopicId = T.Id and  R.DeleteStatus<>9 and T.DeleteStatus<>9  ), T.Id) As LastPostId , " +
		" coalesce ( (select count(*)  from ForumResponse R where R.TopicId = T.Id and R.DeleteStatus<>9 and T.DeleteStatus<>9), 0) as ResponseCount " +
		" from ForumTopic T, Forum F, Community C  where T.ForumId = F.Id and F.CommunityId = C.ID and C.SysType != 'PrivateCommunity' and T.DeleteStatus<>9 order by ResponseCount desc ";                    

		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List<ForumTopicPannelDto> list = getBeanList(sql, ForumTopicPannelDto.class);           
		return list;                      
	}
	
	public ForumItemHeaderDto getForumItemForHeader(int itemId) throws Exception
	{
		String query = " select f.id, f.subject, CAST(f.BODY as CHAR(1000) ) itemDesc, " +
			" (select COUNT(ID) from ForumResponse R where R.TopicId = f.id and R.DeleteStatus <> 9) as commentCount, f.DatePosted datePosted, " +
			" f.Visitors cVisitors, U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
			" (select COUNT(ID) from Image I where I.ForumItemId = f.Id) ImageCount , " +
			" (select COUNT(ID) from ForumItemLike FL where FL.ForumItemId = f.id) likeCount " +
			" from ForumTopic f, User U where U.Id = f.AuthorId and f.DeleteStatus <> 9 and f.Id = ? " ;
		return getBean(query, ForumItemHeaderDto.class, itemId);
	}
}