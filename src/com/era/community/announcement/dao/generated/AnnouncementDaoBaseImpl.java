package com.era.community.announcement.dao.generated; 

import support.community.database.*;
import com.era.community.announcement.dao.*;

public abstract class AnnouncementDaoBaseImpl extends AbstractJdbcDaoSupport implements AnnouncementDaoBase
{
   /*
   *
  */
  public Announcement getAnnouncementForId(int id) throws Exception
  {
      Object[] keys = new Object[] { new Integer(id) };
      return (Announcement)getEntity(Announcement.class, keys);
  }

    /*
     *
    */
    public void deleteAnnouncementForId(int id) throws Exception
    {
        Object[] keys = new Object[] { new Integer(id) };
        deleteEntity(keys);
    }

    /*
     *
    */
    public void delete(Announcement o) throws Exception
    {
        deleteEntity(o);
    }

    /*
    *
   */
   public void store(Announcement o) throws Exception
   {
       storeEntity(o);
   }

  public BlobData readFile1(Announcement o) throws Exception
  {
      return  readBlobItem(o, "File1");
  } 
  public void storeFile1(Announcement o, java.io.InputStream data, int dataLength, String contentType) throws Exception
  {
      storeBlob(o, "File1", data, dataLength, contentType);
  }
  public void storeFile1(Announcement o, org.springframework.web.multipart.MultipartFile data) throws Exception
  {
      storeBlob(o, "File1", data);
  }
  public String getFile1ContentType(Announcement o) throws Exception
  {
	  return (String)getColumn(o, "File1ContentType", String.class);
  } 
  public String getFile1SearchText(Announcement o) throws Exception
  {
	  return (String)getColumn(o, "File1SearchText", String.class);
  } 
  public void setFile1SearchText(Announcement o, String s) throws Exception
  {
      setColumn(o, "File1SearchText", s);
  } 
  public void markFile1Relocated(Announcement o) throws Exception
  {
      setColumn(o, "File1", null);
  }
  public BlobData readFile2(Announcement o) throws Exception
  {
      return  readBlobItem(o, "File2");
  } 
  public void storeFile2(Announcement o, java.io.InputStream data, int dataLength, String contentType) throws Exception
  {
      storeBlob(o, "File2", data, dataLength, contentType);
  }
  public void storeFile2(Announcement o, org.springframework.web.multipart.MultipartFile data) throws Exception
  {
      storeBlob(o, "File2", data);
  }
  public String getFile2ContentType(Announcement o) throws Exception
  {
	  return (String)getColumn(o, "File2ContentType", String.class);
  } 
  public String getFile2SearchText(Announcement o) throws Exception
  {
	  return (String)getColumn(o, "File2SearchText", String.class);
  } 
  public void setFile2SearchText(Announcement o, String s) throws Exception
  {
      setColumn(o, "File2SearchText", s);
  } 
  public void markFile2Relocated(Announcement o) throws Exception
  {
      setColumn(o, "File2", null);
  }
  public BlobData readFile3(Announcement o) throws Exception
  {
      return  readBlobItem(o, "File3");
  } 
  public void storeFile3(Announcement o, java.io.InputStream data, int dataLength, String contentType) throws Exception
  {
      storeBlob(o, "File3", data, dataLength, contentType);
  }
  public void storeFile3(Announcement o, org.springframework.web.multipart.MultipartFile data) throws Exception
  {
      storeBlob(o, "File3", data);
  }
  public String getFile3ContentType(Announcement o) throws Exception
  {
	  return (String)getColumn(o, "File3ContentType", String.class);
  } 
  public String getFile3SearchText(Announcement o) throws Exception
  {
	  return (String)getColumn(o, "File3SearchText", String.class);
  } 
  public void setFile3SearchText(Announcement o, String s) throws Exception
  {
      setColumn(o, "File3SearchText", s);
  } 
  public void markFile3Relocated(Announcement o) throws Exception
  {
      setColumn(o, "File3", null);
  }

    /*
    *
   */
   public Announcement newAnnouncement() throws Exception
   {
       return (Announcement)newEntity(Announcement.class);
   }

}
