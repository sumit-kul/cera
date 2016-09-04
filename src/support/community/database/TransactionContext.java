package support.community.database;

import support.community.util.*;

import org.springframework.transaction.*;

/**
 *  Transaction context established as a thread local by TransactionContextHolder. 
 *  Simply holds the spring TransactionStatus and a simple cache for use by JdbcDao.
 */
public class TransactionContext 
{
    private TransactionStatus status;
    private Cache daoCache;
    
    /**  
     * Package access constructor.
     */
    TransactionContext (TransactionStatus status, Cache map)
    {
        this.status = status;
        this.daoCache = map;
    }
    
    /**
     * Get the dao cache for the current thread.
     */
    public final Cache getDaoCache()
    {
        return daoCache;
    }
    
    /**
     *  Set the current transaction to rollback only. 
     */
    public void setRollbackOnly()
    {
        status.setRollbackOnly();
    }

    /*
     * Get the TransactionStatus object. This is currently package access because
     * without a reference to the TransactionManager it is useless. 
     */
    final TransactionStatus getStatus()
    {
        return status;
    }

    /*
     * Set the TransactionStatus object. 
     */
    final void setStatus(TransactionStatus status)
    {
        this.status = status;
    }

}

