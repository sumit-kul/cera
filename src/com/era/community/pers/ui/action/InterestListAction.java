package com.era.community.pers.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.Interest;
import com.era.community.pers.dao.InterestFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/interestList.ajx" 
 */
public class InterestListAction extends AbstractCommandAction
{
    private CommunityEraContextManager contextManager; 
    private InterestFinder interestFinder;

    protected ModelAndView handle(Object data) throws Exception
    {
    	Command cmd = (Command)data; 
    	HttpServletResponse resp = contextManager.getContext().getResponse();
    	try {
    		if (cmd.getCatId() > 0) {
    			List<Interest> intList = null;
    			if(cmd.getOffset() == 1) {
    				int interestCount = interestFinder.countInterestsPerCategory(cmd.getCatId());
    				cmd.setInterestCount(interestCount);
    				intList = interestFinder.getInterestList(cmd.getCatId(), cmd.getOffset(), cmd.getRows());
    				cmd.setOffset(cmd.getOffset() + cmd.getRows());
    			} else {
    				if((cmd.getOffset() - 1 + cmd.getRows()) < cmd.getInterestCount()){
    					intList = interestFinder.getInterestList(cmd.getCatId(), cmd.getOffset(), cmd.getRows());
        				cmd.setOffset(cmd.getOffset() + cmd.getRows());
    				} else {
    					List<Interest> intListTemp = new ArrayList<Interest>();
    					int rem = cmd.getInterestCount() - (cmd.getOffset() - 1);
    					intList = interestFinder.getInterestList(cmd.getCatId(), cmd.getOffset(), rem);
    					intListTemp.addAll(intList);
    					int nextcall = cmd.getRows() - rem;
    					cmd.setOffset(nextcall+1);
    					intList = interestFinder.getInterestList(cmd.getCatId(), 1, nextcall);
    					intListTemp.addAll(intList);
    					intList = intListTemp;
    				}
    			}
    			
		    	if (intList != null && intList.size() > 0) {
		    		JSONObject json = new JSONObject();
			        JSONArray jData = new JSONArray();
			        for (Iterator iterator = intList.iterator(); iterator.hasNext();) {
			        	Interest interest = (Interest) iterator.next();
			        	JSONObject name = new JSONObject();
			        	name.put("interestId", interest.getId());
			        	name.put("interest", interest.getInterest());
			        	jData.add(name);
					}
			        json.put("aData", jData);
			        json.put("offset", cmd.getOffset());
			        json.put("interestCount", cmd.getInterestCount());
			        String jsonString = json.serialize();
					resp.setContentType("text/json");
					Writer out = resp.getWriter();
					out.write(jsonString);
					out.close();
		    	}
			} else {
				JSONObject json = new JSONObject();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(json.serialize());
				out.close();
			}
		} catch (ElementNotFoundException e) {
		}
		return null;
    }

    public class Command extends CommandBeanImpl implements CommandBean
    {
        private int catId;
        private int offset = 1;
        private int rows = 14;
        private int interestCount = 0;

		public int getCatId() {
			return catId;
		}

		public void setCatId(int catId) {
			this.catId = catId;
		}

		public int getOffset() {
			return offset;
		}

		public void setOffset(int offset) {
			this.offset = offset;
		}

		public int getRows() {
			return rows;
		}

		public void setRows(int rows) {
			this.rows = rows;
		}

		public int getInterestCount() {
			return interestCount;
		}

		public void setInterestCount(int interestCount) {
			this.interestCount = interestCount;
		}
    }
    
    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

	public void setInterestFinder(InterestFinder interestFinder) {
		this.interestFinder = interestFinder;
	}
}