package com.era.community.faqs.ui.action;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.EntityWrapper;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.faqs.dao.HelpEntry;
import com.era.community.faqs.dao.HelpEntryFinder;
import com.era.community.faqs.dao.generated.HelpEntryEntity;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/faq/showMenuEntries.ajx" 
 */
public class ShowMenuEntriesAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private HelpEntryFinder helpEntryFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data; 
		User currUser = context.getCurrentUser(); 
		HttpServletResponse resp = context.getResponse();
		try {
			if (cmd.getParentId() > 0) {
				List<HelpEntry> lst = helpEntryFinder.listHelpEntrys(cmd.getParentId());
				if (lst != null && lst.size() > 0) {
		    		JSONObject json = new JSONObject();
			        JSONArray jData = new JSONArray();
			        for (HelpEntry ent : lst) {
			        	JSONObject name = new JSONObject();
			        	name.put("question", ent.getQuestion());
			        	name.put("answer", ent.getAnswer());
			        	name.put("parentId", ent.getParentId());
			        	name.put("sequence", ent.getSequence());
			        	name.put("id", ent.getId());
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
		private int parentId;

		public int getParentId() {
			return parentId;
		}

		public void setParentId(int parentId) {
			this.parentId = parentId;
		}
	}
	
	public class RowBean extends HelpEntryEntity implements EntityWrapper
	{
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setHelpEntryFinder(HelpEntryFinder helpEntryFinder) {
		this.helpEntryFinder = helpEntryFinder;
	}
}