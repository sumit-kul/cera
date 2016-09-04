package com.era.community.config.ui.action;

import support.community.database.ListPaginator;
import support.community.database.QueryPaginator;
import support.community.framework.AbstractIndexAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.config.dao.MailMessageConfig;

/**
 * 
 * Lists the configurable email messages
 * @spring.bean name="/param/mail-message-index.do"
 */
public class MailMessageIndexAction extends AbstractIndexAction
{
    /*
     * This action requires authentication
     */
    public static final String REQUIRES_AUTHENTICATION = "";

    /*
     * Injected references.
     */
    protected MailMessageConfig mailMessageConfig;
    protected CommunityEraContextManager contextManager;


    @Override
    protected QueryPaginator getScroller(IndexCommandBean bean) throws Exception
    {
        contextManager.getContext().setH2Header("System Mail Messages");
        return new ListPaginator(mailMessageConfig.getMessages());
    }

    @Override
    protected String getView(IndexCommandBean bean) throws Exception
    {
        return "param/mail-message-index";
    }

    public final void setMailMessageConfig(MailMessageConfig mailMessageConfig)
    {
        this.mailMessageConfig = mailMessageConfig;
    }
    
    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
    
    public class Command extends IndexCommandBeanImpl
    {
        
        
    }
    
    

}
