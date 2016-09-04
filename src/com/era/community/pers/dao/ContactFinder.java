package com.era.community.pers.dao; 

import java.util.List;

import com.era.community.pers.ui.dto.UserDto;

import support.community.database.QueryScroller;

public interface ContactFinder extends com.era.community.pers.dao.generated.ContactFinderBase
{
	public Contact getContact(int contactOwnerId, int contactId) throws Exception;
	public void deleteAllContactsForUser(int contactOwnerId) throws Exception;
	public void deleteUserAsContact(int contactUserId) throws Exception;
	public void deleteUserAsConnection(int contactOwnerId, int contactUserId) throws Exception;
	public int getConnectionRequestReceivedCountForCurrentUser(int currentUserId) throws Exception;
	public int getConnectionRequestPendingCountForCurrentUser(int currentUserId) throws Exception;
	public QueryScroller listReceivedContactRequests(int userId) throws Exception;
	public QueryScroller listSentContactRequests(int userId) throws Exception;
	public int countReceivedContactRequests(int userId) throws Exception;
	public int countSentContactRequests(int userId) throws Exception;
	public QueryScroller listCommonConnections(int ownerId, int currentUserId) throws Exception;
	public int countCommonConnections(int ownerId, int currentUserId) throws Exception;
	public List listAllMyContacts(int userId, int max) throws Exception;
	
	public List<UserDto> listConnectionForHeader(int userId) throws Exception;
	public List<UserDto> listPeopleYouMayKnowForHeader(int userId, int max) throws Exception;
	public QueryScroller showPeopleYouMayKnow(int userId) throws Exception;
}