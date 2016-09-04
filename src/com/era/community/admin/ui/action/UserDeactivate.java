package com.era.community.admin.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.connections.communities.dao.MembershipFinder;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * Admin action to deactivate a user record for a user who no longer wants to be registered
 * 
 * @spring.bean name="/admin/user-deactivate.do"
 */
public class UserDeactivate extends AbstractCommandAction
{
    /*
     * Access markers.
     */
    public static final String REQUIRES_AUTHENTICATION = "";

    /* Injected references */
    protected UserFinder userFinder;
    private CommunityEraContextManager contextManager;
    private SubscriptionFinder subscriptionFinder;
    private MembershipFinder membershipFinder;
    private ContactFinder contactFinder;


    protected ModelAndView handle(Object data) throws Exception
    {

        Command cmd = (Command) data;

        CommunityEraContext context = contextManager.getContext();

        User user = userFinder.getUserEntity(cmd.getId());

        user.setInactive(true);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date now = new Date();
    	String dt = sdf.format(now);
    	Timestamp ts = Timestamp.valueOf(dt);
        user.setDateDeactivated(ts);
        user.update();

        
        // Find all the user subscriptions and delete
        subscriptionFinder.deleteSubscriptionsForUser(user.getId());
        
       // Find all the user memberships and delete
        membershipFinder.deleteAllMembershipsForUser(user.getId());
        
        // Find all the user's contacts and delete
        contactFinder.deleteAllContactsForUser(user.getId());
        
        // Remove this user from other people's contact lists
        contactFinder.deleteUserAsContact(user.getId());

        /* Return to userscreen */
        ModelAndView fwd = new ModelAndView("redirect:");
        return fwd;
    }

    public static class Command extends CommandBeanImpl implements CommandBean
    {
        private int id; 

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }
    }

    /* Used by Spring to inject reference */
    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

        public final SubscriptionFinder getSubscriptionFinder()
    {
        return subscriptionFinder;
    }

    public final void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
    {
        this.subscriptionFinder = subscriptionFinder;
    }

    public final UserFinder getUserFinder()
    {
        return userFinder;
    }

    public final void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }
    
        public ContactFinder getContactFinder()
    {
        return contactFinder;
    }

    public void setContactFinder(ContactFinder contactFinder)
    {
        this.contactFinder = contactFinder;
    }

    public MembershipFinder getMembershipFinder()
    {
        return membershipFinder;
    }

    public void setMembershipFinder(MembershipFinder membershipFinder)
    {
        this.membershipFinder = membershipFinder;
    }

    public final CommunityEraContextManager getContextHolder()
    {
        return contextManager;
    }


 
}
