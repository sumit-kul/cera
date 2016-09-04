package support.community.lucene.search;

import org.apache.lucene.document.*;

public interface EntitySearchScrollerCallback
{
    public void handleRow(Document doc, float score, int rowNum) throws Exception;
}