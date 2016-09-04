package support.community.framework;

import java.util.*;

import org.apache.commons.logging.*;


/**
 * 
 */
public class ThreadLifecycleTracker implements ThreadDecorator 
{
    /**
     * Create a logger available to all subclasses.
     */
    protected final Log logger = LogFactory.getLog(getClass());
    
    protected List listeners;

    /**
     * 
      */
    public final void initThread() 
    {
        for (int n=0; n<listeners.size(); n++) {
            ThreadLifecycleListener listener = (ThreadLifecycleListener)listeners.get(n);
            try {
                listener.onThreadBegin();
            }
            catch (Exception e) {
                logger.error("", e);
                throw new RuntimeException(e.toString());
            }
        }
    }

    /**
     * 
      */
    public final void clearThread(Throwable ex) 
    {
        for (int n=0; n<listeners.size(); n++) {
            ThreadLifecycleListener listener = (ThreadLifecycleListener)listeners.get(n);
            try {
                listener.onThreadEnd();
            }
            catch (Exception e) {
                logger.error("", e);
                throw new RuntimeException(e.toString());
            }
        }
    }
       

    public final void setListeners(List listeners)
    {
        this.listeners = listeners;
    }
}

