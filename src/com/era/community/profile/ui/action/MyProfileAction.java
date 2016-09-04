package com.era.community.profile.ui.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.CommandValidator;
import support.community.framework.Option;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.LinkBuilderContext;
import com.era.community.base.UserRoleConstants;
import com.era.community.location.dao.Country;
import com.era.community.location.dao.CountryFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;
import com.era.community.pers.ui.validator.UserValidator;

/**
 * @spring.bean name="/pers/myProfile.do"
 * 
 * This action allow the user to maintain his personal profile
 */
public class MyProfileAction extends AbstractFormAction implements UserRoleConstants
{
	protected UserFinder userFinder;
	protected CountryFinder CountryFinder;
	protected CommunityEraContextManager contextManager; 

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	protected String getView()
	{
		return "profile/myProfile";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command command = (Command)data;

		User user;
		if (command.getId()==0)
			user = contextManager.getContext().getCurrentUser();
		else
			user = userFinder.getUserEntity(command.getId());

		command.copyPropertiesFrom(user);
		if (user.getProfileName() == null || "".equals(user.getProfileName())) {
			command.setProfileName(Integer.toString(user.getId()));
		}
		command.setExpertiseIds(user.getExpertiseIds());
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}
		
		Command cmd = (Command)data;
		User user = userFinder.getUserEntity(cmd.getId());

		cmd.copyRequestDataTo(user);
		user.update();

		user.setExpertise(cmd.getExpertiseIds());

		if (cmd.getPhoto()!=null && !cmd.getPhoto().isEmpty()) {
			user.storePhoto(cmd.getPhoto());
		}
		return (user.getId()==contextManager.getContext().getCurrentUser().getId()) ? new ModelAndView("redirect:/pers/myProfile.do") : REDIRECT_TO_BACKLINK; 
	}

	protected Map referenceData(Object data) throws Exception
	{
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();
		LinkBuilderContext linkBuilder = context.getLinkBuilder();
		if (context.getCurrentUser().getId()==cmd.getId() && !cmd.getMode().equals("u")) {
			linkBuilder.addToolLink("Edit my profile", "/pers/myProfile.do?mode=u", "Edit my personal profile", "");
		} 
		
		if (context.getCurrentUser().getId()==cmd.getId() && cmd.getMode().equals("u")) {
			cmd.setCountryList(stringToOptionList(CountryFinder.getAllCountry()));
		} 	

		if (context.getCurrentUser().isNewMailAvailable(null))
		{
			int unread = context.getCurrentUser().getUnreadMessagesCount(null);
			linkBuilder.addToolLink("View unread messages", "/reg/messages.do?order=unread",  "View "+unread+" unread messages from community members", "");
		}
		return new HashMap();
	}
	
	private List stringToOptionList(List items)
    {
        List<Option> options = new ArrayList<Option>(items.size());
        for (int n=0; n<items.size(); n++) {
        	Country lc = (Country)items.get(n);
        	Option opt = new Option();
            opt.setLabel(lc.getCountryName() + " (" + lc.getPhoneCountryCode() + ")");
            opt.setValue(lc.getId());
            options.add(opt);
        }
        return options;
    }

	public class Validator extends UserValidator
	{
		public String validateNewPassword(Object value, CommandBeanImpl command) throws Exception
		{
			Command cmd = (Command)command;
			if (isNullOrWhitespace(value)) return null;
			if (value.toString().length()<8) return "Please enter a password of at least 8 characters";
			if (!value.equals(cmd.getConfirmPassword())) return "Password and confirmation do not match";
			return null;       
		}
	}

	public class Command extends UserDto implements CommandBean
	{
		private User user = new User();

		private int[] expertiseIds ; 
		private String confirmEmailAddress;
		private String newPassword;
		private String confirmPassword;        
		private MultipartFile photo; 
		private List countryList;
		private int phoneCode ;

		public Command()
		{
			setMode("r");
		}

		@Override
		public void copyRequestDataTo(Object target) throws Exception
		{
			super.copyRequestDataTo(target);
			if (newPassword!=null&&newPassword.trim().length()>0) {
				setTargetProperty("password", newPassword, target);
			}
		}

		public boolean isAllowLoginUpdate() throws Exception
		{
			if (contextManager.getContext().getCurrentUser().getId()==getId()) return true;
			return contextManager.getContext().isUserSysAdmin();
		}        
		public User getUser()
		{
			return user;   
		}
		public void setUser(User user)
		{
			this.user = user;
		}
		public final String getConfirmPassword()
		{
			return confirmPassword;
		}
		public final void setConfirmPassword(String confirmPassword)
		{
			this.confirmPassword = confirmPassword;
		}
		public final MultipartFile getPhoto()
		{
			return photo;
		}
		public final void setPhoto(MultipartFile photo)
		{
			this.photo = photo;
		}
		public int[] getExpertiseIds()
		{
			return expertiseIds;
		}
		public void setExpertiseIds(int[] expertiseIds)
		{
			this.expertiseIds = expertiseIds;
		}
		public final String getNewPassword()
		{
			return newPassword;
		}
		public final void setNewPassword(String newPassword)
		{
			this.newPassword = newPassword;
		}

		public String getConfirmEmailAddress() {
			return confirmEmailAddress;
		}

		public void setConfirmEmailAddress(String confirmEmailAddress) {
			this.confirmEmailAddress = confirmEmailAddress;
		}

		public List getCountryList() {
			return countryList;
		}

		public void setCountryList(List countryList) {
			this.countryList = countryList;
		}

		public int getPhoneCode() {
			return phoneCode;
		}

		public void setPhoneCode(int phoneCode) {
			this.phoneCode = phoneCode;
		}  
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setCountryFinder(CountryFinder CountryFinder) {
		this.CountryFinder = CountryFinder;
	}
}