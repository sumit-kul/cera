package com.era.community.announcement.ui.dto; 

import org.springframework.web.multipart.*;

import com.era.community.base.*;

public class AnnouncementDto extends com.era.community.announcement.dao.generated.AnnouncementEntity 
{  
    private MultipartFile upload1;
    private MultipartFile upload2;
    private MultipartFile upload3;
    
    private long sizeInBytes1;
    private long sizeInBytes2;
    private long sizeInBytes3;
   
    private String iconImage1;
    private String iconImage2;
    private String iconImage3;
    
    private String authorName;
    
    public final String getAuthorName()
    {
        return authorName;
    }

    public final void setAuthorName(String authorName)
    {
        this.authorName = authorName;
    }

    public final MultipartFile getUpload1()
    {
        return upload1;
    }

    public final void setUpload1(MultipartFile upload1)
    {
        this.upload1 = upload1;
    }

    public final MultipartFile getUpload2()
    {
        return upload2;
    }

    public final void setUpload2(MultipartFile upload2)
    {
        this.upload2 = upload2;
    }

    public final MultipartFile getUpload3()
    {
        return upload3;
    }
    
    public final long getSizeInBytes1()
    {
        return sizeInBytes1;
    }

    public final void setSizeInBytes1(long sizeInBytes1)
    {
        this.sizeInBytes1 = sizeInBytes1;
    }    
    
    public final long getSizeInBytes2()
    {
        return sizeInBytes2;
    }

    public final void setSizeInBytes2(long sizeInBytes2)
    {
        this.sizeInBytes2 = sizeInBytes2;
    }

    public final long getSizeInBytes3()
    {
        return sizeInBytes3;
    }

    public final void setSizeInBytes3(long sizeInBytes3)
    {
        this.sizeInBytes3 = sizeInBytes3;
    }

    public String getSizeInKb1() {
        return FileManager.getSizeInKb(getFile1Length());
    }
    
    public String getSizeInKb2() {
        return FileManager.getSizeInKb(getFile2Length());
    }
    
    public String getSizeInKb3() {
        return FileManager.getSizeInKb(getFile3Length());
    }    

    public String getFile1IconClass() throws Exception
    {
        String image = FileManager.computeIconImage(this.getFile1ContentType());
        String image1 = image.replaceFirst("/img/contenticon/", "");
        return image1.replaceFirst("icon.gif", "File");
    }
    
        public String getFile2IconClass() throws Exception
    {
        String image = FileManager.computeIconImage(this.getFile2ContentType());
        String image1 = image.replaceFirst("/img/contenticon/", "");
        return image1.replaceFirst("icon.gif", "File");
    }
        
    public String getFile3IconClass() throws Exception
    {
        String image = FileManager.computeIconImage(this.getFile3ContentType());
        String image1 = image.replaceFirst("/img/contenticon/", "");
        return image1.replaceFirst("icon.gif", "File");
    }
         
    public String getIconImage1() {
        return FileManager.computeIconImage( getFile1ContentType() );
    }
    
    public String getIconImage2() {
        return FileManager.computeIconImage( getFile2ContentType() );
    }
    
    public String getIconImage3() {
        return FileManager.computeIconImage( getFile3ContentType() );
    }
                    
    public void setIconImage3(String iconImage3)
    {
        this.iconImage3 = iconImage3;
    }    
    
    public final void setIconImage1(String iconImage1)
    {
        this.iconImage1 = iconImage1;
    }
    
    public final void setIconImage2(String iconImage2)
    {
        this.iconImage2 = iconImage2;
    }
}
