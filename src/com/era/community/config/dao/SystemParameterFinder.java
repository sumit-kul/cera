package com.era.community.config.dao; 

public interface SystemParameterFinder extends com.era.community.config.dao.generated.SystemParameterFinderBase
{
    public SystemParameter getSystemParameterForName(String name) throws Exception;
    public String getParameterText(String name, String defaultText) throws Exception;
    public String getParameterLongText(String name, String defaultText) throws Exception;
}

