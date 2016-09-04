package com.era.community.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import support.community.framework.RuntimeContext;
import support.community.framework.ThreadDecorator;

public class RunAsAsyncThread extends SimpleAsyncTaskExecutor
{
	protected Log logger = LogFactory.getLog(getClass());
	
	private RuntimeContext runtimeContext;
    private List threadDecorators = new ArrayList();

	public RunAsAsyncThread() 
	{
	}

	protected void initThread() 
	{
		for (int n=0; n<threadDecorators.size(); n++) {
			ThreadDecorator decorator = (ThreadDecorator)threadDecorators.get(n);
			decorator.initThread();
		}
	}

	protected void clearThread(Throwable ex) 
	{
		for (int n=0; n<threadDecorators.size(); n++) {
			ThreadDecorator decorator = (ThreadDecorator)threadDecorators.get(n);
			decorator.clearThread(ex);
		}
	}
	
	

	public final void runTask(Runnable runnable)
	{
		//initThread();
		try {
			execute(runnable);
			//clearThread(null);
		}
		catch (Throwable e) {
			logger.error("", e);
			//clearThread(e);
		}
	}  

	public RuntimeContext getRuntimeContext() {
		return runtimeContext;
	}

	public void setRuntimeContext(RuntimeContext runtimeContext) {
		this.runtimeContext = runtimeContext;
	}

	public List getThreadDecorators() {
		return threadDecorators;
	}

	public void setThreadDecorators(List threadDecorators) {
		this.threadDecorators = threadDecorators;
	}
}