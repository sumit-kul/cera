package com.era.community.communities.ui.action;

import org.springframework.mail.MailSender;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.dao.CommunityJoiningRequest;
import com.era.community.communities.dao.CommunityJoiningRequestFinder;
import com.era.community.config.dao.MailMessageConfig;
import com.era.community.pers.dao.User;

/**
 * 
 * @spring.bean name="/community/joinCommunityRequest.do"
 */
public class JoinCommunityRequestSendAction extends AbstractCommandAction
{

	public static final String REQUIRES_AUTHENTICATION = "";

	protected CommunityEraContextManager contextManager;
	protected CommunityFinder communityFinder; 
	protected CommunityJoiningRequestFinder joiningRequestFinder;
	protected MailSender mailSender;
	protected MailMessageConfig mailMessageConfig;
	
	@Override
	protected ModelAndView handle(Object data) throws Exception
		{
			Command cmd = (Command)data;
			CommunityEraContext context = contextManager.getContext();
			Community comm = communityFinder.getCommunityForId(cmd.getId());
			if (contextManager.getContext().getCurrentUser() == null) {
				String reqUrl = context.getRequestUrl();
	        	if(reqUrl != null) {
	        		context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
	        	}
				return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(contextManager.getContext().getRequestUrl()));
			}
			User user = contextManager.getContext().getCurrentUser();

			if (comm.isMember(user.getId())) {
				return new ModelAndView("redirect:/cid/"+comm.getId()+"/home.do?redirect=" + StringFormat.escape(contextManager.getContext().getRequestUrl()));
			}

			
			try { 
				cmd.setApprovalRequired(!comm.userCanJoinWithoutApproval(user));
				CommunityJoiningRequest joiningRequest = joiningRequestFinder.getRequestForUserAndCommunity(user.getId(), comm.getId());
				if (joiningRequest != null && joiningRequest.getStatus() == 0) {
					cmd.setMessage("You have already requested to join this community.");
				} else if (joiningRequest != null && joiningRequest.getStatus() == 2) {
					String message = "<p class='extraWord'><strong>This is a private community.</strong><br/><br/>"
						+ "Your earlier request to join this community has been rejected by the community admin.  <br />"
						+ "Notification of your acceptance to the community will be sent to your email address.<br/></p>"
						+ "You can request membership again by clicking <a onclick='joinRequest(&quot;"+comm.getId()+"&quot;,&quot;true&quot;)' href='javascript:void(0);' "
						+ "onmouseover='tip(this,&quot;Apply to join &#39;"+user.getFullName()+"&#39;&quot;)'>Apply to join</a>.</p>";
					
					cmd.setMessage(message);
				}
			} 
			catch (ElementNotFoundException ex) { 
				if (cmd.isApprovalRequired()) {
					String message = "<p class='extraWord'><strong>This is a private community.</strong><br/><br/>"
						+ "To request membership click <a onclick='joinRequest(&quot;"+comm.getId()+"&quot;,&quot;true&quot;)' href='javascript:void(0);' "
						+ "onmouseover='tip(this,&quot;Apply to join &#39;"+user.getFullName()+"&#39;&quot;)'>Apply to join</a></p>";
					
					cmd.setMessage(message);
				}
			}
			cmd.setCommunityName(comm.getName());
			return new ModelAndView("community/memberRequestConfirm",  "command", cmd );
		}

		public static class Command extends CommandBeanImpl implements CommandBean
		{
			private int id;
			private boolean approvalRequired;
			private boolean alreadyRequested;
			private String message;
			private String communityName;
			
			public final boolean isApprovalRequired()
			{
				return approvalRequired;
			}
			public final void setApprovalRequired(boolean requiresApproval)
			{
				this.approvalRequired = requiresApproval;
			}
			public final boolean isAlreadyRequested()
			{
				return alreadyRequested;
			}
			public final void setAlreadyRequested(boolean alreadyRequested)
			{
				this.alreadyRequested = alreadyRequested;
			}
			public String getMessage() {
				return message;
			}
			public void setMessage(String message) {
				this.message = message;
			}
			public int getId() {
				return id;
			}
			public void setId(int id) {
				this.id = id;
			}
			public String getCommunityName() {
				return communityName;
			}
			public void setCommunityName(String communityName) {
				this.communityName = communityName;
			}
		}

		public void setCommunityFinder(CommunityFinder communityFinder)
		{
			this.communityFinder = communityFinder;
		}

		public void setContextHolder(CommunityEraContextManager contextManager)
		{
			this.contextManager = contextManager;
		}

		public final void setJoiningRequestFinder(CommunityJoiningRequestFinder joiningRequestFinder)
		{
			this.joiningRequestFinder = joiningRequestFinder;
		}
		public CommunityEraContextManager getContextHolder()
		{
			return contextManager;
		}
	}