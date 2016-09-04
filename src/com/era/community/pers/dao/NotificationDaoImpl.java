package com.era.community.pers.dao; 

import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.pers.ui.dto.NotificationDto;

public class NotificationDaoImpl extends com.era.community.pers.dao.generated.NotificationDaoBaseImpl implements NotificationDao
{
	public List<NotificationDto> listNotificationForHeader(int userId) throws Exception {
		String query = "select * from  ( "
			
			// Community
			+ " select  C.Id itemId, C.Name itemTitle, C.Systype itemType, C.Created datePosted, N.Communityid communityId, C.Name, C.CommunityLogoPresent logoPresent, "
			+ " U.Id PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT PHOTOPRESENT "
			+ " from Notification N, User U, Community C "
			+ " where U.Id = C.CreatorId and N.ItemId = C.Id and N.CommunityId = C.Id and N.UserId = ? "
			+ " union "
			
			// Documents
			+ " select  D.Id itemId, D.Title itemTitle, D.Systype itemType, D.DatePosted datePosted, N.Communityid communityId, C.Name, C.CommunityLogoPresent logoPresent, "
			+ " U.Id PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT PHOTOPRESENT "
			+ " from Notification N, Document D, User U, Community C "
			+ " where U.Id = D.PosterId and N.ItemId = D.Id and N.CommunityId = C.Id and N.UserId = ? "
			+ " union "

			// Events
			+ " select  E.Id itemId, E.Name itemTitle, E.Systype itemType, E.Modified datePosted, N.Communityid communityId, C.Name, C.CommunityLogoPresent logoPresent, "
			+ " U.Id PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT PHOTOPRESENT "
			+ " from Notification N, Event E, User U, Community C "
			+ " where U.Id = E.PosterId and N.ItemId = E.Id and N.CommunityId = C.Id and N.UserId = ? "
			+ " union "

			// blog
			+ " select  E.Id itemId, E.Title itemTitle, E.Systype itemType, E.DatePosted datePosted, N.Communityid communityId, C.Name, C.CommunityLogoPresent logoPresent, "
			+ " U.Id PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT PHOTOPRESENT "
			+ " from Notification N, BlogEntry E, User U, Community C "
			+ " where U.Id = E.PosterId and N.ItemId = E.Id and N.CommunityId = C.Id and N.UserId = ? "
			+ " union "

			// Wiki
			+ " select  W.Id itemId, W.Title itemTitle, W.Systype itemType, W.DatePosted datePosted, N.Communityid communityId, C.Name, C.CommunityLogoPresent logoPresent, "
			+ " U.Id PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT PHOTOPRESENT "
			+ " from Notification N, WikiEntry W, User U, Community C "
			+ " where U.Id = W.PosterId and N.ItemId = W.Id and N.CommunityId = C.Id and N.UserId = ? and "
			+ Integer.MAX_VALUE
			+ " union "

			// Forum topics and responses - excluding deleted items
			+ " select  F.Id itemId, F.Subject itemTitle, F.Systype itemType, F.DatePosted datePosted, N.Communityid communityId, C.Name, C.CommunityLogoPresent logoPresent, "
			+ " U.Id PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT PHOTOPRESENT "
			+ " from Notification N, ForumTopic F, User U, Community C "
			+ " where U.Id = F.AuthorId and N.ItemId = F.Id and N.CommunityId = C.Id and F.deletestatus <> 9 and N.UserId = ? "
			+ " ) A order by A.DatePosted desc LIMIT 8 ";

		List <NotificationDto> list = getBeanList(query, NotificationDto.class, userId, userId, userId, userId, userId, userId);
		return list;
	}
	
	public QueryScroller listNotificationForUser(int userId) throws Exception 
	{
		QueryScroller scroller = null;
		
		String query = "select * from  ( "
			
			// Community
			+ " select  C.Id itemId, C.Name itemTitle, C.Systype itemType, C.Created datePosted, N.Communityid communityId, C.Name, C.CommunityLogoPresent logoPresent, "
			+ " U.Id PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT PHOTOPRESENT "
			+ " from Notification N, User U, Community C "
			+ " where U.Id = C.CreatorId and N.ItemId = C.Id and N.CommunityId = C.Id and N.UserId = ? "
			+ " union "
			
			// Documents
			+ " select  D.Id itemId, D.Title itemTitle, D.Systype itemType, D.DatePosted datePosted, N.Communityid communityId, C.Name, C.CommunityLogoPresent logoPresent, "
			+ " U.Id PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT PHOTOPRESENT "
			+ " from Notification N, Document D, User U, Community C "
			+ " where U.Id = D.PosterId and N.ItemId = D.Id and N.CommunityId = C.Id and N.UserId = ? "
			+ " union "

			// Events
			+ " select  E.Id itemId, E.Name itemTitle, E.Systype itemType, E.Modified datePosted, N.Communityid communityId, C.Name, C.CommunityLogoPresent logoPresent, "
			+ " U.Id PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT PHOTOPRESENT "
			+ " from Notification N, Event E, User U, Community C "
			+ " where U.Id = E.PosterId and N.ItemId = E.Id and N.CommunityId = C.Id and N.UserId = ? "
			+ " union "

			// blog
			+ " select  E.Id itemId, E.Title itemTitle, E.Systype itemType, E.DatePosted datePosted, N.Communityid communityId, C.Name, C.CommunityLogoPresent logoPresent, "
			+ " U.Id PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT PHOTOPRESENT "
			+ " from Notification N, BlogEntry E, User U, Community C "
			+ " where U.Id = E.PosterId and N.ItemId = E.Id and N.CommunityId = C.Id and N.UserId = ? "
			+ " union "

			// Wiki
			+ " select  W.Id itemId, W.Title itemTitle, W.Systype itemType, W.DatePosted datePosted, N.Communityid communityId, C.Name, C.CommunityLogoPresent logoPresent, "
			+ " U.Id PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT PHOTOPRESENT "
			+ " from Notification N, WikiEntry W, User U, Community C "
			+ " where U.Id = W.PosterId and N.ItemId = W.Id and N.CommunityId = C.Id and N.UserId = ? and "
			+ Integer.MAX_VALUE
			+ " union "

			// Forum topics and responses - excluding deleted items
			+ " select  F.Id itemId, F.Subject itemTitle, F.Systype itemType, F.DatePosted datePosted, N.Communityid communityId, C.Name, C.CommunityLogoPresent logoPresent, "
			+ " U.Id PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT PHOTOPRESENT "
			+ " from Notification N, ForumTopic F, User U, Community C "
			+ " where U.Id = F.AuthorId and N.ItemId = F.Id and N.CommunityId = C.Id and F.deletestatus <> 9 and N.UserId = ? "
			+ " ) A ";

		List <NotificationDto> list = getBeanList(query, NotificationDto.class, userId, userId, userId, userId, userId, userId);
		scroller = getQueryScroller(query, null, userId, userId, userId, userId, userId, userId);
		scroller.addScrollKey("STEMP.datePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}
}