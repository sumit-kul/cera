package com.era.community.communities.ui.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.EntityWrapper;
import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.AbstractIndexFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.LinkBuilderContext;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityJoiningRequest;
import com.era.community.communities.dao.CommunityJoiningRequestFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/** 
 * @spring.bean name="/cid/[cec]/admin/rejected-requests.do"
 */
public class RejectedRequestsIndexAction extends AbstractIndexFormAction
{

	public static final String REQUIRES_AUTHENTICATION = "";

	/*
	 * Injected dependencies.
	 */
	protected CommunityEraContextManager contextManager;     
	protected CommunityJoiningRequestFinder requestFinder;
	protected UserFinder userFinder;

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	protected String getView()
	{
		return "/comm/rejected-request-index";
	}

	protected QueryPaginator getScroller(IndexCommandBean bean) throws Exception
	{
		Command cmd = (Command) bean;
		Community comm = contextManager.getContext().getCurrentCommunity();

		QueryScroller scroller = comm.listUsersRejectedMembership();
		scroller.setBeanClass(RowBean.class);

		cmd.setNumberOfRejectedRequests(scroller.readRowCount());

		return scroller;
	}

	@Override
	protected Map getReferenceData(Object data) throws Exception
	{        
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();
		LinkBuilderContext linkBuilder = context.getLinkBuilder();
		return new HashMap();
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command)data;

		CommunityEraContext context = contextManager.getContext();
		Community comm = contextManager.getContext().getCurrentCommunity();

		if (cmd.getSelectedIds() == null || cmd.getSelectedIds().length == 0) {
			return null;
		}

		//  Delete selected membership requests
		for (int userId : cmd.getSelectedIds()) {

			User u = userFinder.getUserEntity(userId);
			CommunityJoiningRequest req = requestFinder.getRequestForUserAndCommunity(u.getId(), comm.getId());
			req.delete();    

		}
		return new ModelAndView("redirect:/cid/"+comm.getId()+"/connections/showJoiningRequests.do");

	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}


	public class Validator extends CommandValidator 
	{

	}

	public static class RowBean implements EntityWrapper
	{      
		private User user;
		private int resultSetIndex;

		private int numberOfRequests;

		private Date requestDate;

		public Date getRequestDate()
		{
			return requestDate;
		}
		public void setRequestDate(Date requestDate)
		{
			this.requestDate = requestDate;
		}
		public int getNumberOfRequests()
		{
			return numberOfRequests;
		}
		public void setNumberOfRequests(int numberOfRequests)
		{
			this.numberOfRequests = numberOfRequests;
		}
		public boolean isEvenRow()
		{
			return resultSetIndex%2==0;
		}
		public boolean isOddRow()
		{
			return resultSetIndex%2==1;
		}

		public final int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public final void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
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

	public static class Command extends IndexCommandBeanImpl implements CommandBean
	{
		private int[] selectedIds;

		private int numberOfRejectedRequests;

		public int getNumberOfRejectedRequests()
		{
			return numberOfRejectedRequests;
		}
		public void setNumberOfRejectedRequests(int numberOfRejectedRequests)
		{
			this.numberOfRejectedRequests = numberOfRejectedRequests;
		}
		public final int[] getSelectedIds()
		{
			return selectedIds;
		}
		public final void setSelectedIds(int[] selectedIds)
		{
			this.selectedIds = selectedIds;
		}

	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setRequestFinder(CommunityJoiningRequestFinder requestFinder)
	{
		this.requestFinder = requestFinder;
	}


}
