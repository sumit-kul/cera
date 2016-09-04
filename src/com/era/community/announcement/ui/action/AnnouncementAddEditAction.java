package com.era.community.announcement.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import support.community.database.BlobData;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.announcement.dao.Announcement;
import com.era.community.announcement.dao.AnnouncementFinder;
import com.era.community.announcement.ui.dto.AnnouncementDto;
import com.era.community.announcement.ui.validator.AnnouncementValidator;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.LinkBuilderContext;

/**
 * Create a new announcement
 * 
 * @spring.bean name="/announcement/announcement-add-edit.do"
 */
public class AnnouncementAddEditAction extends AbstractFormAction
{
    public static final String REQUIRES_AUTHENTICATION = "";   

    protected CommunityEraContextManager contextManager;
    protected AnnouncementFinder announcementFinder;

    protected String getView()
    {
        return "announcement/announcement-add-edit";             // jsp
    }

    protected void onDisplay(Object data) throws Exception
    {
        Command cmd = (Command) data; 
        CommunityEraContext context = contextManager.getContext();
        context.setH2Header("New announcement");

        /* If there is a announcement id in the command bean, load the details of the specified announcement for editing */
        if (cmd.getId() != 0) {

            Announcement announcement = announcementFinder.getAnnouncementForId(cmd.getId());
            context.setH2Header(announcement.getTitle());
            cmd.copyPropertiesFrom(announcement);

            cmd.setActionLabel("Edit existing");

            if (announcement.isFile1Present()) {
                cmd.setFile1Present(true);
                BlobData file1 = announcement.getFile1();
                cmd.setSizeInBytes1(file1.getLength());
            }

            if (announcement.isFile2Present()) {
                cmd.setFile2Present(true);
                BlobData file2 = announcement.getFile2();
                cmd.setSizeInBytes2(file2.getLength());
            }

            if (announcement.isFile3Present()) {
                cmd.setFile3Present(true);
                BlobData file3 = announcement.getFile3();
                cmd.setSizeInBytes3(file3.getLength());
            }          
        }
    }
    
    protected Map referenceData(Object command) throws Exception
    {
        Command cmd = (Command) command;
        CommunityEraContext context = contextManager.getContext();
        LinkBuilderContext linkBuilder = context.getLinkBuilder();

        if (cmd.getId() != 0) {
            // If editing an existing announcement, show the Delete tool
            Announcement announcement = announcementFinder.getAnnouncementForId(cmd.getId());
            if (context.getCurrentUser() != null && context.getCurrentUser().isSystemAdministrator()) {
                if (cmd.getStatus() == 1)
                    linkBuilder.addToolLink("Publish", "/announcement/announcement-change-status.do?id=" + announcement.getId() + "&amp;actionType=live", "Publish this announcement", "important");
                
                if (cmd.getStatus() == 2)
                    linkBuilder.addToolLink("Archive", "/announcement/announcement-change-status.do?id=" + announcement.getId() + "&amp;actionType=archive", "Archive this announcement", "important");
             
                linkBuilder.addToolLink("Delete this announcement", "/announcement/announcement-delete.do?id=" + announcement.getId(), "Delete this announcement", "cross");
            }

        }

        if (cmd.getMode().equals("c")) {
            cmd.setActionLabel("Create new");       
            cmd.setStatus(Announcement.STATUS_DRAFT);
            cmd.setMessageType(1);
        }

        return new HashMap();
    }

    protected ModelAndView onSubmit(Object data) throws Exception
    {
        Command cmd = (Command) data;
        CommunityEraContext context = contextManager.getContext();

        if (context.getCurrentUser() == null ) {
        	String reqUrl = context.getRequestUrl();
        	if(reqUrl != null) {
        	    context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
        	}
        	return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}
        boolean isNewAnnouncement = false;
        if (cmd.getId()==0)
            isNewAnnouncement=true;
        Announcement announcement = null;

        if (isNewAnnouncement) {
            /* Creating a new announcement */
            announcement = announcementFinder.newAnnouncement();
        }
        else {
            /* Editing an existing announcement */
            announcement = announcementFinder.getAnnouncementForId(cmd.getId());
        } 
        
        cmd.copyNonNullPropertiesTo(announcement);

        announcement.setAuthorId(context.getCurrentUser().getId());    
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date now = new Date();
    	String dt = sdf.format(now);
    	Timestamp ts = Timestamp.valueOf(dt);
        announcement.setDatePosted(ts);        
        if (cmd.getId()==0)
            announcement.setStatus(Announcement.STATUS_DRAFT);
            

        if (cmd.getUpload1() != null && !cmd.getUpload1().isEmpty()) {
            MultipartFile file1 = cmd.getUpload1();
            announcement.storeFile1(file1);
            announcement.setFileName1(file1.getOriginalFilename());
        }

        if (cmd.getUpload2() != null && !cmd.getUpload2().isEmpty()) {
            MultipartFile file2 = cmd.getUpload2();
            announcement.storeFile2(file2);
            announcement.setFileName2(file2.getOriginalFilename());
        }

        if (cmd.getUpload3() != null && !cmd.getUpload3().isEmpty()) {
            MultipartFile file3 = cmd.getUpload3();
            announcement.storeFile3(file3);
            announcement.setFileName3(file3.getOriginalFilename());
        }

        announcement.update();

        return new ModelAndView("redirect:/announcement/announcement-index.do");

    }

    public class Command extends AnnouncementDto implements CommandBean
    {
        private String actionLabel = "Create a new";

        public String getActionLabel()
        {
            return actionLabel;
        }

        public void setActionLabel(String actionLabel)
        {
            this.actionLabel = actionLabel;
        }
    }

    public class Validator extends AnnouncementValidator
    {
        public String validateSubject(Object value, CommandBean cmd)
        {
            if (value.toString().equals("")) {
                return "You must enter a title for your announcement";
            }
            if (value.toString().length() > 1000) {
                return "The maximum length of the title is 1000 characters, please shorten the text";
            }
            return null;
        }

    }

    protected CommandValidator createValidator()
    {
        return new Validator();
    }

    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public final void setAnnouncementFinder(AnnouncementFinder announcementFinder)
    {
        this.announcementFinder = announcementFinder;
    }

}
