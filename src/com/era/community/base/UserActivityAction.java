package com.era.community.base;

import javax.servlet.http.*;

import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.*;

/**
 * 
 * See app.properties : # User activity recording
*  activityManager.url.list=http://localhost/user-activity.do
 * 
 * @spring.bean name="/user-activity.do"
*/
public class UserActivityAction extends AbstractController
{
    private UserActivityManager activityManager;
 
    public final void setActivityManager(UserActivityManager activityManager)
    {
        this.activityManager = activityManager;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String userId = request.getParameter("userId");
        String  userName = request.getParameter("userName");
        String placeName = request.getParameter("placeName");
        activityManager.add(Integer.parseInt(userId), userName,  placeName);
        return null;
    }
        
}
