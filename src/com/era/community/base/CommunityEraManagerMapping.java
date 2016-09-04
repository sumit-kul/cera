package com.era.community.base;

import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

public class CommunityEraManagerMapping extends BeanNameUrlHandlerMapping
{
	private CommunityEraFilterDefinitionSource filterDefinitions;

	protected String[] determineUrlsForHandler(String beanName) 
	{
		String[] paths = super.determineUrlsForHandler(beanName); 

		for (int n=0; n<paths.length; n++) {
			String p = paths[n];
			if (p.startsWith("/cid/[cec]/")) 
				paths[n] = "/cid/*"+beanName.substring(10);
		}
		return paths;
	}

	public final void setFilterDefinitions(CommunityEraFilterDefinitionSource filterDefinitions)
	{ 
		this.filterDefinitions = filterDefinitions;
	}
}