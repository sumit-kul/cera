package com.era.community.accesslog.dao.generated; 
import com.era.community.accesslog.dao.*;

public interface AccessLogFinderBase
{
    public AccessLog getAccessLogForId(int id) throws Exception;
    public AccessLog newAccessLog() throws Exception;
}