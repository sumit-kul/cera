package support.community.lucene.index;

import org.apache.commons.logging.*;
import org.apache.lucene.document.*;
import org.springframework.beans.factory.*;
import org.springframework.context.*;
import org.springframework.context.event.*;
import org.springframework.util.*;

public class EntityIndexCleaner implements InitializingBean, Runnable, ApplicationListener
{
    protected Log logger = LogFactory.getLog(getClass()); 

    protected EntityIndex index;
    
    private boolean shuttingDown = false;
    
    public void afterPropertiesSet() throws Exception 
    {
        Assert.notNull(index, "The index property has not been set");
    }

    public void run() 
    {
        logger.info("Starting index cleaner.");
        try {
            index.beginUpdate();
            int deletions = cleanIndex();
            index.endUpdate(false);
            logger.info("Index cleaner removed "+deletions+" entries");
        }
        catch (Throwable e) {
            logger.error("", e);
            index.abortUpdate();
        }
        logger.info("Index cleaner ended.");
    }

    private int cleanIndex() throws Exception 
    {
        int deletionCount = 0;
        
        BatchEntityIndexUpdater indexUpdater = new BatchEntityIndexUpdater(index);

        int maxDoc = index.getMaxDocNumber();
        
        for (int n=0; n<maxDoc&&!shuttingDown; n++) {
            try {
                Document doc = index.getEntry(n);
                if (doc==null) continue;
                if (index.getEntity(doc)==null) {
                    indexUpdater.addDeletion(doc);
                    deletionCount++;
                }
            }
            catch (Exception e) {
                logger.error("", e);
            }
        }

        if (shuttingDown) return deletionCount;
        
        try {
            indexUpdater.flush(false);
        }
        catch (Exception e) {
            logger.error("", e);
        }
        
        return deletionCount;
    }

    /*
     * Terminate processing when the spring context is closed (usually because the
     * application is restarted in the server). It seems this may be called multiple
     * times, once for the web application context and once for the root context.
     */
    public void onApplicationEvent(ApplicationEvent event)
    {
        if (event instanceof ContextClosedEvent) {
            try {
                logger.info("Entity index cleaner notified of application shutdown");
                shuttingDown = true;
                index.shutdown();
                for (int n=0; n<80; n++) {
                    if (!index.isOpenForUpdate()) return;
                    Thread.sleep(50);
                }
                logger.error("***********************************************************");
                logger.error("*  Application is closing while index is open for update                        *");
                logger.error("***********************************************************");
            }
            catch (Exception x) {
                logger.error("", x);
            }
        }
    }
    
    public final void setIndex(EntityIndex index)
    {
        this.index = index;
    }
}