package com.era.community.search.ui.scheduler;

import org.quartz.Scheduler;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

/**
 * This action can be used to run ANY scheduled task as a once off, /search/runScheduledTask.do?task=xxxxxx
 * e.g. /search/runScheduledTask.do?task=mailSubscriptionsTaskDaily or search/runScheduledTask.do?task=IndexerTask etc
 * 
 * @spring.bean name="/search/runScheduledTask.do"
 */
public class RunScheduledTask extends AbstractCommandAction
{
    private Scheduler scheduler;

    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command)data;
        
        for (String name :  scheduler.getJobNames(Scheduler.DEFAULT_GROUP)) {
            logger.info("*************"+name);
        }
           
        scheduler.triggerJobWithVolatileTrigger(cmd.getTask(), Scheduler.DEFAULT_GROUP);
        
        return null;
    }
    
    public static class Command extends CommandBeanImpl implements CommandBean
    {
            private String task;

            public final String getTask()
            {
                return task;
            }

            public final void setTask(String taskName)
            {
                this.task = taskName;
            }
    }

    public final void setScheduler(Scheduler scheduler)
    {
        this.scheduler = scheduler;
    }
 }