package com.era.community.wiki.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.wiki.dao.WikiEntryLike;
import com.era.community.wiki.dao.WikiEntryLikeFinder;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/wiki/likeWikiEntry.ajx"
 */
public class LikeWikiEntryAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	protected WikiEntryLikeFinder wikiEntryLikeFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() != null) {
			int currentUserId = context.getCurrentUser().getId();
			JSONObject json = new JSONObject();
			Command cmd = (Command) data;
			if (context.getRequest().getParameter("wikiEntryId") != null 
					&& !"".equals(context.getRequest().getParameter("wikiEntryId"))) {
				int id = Integer.parseInt(context.getRequest().getParameter("wikiEntryId"));
				cmd.setWikiEntryId(id);
			}
			WikiEntryLike wikiEntryLike;
			if (cmd.getWikiEntryId() > 0) {
				try {
					wikiEntryLike = wikiEntryLikeFinder.getLikeForWikiEntryAndUser(cmd.getWikiEntryId(), currentUserId);
					//If like exists, delete this like record
					wikiEntryLike.delete();
					int count = wikiEntryLikeFinder.getLikeCountForWikiEntry(cmd.getWikiEntryId());
					json.put("count", count);
					json.put("newLType", "Like");
				} catch (ElementNotFoundException e) {
					//If no like exists, create a new like record
					wikiEntryLike = wikiEntryLikeFinder.newWikiEntryLike();

					wikiEntryLike.setWikiEntryId(cmd.getWikiEntryId());
					wikiEntryLike.setPosterId(currentUserId);
					wikiEntryLike.update();     

					int count = wikiEntryLikeFinder.getLikeCountForWikiEntry(cmd.getWikiEntryId());
					json.put("count", count);
					json.put("newLType", "Unlike");
				}
				HttpServletResponse resp = contextManager.getContext().getResponse();
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
		}
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int wikiEntryId;

		public int getWikiEntryId() {
			return wikiEntryId;
		}

		public void setWikiEntryId(int wikiEntryId) {
			this.wikiEntryId = wikiEntryId;
		}

	}

	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setWikiEntryLikeFinder(WikiEntryLikeFinder wikiEntryLikeFinder) {
		this.wikiEntryLikeFinder = wikiEntryLikeFinder;
	}
}