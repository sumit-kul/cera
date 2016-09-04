package com.era.community.pers.ui.action;

import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.InterestFinder;
import com.era.community.pers.dao.MasterData;
import com.era.community.pers.dao.MasterDataFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/interesCatList.ajx" 
 */
public class InterestCatListAction extends AbstractCommandAction
{
    private CommunityEraContextManager contextManager; 
    private MasterDataFinder masterDataFinder;

    protected ModelAndView handle(Object data) throws Exception
    {
    	Command cmd = (Command)data; 
    	HttpServletResponse resp = contextManager.getContext().getResponse();
    	try {
    		if (cmd.getCatId() > 0) {
    			/*List<Interest> intList = interestFinder.getInterestList(cmd.getCatId());
		    	if (intList != null && intList.size() > 0) {
		    		JSONObject json = new JSONObject();
			        JSONArray jData = new JSONArray();
			        for (Iterator iterator = intList.iterator(); iterator.hasNext();) {
			        	Interest interest = (Interest) iterator.next();
			        	JSONObject name = new JSONObject();
			        	name.put("categoryId", interest.getLabel());
			        	name.put("interest", interest.getInterest());
			        	jData.add(name);
					}
			        json.put("bData", jData);
			        String jsonString = json.serialize();
					resp.setContentType("text/json");
					Writer out = resp.getWriter();
					out.write(jsonString);
					out.close();
		    	}*/
			} else {
				List<MasterData> intCatList = masterDataFinder.listAllCategory();
		    	if (intCatList != null && intCatList.size() > 0) {
		    		JSONObject json = new JSONObject();
			        JSONArray jData = new JSONArray();
			        for (Iterator iterator = intCatList.iterator(); iterator.hasNext();) {
			        	MasterData intCat = (MasterData) iterator.next();
			        	JSONObject name = new JSONObject();
			        	name.put("category", intCat.getLabel());
			        	name.put("categoryId", intCat.getId());
			        	jData.add(name);
					}
			        json.put("aData", jData);
			        String jsonString = json.serialize();
					resp.setContentType("text/json");
					Writer out = resp.getWriter();
					out.write(jsonString);
					out.close();
		    	}
			}
		} catch (ElementNotFoundException e) {
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