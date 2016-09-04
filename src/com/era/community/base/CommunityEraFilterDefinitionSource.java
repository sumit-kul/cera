package com.era.community.base;

import java.util.Iterator;

import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.ConfigAttributeEditor;
import org.acegisecurity.intercept.web.FilterInvocationDefinitionSource;
import org.acegisecurity.intercept.web.PathBasedFilterInvocationDefinitionMap;

public class CommunityEraFilterDefinitionSource implements FilterInvocationDefinitionSource
{
    private PathBasedFilterInvocationDefinitionMap data;
    
    public void addSecureUrl(String name, String value)
    {
        ConfigAttributeEditor configAttribEd = new ConfigAttributeEditor();
        configAttribEd.setAsText(value);
        ConfigAttributeDefinition attr = (ConfigAttributeDefinition) configAttribEd.getValue();
        data.addSecureUrl(name, attr);
    }
    
    public Iterator getConfigAttributeDefinitions()
    {
        return data.getConfigAttributeDefinitions();
    }

    public ConfigAttributeDefinition getAttributes(Object object) throws IllegalArgumentException
    {
        return data.getAttributes(object);
    }

    public boolean supports(Class clazz)
    {
        return data.supports(clazz);
    }

    public final void setData(FilterInvocationDefinitionSource source)
    {
        data = (PathBasedFilterInvocationDefinitionMap)source;
    }
}