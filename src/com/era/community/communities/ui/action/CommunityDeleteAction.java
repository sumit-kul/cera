package com.era.community.communities.ui.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractWizardAction;
import support.community.framework.AppRequestContextHolder;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.dao.DeletedCommunity;
import com.era.community.communities.dao.DeletedCommunityFinder;
import com.era.community.communities.ui.validator.DeletedCommunityValidator;

/**
 * @spring.bean name="/cid/[cec]/admin/delete-community.do"
 * TODO need to add later
 */
public class CommunityDeleteAction extends AbstractWizardAction
{
	private CommunityEraContextManager contextManager;
	protected CommunityFinder communityFinder;
	protected DeletedCommunityFinder delComFinder;
	private AppRequestContextHolder requestcontextManager;

	protected String[] getPageList()
	{
		return new String[] { "comm/community-delete-warning"};
	}

	protected int getTargetPage(HttpServletRequest request, Object data, Errors errors, int currentPage)
	{
		int target = getTargetPage(request, currentPage);

		if (target != currentPage){
			return target;
		}
		return currentPage;

	}

	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
	{
		Command cmd = (Command) command;
		CommunityEraContext context = contextManager.getContext();

		if (context.getCurrentUser() == null) {
			String reqUrl = context.getRequestUrl();
        	if(reqUrl != null) {
        		context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
        	}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

		Community comm = context.getCurrentCommunity();

		if (!context.isUserCommunityAdmin())
			throw new Exception("You are not authorized to carry out this action");
		DeletedCommunity delCom = delComFinder.newDeletedCommunity();
		delCom.setCommunityId(comm.getId());
		delCom.setDeleterId(context.getCurrentUser().getId());
		delCom.setComment(cmd.getComment());

		if ( comm != null ) {
			delCom.update();
			comm.delete();
			return new ModelAndView("comm/community-delete-confirm");
		}
		return null;
	}

	protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();

		return new ModelAndView("redirect:/cid/"+context.getCurrentCommunity().getId()+"/home.do");
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	public class Validator extends DeletedCommunityValidator
	{}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private String comment;

		public String getComment()
		{
			return comment;
		}
		public void setComment(String comment)
		{
			this.comment = comment;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public void setDeletedCommunityFinder(DeletedCommunityFinder delComFinder)
	{
		this.delComFinder = delComFinder;
	}
}