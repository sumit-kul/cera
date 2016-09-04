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

import support.community.application.ElementNotFoundException;
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
import com.era.community.doclib.dao.DocumentRating;
import com.era.community.doclib.dao.DocumentRatingFinder;
import com.era.community.monitor.dao.Subscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/cid/[cec]/doclib/add-comment-rate.do"
 */
public class DocumentCommentRateAddAction extends AbstractCommandAction
{
    private DocumentCommentFinder docCommentFinder;
    private CommunityEraContextManager contextManager;
    protected MailSender mailSender;
    protected MailMessageConfig mailMessageConfig;
    protected SubscriptionFinder subscriptionFinder;
    protected UserFinder userFinder;
    protected DocumentRatingFinder ratingFinder;

    public DocumentRatingFinder getRatingFinder()
    {
        return ratingFinder;
    }

    public void setRatingFinder(DocumentRatingFinder ratingFinder)
    {
        this.ratingFinder = ratingFinder;
    }

    public final UserFinder getUserFinder()
    {
        return userFinder;
    }

    public final void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }

    public final MailSender getMailSender()
    {
        return mailSender;
    }

    public final void setMailSender(MailSender mailSender)
    {
        this.mailSender = mailSender;
    }


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
        
        ModelAndView fwd = new ModelAndView("redirect:" + context.getCurrentCommunityUrl()
                + "/library/documentdisplay.do?id=" + cmd.getId());

        /* Don't submit a comment that has no text */
        if (cmd.getComment() != null && cmd.getComment().length() > 0) {

            DocumentComment comment = docCommentFinder.newDocumentComment();

            /* Populate the comment entity with data */
            comment.setComment(cmd.getComment());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	Date now = new Date();
        	String dt = sdf.format(now);
        	Timestamp ts = Timestamp.valueOf(dt);
            comment.setDatePosted(ts);
            comment.setDocumentId(cmd.getId());
            comment.setPosterId(context.getCurrentUser().getId());

            /* Save the comment */
            comment.update();
                    /* Mail the document subscribers */
         mailSubscribers(comment, context);
        }
        
        if (cmd.getRating()==null) return fwd;

        /*
         * Check if the user already has a rating record for this document
         */
        DocumentRating rating;
        try {
            rating = ratingFinder.getRatingForUserAndDocument(context.getCurrentUser().getId(), cmd.getId());
        } catch (ElementNotFoundException e) {

            /*
             * If no rating exists, create a new rating record for this user and document
             */
            rating = ratingFinder.newDocumentRating();
            rating.setDocumentId(cmd.getId());
            rating.setPosterId(context.getCurrentUser().getId());

        }

        /* Save */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date now = new Date();
    	String dt = sdf.format(now);
    	Timestamp ts = Timestamp.valueOf(dt);
        rating.setDatePosted(ts);
        rating.setStars(Integer.parseInt(cmd.getRating()));
        rating.update();

        /* Return to document display screen */
        return fwd;
    }

    /*
     * FIXME Need to optimize this, a synchronous send is not going to work in a large community.
     */
    private void mailSubscribers(DocumentComment comment, CommunityEraContext context) throws Exception
    {

        Document doc = comment.getParentDocument();

        /* Loop thru the list of Document Subscriptions */
        Iterator it = subscriptionFinder.getSubscriptionsForDocument(doc.getId()).iterator();
        while (it.hasNext()) {
            Subscription sub = (Subscription) it.next();
            if (sub.getFrequency() == 0) { // Only email the subscribers who want immediate alerts
                mailSubscriber(sub, comment, doc, context);
            }
        }

    }

    private void mailSubscriber(Subscription sub, DocumentComment comment, Document doc, CommunityEraContext context)
            throws Exception
            {
        /*
         * Email the document subscribers to alert that a new response has been posted
         */
        User subscriber = userFinder.getUserEntity(sub.getUserId());

        /*
         * Parameters to substitute into the body text of the Email.
         */
        Map<String, String> params = new HashMap<String, String>(11);

        String sLink = context.getCurrentCommunityUrl() + "/library/documentdisplay.do?id=" + doc.getId();
        String sUnSubscribe = context.getContextUrl() + "reg/watch.do";

        params.put("#communityName#", context.getCurrentCommunity().getName());
        params.put("#documentTitle#", doc.getTitle());
        params.put("#documentLink#", sLink);
        params.put("#unSubscribe#", sUnSubscribe);

        /*
         * Create and send the mail message.
         */
        SimpleMailMessage msg = mailMessageConfig.createMailMessage("new-response-alert", params);
        msg.setTo(subscriber.getEmailAddress());
        msg.setBcc("support@jhapak.com");

        // Don't mail the post author
        if (subscriber.getId() != context.getCurrentUser().getId() && !subscriber.isSuperAdministrator())
            mailSender.send(msg);
            }

    public static class Command extends CommandBeanImpl implements CommandBean
    {
        private int id; /* Id of document we are adding comment to */
        private String comment; /* Comment string */

        private String rating;

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

        public String getRating()
        {
            return rating;
        }

        public void setRating(String rating)
        {
            this.rating = rating;
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
    
}
