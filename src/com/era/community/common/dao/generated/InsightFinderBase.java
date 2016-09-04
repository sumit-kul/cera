package com.era.community.common.dao.generated; 
import com.era.community.common.dao.Insight;

public interface InsightFinderBase
{
    public Insight getInsightForId(int id) throws Exception;
    public Insight newInsight() throws Exception;
}