package support.community.scheduledTask;

import java.util.*;

import support.community.framework.*;

import org.acegisecurity.userdetails.memory.*;

public class AppTaskConfig 
{
  
    private RuntimeContext runtimeContext;
    
    private String securityProviderKey;
    private UserAttribute userAttribute;
   
    private RunAsServerTemplate runAsServerTemplate;
    
    private List threadDecorators = new ArrayList();
    
  
  public AppTaskConfig() throws Exception
  {
  }

    public void setThreadDecorators(List threadDecorators)
    {
        this.threadDecorators = threadDecorators;
    }
    public void setRuntimeContext(RuntimeContext runtimeContext)
    {
        this.runtimeContext = runtimeContext;
    }
    public void setRunAsServerTemplate(RunAsServerTemplate runAsServerTemplate)
    {
        this.runAsServerTemplate = runAsServerTemplate;
    }
    public void setSecurityProviderKey(String securityProviderKey)
    {
        this.securityProviderKey = securityProviderKey;
    }
    public void setUserAttribute(UserAttribute userAttribute)
    {
        this.userAttribute = userAttribute;
    }

    public final RunAsServerTemplate getRunAsServerTemplate()
    {
      return runAsServerTemplate;
    }

    public final RuntimeContext getRuntimeContext()
    {
      return runtimeContext;
    }

    public final String getSecurityProviderKey()
    {
      return securityProviderKey;
    }

    public final List getThreadDecorators()
    {
      return threadDecorators;
    }

    public final UserAttribute getUserAttribute()
    {
      return userAttribute;
    }

}