package support.community.database;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import support.community.framework.AbstractThreadLocalHolder;

/**
 *  Context holder to establish a spring transaction as a thread local for the current request.
 *  Typically used as a filter to set up a transaction before any other code runs. 
 */
public class TransactionContextHolder extends AbstractThreadLocalHolder
{
    /*
     * Get a loger for the class.
     */
     protected final Log logger = LogFactory.getLog(getClass());

     /*
      * Transaction manager.
      */
     private PlatformTransactionManager txManager;
     
     /*
      * DataSource is only needed to workaround websphere bug (see below).
      */
     private DataSource dataSource;

     /*
      * Default transaction properties.
      */
     private DefaultTransactionDefinition def = new DefaultTransactionDefinition();

     /**
      *  Called by parent when a request is started to create the thread local object. 
      */
    protected Object createObject() throws Exception
    {
        logger.info("Creating transaction context");
        
       /*
        * Create a transaction for the request.
        */
       def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
       def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
       TransactionStatus status = txManager.getTransaction(def);
       
       
       return new TransactionContext(status, new TransactionCache(89, 8192));
    }
  
    /**
     *  Called by parent when a request is complete to discard the thread local object. 
     */
    protected void destroyObject(Object o, Throwable ex) 
    {
        TransactionContext ctx = (TransactionContext)o;
        TransactionStatus status = ctx.getStatus();
        
        if (ex!=null || status.isRollbackOnly()) {
            if (status.isRollbackOnly()) logger.error("Rollback being issued due to setRollbackOnly");       
            else if (ex!=null) logger.error("Rollback being issued due to "+ex);       
            txManager.rollback(status);
            logger.error("Rollback complete");
        }
        else {
            logger.info("Commit being issued");       
            txManager.commit(status);
            logger.info("Transaction complete");
        }
    }


    public TransactionContext getContext()
    {
        return (TransactionContext)getHolderContents();
    }
    
    public void commit()
    {
         logger.info("Commit being issued");       
         TransactionContext ctx = getContext();
         txManager.commit(ctx.getStatus());
         ctx.setStatus(txManager.getTransaction(def));
         ctx.getDaoCache().clearConditional(); 
    }
    
    public final void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public final void setTransactionManager(PlatformTransactionManager txManager)
    {
        this.txManager = txManager;
    }
}