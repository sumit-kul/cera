package com.era.community.home.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.RandomString;
import com.era.community.common.dao.Insight;
import com.era.community.common.dao.InsightFinder;
import com.era.community.common.dao.UserSession;
import com.era.community.common.dao.UserSessionFinder;
import com.era.community.common.dao.generated.UserSessionEntity;
import com.era.community.pers.dao.User;

/**
 * @spring.bean name="/handleInsight.ajx"
 */
public class HandleInsightAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager;
	protected UserSessionFinder userSessionFinder;
	protected InsightFinder insightFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception {
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		HttpServletRequest request = context.getRequest(); 
		UserSession uSession = null;
		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String dt = sdf.format(now);
			Timestamp ts = Timestamp.valueOf(dt);
			User user = context.getCurrentUser();
			Object sessId = request.getSession().getAttribute(context.getContextUrl());
			Insight previous = null;
			
			if (sessId == null) {
				uSession = newUserSession(cmd, request);
				String sessionKey = RandomString.nextString();
				uSession.setModified(ts);
				uSession.setSessionKey(sessionKey);
				String outreferer = (String)request.getSession().getAttribute("Out-Referer");
				if (outreferer != null) {
					if (outreferer.length() > 192) {
						uSession.setReferer(outreferer.substring(0, 190));
					} else {
						uSession.setReferer(outreferer);
					}
				}
				uSession.update();
				request.getSession().setAttribute(context.getContextUrl(), sessionKey);
				if (outreferer != null) {
					request.getSession().removeAttribute("Out-Referer");
				}
			} else {
				try {
					uSession = userSessionFinder.getUserSessionForSessionKey((String)sessId);
					if (user != null && uSession.getUserId() == 0) {
						uSession.setUserId(user.getId());
						uSession.update();
					}
				} catch (ElementNotFoundException e) {
					uSession = newUserSession(cmd, request);
					String sessionKey = RandomString.nextString();
					uSession.setSessionKey(sessionKey);
					uSession.setModified(ts);
					if (user != null && uSession.getUserId() == 0) {
						uSession.setUserId(user.getId());
					}
					String outreferer = (String)request.getSession().getAttribute("Out-Referer");
					if (outreferer != null) {
						if (outreferer.length() > 192) {
							uSession.setReferer(outreferer.substring(0, 190));
						} else {
							uSession.setReferer(outreferer);
						}
					}
					uSession.update();
					request.getSession().setAttribute(context.getContextUrl(), sessionKey);
					if (outreferer != null) {
						request.getSession().removeAttribute("Out-Referer");
					}
				}
				
				try {
					previous = insightFinder.findPreviousScreenForUserSession(uSession.getId());
				} catch (ElementNotFoundException e) {}
				
			}
			
			String screen = request.getHeader("Referer");	
			if (screen == null) {
				screen = context.getCurrentUrl();
			}
			Insight insight = insightFinder.newInsight();
			insight.setSessionId(uSession.getId());
			insight.setScreen(screen.length() > 180? screen.substring(0, 179) : screen);
			insight.setModified(ts);
			if (previous != null) {
				insight.setPreviousPageId(previous.getId());
				int stayTime = (int) (now.getTime() - previous.getCreated().getTime()) / 1000;
				insight.setTimeSpent(stayTime);
			}
			insight.update();
		return null;
	}

	private UserSession newUserSession(Command cmd, HttpServletRequest request) throws Exception{
		UserSession uSession = userSessionFinder.newUserSession();
		uSession.setDeviceType(cmd.getDeviceType());
		uSession.setDeviceBrand(cmd.getDeviceBrand());
		uSession.setDeviceOS(cmd.getDeviceOS());
		uSession.setBrowser(cmd.getBrowser());
		uSession.setVersion(cmd.getVersion());
		uSession.setIp(cmd.getIp());
		uSession.setHostname(cmd.getHostname());
		uSession.setOrg(cmd.getOrg());
		uSession.setCountry(cmd.getCountry());
		uSession.setRegion(cmd.getRegion());
		uSession.setCity(cmd.getCity());
		uSession.setLoc(cmd.getLoc());

		return uSession;
	}

	public class Command extends UserSessionEntity implements CommandBean
	{

	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserSessionFinder(UserSessionFinder userSessionFinder) {
		this.userSessionFinder = userSessionFinder;
	}

	public void setInsightFinder(InsightFinder insightFinder) {
		this.insightFinder = insightFinder;
	}
}