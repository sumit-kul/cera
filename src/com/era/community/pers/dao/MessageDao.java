package com.era.community.pers.dao; 

import java.util.List;
import support.community.database.QueryScroller;

interface MessageDao extends com.era.community.pers.dao.generated.MessageDaoBase, MessageFinder
{
	public QueryScroller listMessagesForUserByDate(User  user,String order) throws Exception;
	public QueryScroller listUnreadMessagesForUserByDate(User user,String order) throws Exception;
	public int getMessageCount(User user,String msgType) throws Exception;
	public QueryScroller listSentMessagesForUserByDate(User user,String order) throws Exception;
	public QueryScroller listArchivedMessagesForUserByDate(User user,String order) throws Exception;
	public QueryScroller listReceivedMessagesForUserByDate(User  user,String order) throws Exception;
	public QueryScroller getListOfMessageIds(User user,String msgType) throws Exception;
	public int getNewMessageCountAfterLogin(User user) throws Exception;
	public List <Message> listMessagesToArchive(int userId) throws Exception;
	public List <Message> listMessagesToRestore(int userId) throws Exception;
	public List <Message> listAllMessagesToReadUnread(int userId) throws Exception;
	public void deleteAllMessage(int userId) throws Exception;
}