package com.era.community.doclib.dao.generated; 
import com.era.community.doclib.dao.*;

public interface DocumentFinderBase
{
    public Document getDocumentForId(int id) throws Exception;
    public Document newDocument() throws Exception;
}