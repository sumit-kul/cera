package support.community.lucene.index;

import support.community.database.*;

import org.apache.commons.logging.*;
import org.apache.lucene.document.*;

public abstract class IndexSet 
{
    protected Log logger = LogFactory.getLog(getClass());
    

    public abstract QueryScroller getScroller() throws Exception;  

    public abstract boolean requiresUpdate(Object entity, Document doc) throws Exception;
}