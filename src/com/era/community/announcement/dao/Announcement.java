package com.era.community.announcement.dao;

import java.io.InputStream;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import support.community.database.BlobData;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 *
 * @entity name="ANMT"
 * 
 * @entity.blob name="File1"
 * @entity.blob name="File2"
 * @entity.blob name="File3"
 *    
 * @entity.index name="01" unique="no" columns="AuthorId"   
 *   
 * @entity.foreignkey name="01" columns="AuthorId" target="USER" ondelete="restrict"  
  * 
 */
public class Announcement  extends CecAbstractEntity
{
    
    /**
     * @column varchar(200) not null with default
     */
    protected String Title ;
    
    /**
     * @column varchar(1000) not null with default
     */
    protected String Subject ;
    
    /**
     * @column integer not null
     */
    protected int AuthorId ;
        
    /**
     * @column timestamp not null with default
     */
    protected Date DatePosted ;

    /**
     * @column integer not null with default
     */
    protected int Status = 0;
    
    /**
     * @column integer not null with default
     */
    protected int MessageType = 0;
    
    /**
     * @column long varchar 
     */
    protected String Body ;
    
    /**
     * @column varchar(150)
     */
    protected String FileName1;    
    
    /**
     * @column varchar(150)
     */
    protected String FileName2;  
    
    /**
     * @column varchar(150)
     */
    protected String FileName3;
    
    public static final int STATUS_DRAFT = 1;
    public static final int STATUS_LIVE = 2;
    public static final int STATUS_ARCHIVED = 3;
    
    
    public static final int TYPE_SERVICE = 0;
    public static final int TYPE_INFORMATION = 1;
    /*
     * Injected references.
     */
    protected AnnouncementDao dao;
    private AnnouncementFinder announcementFinder;


    public String getSubject()
    {
        return Subject;
    }
    
    public void setSubject(String subject)
    {
        Subject = subject;
    }
    
    public String getBody()
    {
        return Body;
    }
    
    public void setBody(String body)
    {
        Body = body;
    }
    
    public int getAuthorId()
    {
        return AuthorId;
    }
    
    public void setAuthorId(int authorId)
    {
        AuthorId = authorId;
    }
    
    public int getStatus()
    {
        return Status;
    }
    
    public void setStatus(int status)
    {
        Status = status;
    }
    
    public int getMessageType()
    {
        return MessageType;
    }
    
    public void setMessageType(int messageType)
    {
        MessageType = messageType;
    }
    
    public Date getDatePosted()
    {
        return DatePosted;
    }
    
    public void setDatePosted(Date datePosted)
    {
        DatePosted = datePosted;
    }
    
    public BlobData getFile1() throws Exception
    {
        return dao.readFile1(this);
    }
    
    public void clearFile1() throws Exception
    {
        dao.clearFile1(this);
    }
    
    public BlobData getFile2() throws Exception
    {
        return dao.readFile2(this);
    }
    
    public void clearFile2() throws Exception
    {
        dao.clearFile2(this);
    }
    
     public void clearFile3() throws Exception
    {
        dao.clearFile3(this);
    }
    
    public BlobData getFile3() throws Exception
    {
        return dao.readFile3(this);
    }
    
    public final String getFileName1()
    {
        return FileName1;
    }
    
    public final void setFileName1(String fileName1)
    {
        FileName1 = fileName1;
    }
    
    public final String getFileName2()
    {
        return FileName2;
    }
    
    public final void setFileName2(String fileName2)
    {
        FileName2 = fileName2;
    }
    
    public final String getFileName3()
    {
        return FileName3;
    }
    
    public final void setFileName3(String fileName3)
    {
        FileName3 = fileName3;
    }
    
    public final String getFile1ContentType() throws Exception
    {
        return dao.getFile1ContentType(this);
    }
    
    public final String getFile2ContentType() throws Exception
    {
        return dao.getFile2ContentType(this);
    }
    
    public final String getFile3ContentType() throws Exception
    {
        return dao.getFile3ContentType(this);
    }    
    
    public void storeFile1(MultipartFile data) throws Exception
    {
        dao.storeFile1(this, data);
    }
    
    public void storeFile2(MultipartFile data) throws Exception
    {
        dao.storeFile2(this, data);
    }
    
    public void storeFile3(MultipartFile data) throws Exception
    {
        dao.storeFile3(this, data);
    }
    
    public void storeFile1(InputStream data, int length, String contentType) throws Exception
    {
        dao.storeFile1(this, data, length, contentType);
    }
    
    public void storeFile2(InputStream data, int length, String contentType) throws Exception
    {
        dao.storeFile2(this, data, length, contentType);
    }
    
    public void storeFile3(InputStream data, int length, String contentType) throws Exception
    {
        dao.storeFile3(this, data, length, contentType);
    }
    
    public boolean isFile1Present() throws Exception
    {
        return dao.isFile1Present(this);
    }    

    public boolean isFile2Present() throws Exception
    {
        return dao.isFile2Present(this);
    }
    
    public boolean isFile3Present() throws Exception
    {
        return dao.isFile3Present(this);
    }
    
    public boolean isReadAllowed(UserEntity user) throws Exception
    {
       return true;
   }
    
    public boolean isWriteAllowed(UserEntity user) throws Exception
    {
        CommunityEraContext context = getCommunityEraContext();
        if (context.getCurrentUser() == null) {
        	return false;
        }
        if (context.getCurrentUser().isSystemAdministrator())
        return true;
        else
            return false;
    }
        
    /**
     * Update or insert this entity in the database.
     */
    public void update() throws Exception
    {
       dao.store(this); 
    }

    /** 
     *  Delete this entity from the database.
     */
    public void delete() throws Exception
    {
        dao.delete(this);
    } 

    public final void setDao(AnnouncementDao dao)
    {
        this.dao = dao;
    }
        
    public final AnnouncementDao getDao()
    {
        return dao;
    }

    public final void setAnnouncementFinder(AnnouncementFinder announcementFinder)
    {
        this.announcementFinder = announcementFinder;
    }

    public final String getTitle()
    {
        return Title;
    }

    public final void setTitle(String title)
    {
        Title = title;
    }   
    
}
