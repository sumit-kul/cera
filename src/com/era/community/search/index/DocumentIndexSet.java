package com.era.community.search.index;

import support.community.lucene.index.*;
import support.community.database.*;

import com.era.community.doclib.dao.*;

public class DocumentIndexSet extends IndexSet
{
    private DocumentFinder documentFinder;

    public QueryScroller getScroller() throws Exception
    {
        return documentFinder.listAllDocuments();
    }
 
    public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
    {
        return true;
    }
    
    public final void setDocumentFinder(DocumentFinder documentFinder)
    {
        this.documentFinder = documentFinder;
    }
}