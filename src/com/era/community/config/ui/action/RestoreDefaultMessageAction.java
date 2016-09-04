package com.era.community.config.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.config.dao.MailMessageDefinition;
import com.era.community.config.dao.MailMessageDefinitionFinder;
import com.era.community.config.ui.dto.MailMessageDefinitionDto;

/**
 * @spring.bean name="/param/restore-default-message.do"
 */
public class RestoreDefaultMessageAction extends AbstractCommandAction
{
    private MailMessageDefinitionFinder mailMessageDefinitionFinder;
   
    @Override
    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command) data;
        MailMessageDefinition message = null;
        try {
            message = mailMessageDefinitionFinder.getMailMessageForName(cmd.getName());
            message.delete();
        }
        catch (RuntimeException e) {
        	e.printStackTrace();
        }

        return new ModelAndView("redirect:/param/mail-message-details.do?name="+cmd.getName());
    }

    public static class Command extends MailMessageDefinitionDto implements CommandBean 
    {
    }
    
    public final void setMailMessageDefinitionFinder(MailMessageDefinitionFinder mailMessageDefinitionFinder)
    {
        this.mailMessageDefinitionFinder = mailMessageDefinitionFinder;
    }
}