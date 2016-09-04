package com.era.community.events.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.events.dao.EventFinder;
import com.era.community.events.ui.dto.EventPannelDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/event/eventPannel.ajx"
 */
public class EventPannelAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected EventFinder eventFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		HttpServletResponse resp = context.getResponse();

		JSONObject json = new JSONObject();
		JSONArray aData = new JSONArray();
		List<EventPannelDto> stories = new ArrayList<EventPannelDto>();
		if ("popular".equalsIgnoreCase(cmd.getType())){
			stories = eventFinder.listEventsForPannel(cmd.getType(), 8);
		} else {
			stories = eventFinder.listEventsForPannel(cmd.getType(), 8);
		}

		for (EventPannelDto dto : stories) {
			aData.add(toJsonStringForMedia(dto));
		}

		json.put("aData", aData);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	private JSONObject toJsonStringForMedia(EventPannelDto dto) throws Exception
	{
		JSONObject row = new JSONObject();
		row.put("entryId", dto.getEntryId());
		row.put("name", dto.getName());
		row.put("posterId", dto.getPosterId());
		row.put("contactName", dto.getContactName());
		row.put("photoPresent", dto.isPhotoPresent());
		row.put("eventPhotoPresent", dto.isEventPhotoPresent());
		row.put("communityId", dto.getCommunityID());
		return row;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private String type;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setEventFinder(EventFinder eventFinder) {
		this.eventFinder = eventFinder;
	}
}