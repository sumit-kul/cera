package com.era.community.base;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import support.community.application.ElementNotFoundException;

import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.User;
import com.era.community.profile.dao.ProfileVisitFinder;

public class UserActivityFilter implements  Filter
{
	protected Log logger = LogFactory.getLog(getClass());

	protected CommunityEraContextManager contextManager;
	protected UserActivityManager activityManager;
	protected DashBoardAlertFinder dashBoardAlertFinder;
	protected ContactFinder contactFinder;
	protected ProfileVisitFinder profileVisitFinder;

	public final void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
	{
		try {
			CommunityEraContext context = contextManager.getContext();
			LinkBuilderContext linkBuilder = context.getLinkBuilder();
			User currentUser = context.getCurrentUser();
			if (currentUser != null) {
				// get dash board alerts for the current user
				try {
					DashBoardAlert alert = dashBoardAlertFinder.getDashBoardAlertForUserId(currentUser.getId());
					context.setAlert(alert);
					// get profile visit count for current user
					/*if (alerts.getProfileVisitCount() > 0) {
						String lText = "Your profile viewed by <span class='redMark'>"+alerts.getProfileVisitCount()+"</span> " + (alerts.getProfileVisitCount() > 1 ? " users" : " user");
						linkBuilder.addDashBoardLink("dbProVisId", lText, "pers/visitMyProfile.do", alerts.getProfileVisitCount() + (alerts.getProfileVisitCount() > 1 ? " users" : " user") + " visited your profile recently", "normalTip");
					} else {*/
						/*if (! (context.getRequestUri().contains("pers/visitMyProfile.do") || 
								context.getRequestUri().contains(".ajx"))){
							profileVisitFinder.deleteAllProfileVisitsForUser(currentUser.getId());
						}*/
					//}
					// get connection request count for current user
					/*if (alerts.getConnectionReceivedCount() > 0) {
						String lText = "Received connection request"+(alerts.getConnectionReceivedCount() > 1 ? "s" : "")+" <span class='redMark'>"+alerts.getConnectionReceivedCount()+"</span>";
						linkBuilder.addDashBoardLink("dbconRecCntId", lText, "pers/pendingConnectionRequests.do", "You have received "+ alerts.getConnectionReceivedCount() + " new connection request"+ (alerts.getConnectionReceivedCount() > 1 ? "s" : ""), "normalTip");
					}*/
					// get connection request approval count for current user
					/*if (alerts.getConnectionApprovedCount() > 0) {
						String lText = "Accepted connection request"+(alerts.getConnectionApprovedCount() > 1 ? "s" : "")+" <span class='redMark'>"+alerts.getConnectionApprovedCount()+"</span>";
						linkBuilder.addDashBoardLink(lText, "pers/visitMyProfile.do", "Your "+alerts.getConnectionApprovedCount()+" connection request"+(alerts.getConnectionApprovedCount() > 1 ? "s have" : " has")+" been accepted", "normalTip");
					}*/
					// get like count for current user
					/*if (alerts.getLikeCount() > 0) {
						String lText = "You have received <span class='redMark'>"+alerts.getLikeCount()+"</span> like"+(alerts.getLikeCount() > 1 ? "s" : "");
						linkBuilder.addDashBoardLink(lText, "pers/visitMyProfile.do", lText, "normalTip");
					}*/
					
					// get message count for current user
					/*if (alerts.getMessageCount() > 0) {
						String ltitle = "There are "+alerts.getMessageCount()+" new message"+(alerts.getMessageCount() > 1 ? "s " : " ") + "you haven&#39;t checked yet";
						String lText = "You have received <span class='redMark'>"+alerts.getMessageCount()+"</span> new message"+(alerts.getMessageCount() > 1 ? "s" : "");
						linkBuilder.addDashBoardLink("dbRecMesId", lText, "pers/myMessages.do?actionFrom=newMessages&msgType=Unread", ltitle, "normalTip");
					}*/
				} catch (ElementNotFoundException e) {
					context.setAlert(new DashBoardAlert());
				}

				/*if (comm!=null) {
					try {
						logger.debug("Adding activity:"+currentUser.getId()+"::"+currentUser.getFullName()+"::community"+comm.getId());
						if (comm.isMember(currentUser.getId())) {
							activityManager.addActivity(currentUser.getId(), currentUser.getFullName(), "community"+comm.getId());
						}
					}
					catch (Exception x) { logger.error("", x); }
				}*/
			}
			chain.doFilter(req, resp);
		}
		catch (Exception x) {
			logger.error("", x);
			// addErrorLog will be removed
			//throw new ServletException(x);
		}
	}
	
	public final void destroy()  {}
	public final void init(FilterConfig arg0) throws ServletException  { }

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setActivityManager(UserActivityManager activityManager)
	{
		this.activityManager = activityManager;
	}

	public void setDashBoardAlertFinder(DashBoardAlertFinder dashBoardAlertFinder) {
		this.dashBoardAlertFinder = dashBoardAlertFinder;
	}

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}

	public void setProfileVisitFinder(ProfileVisitFinder profileVisitFinder) {
		this.profileVisitFinder = profileVisitFinder;
	}

}