package com.era.community.base;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.util.UrlPathHelper;

import support.community.application.ElementNotFoundException;

import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityVisit;
import com.era.community.communities.dao.CommunityVisitFinder;
import com.era.community.connections.communities.dao.Membership;
import com.era.community.connections.communities.dao.MembershipFinder;
import com.era.community.pers.dao.User;

public class CommunityMemberRelationCheckFilter implements Filter
{
	protected Log logger = LogFactory.getLog(getClass());

	protected CommunityEraContextManager contextManager;
	protected MembershipFinder membershipFinder;
	protected CommunityVisitFinder communityVisitFinder;

	public final void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
	ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String referer = request.getHeader("Referer");
		
		if (referer != null && !(referer.startsWith("https://jhapak.com") || referer.startsWith("https://jhapak.com"))) {
			request.getSession().setAttribute("Out-Referer", referer);
		}
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");

		Date today = new Date();
		String sToday = fmt.format(today);

		try {
			CommunityEraContext context = contextManager.getContext();
			User user = context.getCurrentUser();
			Community comm = context.getCurrentCommunity();
			if (comm == null) { //if url contain community id (/cid/), it must exist...
				UrlPathHelper uph = new UrlPathHelper();
				String p = uph.getPathWithinApplication((HttpServletRequest)req);
				/*
				String ContextPath = uph.getContextPath((HttpServletRequest)req);
				String Looku = uph.getLookupPathForRequest((HttpServletRequest)req);
				String OriginatingContextPath = uph.getOriginatingContextPath((HttpServletRequest)req);
				String OriginatingRequestUri = uph.getOriginatingRequestUri((HttpServletRequest)req);
				String PathWithinServletMappi = uph.getPathWithinServletMapping((HttpServletRequest)req);
				String RequestUri = uph.getRequestUri((HttpServletRequest)req);
				String ServletPath = uph.getServletPath((HttpServletRequest)req);
				*/
				if (p!=null && p.length()>1 && (p.startsWith("/cid/"))) {
					response.sendRedirect(context.getContextUrl() + "/error/pageNotFound.do");
				}
			} else { // community exists
				if (comm.getCommunityType().equals("Private")) {
					if (user == null) { // to access a private community, user must be logged in...
						if (!context.getRequestUri().contains("/commLogoDisplay.img")) { // displaying community logo is not a issue for private community...
							logger.debug("Redirecting request for pag in " + comm.getName() + " to login screen");
							String reqUrl = context.getRequestUrl();
							if(reqUrl != null) {
								request.getSession().setAttribute("url_prior_login", reqUrl);
							}
							response.sendRedirect(context.getContextUrl()+"/login.do");
						}
					} else {
						if (!(comm.isMember(user) || user.isSystemAdministrator() || user.isSuperAdministrator())) { // for private community, user must be a member...
							logger.debug("Redirecting request for pag in " + comm.getName() + " to join screen");
							response.sendRedirect(context.getContextUrl() + "/community/joinCommunityRequestSend.do?id=" + comm.getId());
						} 
					}
				}
			}
			if (user != null) {
				if (context.getRequestUri().contains("pers/registerMe.do")) {
					response.sendRedirect(context.getContextUrl()+"/pers/myHome.do");
				}
				// user's last visit date
				if (!fmt.format(user.getDateLastVisit()).equals(sToday)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date now = new Date();
					String dt = sdf.format(now);
					Timestamp ts = Timestamp.valueOf(dt);
					user.setDateLastVisit(ts);
					try {
						user.update();
					} catch (RuntimeException e) {
						e.printStackTrace();
					}
				}
				if (comm != null) {
					if (comm.isMember(user)) {
						Membership membership = membershipFinder.getMembershipForUserAndMemberList(user.getId(), comm.getMemberList().getId());
						if (!fmt.format(membership.getDateLastVisit()).equals(sToday)) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date now = new Date();
							String dt = sdf.format(now);
							Timestamp ts = Timestamp.valueOf(dt);
							membership.setDateLastVisit(ts);
							membership.update();
						}
					} else if (isMembershipRequired(context)){
						logger.debug("Redirecting request for pag in " + comm.getName() + " to join screen");
						response.sendRedirect(context.getContextUrl() + "/community/joinCommunityRequestSend.do?id=" + comm.getId());
					}
				}
				if (comm != null && !comm.isPrivate()) {
					CommunityVisit cvisit = null;
					Date now = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
	            	String dt = sdf.format(now);
	            	Timestamp ts = Timestamp.valueOf(dt);
					try {
						cvisit = communityVisitFinder.getCommunityVisit(comm.getId(), user.getId());
						if (!fmter.format(cvisit.getCreated()).equals(fmter.format(now))) {
							cvisit = communityVisitFinder.newCommunityVisit();
							cvisit.setCommunityId(comm.getId());
							cvisit.setVisitingUserId(user.getId());
							cvisit.setModified(ts);
							cvisit.update();
							comm.setVisitCount(comm.getVisitCount() + 1);
							comm.update();
						}
					} catch (ElementNotFoundException e) {
						cvisit = communityVisitFinder.newCommunityVisit();
						cvisit.setCommunityId(comm.getId());
						cvisit.setVisitingUserId(user.getId());
						cvisit.setModified(ts);
						cvisit.update();
						comm.setVisitCount(comm.getVisitCount() + 1);
						comm.update();
					}
				}
			} else if (isLoginRequired(context)) {
				String reqUrl = context.getRequestUrl();
				if(reqUrl != null) {
					request.getSession().setAttribute("url_prior_login", reqUrl);
				}
				response.sendRedirect(context.getContextUrl()+"/login.do");
			} 
			chain.doFilter(req, resp);
		} catch (Exception x) {
			logger.error("", x);
			//throw new ServletException(x);
		}
	}

	private boolean isLoginRequired(CommunityEraContext context) {
		boolean loginRequired = false;
		if (context.getRequestUri().contains("/createNewCommunity.do") || 
				context.getRequestUri().contains("blog/addEditBlog.do") ||
				context.getRequestUri().contains("event/addEvent.do") ||
				context.getRequestUri().contains("wiki/addWiki.do") ||
				context.getRequestUri().contains("announcement/announcement-add-edit.do") ||
				context.getRequestUri().contains("faq/faqCreate.do") ||
				context.getRequestUri().contains("faq/helpQCreate.do") ||
				//context.getRequestUri().contains("pers/createNewPassword.do") ||
				context.getRequestUri().contains("library/editDocument.do") ||
				context.getRequestUri().contains("blog/editBlog.do") ||
				context.getRequestUri().contains("library/editDocument.do") ||
				context.getRequestUri().contains("community/editCommunity.do") ||
				context.getRequestUri().contains("event/editEvent.do") ||
				context.getRequestUri().contains("faq/faqEdit.do") ||
				context.getRequestUri().contains("wiki/editWiki.do") ||
				context.getRequestUri().contains("members/expel-member.do") ||
				context.getRequestUri().contains("forum/editForumitem.do") ||
				context.getRequestUri().contains("connections/manageMembership.do") ||
				context.getRequestUri().contains("pers/manageSubscriptions.do") ||
				context.getRequestUri().contains("pers/myProfile.do") ||
				context.getRequestUri().contains("pers/sendInvitation.do") ||
				context.getRequestUri().contains("pers/startBlog.do") ||
				context.getRequestUri().contains("forum/startTopic.do")	) {
			loginRequired = true;
		}
		return loginRequired;
	}
	
	private boolean isMembershipRequired(CommunityEraContext context) {
		boolean membershipRequired = false;
		if (context.getRequestUri().contains("blog/addEditBlog.do") ||
				context.getRequestUri().contains("event/addEvent.do") ||
				context.getRequestUri().contains("wiki/addWiki.do") ||
				context.getRequestUri().contains("library/editDocument.do") ||
				context.getRequestUri().contains("blog/editBlog.do") ||
				context.getRequestUri().contains("library/editDocument.do") ||
				context.getRequestUri().contains("community/editCommunity.do") ||
				context.getRequestUri().contains("event/editEvent.do") ||
				context.getRequestUri().contains("wiki/editWiki.do") ||
				context.getRequestUri().contains("members/expel-member.do") ||
				context.getRequestUri().contains("forum/editForumitem.do") ||
				context.getRequestUri().contains("forum/startTopic.do")	) {
			membershipRequired = true;
		}
		return membershipRequired;
	}

	public final void destroy()
	{
	}

	public final void init(FilterConfig arg0) throws ServletException
	{
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setMembershipFinder(MembershipFinder membershipFinder)
	{
		this.membershipFinder = membershipFinder;
	}

	public final MembershipFinder getMembershipFinder()
	{
		return membershipFinder;
	}

	public void setCommunityVisitFinder(CommunityVisitFinder communityVisitFinder) {
		this.communityVisitFinder = communityVisitFinder;
	}
}