package com.era.community.blog.ui.action;

import java.util.Date;
import java.util.TreeMap;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;
import support.community.util.StringHelper;

import com.era.community.base.FileManager;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.Taggable;
import com.era.community.blog.ui.dto.BlogEntryCommentDto;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.User;


/**
 * @spring.bean name="/blog/blogEntry.do"
 */
public class BlogDisplayEntryAction extends AbstractCommandAction
{

    public static final String REQUIRES_AUTHENTICATION = "";
    
    protected CommunityFinder communityFinder;
    protected CommunityEraContextManager contextManager;
    

    protected ModelAndView handle(Object data) throws Exception
	{
    	CommunityEraContext context = contextManager.getContext();
        User user = context.getCurrentUser();
        Command cmd = (Command)data;
        return null;
	    //return new ModelAndView("redirect:" + context.getContextUrl()+"/C/"+ + "/blog/blogEntry.do?id=" + cmd.getId());
	}
    
    public static class Command extends IndexCommandBeanImpl implements CommandBean, Taggable
    {              
        private String userId;
        private String comment;        
        private int id;
        private String title;
        private String body;
        private Date datePosted;
        private int posterId;
        private String displayName;
        private boolean file1Present;
        private boolean file2Present;
        private boolean file3Present;
        private String fileName1;
        private String fileName2;
        private String fileName3;
        private String file1ContentType;
        private String file2ContentType;
        private String file3ContentType;
        private long  sizeInBytes1;
        private long sizeInBytes2;
        private long sizeInBytes3;
        private String displayBody;
        private String tags;
        private TreeMap popularTags;
            
        public String getTags() {
			return tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public String getUserId()
        {
            return userId;
        }
        
        public void setUserId(String userId)
        {
            this.userId = userId;
        }
        
        public String getComment()
        {
            return comment;
        }
        
        public void setComment(String comment)
        {
            this.comment = comment;
        }
                
        public String getBody()
        {
            return body;
        }
        
        public void setBody(String body)
        {
            this.body = body;
        }
        
        public Date getDatePosted()
        {
            return datePosted;
        }
        
        public void setDatePosted(Date datePosted)
        {
            this.datePosted = datePosted;
        }
        
        public int getId()
        {
            return id;
        }
        
        public void setId(int id)
        {
            this.id = id;
        }
                
        public String getDisplayName()
        {
            return displayName;
        }
        
        public void setDisplayName(String displayName)
        {
            this.displayName = displayName;
        }                    
        
        public int getPosterId()
        {
            return posterId;
        }

        public void setPosterId(int posterId)
        {
            this.posterId = posterId;
        }
        
        public String getTitle()
        {
            return title;
        }
        
        public void setTitle(String title)
        {
            this.title = title;
        }
        
        public String getDisplayBody()
        {

            return StringHelper.formatForDisplay(this.getBody());
        }
                
        public String getFile1ContentType()
        {
            return file1ContentType;
        }
        
        public void setFile1ContentType(String file1ContentType)
        {
            this.file1ContentType = file1ContentType;
        }
        
        public boolean isFile1Present()
        {
            return file1Present;
        }
        
        public void setFile1Present(boolean file1Present)
        {
            this.file1Present = file1Present;
        }
        
        public String getFile2ContentType()
        {
            return file2ContentType;
        }
        
        public void setFile2ContentType(String file2ContentType)
        {
            this.file2ContentType = file2ContentType;
        }
        
        public boolean isFile2Present()
        {
            return file2Present;
        }
        
        public void setFile2Present(boolean file2Present)
        {
            this.file2Present = file2Present;
        }
        
        public String getFile3ContentType()
        {
            return file3ContentType;
        }
        
        public void setFile3ContentType(String file3ContentType)
        {
            this.file3ContentType = file3ContentType;
        }
        
        public boolean isFile3Present()
        {
            return file3Present;
        }
        
        public void setFile3Present(boolean file3Present)
        {
            this.file3Present = file3Present;
        }
        
        public String getFileName1()
        {
            return fileName1;
        }
        
        public void setFileName1(String fileName1)
        {
            this.fileName1 = fileName1;
        }
        
        public String getFileName2()
        {
            return fileName2;
        }
        
        public void setFileName2(String fileName2)
        {
            this.fileName2 = fileName2;
        }
        
        public String getFileName3()
        {
            return fileName3;
        }
        
        public void setFileName3(String fileName3)
        {
            this.fileName3 = fileName3;
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
                        
        public long getSizeInBytes1()
        {
            return sizeInBytes1;
        }
        
        public void setSizeInBytes1(long sizeInBytes1)
        {
            this.sizeInBytes1 = sizeInBytes1;
        }
        
        public long getSizeInBytes2()
        {
            return sizeInBytes2;
        }
        
        public void setSizeInBytes2(long sizeInBytes2)
        {
            this.sizeInBytes2 = sizeInBytes2;
        }
        
        public long getSizeInBytes3()
        {
            return sizeInBytes3;
        }
        
        public void setSizeInBytes3(long sizeInBytes3)
        {
            this.sizeInBytes3 = sizeInBytes3;
        }
        
        public String getSizeInKb1() {
            return FileManager.getSizeInKb( getSizeInBytes1() );
        }

        public String getSizeInKb2() {
            return FileManager.getSizeInKb( getSizeInBytes2() );
        }
        
        public String getSizeInKb3() {
            return FileManager.getSizeInKb( getSizeInBytes3() );
        }

        public TreeMap getPopularTags()
        {
            return popularTags;
        }

        public void setPopularTags(TreeMap popularTags)
        {
            this.popularTags = popularTags;
        }                
        
    }
    
    public static class RowBean extends BlogEntryCommentDto
    {  
        private int resultSetIndex;        
        private String displayName;
        
        public int getResultSetIndex()
        {
            return resultSetIndex; 
        }
        
        public void setResultSetIndex(int resultSetIndex)
        {
            this.resultSetIndex = resultSetIndex;
        }
        
        public boolean isEvenRow()
        {
            return resultSetIndex%2==0;
        }
        
        public boolean isOddRow()
        {
            return resultSetIndex%2==1;
        }        
                
        public String getDisplayName()
        {
            return displayName;
        }
        
        public void setDisplayName(String displayName)
        {
            this.displayName = displayName;
        }
    }
}
