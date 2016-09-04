package com.era.community.monitor.dao;

import java.util.Date;

import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;

public class DocumentSubscription extends Subscription
{
    protected Integer DocumentId;
    protected DocumentFinder documentFinder;

    public Integer getDocumentId()
    {
        return DocumentId;
    }

    public void setDocumentId(Integer documentId)
    {
        DocumentId = documentId;
    }

    @Override
    public String getItemName() throws Exception
    {
        Document doc = documentFinder.getDocumentForId(DocumentId);
        return doc.getTitle();
    }

    public final void setDocumentFinder(DocumentFinder documentFinder)
    {
        this.documentFinder = documentFinder;
    }

    @Override
    public String getItemUrl() throws Exception
    {
        return "cid/" + this.getCommunityId() + "/library/documentdisplay.do?id=" + this.DocumentId.intValue();
    }

    @Override
    public String getItemType() throws Exception
    {
        return "Document";
    }

    @Override
    public Date getItemLastUpdateDate() throws Exception
    {
        return documentFinder.getDocumentForId(this.getDocumentId()).getLatestPostDate();
    }

    @Override
    public Object getItem() throws Exception
    {
        return documentFinder.getDocumentForId(this.getDocumentId());
    }

    @Override
    public int getSortOrder() throws Exception
    {
        return 5;
    }

    public int compareTo(Subscription o)
    {
        return this.sortOrder - o.sortOrder;
    }
}