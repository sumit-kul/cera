package com.era.community.profile.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.era.community.profile.dao.ProfileVisit;
import com.era.community.profile.dao.ProfileVisitFinder;

/**
 * @spring.bean name="/pers/deleteProfileVisiting.ajx"
 */
public class DeleteProfileVisitAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected ProfileVisitFinder profileVisitFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();
		String returnString = "";
		Command cmd = (Command) data;
		
		String[] myJsonData = context.getRequest().getParameterValues("json[]");
		if(myJsonData != null && myJsonData.length > 0) {
			for (int i=0; i < myJsonData.length; ++i) {
				String entId = myJsonData[i];
				int pvisitId = Integer.parseInt(entId);
				try {
					if ("deleteSelected".equals(cmd.getAction())) {
						profileVisitFinder.deleteProfileVisitsForUserAndId(currentUser.getId(), pvisitId);
					} else {
						ProfileVisit pv = profileVisitFinder.getProfileVisitForId(pvisitId);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    	Date now = new Date();
				    	String dt = sdf.format(now);
				    	Timestamp ts = Timestamp.valueOf(dt);
						pv.setStatus(1);
						pv.setModified(ts);
						
						pv.update();
					}
				} catch (ElementNotFoundException ex) {
				}
			}
		}

		HttpServletResponse resp = contextManager.getContext().getResponse();
		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write(returnString);
		out.close();
		return null;
	}

	public static class Command extends IndexCommandBeanImpl implements CommandBean
	{
		private String action;

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setProfileVisitFinder(ProfileVisitFinder profileVisitFinder) {
		this.profileVisitFinder = profileVisitFinder;
	}
}