package com.era.community.events.ui.action;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.events.dao.EventLike;
import com.era.community.events.dao.EventLikeFinder;

/**
 * @spring.bean name="/event/likeEvent.ajx"
 */
public class LikeEventAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	protected EventLikeFinder eventLikeFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() != null) {
			int currentUserId = context.getCurrentUser().getId();
			Command cmd = (Command) data;
			if (context.getRequest().getParameter("eventId") != null 
					&& !"".equals(context.getRequest().getParameter("eventId"))) {
				int id = Integer.parseInt(context.getRequest().getParameter("eventId"));
				cmd.setEventId(id);
			}
			EventLike eventLike;
			String returnString = "";
			String likeCount = "";
			if (cmd.getEventId() > 0) {
				try {
					eventLike = eventLikeFinder.getLikeForEventAndUser(cmd.getEventId(), currentUserId);
					//If no like exists, delete this like record
					eventLike.delete();
					int count = eventLikeFinder.getLikeCountForEvent(cmd.getEventId());
					likeCount = String.valueOf(count);
					if (count > 1) {
						likeCount = likeCount + " likes";
					} else {
						likeCount = likeCount + " like";
					}
					returnString = "<span>"+likeCount+"</span><i class='a-icon-text-separator'></i><a href='javascript: likeEntry("+cmd.getEventId()+")' class='euInfoSelect' style='font-weight: bold; padding-left:0px;' >Like</a>";
				} catch (ElementNotFoundException e) {
					//If no like exists, create a new like record
					eventLike = eventLikeFinder.newEventLike();
					
					eventLike.setEventId(cmd.getEventId());
					eventLike.setPosterId(currentUserId);
					eventLike.update();     
					
					int count = eventLikeFinder.getLikeCountForEvent(cmd.getEventId());
					likeCount = String.valueOf(count);
					if (count > 1) {
						likeCount = likeCount + " likes";
					} else {
						likeCount = likeCount + " like";
					}
					returnString = "<span>"+likeCount+"</span><i class='a-icon-text-separator'></i><a href='javascript: likeEntry("+cmd.getEventId()+")' class='euInfoSelect' style='font-weight: bold; padding-left:0px;' >Unlike</a>";
				}
				
				HttpServletResponse resp = contextManager.getContext().getResponse();
				resp.setContentType("text/html");
				Writer out = resp.getWriter();
				out.write(returnString);
				out.close();
				return null;
			}
		}
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int eventId;

		/**
		 * @return the eventId
		 */
		public int getEventId() {
			return eventId;
		}

		/**
		 * @param eventId the eventId to set
		 */
		public void setEventId(int eventId) {
			this.eventId = eventId;
		}
	}

	/**
	 * @param eventLikeFinder the eventLikeFinder to set
	 */
	public void setEventLikeFinder(EventLikeFinder eventLikeFinder) {
		this.eventLikeFinder = eventLikeFinder;
	}

	/**
	 * @param contextManager the contextManager to set
	 */
	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}
}