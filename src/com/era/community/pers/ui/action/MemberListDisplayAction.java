package com.era.community.pers.ui.action;

import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.ui.dto.UserDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/memberList.ajx" 
 */
public class MemberListDisplayAction extends AbstractCommandAction
{
    private CommunityEraContextManager contextManager; 
    private CommunityFinder communityFinder;

    protected ModelAndView handle(Object data) throws Exception
    {
    	Command cmd = (Command)data; 
    	CommunityEraContext context = contextManager.getContext();
    	HttpServletResponse resp = contextManager.getContext().getResponse();
    	Community community = null;
    	try {
    		community = communityFinder.getCommunityForId(cmd.getCommunityId());
    		int memCount = community.getMemberCount();
    		List<String> memList = community.getRecentMemberNames();
	    	int remaining = memCount > 20 ? memCount - 20 : 0 ;
	    	if (memList != null && memList.size() > 0) {
	    		JSONObject json = new JSONObject();
		        JSONArray jData = new JSONArray();
		        for (Iterator iterator = memList.iterator(); iterator.hasNext();) {
					String member = (String) iterator.next();
		        	JSONObject name = new JSONObject();
		        	name.put("memberName", member);
		        	jData.add(name);
				}
		        json.put("aData", jData);
		        json.put("remaining", remaining);
		        String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
	    	}
		} catch (ElementNotFoundException e) {
		}
		return null;
    }

    public class Command extends UserDto implements CommandBean
    {
        private int communityId;

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
    }
    
    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}
}