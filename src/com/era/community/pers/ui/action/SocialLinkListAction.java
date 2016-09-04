package com.era.community.pers.ui.action;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.MasterDataFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.ui.dto.SocialLinkDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/socialLinkList.ajx" 
 */
public class SocialLinkListAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private MasterDataFinder masterDataFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		HttpServletResponse resp = contextManager.getContext().getResponse();
		User currentuser = contextManager.getContext().getCurrentUser();
		if (currentuser != null) {
			try {
				List<SocialLinkDto> links = masterDataFinder.getAllSocialLink(currentuser.getId());
				if (links != null && links.size() > 0) {
					JSONObject json = new JSONObject();
					JSONArray jData = new JSONArray();
					for (SocialLinkDto lnk : links) {
						JSONObject name = new JSONObject();
						name.put("id", lnk.getId());
						name.put("name", lnk.getName());
						name.put("selected", lnk.getSelected());
						if (lnk.getName().equalsIgnoreCase("")) {
							name.put("iconname", lnk.getName().toLowerCase());
						} else {
							name.put("iconname", lnk.getName().toLowerCase());
						}
						jData.add(name);
					}
					json.put("aData", jData);
					String jsonString = json.serialize();
					resp.setContentType("text/json");
					Writer out = resp.getWriter();
					out.write(jsonString);
					out.close();
				}
			} catch (ElementNotFoundException e) {
			}
		}

		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int catId;

		public int getCatId() {
			return catId;
		}

		public void setCatId(int catId) {
			this.catId = catId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setMasterDataFinder(MasterDataFinder masterDataFinder) {
		this.masterDataFinder = masterDataFinder;
	}
}