package com.era.community.pers.dao; 

import java.util.List;
import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import com.era.community.pers.ui.dto.MessageDto;

public class MessageDaoImpl extends com.era.community.pers.dao.generated.MessageDaoBaseImpl implements MessageDao
{
	public QueryScroller listMessagesForUserByDate(User user,String order) throws Exception
	{
		String query = "Select M.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PHOTOPRESENT as PhotoPresent from Message M, User U"
			+ " where M.ToUserId = ? and M.FromUserId = U.Id and M.DeleteFlag <> 1";

		QueryScroller scroller = getQueryScroller(query, MessageDto.class, user.getId());

		if(order != null && order.equalsIgnoreCase(com.era.community.common.Constants.NAME)){
			scroller.addScrollKey("STEMP.FirstName","DisplayName", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		}
		else{
			scroller.addScrollKey("STEMP.DateSent", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}

	public QueryScroller listReceivedMessagesForUserByDate(User user,String order) throws Exception
	{
		String query = "Select M.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PHOTOPRESENT as PhotoPresent from ReceivedMessage M, User U"
			+ " where M.ToUserId = ? and M.FromUserId = U.Id  and M.DeleteFlag <> 1";

		QueryScroller scroller = getQueryScroller(query, MessageDto.class, user.getId());
		if(order != null && order.equalsIgnoreCase(com.era.community.common.Constants.NAME)){
			scroller.addScrollKey("STEMP.FirstName","DisplayName", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		}
		else{
			scroller.addScrollKey("STEMP.DateSent", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}

	/* (non-Javadoc)
	 * @see com.era.community.pers.dao.MessageDao#listUnreadMessagesForUserByDate(com.era.community.pers.dao.User)
	 */
	public QueryScroller listUnreadMessagesForUserByDate(User user,String order) throws Exception
	{
		String UNREAD_MESSAGE_SQL = "Select M.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PHOTOPRESENT as PhotoPresent from ReceivedMessage M, User U"
			+ " where M.ToUserId = ? and (M.AlreadyRead = '' OR M.AlreadyRead = 'N')and M.FromUserId = U.Id  and M.DeleteFlag <> 1";

		QueryScroller scroller = getQueryScroller(UNREAD_MESSAGE_SQL, MessageDto.class, user.getId());
		if(order != null && order.equalsIgnoreCase(com.era.community.common.Constants.NAME)){
			scroller.addScrollKey("STEMP.FirstName","DisplayName", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		}
		else{
			scroller.addScrollKey("STEMP.DateSent", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}

		return scroller;
	}

	/* (non-Javadoc)
	 * @see com.era.community.pers.dao.MessageDao#listUnreadMessagesForUserByDate(com.era.community.pers.dao.User)
	 */
	public int getMessageCount(User user,String msgType) throws Exception
	{
		String query = "select count(*) from Message M, User U where M.ToUserId = ? and M.FromUserId = U.Id  and M.DeleteFlag <> 1";

		if(msgType != null && msgType.equalsIgnoreCase(com.era.community.common.Constants.UNREAD)){
			query = "Select count(*) from ReceivedMessage M, User U where M.ToUserId = ? and (M.AlreadyRead = '' OR M.AlreadyRead = 'N') and M.FromUserId = U.Id  and M.DeleteFlag <> 1";
			return getSimpleJdbcTemplate().queryForInt(query,user.getId() );
		}
		if(msgType != null && msgType.equalsIgnoreCase(com.era.community.common.Constants.RECEIVED)){
			query = "Select count(*) from ReceivedMessage M, User U where M.ToUserId = ? and M.FromUserId = U.Id  and M.DeleteFlag <> 1";
			return getSimpleJdbcTemplate().queryForInt(query ,user.getId());
		}
		if(msgType != null && msgType.equalsIgnoreCase(com.era.community.common.Constants.SENT)){
			query = "Select count(*) from SentMessage M, User U where M.FromUserId = ? and M.ToUserId = U.Id  and M.DeleteFlag <> 1";
			return getSimpleJdbcTemplate().queryForInt(query ,user.getId());
		}
		if(msgType != null && msgType.equalsIgnoreCase(com.era.community.common.Constants.ARCHIVED)){
			query = "Select count(*) from Message M, User U"
			+ " where M.ToUserId = ? and M.FromUserId = U.Id and M.DeleteFlag = 1";
			return getSimpleJdbcTemplate().queryForInt(query ,user.getId());
		}
		else{
			return getSimpleJdbcTemplate().queryForInt(query,user.getId());
		}

	}

	/* (non-Javadoc)
	 * @see com.era.community.pers.dao.MessageDao#listUnreadMessagesForUserByDate(com.era.community.pers.dao.User)
	 */
	public QueryScroller getListOfMessageIds(User user,String msgType) throws Exception
	{
		String query = "select M.id from Message M, User U where M.ToUserId = ? and M.FromUserId = U.Id  and M.DeleteFlag <> 1";

		if(msgType != null && msgType.equalsIgnoreCase(com.era.community.common.Constants.UNREAD)){
			query = "Select M.id from ReceivedMessage M, User U where M.ToUserId = ? and (M.AlreadyRead = '' OR M.AlreadyRead = 'N') and M.FromUserId = U.Id  and M.DeleteFlag <> 1";
		}
		if(msgType != null && msgType.equalsIgnoreCase(com.era.community.common.Constants.RECEIVED)){
			query = "Select M.id from ReceivedMessage M, User U where M.ToUserId = ? and M.FromUserId = U.Id  and M.DeleteFlag <> 1";
		}
		if(msgType != null && msgType.equalsIgnoreCase(com.era.community.common.Constants.SENT)){
			query = "Select M.id from SentMessage M, User U where M.FromUserId = ? and M.ToUserId = U.Id  and M.DeleteFlag <> 1";
		}
		if(msgType != null && msgType.equalsIgnoreCase(com.era.community.common.Constants.ARCHIVED)){
			query = "Select M.id from Message M, User U"
			+ " where M.ToUserId = ? and M.FromUserId = U.Id and M.DeleteFlag = 1";
		}

		QueryScroller scroller = getQueryScroller(query, MessageDto.class, user.getId());
		scroller.addScrollKey("STEMP.id", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_INTEGER);
		return scroller;
	}

	/* (non-Javadoc)
	 * @see com.era.community.pers.dao.MessageDao#listSentMessagesForUserByDate(com.era.community.pers.dao.User)
	 */
	public QueryScroller listSentMessagesForUserByDate(User user,String order) throws Exception
	{
		String query = "Select M.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PHOTOPRESENT as PhotoPresent from SentMessage M, User U"
			+ " where M.FromUserId = ? and M.ToUserId = U.Id  and M.DeleteFlag <> 1";

		QueryScroller scroller = getQueryScroller(query, MessageDto.class, user.getId());

		if(order != null && order.equalsIgnoreCase(com.era.community.common.Constants.NAME)){
			scroller.addScrollKey("STEMP.FirstName","DisplayName", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		}
		else{
			scroller.addScrollKey("STEMP.DateSent", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}

	/* (non-Javadoc)
	 * @see com.era.community.pers.dao.MessageDao#listArchivedMessagesForUserByDate(com.era.community.pers.dao.User)
	 * For which DeleteFlag == 1 Those messages are Archived
	 */
	public QueryScroller listArchivedMessagesForUserByDate(User user,String order) throws Exception
	{
		String query = "Select M.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PHOTOPRESENT as PhotoPresent from Message M, User U"
			+ " where M.ToUserId = ? and M.FromUserId = U.Id and M.DeleteFlag = 1";	

		QueryScroller scroller = getQueryScroller(query, MessageDto.class, user.getId());

		if(order != null && order.equalsIgnoreCase(com.era.community.common.Constants.NAME)){
			scroller.addScrollKey("STEMP.FirstName","DisplayName", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		}
		else{
			scroller.addScrollKey("STEMP.DateSent", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}

	public int getMessageCount(String sRegion) throws Exception
	{
		String query = "select count(*) from SentMessage";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getNewMessageCountAfterLogin(User user) throws Exception
	{
		String query = "Select count(*) from ReceivedMessage M, User U where M.ToUserId = ? and M.FromUserId = U.Id " +
		" and M.CREATED > U.DATELASTVISIT ";

		return getSimpleJdbcTemplate().queryForInt(query,user.getId());
	}

	public int getMessageSenderCount(String sRegion) throws Exception
	{
		String query = "select count(*) from User U where u.region = '"+sRegion +"' exists (select * from Message M where M.FromUserId = U.Id)";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getMessageReceiverCount(String sRegion) throws Exception
	{
		String query = "select count(*) from User U where  u.region = '"+sRegion +"' exists (select * from Message M where M.ToUserId = U.Id)";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getMessageCount() throws Exception
	{
		String query = "select count(*) from SentMessage";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getMessageSenderCount() throws Exception
	{
		String query = "select count(*) from User U where exists (select * from Message M where M.FromUserId = U.Id)";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getMessageReceiverCount() throws Exception
	{
		String query = "select count(*) from User U where exists (select * from Message M where M.ToUserId = U.Id)";
		return getJdbcTemplate().queryForInt(query);
	}

	public List <Message> listMessagesToArchive(int userId) throws Exception {
		String query = "Select M.* from Message M, User U"
			+ " where M.ToUserId = ? and M.FromUserId = U.Id and M.DeleteFlag <> 1";
		List <Message>list = getBeanList(query, Message.class, userId);
		return list;
	}
	
	public List <Message> listMessagesToRestore(int userId) throws Exception {
		String query = "Select M.* from Message M, User U"
			+ " where M.ToUserId = ? and M.FromUserId = U.Id and M.DeleteFlag = 1";
		List <Message>list = getBeanList(query, Message.class, userId);
		return list;
	}
	
	public List <Message> listAllMessagesToReadUnread(int userId) throws Exception {
		String query = "Select M.* from ReceivedMessage M, User U"
			+ " where M.ToUserId = ? and M.FromUserId = U.Id and M.DeleteFlag <> 1";
		List <Message>list = getBeanList(query, Message.class, userId);
		return list;
	}
	
	public void deleteAllMessage(int userId) throws Exception { //Delete all archived messages
		String query = "Select M.* from Message M, User U"
			+ " where M.ToUserId = ? and M.FromUserId = U.Id and M.DeleteFlag = 1";
		List <Message>list = getBeanList(query, Message.class, userId);
		for (Message msg : list) {
			deleteMessageForId(msg.getId());
		}
	}

}