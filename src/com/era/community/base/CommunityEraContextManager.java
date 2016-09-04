package com.era.community.base;

import javax.servlet.http.*;

import org.springframework.beans.*;
import org.springframework.context.*;

import com.era.community.communities.dao.*;
import com.era.community.pers.dao.*;

import support.community.framework.*;

public class CommunityEraContextManager extends AbstractRequestThreadLocalHolder implements ApplicationContextAware
{
    protected ApplicationContext springContext;
    protected AppRequestContextHolder requestContextHolder;
    protected RunAsServerTemplate runAsServerTemplate;
    protected UserFinder userFinder;
    protected CommunityFinder communityFinder;
    protected boolean production;
    
    protected Object createObject(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        final HttpServletRequest req = request;
        final HttpServletResponse resp = response;
        
        return runAsServerTemplate.execute(new RunAsServerCallback() {
            public Object doInSecurityContext() throws Exception
            {
                CommunityEraContext context = new CommunityEraContext(requestContextHolder.getRequestContext(), springContext, communityFinder, userFinder);
                req.setAttribute(getExposeInViewAs(), context);
                context.init(req);
                context.setProduction(isProduction());
                return context;
            }
        });
    }

    public CommunityEraContext getContext() 
    {
        return (CommunityEraContext) getHolderContents();
    }
    
    public void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        springContext = applicationContext;
    }
    public final void setCommunityFinder(CommunityFinder communityFinder)
    {
        this.communityFinder = communityFinder;
    }
    public final void setRunAsServerTemplate(RunAsServerTemplate runAsServerTemplate)
    {
        this.runAsServerTemplate = runAsServerTemplate;
    }
    public final void setRequestContextHolder(AppRequestContextHolder requestContextHolder)
    {
        this.requestContextHolder = requestContextHolder;
    }

	public boolean isProduction() {
		return production;
	}

	public void setProduction(boolean production) {
		this.production = production;
	}
}