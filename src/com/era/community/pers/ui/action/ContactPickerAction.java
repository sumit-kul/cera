package com.era.community.pers.ui.action;

import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.era.community.pers.ui.dto.UserDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/contactPicker.ajx" 
 */
public class ContactPickerAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		User currentUser = context.getCurrentUser();

		if (currentUser != null) {
			QueryScroller contacts = currentUser.listContacts();
        	contacts.setPageSize(9999);
            List contactList = contacts.readPage(1);  
            if (contactList != null && contactList.size() > 0) {
	    		JSONObject json = new JSONObject();
		        JSONArray jData = new JSONArray();
		        for (Iterator iterator = contactList.iterator(); iterator.hasNext();) {
		        	UserDto contact = (UserDto) iterator.next();
		        	JSONObject name = new JSONObject();
		        	name.put("photoPresent", contact.getPhotoPresent());
		        	name.put("contactId", contact.getId());
		        	name.put("contactName", contact.getFullName());
		        	name.put("contactEmail", contact.getEmailAddress());
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
		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
}