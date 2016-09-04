package com.era.community.admin.ui.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.config.dao.MailMessageConfig;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;

/**
 *
 * @spring.bean name="/admin/make-administrator.do"
 */
public class MakeSystemAdministratorAction extends AbstractCommandAction
{

    public static final String REQUIRES_AUTHENTICATION = "";

    /* Injected references */
    private CommunityEraContextManager contextManager;
    protected UserFinder userFinder;
    protected MailSender mailSender;
    protected MailMessageConfig mailMessageConfig;

    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command) data;
        CommunityEraContext context = contextManager.getContext();
        User user = userFinder.getUserEntity(cmd.getId());
        cmd.copyPropertiesFrom(user);
        context.setH2Header(user.getFullName());
        if ( user != null && context.getCurrentUser() != null) {
            mailUser(user, context);
            user.setSystemAdministrator(true);
            user.update();
            return new ModelAndView("admin/make-admin-confirm");
        }
        else {
            return null;
        }
    }
    
     private void mailUser(User user, CommunityEraContext context) throws Exception
    {
        /*
         * Parameters to substitute into the body text of the Email.
         */
        Map<String, String> params = new HashMap<String, String>(11);
       
        params.put("#user#", user.getFullName());

        SimpleMailMessage msg = mailMessageConfig.createMailMessage("make-administrator", params);     

        try {
            msg.setTo(user.getEmailAddress());
            if (context.getCurrentUser() != null && !context.getCurrentUser().isSuperAdministrator()) {
            msg.setCc(context.getCurrentUser().getEmailAddress());
            mailSender.send(msg);
            }
            mailSender.send(msg);
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }
    }
    
    public class Command extends UserEntity implements CommandBean
    {
    }

    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public final void setMailSender(MailSender mailSender)
    {
        this.mailSender = mailSender;
    }

    public void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }

    public final void setMailMessageConfig(MailMessageConfig mailMessageConfig)
    {
        this.mailMessageConfig = mailMessageConfig;
    }
}