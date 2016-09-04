
package com.era.community.search.index;

import java.util.*;

import support.community.lucene.index.*;

import com.era.community.doclib.dao.*;

/**
 * 
 */
public class DocumentEntityHandler extends CecAbstractEntityHandler 
{
    public boolean supports(Class type) throws Exception
    {
        return Document.class.isAssignableFrom(type);
    }

    public int getEntityId(Object o) throws Exception
    {
        return ((Document)o).getId();
    }

    public boolean requiresUpdate(Object rowBean, org.apache.lucene.document.Document doc) throws Exception
    {
        // TODO check sequence ............
        return true;
    }
    
    public String getContent(Object rowBean, BinaryDataHandler handler, boolean fast) throws Exception
    {
        Document doc = (Document)rowBean;
        StringBuffer buf = new StringBuffer(4096);
        getContentForFile(doc.getFileName(), doc.getFile(), doc.getFileContentType(), handler, buf, fast);
        return buf.toString();
    }

    public String getTitle(Object rowBean) throws Exception
    {
        Document doc = (Document)rowBean;
        return doc.getTitle();
    }

    public String getDescription(Object rowBean) throws Exception
    {
        Document doc = (Document)rowBean;
        return doc.getDescription();
    }

    public Date getDate(Object rowBean) throws Exception
    {
        Document doc = (Document)rowBean;
        return doc.getDatePosted();
    }

    public Date getModified(Object rowBean) throws Exception
    {
        Document doc = (Document)rowBean;
        return doc.getDateRevised();
    }

    public int getCommunityId(Object rowBean) throws Exception
    {
        Document doc = (Document)rowBean;
        return doc.getLibrary().getCommunityId();
    }

    public Map<String, Object> getAdditionalDataFields(Object rowBean) throws Exception
    {
        return null;
    }

    public String getTitle3(Object entity, String query, EntityIndex index) throws Exception
    {
        Document doc = (Document)entity;
        return DATE_FORMAT.format(doc.getDateRevised());
    }
    
    public String getCommunityRelativeUrl(Object rowBean, String query, EntityIndex index) throws Exception
    {
         return "library/documentdisplay.do?id="+getEntityId(rowBean);
    }

}
