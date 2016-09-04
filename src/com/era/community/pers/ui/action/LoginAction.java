package com.era.community.pers.ui.action;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/login.do"
 */
public class LoginAction extends AbstractCommandAction 
{
    // Injected references
    private CommunityEraContextManager contextManager;
    private CommunityFinder communityFinder;
    private UserFinder userFinder;


    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command)data;
        CommunityEraContext context = contextManager.getContext();
        cmd.setLoginMessage(null);
        if (context.getCurrentUser() != null) {
        	return new ModelAndView("redirect:/pers/myHome.do");
		}
        
        try {
            HttpServletRequest request = contextManager.getContext().getRequest(); 
            
            String reqUrl = (String) request.getSession().getAttribute("url_prior_login");
            
            // Saved request is the original request that was bounced
            SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute(AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY);
            if (savedRequest != null) reqUrl = savedRequest.getFullRequestUrl();
            if (reqUrl != null || "".equals(reqUrl)) {
                int id = getCommunityIdFromUrl(reqUrl);
                if (id>0) {
                    Community comm =  communityFinder.getCommunityForId(id);
                    if (comm.getCommunityType().equals("Private")) {
                    	cmd.setLoginMessage(comm==null ? null : "'" +comm.getName()+ "' is a private community. Please login first to access this community.");
					} else {
						cmd.setLoginMessage(comm==null ? null : "Please login first to access this feature of public community " + "'" +comm.getName()+ "'.");
					}
                    
                }
            }
            
            Exception lastException = (Exception) request.getSession().getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY);
            if (lastException != null) {
                if (lastException.getClass() == org.acegisecurity.BadCredentialsException.class ) {
                    String msg = cmd.getLoginMessage();
                    if (msg == null) {
                        msg = "";
                    }                    
                    else if (!msg.equals("")) {                       
                        msg += "<br/><br/>";
                    }
                    cmd.setLoginMessage( msg + "Login failed, either email is not registered or password is not correct.");
                }
                request.getSession().removeAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY);
            } else {
            	//cmd.setLoginMessage(null);
            }
        }
        catch (Exception e) {
            logger.error("", e);
        }
        return new ModelAndView("/login");
    }

    private int getCommunityIdFromUrl(String url)
    {
    	try {
    		int i = url.indexOf("/cid/");
            if (i<0) return 0;
            String copId = url.substring(i+3, url.indexOf('/', i+3));
            return Integer.parseInt(copId);
		} catch (NumberFormatException e) {
			return 0;
		}
    }
    
    public class Command extends CommandBeanImpl implements CommandBean
    {
        private String emailAddress;
        private String password;
        private String loginMessage;
        
        public final String getEmailAddress()
        {
            return emailAddress;
        }

        public final void setEmailAddress(String emailAddress)
        {
            this.emailAddress = emailAddress;
        }

        public final String getLoginMessage()
        {
            return loginMessage;
        }

        public final void setLoginMessage(String loginMessage)
        {
            this.loginMessage = loginMessage;
        }

        public final String getPassword()
        {
            return password;
        }

        public final void setPassword(String password)
        {
            this.password = password;
        }
    }
    
    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public final void setCommunityFinder(CommunityFinder communityFinder)
    {
        this.communityFinder = communityFinder;
    }
    
    public final UserFinder getUserFinder()
    {
        return userFinder;
    }

    public final void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }
}
