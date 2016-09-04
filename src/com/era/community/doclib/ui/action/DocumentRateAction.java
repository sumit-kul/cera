package com.era.community.doclib.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.doclib.dao.DocumentRating;
import com.era.community.doclib.dao.DocumentRatingFinder;

/**
 * @spring.bean name="/cid/[cec]/doclib/rate.do"
 */
public class DocumentRateAction extends AbstractCommandAction
{
    /*
     * Access markers.
     */
    public static final String REQUIRES_AUTHENTICATION = "";

    /* Injected references */
    protected DocumentRatingFinder ratingFinder;
    private CommunityEraContextManager contextManager;

    /*
     * (non-Javadoc)
     * 
     * @see support.community.framework.AbstractCommandAction#handle(java.lang.Object)
     */
    protected ModelAndView handle(Object data) throws Exception
    {
        /* Add rating using data from command */
        Command cmd = (Command) data;

        CommunityEraContext context = contextManager.getContext();
        
        if (context.getCurrentUser() == null) {
        	String reqUrl = context.getRequestUrl();
        	if(reqUrl != null) {
        		context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
        	}
    		return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
        }

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
        ModelAndView fwd = new ModelAndView("redirect:" + context.getCurrentCommunityUrl()
                + "/library/documentdisplay.do?id=" + cmd.getId());
        return fwd;
    }

    public static class Command extends CommandBeanImpl implements CommandBean
    {
        private int id; /* Id of document we are rating */
        private String rating;

        public final String getRating()
        {
            return rating;
        }

        public final void setRating(String rating)
        {
            this.rating = rating;
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
    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public final DocumentRatingFinder getRatingFinder()
    {
        return ratingFinder;
    }

    public final void setRatingFinder(DocumentRatingFinder ratingFinder)
    {
        this.ratingFinder = ratingFinder;
    }
}
