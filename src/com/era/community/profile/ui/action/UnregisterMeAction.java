package com.era.community.profile.ui.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractWizardAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.config.dao.MailMessageConfig;
import com.era.community.connections.communities.dao.MemberList;
import com.era.community.connections.communities.dao.MemberListFinder;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;

/**
 * @spring.bean name="/pers/unregisterMe.do"
 */
public class UnregisterMeAction extends AbstractWizardAction
{
	private CommunityEraContextManager contextManager;
	protected CommunityFinder communityFinder; 
	protected UserFinder userFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected MemberListFinder memberListFinder;
	protected MailSender mailSender;
	protected MailMessageConfig mailMessageConfig;

	protected String[] getPageList()
	{
		return new String[] { "pers/removeRegistrationWarning"};
	}

	protected int getTargetPage(HttpServletRequest request, Object data, Errors errors, int currentPage)
	{
		return currentPage;
	}

	protected void beginSession(Object data) throws Exception
	{
	}

	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
	{
		Command cmd = (Command) command;
		CommunityEraContext context = contextManager.getContext();
		Community comm = null;
		User user = contextManager.getContext().getCurrentUser();
		List list = communityFinder.getActiveCommunitiesForMember(user);

		for (Iterator iter = list.iterator(); iter.hasNext();) {
			comm = (Community) iter.next();  
			mailAdmin(comm, user);
			comm.getMemberList().removeMember(user); 
			subscriptionFinder.deleteSubscriptionsForUserAndCommunity(user.getId(), comm.getId()); 
		}        

		return new ModelAndView("profile/removeRegistrationConfirm");
	}

	/*
	 * The processCancel method is called if there is a submit that contains the "_cancel" request parameter.
	 */
	protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
	{
		return new ModelAndView("redirect:/pers/myProfile.do");
	}

	/*
	 * Email the admins
	 */
	@SuppressWarnings("unchecked")
	private void mailAdmin (Community comm, User user) throws Exception
	{
		/*
		 * Parameters to substitute into the body text of the Email.
		 */
		Map<String, String> params = new HashMap<String, String>(11);
		params.put("#communityName#", comm.getName());
		params.put("#user#", user.getFullName());

		SimpleMailMessage msg = mailMessageConfig.createMailMessage("remove-registration", params);

		User leadAdmin = userFinder.getUserEntity(comm.getCreator().getId());

		MemberList mlist = (MemberList) memberListFinder.getMemberListForCommunity(comm);
		List<User> adminList = mlist.getAdminByName();

		ArrayList<String> adminAddresses = new ArrayList<String>();
		adminAddresses.add(leadAdmin.getEmailAddress());

		for (User u  : adminList)
		{
			adminAddresses.add(u.getEmailAddress());
		}       

		String[] recipients = (String[]) adminAddresses.toArray(new String[adminAddresses.size()]);       

		try {
			msg.setTo(recipients);
			mailSender.send(msg);
		} catch (RuntimeException e) {
			e.printStackTrace();        
		}
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	public class Validator extends CommandValidator
	{}

	public static class Command extends UserDto implements CommandBean
	{
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public final void setMailSender(MailSender mailSender)
	{
		this.mailSender = mailSender;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setMailMessageConfig(MailMessageConfig mailMessageConfig)
	{
		this.mailMessageConfig = mailMessageConfig;
	}

	public void setMemberListFinder(MemberListFinder memberListFinder)
	{
		this.memberListFinder = memberListFinder;
	}

}
