package com.era.community.base;

import java.util.*;

public class SystemParameters
{
    private Map<String, String> parameters;

    public void setParameters(Map<String, String> parameters)
    {
        this.parameters = parameters;
    }

    public String getParameter(String key) 
    {
        return parameters.get(key);
    }
    
}
