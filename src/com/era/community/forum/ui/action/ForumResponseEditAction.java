package com.era.community.forum.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumItemFinder;
import com.era.community.forum.ui.dto.ForumItemDto;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/cid/[cec]/forum/editForumResponse.ajx"
 */
public class ForumResponseEditAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager;
	private ForumItemFinder itemFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		User currUser = context.getCurrentUser();
		if (currUser != null) {
			ForumItem response = itemFinder.getForumItemForId(cmd.getId());
			if (response != null && response.getAuthorId() == currUser.getId()) {
				response.setSubject(cmd.getSubject());
				response.setBody(cmd.getBody());
				response.update();
			}
			JSONObject json = new JSONObject();
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
		}
		return null;
	}

	public class Command extends ForumItemDto implements CommandBean
	{
	}

	public final CommunityEraContextManager getContextHolder()
	{
		return contextManager;
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setItemFinder(ForumItemFinder itemFinder)
	{
		this.itemFinder = itemFinder;
	}
}