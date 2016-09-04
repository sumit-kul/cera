package com.era.community.communities.ui.action;

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
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.ui.dto.CommunityEntryPannelDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *
 * @spring.bean name="/community/communityPannel.ajx"
 */
public class CommunityPannelAction extends AbstractCommandAction 
{
    protected CommunityEraContextManager contextManager;
    protected CommunityFinder communityFinder;
        
    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command) data;
        CommunityEraContext context = contextManager.getContext();
        HttpServletResponse resp = context.getResponse();

		JSONObject json = new JSONObject();
		JSONArray aData = new JSONArray();
		
		List<CommunityEntryPannelDto> comms = new ArrayList<CommunityEntryPannelDto>();
		
		if ("top".equalsIgnoreCase(cmd.getType())){
			comms = context.getMostActiveCommunities();
		} else {
			comms = context.getMostRecentCommunities();
		}

		for (CommunityEntryPannelDto dto : comms) {
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
    
    private JSONObject toJsonStringForMedia(CommunityEntryPannelDto dto) throws Exception
	{
		JSONObject row = new JSONObject();
		row.put("entryId", dto.getEntryId());
		row.put("name", dto.getName());
		row.put("editedName", dto.getEditedName());
		row.put("logoPresent", dto.isLogoPresent());
		row.put("memberCount", dto.getMemberCount());
		row.put("createdOn", dto.getCreatedOn());
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

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}
}