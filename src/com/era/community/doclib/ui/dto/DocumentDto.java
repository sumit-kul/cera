package com.era.community.doclib.ui.dto; 

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

import org.springframework.web.multipart.MultipartFile;

import com.era.community.base.FileManager;

public class DocumentDto extends com.era.community.doclib.dao.generated.DocumentEntity 
{
    MultipartFile upload;
    String tags;        
    private long sizeInBytes;

    private String displayName;
    private boolean photoPresent;
    private int starRating;           
    private int countUser;
    private String latestPostDate;
    
    public String getDatePostedOn() throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
		try {
			Date date = formatter.parse(getDatePosted());
			return fmt2.format(date);
		} catch (Exception e) {
			return getDatePosted();
		}
	}
    
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public final int getStarRating()
    {
        return starRating;
    }
    
    public String getIconType() {
    	if (getFileContentType() != null) {
    		if (getFileContentType().equalsIgnoreCase("application/vnd.ms-excel") || getFileContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
        		return "xls";
    		} else if (getFileContentType().equalsIgnoreCase("application/x-tar") || getFileContentType().equalsIgnoreCase("application/zip")) {
    			return "zip";
    		} else if (getFileContentType().equalsIgnoreCase("application/pdf")) {
    			return "pdf";
    		} else if (getFileContentType().equalsIgnoreCase("application/xml")) {
    			return "xml";
    		} else if (getFileContentType().equalsIgnoreCase("image/jpeg")) {
    			return "img";
    		} else if (getFileContentType().equalsIgnoreCase("text/richtext") || getFileContentType().equalsIgnoreCase("application/msword") || getFileContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
    			return "doc";
    		} else if (getFileContentType().equalsIgnoreCase("application/vnd.ms-powerpoint") || getFileContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
    			return "ppt";
    		} 
    		return "file";
		} else {
			return "file";
		}
    }

    public final void setStarRating(java.math.BigDecimal starRating)
    {
        this.starRating = starRating.intValue();
    }
    
    public final int getCountUser()
    {
        return countUser;
    }

    public final void setCountUser(Long countUser)
    {
        this.countUser = countUser.intValue();
    }
    
    public final MultipartFile getUpload()
    {
        return upload;
    }

    public final void setUpload(MultipartFile file)
    {
        this.upload = file;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public TreeMap getPopularTags()
    {
        return null;
    }

    public void setSizeInBytes(long size) {
        this.sizeInBytes = size;
    }
    
    public long getSizeInBytes() {
        return this.sizeInBytes;
    }
    
    public String getSizeInKb() {
        return FileManager.getSizeInKb( getSizeInBytes() );
    }
    
    public final String getLatestPostDate() 
    { 
        return latestPostDate; 
    }
    public final void setLatestPostDate(Date latestPostDate) 
    {  
        this.latestPostDate = latestPostDate.toString(); 
    }

	public boolean isPhotoPresent() {
		return photoPresent;
	}

	public void setPhotoPresent(boolean photoPresent) {
		this.photoPresent = photoPresent;
	}
}