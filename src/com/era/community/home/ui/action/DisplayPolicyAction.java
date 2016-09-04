package com.era.community.home.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

/**
 *  @spring.bean name="/privacy.do" 
 */
public class DisplayPolicyAction extends AbstractCommandAction
{
    
    protected ModelAndView handle(Object data) throws Exception
    {            
        Command cmd=(Command)data;                              
        return new ModelAndView("/privacyPolicy");
     }


    public static class Command extends CommandBeanImpl implements CommandBean
    {                  
    }

}
