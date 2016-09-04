package com.era.community.announcement.dao.generated; 
import com.era.community.announcement.dao.*;

public interface AnnouncementFinderBase
{
    public Announcement getAnnouncementForId(int id) throws Exception;
    public Announcement newAnnouncement() throws Exception;
}