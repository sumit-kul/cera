package com.era.community.pers.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/messageMailAlert.ajx"
 */
public class MessageMailAlertSwitchAction extends AbstractCommandAction
{
    protected CommunityEraContextManager contextManager;    
    
    @Override
    protected ModelAndView handle(Object data) throws Exception
    {
        CommunityEraContext context = contextManager.getContext();
        HttpServletResponse resp = context.getResponse();
        User currentUser = context.getCurrentUser();
        if (currentUser.isMsgAlert()) {
        	currentUser.setMsgAlert(false);
        }
        else {
        	currentUser.setMsgAlert(true);
        }
        currentUser.update();
        
        JSONObject json = new JSONObject();
        json.put("msgAlert", currentUser.isMsgAlert());
        String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
    }

    public static class Command extends CommandBeanImpl implements CommandBean        
    {        
    }

    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
}