package com.era.community.pers.ui.action;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.Option;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.ExtraInfoUser;
import com.era.community.pers.dao.ExtraInfoUserFinder;
import com.era.community.pers.dao.MasterDataFinder;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/occupationList.ajx" 
 */
public class OccupationListAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private MasterDataFinder masterDataFinder;
	private ExtraInfoUserFinder extraInfoUserFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		HttpServletResponse resp = contextManager.getContext().getResponse();
		User currentUser = contextManager.getContext().getCurrentUser();
		if (currentUser != null) {
			try {
				List<Option> occupationList = masterDataFinder.getOccupationList();
				ExtraInfoUser eiuser = extraInfoUserFinder.getExtraInfoForUser(currentUser.getId());
				if (occupationList != null && occupationList.size() > 0) {
					int occu1 = eiuser.getOccupation1();
					int occu2 = eiuser.getOccupation2();
					int occu3 = eiuser.getOccupation3();
					JSONObject json = new JSONObject();
					JSONArray jData = new JSONArray();
					for (Option mData : occupationList) {
						JSONObject name = new JSONObject();
						name.put("occupation", mData.getLabel());
						name.put("occupationId", mData.getValue());
						if (occu1 == (Integer)mData.getValue() || occu2 == (Integer)mData.getValue()
								|| occu3 == (Integer)mData.getValue()) {
							name.put("selectedOcc", true);
						} else {
							name.put("selectedOcc", false);
						}
						jData.add(name);
					}
					json.put("aData", jData);
					json.put("compname", eiuser.getCompany());
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

	public void setExtraInfoUserFinder(ExtraInfoUserFinder extraInfoUserFinder) {
		this.extraInfoUserFinder = extraInfoUserFinder;
	}
}