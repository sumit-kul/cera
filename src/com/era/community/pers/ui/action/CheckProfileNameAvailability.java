package com.era.community.pers.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/pers/checkAvailability.ajx"
 * 
 * Check email availability for new user registration action.
 * 
 */
public class CheckProfileNameAvailability extends AbstractCommandAction
{
    protected CommunityEraContextManager contextManager;
    protected UserFinder userFinder;

    public static class Command extends CommandBeanImpl implements CommandBean
    {
    	private String pName;

		public String getPName() {
			return pName;
		}

		public void setPName(String name) {
			pName = name;
		}
    }
    
    public final void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }

    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
    
	protected ModelAndView handle(Object data) throws Exception {
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data;
		String returnString = "";
		if (context.getRequest().getParameter("pName") != null 
				&& !"".equals(context.getRequest().getParameter("pName"))) {
			cmd.setPName(context.getRequest().getParameter("pName"));
			 try {
				 User cUser = contextManager.getContext().getCurrentUser();
				 if (cmd.getPName().equalsIgnoreCase(cUser.getProfileName())) {
					 returnString =  "<p class='availableFail'>The Profile Name that you are looking for is not different from your current one.</p>";
				} else {
	                userFinder.getUserForProfileName(cmd.getPName());
	                returnString =  "<p class='availableFail'>Sorry, the Profile Name that you are looking for is taken.</p>";
				}
            } catch (ElementNotFoundException e) { 
            	returnString =  "<p class='availablePass'>The Profile Name you've chosen is available.</p>";
            }
		} else {
			returnString =  "<p class='availableFail'>The Profile Name cannot be blank. Please enter a valid Profile Name.</p>";
		}
		HttpServletResponse resp = contextManager.getContext().getResponse();
		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write(returnString);
		out.close();
		return null;
	}
}