package support.community.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import support.community.database.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/** 
 * 
 */
public class AppMappingExceptionResolver extends SimpleMappingExceptionResolver
{
    TransactionContextHolder transactionContextHolder;
    
    /** 
     * 
     */
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object o, Exception e)
    {
        logger.error("", e);
        transactionContextHolder.getContext().setRollbackOnly();
        logger.error("Transaction has been set rollback only");
        return super.resolveException(req, resp, o, e);
    }

    
    public final void setTransactionContextHolder(TransactionContextHolder transactionContextHolder)
    {
        this.transactionContextHolder = transactionContextHolder;
    }
}
