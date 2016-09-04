package com.era.community.home.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

/**
 *  @spring.bean name="/guide.do" 
 */
public class DisplayGuidelineAction extends AbstractCommandAction
{
    
    protected ModelAndView handle(Object data) throws Exception
    {            
        Command cmd=(Command)data;                              
        return new ModelAndView("/guidelines");
     }


    public static class Command extends CommandBeanImpl implements CommandBean
    {                  
    }

}
