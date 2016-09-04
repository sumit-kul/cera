package com.era.community.home.ui.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.ui.AbstractProcessingFilter;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.AppRequestContextHolder;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.announcement.dao.AnnouncementFinder;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.ui.dto.CommunityEntryPannelDto;
import com.era.community.config.dao.SystemParameterFinder;
import com.era.community.pers.dao.User;

/**
 * @spring.bean name="/"
 * @spring.bean name="/home.do"
 */
public class WelcomeToCommunitiesAction extends AbstractCommandAction
{
	private AppRequestContextHolder requestContextHolder;
	private CommunityEraContextManager contextManager;
	protected AnnouncementFinder announcementFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception {
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() != null ) {
			return new ModelAndView("redirect:/pers/myHome.do");
		}
		
		// Hitting the home page needs to clear out any save request from acegi.
		HttpServletRequest request = contextManager.getContext().getRequest();
		request.getSession().removeAttribute(AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY);
		try {
			List<CommunityEntryPannelDto> topCommList  = contextManager.getContext().getMostViewedCommunities();
			cmd.setLatestCommList(topCommList); 
		}catch (Throwable e){
		}
		requestContextHolder.getRequestContext().getBacklinkStack().clear();
		User currentUser = contextManager.getContext().getCurrentUser();
		// Set the announcementlist
		//List annList = announcementFinder.getLiveAnnouncementList();
		//cmd.setAnnouncementList(annList);
		return new ModelAndView("/welcomeToCommunities");
	}

	protected Map getReferenceData(Object data) throws Exception
	{
		return new HashMap();
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int img;
		private List latestCommList = new ArrayList();
		private List announcementList = new ArrayList();
		private String toggleList = "false";

		public final List getAnnouncementList()
		{
			return announcementList;
		}

		public final void setAnnouncementList(List announcementList)
		{
			this.announcementList = announcementList;
		}

		public final int getImg()
		{
			return img;
		}

		public final void setImg(int img)
		{
			this.img = img;
		}

		public List getLatestCommList() {
			return latestCommList;
		}

		public void setLatestCommList(List latestCommList) {
			this.latestCommList = latestCommList;
		}

		public String getToggleList() {
			return toggleList;
		}

		public void setToggleList(String toggleList) {
			this.toggleList = toggleList;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setRequestContextHolder(AppRequestContextHolder requestContextHolder)
	{
		this.requestContextHolder = requestContextHolder;
	}

	public final void setAnnouncementFinder(AnnouncementFinder announcementFinder)
	{
		this.announcementFinder = announcementFinder;
	}
}