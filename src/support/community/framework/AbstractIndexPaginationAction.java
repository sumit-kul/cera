package support.community.framework;

import java.util.*;

import org.springframework.web.servlet.*;

import support.community.database.*;

/** 
 * 
 */
public abstract class AbstractIndexPaginationAction extends AbstractCommandAction
{
    public AbstractIndexPaginationAction() 
    {
        /*
         * Just validate that the Command inner class is of the correct type. The setting of the
         * member variable will have been done in the superclas constructor.
         */
        try {
            getInnerClassForNameAndType("Command", IndexCommandBean.class);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    protected final ModelAndView handle(Object data) throws Exception
    {
        IndexCommandBeanImpl cmd = (IndexCommandBeanImpl)data;
        
        QueryPaginator paginator =  getScroller(cmd);
        paginator.setPageSize(cmd.getPageSize());

        Map map = referenceData(data);
        
        

        return new ModelAndView(getView(cmd), map);
    }

    protected abstract String getView(IndexCommandBean bean) throws Exception;       
    protected abstract QueryPaginator getScroller(IndexCommandBean bean) throws Exception;
    
    protected Map referenceData(Object data) throws Exception
    {
        return new HashMap(1);
    }
    
    protected final Class getCommandBeanClass() throws Exception
    {
        List found = getClassMembersOfType(IndexCommandBean.class);
        if (found.size()>1) throw new Exception("Controller "+getClass()+" defines mutiple beans of type IndexCommandBean");
        if (found.isEmpty()) return IndexCommandBeanImpl.class;
        return (Class)found.get(0);        
    }
}
