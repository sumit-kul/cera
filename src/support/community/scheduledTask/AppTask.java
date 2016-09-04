package support.community.scheduledTask;

import java.util.*;

import support.community.framework.*;

import org.acegisecurity.*;
import org.acegisecurity.context.*;
import org.acegisecurity.providers.anonymous.*;
import org.apache.commons.logging.*;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;

public abstract class AppTask 
{
	private String sName;
	private List<String> servers = null;
	private boolean runAsServer = true;

	private AppTaskConfig taskConfig;

	private String sStatus = "Idle";

	protected Log logger = LogFactory.getLog(getClass());

	private PlatformTransactionManager txManager;

	private DefaultTransactionDefinition def = new DefaultTransactionDefinition();

	protected void initThread() 
	{
		for (int n=0; n<taskConfig.getThreadDecorators().size(); n++) {
			ThreadDecorator decorator = (ThreadDecorator)taskConfig.getThreadDecorators().get(n);
			decorator.initThread();
		}
	}

	protected void clearThread(Throwable ex) 
	{
		for (int n=0; n<taskConfig.getThreadDecorators().size(); n++) {
			ThreadDecorator decorator = (ThreadDecorator)taskConfig.getThreadDecorators().get(n);
			decorator.clearThread(ex);
		}
	}

	public AppTask() 
	{
	}

	public final void runTask()
	{
		if (servers!=null&&!servers.contains(taskConfig.getRuntimeContext().getServerName())) {
			logger.info("Task "+getClass()+" not set to execute on server "+taskConfig.getRuntimeContext().getServerName());
			return;
		}

		logger.info("Task "+getClass()+" started.");
		setSecurityContext();
		initThread();
		try {
			if (runAsServer)
				executeAsServer();
			else
				execute();
			clearThread(null);
		}
		catch (Throwable e) {
			logger.error("", e);
			clearThread(e);
		}
		finally {
			clearSecurityContext();
			logger.info("Task "+getClass()+" ended.");
		}
	}  

	private void executeAsServer() throws Exception
	{
		taskConfig.getRunAsServerTemplate().execute(new RunAsServerCallback() {
			public Object doInSecurityContext() throws Exception
			{
				execute();
				return null;
			}
		});
	}

	private void setSecurityContext()  
	{
		SecurityContextHolder.getContext().setAuthentication(createAuthentication());
		logger.debug("Populated SecurityContextHolder with anonymous token: '"+ SecurityContextHolder.getContext().getAuthentication() + "'");
	}

	private void clearSecurityContext()  
	{
		SecurityContextHolder.clearContext();
		logger.debug("Cleared security context");
	}

	protected Authentication createAuthentication() 
	{
		AnonymousAuthenticationToken auth = new AnonymousAuthenticationToken(taskConfig.getSecurityProviderKey(), taskConfig.getUserAttribute().getPassword(), taskConfig.getUserAttribute().getAuthorities());
		auth.setDetails("");
		return auth;
	}

	public String getStatus() 
	{
		return sStatus;
	}

	protected abstract void execute() throws Exception;


	public String getName()
	{
		return sName;
	}
	public void setName(String s)
	{
		sName = s;
	}

	public final void setTransactionManager(PlatformTransactionManager txManager)
	{
		this.txManager = txManager;
	}  

	public void setRunAsServer(boolean runAsServer)
	{
		this.runAsServer = runAsServer;
	}

	public final AppTaskConfig getTaskConfig()
	{
		return taskConfig;
	}

	public final void setTaskConfig(AppTaskConfig taskConfig)
	{
		this.taskConfig = taskConfig;
	}

	public final List<String> getServers()
	{
		return servers;
	}

	public final void setServers(List<String> servers)
	{
		this.servers = servers;
	}
}