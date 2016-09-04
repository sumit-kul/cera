package com.era.community.doclib.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.config.dao.MailMessageConfig;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentComment;
import com.era.community.doclib.dao.DocumentCommentFinder;
import com.era.community.monitor.dao.Subscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 *
 * @spring.bean name="/cid/[cec]/doclib/add-comment.do"
 */
public class DocumentCommentAddAction extends AbstractCommandAction
{
    public static final String REQUIRES_AUTHENTICATION = "";
    
    /* Injected references */
    private DocumentCommentFinder docCommentFinder;
    private CommunityEraContextManager contextManager; 
    protected MailSender mailSender;
    protected MailMessageConfig mailMessageConfig;
    protected SubscriptionFinder subscriptionFinder;
    protected UserFinder userFinder;



    protected ModelAndView handle(Object data) throws Exception
    {
        /* Add comment using data from command */
        Command cmd = (Command) data;
        
        CommunityEraContext context = contextManager.getContext();
        
        if (context.getCurrentUser() == null) {
        	String reqUrl = context.getRequestUrl();
        	if(reqUrl != null) {
        		context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
        	}
    		return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
        }
        
        ModelAndView fwd=new ModelAndView("redirect:" + context.getCurrentCommunityUrl() + "/library/documentdisplay.do?id=" + cmd.getId());
        
        /* Don't submit a comment that has no text */
        if(cmd.getComment()==null || cmd.getComment().length()==0)
            return fwd;        
        
        DocumentComment comment = docCommentFinder.newDocumentComment();

        /* Populate the entity with data */        
        comment.setComment( cmd.getComment() );
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date now = new Date();
    	String dt = sdf.format(now);
    	Timestamp ts = Timestamp.valueOf(dt);
        comment.setDatePosted(ts);
        comment.setDocumentId( cmd.getId() );
        comment.setPosterId( context.getCurrentUser().getId() );
        
        /* Save */
        comment.update();
        
        mailSubscribers(comment, context);

        /* Return to document display screen */
        return fwd;      
    }
    
    /*
     * FIXME Need to optimize this, a synchronous send is not going to work in a large community.
     */
    private void mailSubscribers(DocumentComment comment, CommunityEraContext context) throws Exception
    {
        
        Document doc = comment.getParentDocument();
        
        /* Loop thru the list of Document  Subscriptions */
        Iterator it = subscriptionFinder.getSubscriptionsForDocument(doc.getId()).iterator();
        while (it.hasNext()) {
            Subscription sub = (Subscription) it.next();
            if (sub.getFrequency() == 0) { // Only email the subscribers who want immediate alerts
                mailSubscriber(sub, comment, doc, context);
            }
        }
        

    }
    
    private void mailSubscriber(Subscription sub, DocumentComment comment, Document doc, CommunityEraContext context) throws Exception
    {
        /*
         * Email the document subscribers to alert that a new response has been posted
         */
        User subscriber = userFinder.getUserEntity(sub.getUserId());

        /*
         * Parameters to substitute into the body text of the Email.
         */
        Map<String, String> params = new HashMap<String, String>(11);

        String sLink = context.getCurrentCommunityUrl()+"/library/documentdisplay.do?id="+doc.getId();
        String sUnSubscribe = context.getContextUrl() + "reg/watch.do";

        params.put("#communityName#", context.getCurrentCommunity().getName());
        params.put("#documentTitle#", doc.getTitle());
        params.put("#documentLink#", sLink);
        params.put("#unSubscribe#", sUnSubscribe);

        /*
         * Create and send the mail message.
         */
        SimpleMailMessage msg = mailMessageConfig.createMailMessage("new-document-comment", params);
        msg.setTo(subscriber.getEmailAddress());
        
        // Don't mail the post author
        if (subscriber.getId()!= context.getCurrentUser().getId() && !subscriber.isSuperAdministrator())
            mailSender.send(msg);
    }

    public static class Command extends CommandBeanImpl implements CommandBean 
    {
        private int id; /* Id of document we are adding comment to */        
        private String comment; /* Comment string */
                        
        public String getComment()
        {
            return comment;
        }
        
        public void setComment(String comment)
        {
            this.comment = comment;
        }
        
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
    public void setCommentFinder(DocumentCommentFinder docCommentFinder)
    {
        this.docCommentFinder = docCommentFinder;
    }
    
    /* Used by Spring to inject reference */
    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
    
    public final void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
    {
        this.subscriptionFinder = subscriptionFinder;
    }
    
    public final void setMailMessageConfig(MailMessageConfig mailMessageConfig)
    {
        this.mailMessageConfig = mailMessageConfig;
    }
    
    public final void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }


    public final void setMailSender(MailSender mailSender)
    {
        this.mailSender = mailSender;
    }
    
}
