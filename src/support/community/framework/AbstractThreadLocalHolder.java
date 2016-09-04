package support.community.framework;

import org.apache.commons.logging.*;


/**
 * 
 */
public abstract class AbstractThreadLocalHolder implements ThreadDecorator 
{
    /**
     *  ThreadLocal to bind an object to the current thread.
     */
    private InheritableThreadLocal<Object> holder = new InheritableThreadLocal<Object>();

    /**
     * Create a logger available to all subclasses.
     */
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 
      */
    public final void initThread() 
    {
        if (holder.get()!=null) throw new RuntimeException("Thread already has an object bound to it. "+getClass());
        try {
            holder.set(createObject());
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 
     */
    protected final Object getHolderContents() 
    {
        Object o = holder.get();
        if (o==null) throw new RuntimeException("ThreadLocal holder has not been initialized for this thread.");
        return o;
    }

    /**
     * 
      */
    public final void clearThread(Throwable ex) 
    {
        Object o =holder.get();
        holder.set(null);
        destroyObject(o, ex);
    }

    protected abstract Object createObject() throws Exception;
    protected void destroyObject(Object o, Throwable ex)  { }   
       
}

