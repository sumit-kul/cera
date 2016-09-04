package com.era.community.pers.dao; 

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.pers.ui.dto.NotificationDto;

public interface NotificationFinder extends com.era.community.pers.dao.generated.NotificationFinderBase
{
	public List<NotificationDto> listNotificationForHeader(int userId) throws Exception;
	public QueryScroller listNotificationForUser(int userId) throws Exception;
}