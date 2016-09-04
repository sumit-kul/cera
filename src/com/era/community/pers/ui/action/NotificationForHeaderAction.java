package com.era.community.pers.ui.action;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.NotificationFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.ui.dto.NotificationDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/notificationForHeader.ajx"
 */
public class NotificationForHeaderAction extends AbstractCommandAction
{
	public static final String REQUIRES_AUTHENTICATION = "";
	private CommunityEraContextManager contextManager; 
	protected DashBoardAlertFinder dashBoardAlertFinder;
	protected NotificationFinder notificationFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();

		if (currentUser != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = new JSONObject();
			JSONArray jData = new JSONArray();
			try {
				List<NotificationDto> lst = notificationFinder.listNotificationForHeader(currentUser.getId());
				json.put("invCont", lst.size());
				if (lst != null && lst.size() > 0) {
					for (NotificationDto dto : lst) {
						JSONObject name = new JSONObject();
						name.put("itemTitle", dto.getItemTitleDisplay());
						name.put("communityId", dto.getCommunityId());
						name.put("itemId", dto.getItemId());
						name.put("itemType", dto.getItemType());
						name.put("datePosted", dto.getDatePostedOn());
						name.put("posterId", dto.getPosterId());
						name.put("link", dto.getItemUrl());
						name.put("photoPresent", dto.isPhotoPresent());
						name.put("logoPresent", dto.isLogoPresent());
						jData.add(name);
					}
				}
			} catch (ElementNotFoundException e) {
			}
			
			DashBoardAlert alert = dashBoardAlertFinder.getDashBoardAlertForUserId(currentUser.getId());
			alert.setNotificationCount(0);
			alert.update();
			
			json.put("bData", jData);
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
		}
		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean 
	{
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setDashBoardAlertFinder(DashBoardAlertFinder dashBoardAlertFinder) {
		this.dashBoardAlertFinder = dashBoardAlertFinder;
	}

	public void setNotificationFinder(NotificationFinder notificationFinder) {
		this.notificationFinder = notificationFinder;
	}
}