package com.era.community.pers.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/reg/inactivate-user.do"
 */
public class InactivateUserAction extends AbstractCommandAction
{
    private UserFinder userFinder;

    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command)data;
        
        User user = userFinder.getUserEntity(cmd.getId());
  
        user.softDelete();

        try {
            user.delete();
        }
        catch (Exception x) { }
        
        return REDIRECT_TO_BACKLINK;
    }
    
    public static class Command extends CommandBeanImpl implements CommandBean
    {
        private int id;

        public final int getId()
        {
            return id;
        }

        public final void setId(int id)
        {
            this.id = id;
        }
    }
    
    /**
     * @param userFinder The userFinder to set.
     */
    public void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }

}
