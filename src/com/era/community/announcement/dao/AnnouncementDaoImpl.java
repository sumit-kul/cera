package com.era.community.announcement.dao; 

import java.util.*;

import com.era.community.pers.dao.*;

import support.community.database.*;

/**
 *
*/
public class AnnouncementDaoImpl extends com.era.community.announcement.dao.generated.AnnouncementDaoBaseImpl implements AnnouncementDao
{
    public boolean isFile1Present(Announcement announcement) throws Exception
    {
        Integer v = (Integer) getValue(announcement, "case when File1 is null then 0 else 1 end ", Integer.class);
        return v.intValue() == 1;
    }

    public boolean isFile2Present(Announcement announcement) throws Exception
    {
        Integer v = (Integer) getValue(announcement, "case when File2 is null then 0 else 1 end ", Integer.class);
        return v.intValue() == 1;
    }

    public boolean isFile3Present(Announcement announcement) throws Exception
    {
        Integer v = (Integer) getValue(announcement, "case when File3 is null then 0 else 1 end ", Integer.class);
        return v.intValue() == 1;
    }

    public void clearFile1(Announcement announcement) throws Exception
    {
        setColumn(announcement, "File1", null);
        setColumn(announcement, "File1ContentType", null);
        setColumn(announcement, "FileName1", null);
        setColumn(announcement, "File1Length", 0);
    }

    public void clearFile2(Announcement announcement) throws Exception
    {
        setColumn(announcement, "File2", null);
        setColumn(announcement, "File2ContentType", null);
        setColumn(announcement, "FileName21", null);
        setColumn(announcement, "File2Length", 0);
    }

    public void clearFile3(Announcement announcement) throws Exception
    {
        setColumn(announcement, "File3", null);
        setColumn(announcement, "File3ContentType", null);
        setColumn(announcement, "FileName3", null);
        setColumn(announcement, "File3Length", 0);
    }
    
    public QueryScroller listAnnouncementsByDatePosted() throws Exception
    {
        String sQuery =

            "select A.Id, A.Title, A.Subject, A.MessageType, Left(A.Body, 50), A.AuthorId, A.Status, A.DatePosted, CONCAT_WS(' ',U.FirstName,U.LastName) as AuthorName" +
            " from Announcement A, User U where U.Id = A.AuthorId" ;

        QueryScroller scroller = getQueryScroller(sQuery,  Announcement.class);

        scroller.addScrollKey("STEMP.Subject", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
        return scroller;
    }
    
    public List getLiveAnnouncementList() throws Exception
    {
        String query = "select A.*  from Announcement A where A.Status = " +Announcement.STATUS_LIVE + " order by A.MessageType desc, A.Modified desc";
        return getBeanList(query, Announcement.class);
    }
}

