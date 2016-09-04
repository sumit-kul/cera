package com.era.community.announcement.dao; 


/**
 	Interface with package level access to hold methods private to the package.
 */
interface AnnouncementDao extends com.era.community.announcement.dao.generated.AnnouncementDaoBase, AnnouncementFinder
{
    public boolean isFile1Present(Announcement announcement) throws Exception;
    public boolean isFile2Present(Announcement announcement) throws Exception;
    public boolean isFile3Present(Announcement announcement) throws Exception;
    
    public void clearFile1(Announcement announcement) throws Exception;
    public void clearFile2(Announcement announcement) throws Exception;
    public void clearFile3(Announcement announcement) throws Exception;
}

