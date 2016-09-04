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
public class CheckAvailabilityAction extends AbstractCommandAction
{
    protected CommunityEraContextManager contextManager;
    protected UserFinder userFinder;

    public static class Command extends CommandBeanImpl implements CommandBean
    {
    	private String email;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
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
		if (context.getRequest().getParameter("email") != null 
				&& !"".equals(context.getRequest().getParameter("email"))) {
			cmd.setEmail(context.getRequest().getParameter("email"));
			 try {
	                User usr = userFinder.getUserForEmailAddress(cmd.getEmail());
	                if (usr.getEmailAddress() != null && !"".equals(usr.getEmailAddress()) && usr.getEmailAddress().equalsIgnoreCase(cmd.getEmail())) {
	                	returnString =  "<p class='availableFail'>The Email address that you are looking for is not different from your current one.</p>";
					} else {
						returnString =  "<p class='availableFail'>Sorry, the Email address that you are looking for is taken.</p>";
					}
	            } catch (ElementNotFoundException e) { 
	            	returnString =  "<p class='availablePass'>The Email address you've chosen is available.</p>";
	            }
		} else {
			returnString =  "<p class='availableFail'>The Email address cannot be blank. Please enter a valid Email address.</p>";
		}
		HttpServletResponse resp = contextManager.getContext().getResponse();
		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write(returnString);
		out.close();
		return null;
	}
}