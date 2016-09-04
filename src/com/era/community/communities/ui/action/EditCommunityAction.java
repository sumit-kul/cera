package com.era.community.communities.ui.action;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.AuthorizationFailedException;
import support.community.application.ElementNotFoundException;
import support.community.database.BlobData;
import support.community.database.EntityWrapper;
import support.community.database.QueryScroller;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.FileManager;
import com.era.community.base.ImageManipulation;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.ui.dto.CommunityDto;
import com.era.community.communities.ui.validator.CommunityValidator;
import com.era.community.connections.communities.dao.MemberListFeature;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.search.dao.SearchSite;

/**
 * @spring.bean name="/cid/[cec]/community/editCommunity.do"
 */
public class EditCommunityAction extends AbstractFormAction
{
    public static final String REQUIRES_AUTHENTICATION = "";
    
    protected CommunityEraContextManager contextManager;
    protected CommunityFinder communityFinder;
    protected UserFinder userFinder;
    protected MemberListFeature memberListFeature;

    protected String getView()
    {
        return "community/editCommunity";
    }

    protected void onDisplay(Object data) throws Exception
    {
    	Command cmd = (Command) data;
        CommunityEraContext context = contextManager.getContext();
        Community comm = context.getCurrentCommunity();
        cmd.setComm(comm);
        cmd.setSearchType("Community");
        cmd.setQueryText(comm.getName());

        QueryScroller memScroller = comm.listUsersRequestingMembership("Unapproved Requests", null);
        memScroller.setPageSize(5);
        memScroller.setBeanClass(RowBean.class);
        cmd.setRequests(memScroller.readPage(1));
        cmd.setNumberOfRequests(memScroller.readRowCount());
       
        cmd.copyPropertiesFrom(comm);
        if (cmd.getCommunityType().equalsIgnoreCase("Private")) {
        	cmd.setShowCommunityType("Private community (Community Admin must approve new members)");
		} else if (cmd.getCommunityType().equalsIgnoreCase("Protected")) {
        	cmd.setShowCommunityType("Protected community (Community Admin must approve new members)");
		} else {
			cmd.setShowCommunityType("Public community (Registered users can automatically join)");
		}
                
        BlobData logo= comm.getCommunityLogo();
        cmd.setSizeInBytes(logo.getLength());
        cmd.setIconImage(FileManager.computeIconImage(logo.getContentType()));
        cmd.setCommunityLogoPresent(comm.isLogoPresent());
        cmd.setCreated(comm.getCreated());
    }
    
    protected ModelAndView onSubmit(Object data) throws Exception
    {
    	Command cmd = (Command)data;
    	CommunityEraContext context = contextManager.getContext();
    	Community comm = context.getCurrentCommunity();
    	
    	if (context.getCurrentUser() == null ) {
    		String reqUrl = context.getRequestUrl();
        	if(reqUrl != null) {
        		context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
        	}
        	return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

    	if (!context.isUserCommunityAdmin()) 
    		throw new AuthorizationFailedException("You are not authorized to administrate <strong>\'" +comm.getName()+ "\'</strong> community");

    	comm.setName(cmd.getName());
    	comm.setWelcomeText(cmd.getWelcomeText());
    	comm.setDescription( ImageManipulation.manageImages(contextManager.getContext(), cmd.getDescription(), cmd.getName(), context.getCurrentUser().getId(), comm.getId(), "CommunityDescription") );
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
    	comm.setCommunityUpdated(ts);
    	
    	comm.update();
    	return new ModelAndView("redirect:"+contextManager.getContext().getCurrentCommunityUrl()+"/home.do");
    }

    protected CommandValidator createValidator()
    {
        return new Validator(); 
    }
    
    public class Command extends CommunityDto implements CommandBean
    {       
    	private Community comm;
    	
        private String allowedDomains = "";
        private String communityType="";
        private String showCommunityType="";
        private String tags;
        
        private MultipartFile logo;
        private MultipartFile file;
        private long sizeInBytes;       
        private String iconImage;
        
        private int numberOfRequests;
        private int numberOfThemes;
        private List themes = new ArrayList();
        private List requests = new ArrayList();
        private List searchSites = new ArrayList();
        private boolean redirected;
        
        public String getCreatedBy()
		{
			try {
				User creator = userFinder.getUserEntity(this.getCreatorId());
				return creator.getFirstName() +" "+ creator.getLastName();
			} catch (Exception e) {
				return "";
			}
		}
        
        public String getCreatedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {
				if (getCreated() != null) {
					Date date = formatter.parse(getCreated());
					if (fmter.format(date).equals(sToday)) {
						return "Today " + fmt.format(date);
					}
					return fmt2.format(date);
				}
			} catch (ParseException e) {
				return getCreated();
			}
			return "";
		}
        
        public String getSizeInKb() {
            return FileManager.getSizeInKb( getSizeInBytes() );
        }
        public final MultipartFile getLogo()
        {
            return logo;
        }
        public final void setLogo(MultipartFile logo)
        {
            this.logo = logo;
        }
        public final List<SearchSite> getSearchSites() throws Exception
        {
            return comm.getSearchSites();
        }
        public final int getNumberOfSearchSites() throws Exception
        {
            return getSearchSites().size();
        }
        public String getAllowedDomains()
        {
            return allowedDomains;
        }
        public void setAllowedDomains(String allowedDomains)
        {
            this.allowedDomains = allowedDomains;
        }
        public final String getIconImage()
        {
            return iconImage;
        }
        public final void setIconImage(String iconImage)
        {
            this.iconImage = iconImage;
        }
        public final long getSizeInBytes()
        {
            return sizeInBytes;
        }
        public final void setSizeInBytes(long sizeInBytes)
        {
            this.sizeInBytes = sizeInBytes;
        }
        public String getCommunityType()
        {
            return communityType;
        }
        public void setCommunityType(String communityType)
        {
            this.communityType = communityType;
        }
		public String getTags() {
			return tags;
		}
		public void setTags(String tags) {
			this.tags = tags;
		}
		public String getShowCommunityType() {
			return showCommunityType;
		}
		public void setShowCommunityType(String showCommunityType) {
			this.showCommunityType = showCommunityType;
		}
		public MultipartFile getFile() {
			return file;
		}
		public void setFile(MultipartFile file) {
			this.file = file;
		}
		public int getNumberOfRequests() {
			return numberOfRequests;
		}
		public void setNumberOfRequests(int numberOfRequests) {
			this.numberOfRequests = numberOfRequests;
		}
		public int getNumberOfThemes() {
			return numberOfThemes;
		}
		public void setNumberOfThemes(int numberOfThemes) {
			this.numberOfThemes = numberOfThemes;
		}
		public List getThemes() {
			return themes;
		}
		public void setThemes(List themes) {
			this.themes = themes;
		}
		public List getRequests() {
			return requests;
		}
		public void setRequests(List requests) {
			this.requests = requests;
		}
		public void setSearchSites(List searchSites) {
			this.searchSites = searchSites;
		}
		public Community getComm() {
			return comm;
		}
		public void setComm(Community comm) {
			this.comm = comm;
		}
		public boolean isRedirected() {
			return redirected;
		}
		public void setRedirected(boolean redirected) {
			this.redirected = redirected;
		}
    }
    
    public static class RowBean implements EntityWrapper
    {
        private User user; 
        private Date requestDate;

        public Date getRequestDate()
        {
            return requestDate;
        }
        public void setRequestDate(Date requestDate)
        {
            this.requestDate = requestDate;
        }
        public final User getUser()
        {
            return user;
        }
        public final void setUser(User user)
        {
            this.user = user;
        }
    }
    
    public class Validator extends CommunityValidator
    {
        public String validateAdministratorAddress(Object value, CommandBean cmd) throws Exception
        {
            if (value==null||value.toString().trim().length()==0) return null;
            try {
                userFinder.getUserForEmailAddress(value.toString().trim());
                return null;
            }
            catch (ElementNotFoundException e) {
                return "There is no registered user with email address "+value;
            }
        }
        
        public String validateAdministrator2(Object value, CommandBean cmd) throws Exception
        {
            return validateAdministratorAddress(value, cmd);
        }
        public String validateAdministrator3(Object value, CommandBean cmd) throws Exception
        {
            return validateAdministratorAddress(value, cmd);
        }
        public String validateAdministrator4(Object value, CommandBean cmd) throws Exception
        {
            return validateAdministratorAddress(value, cmd);
        }
        /*public String validateAdditionalMembers(Object value, CommandBean bean) throws Exception
        {
            StringBuffer buf = new StringBuffer(1024);
            Command cmd = (Command)bean;
            List<String> list = cmd.getAdditionalMemberList();
            for (int n=0; n<list.size(); n++) {
                String s = list.get(n);
                try {
                    userFinder.getUserForEmailAddress(s);
                }
                catch (ElementNotFoundException e) {
                    if (buf.length()>0) buf.append(",");
                    buf.append(s);
                }
            }
            if (buf.length()==0) return null;
            
            return "No registered user was found for the following addresses: "+buf.toString();
        }*/
    }

    public void setCommunityFinder(CommunityFinder communityFinder)
    {
        this.communityFinder = communityFinder;
    }

    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
    
    public void setMemberListFeature(MemberListFeature memberListFeature)
    {
        this.memberListFeature = memberListFeature;
    }
    
    public void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }
}