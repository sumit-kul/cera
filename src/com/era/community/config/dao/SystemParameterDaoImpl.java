package com.era.community.config.dao; 

import org.springframework.dao.IncorrectResultSizeDataAccessException;

public class SystemParameterDaoImpl extends com.era.community.config.dao.generated.SystemParameterDaoBaseImpl implements SystemParameterDao
{
    
    public String getParameterText(String name, String defaultText) throws Exception
    {
        String sQuery = "select Text from SystemParameter where Name = ?";
        try {
            return (String)getSimpleJdbcTemplate().queryForObject(sQuery, String.class, name );
        }
        catch (IncorrectResultSizeDataAccessException x) {
            return defaultText;
        }
    }

    public String getParameterLongText(String name, String defaultText) throws Exception
    {
        String sQuery = "select LongText from SystemParameter where Name = ?";
        try {
            return (String)getSimpleJdbcTemplate().queryForObject(sQuery,  String.class, name );
        }
        catch (IncorrectResultSizeDataAccessException x) {
            return defaultText;
        }
    }

    public SystemParameter getSystemParameterForName(String name) throws Exception
    {
        return (SystemParameter)getEntityWhere("Name = ?", name );
    }
}

