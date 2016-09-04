package com.era.community.home.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

/**
 *  @spring.bean name="/terms.do" 
 */
public class DisplayTermsAction extends AbstractCommandAction
{
    
    protected ModelAndView handle(Object data) throws Exception
    {            
        Command cmd=(Command)data;                              
        return new ModelAndView("/termsAndConditions");
     }


    public static class Command extends CommandBeanImpl implements CommandBean
    {                  
    }

}
