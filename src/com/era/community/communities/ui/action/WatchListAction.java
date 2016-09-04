package com.era.community.communities.ui.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

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
import com.era.community.monitor.dao.Subscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * Display the user's Monitor List (List of Subscriptions) for one particular community
 * Allow users update the frequency
 * 
 * @spring.bean name="/cid/[cec]/comm/watch.do"
 */
public class WatchListAction extends AbstractIndexFormAction 
{
	public static final String REQUIRES_AUTHENTICATION = "";

	protected CommunityEraContextManager contextManager;
	protected UserFinder userFinder;
	protected SubscriptionFinder subscriptionFinder;


	@Override
	protected String getView()
	{
		return "/comm/watch-list";
	}

	protected QueryPaginator getScroller(IndexCommandBean bean) throws Exception
	{ 
		Command cmd = (Command) bean;
		CommunityEraContext context = contextManager.getContext();
		User user = context.getCurrentUser();

		if (user == null) return null;

		/*
		 * Method on User class to list the UserSubscriptions
		 */
		QueryScroller scroller = user.listUserSubscriptions();

		scroller.setBeanClass( RowBean.class );

		return scroller;
	}


	public static class Command extends IndexCommandBeanImpl implements CommandBean
	{
		private int[] subscriptionIds;
		private int[] subscriptionFreqs;
		private int[] selectedIds;

		private String buttonUpdateOptions;
		private String buttonRemoveSelected;

		public final int[] getSubscriptionFreqs()
		{
			return subscriptionFreqs;
		}

		public final void setSubscriptionFreqs(int[] subscriptionFreqs)
		{
			this.subscriptionFreqs = subscriptionFreqs;
		}
		public final int[] getSubscriptionIds()
		{
			return subscriptionIds;
		}
		public final void setSubscriptionIds(int[] subscriptionIds)
		{
			this.subscriptionIds = subscriptionIds;
		}
		public final int[] getSelectedIds()
		{
			return selectedIds;
		}
		public final void setSelectedIds(int[] selectedIds)
		{
			this.selectedIds = selectedIds;
		}
		public final String getButtonRemoveSelected()
		{
			return buttonRemoveSelected;
		}
		public final void setButtonRemoveSelected(String buttonRemoveSelected)
		{
			this.buttonRemoveSelected = buttonRemoveSelected;
		}
		public final String getButtonUpdateOptions()
		{
			return buttonUpdateOptions;
		}
		public final void setButtonUpdateOptions(String buttonUpdateOptions)
		{
			this.buttonUpdateOptions = buttonUpdateOptions;
		}


	}


	public class Validator extends CommandValidator 
	{

	}


	@Override
	protected CommandValidator createValidator()
	{
		return new Validator();
	}


	@Override
	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command)data;

		if (cmd.getButtonUpdateOptions()!=null) {

			for (int n=0; n<cmd.subscriptionIds.length; n++) {
				Subscription sub = subscriptionFinder.getSubscriptionForId(cmd.getSubscriptionIds()[n]);
				sub.setFrequency(cmd.getSubscriptionFreqs()[n]);
				sub.update();
			}

		}
		else if (cmd.getButtonRemoveSelected()!=null) {

			for (int n=0; cmd.selectedIds != null && n<cmd.selectedIds.length; n++) {
				Subscription sub = subscriptionFinder.getSubscriptionForId(cmd.getSelectedIds()[n]);
				sub.setFrequency(cmd.getSubscriptionFreqs()[n]);
				sub.delete();
			}

		}

		return null;
	}

	/*
	 * 
	 */
	 @Override
	 protected void initializeCommand(HttpServletRequest request, Object command) throws Exception
	 {
		Command cmd = (Command)command;

		int n = WebUtils.getParametersStartingWith(request, "subscriptionFreqs[").size();

		cmd.setSubscriptionIds(new int[n]);
		cmd.setSubscriptionFreqs(new int[n]);
	 }

	 @Override
	 protected Map getReferenceData(Object data) throws Exception
	 {        
		 Command cmd = (Command)data;
		 CommunityEraContext context = contextManager.getContext();
		 List subscriptions = cmd.getScrollerPage();
		 int[] sids = new int[subscriptions.size()];
		 int[] freqs = new int[subscriptions.size()];

		 for (int n=0; n<subscriptions.size(); n++) {
			 RowBean sub = (RowBean)subscriptions.get(n);
			 sids[n] = sub.getId();
			 freqs[n] = sub.getFrequency();
		 }
		 cmd.setSubscriptionIds(sids);
		 cmd.setSubscriptionFreqs(freqs);

		 return new HashMap();
	 }

	 public static class RowBean implements EntityWrapper
	 {  
		 private Subscription sub ;        // Subscription entity

		 private int resultSetIndex;      
		 private String communityName;


		 public String getCommunityName()
		 {
			 return communityName;
		 }

		 public void setCommunityName(String communityName)
		 {
			 this.communityName = communityName;
		 }

		 public int getResultSetIndex()
		 {
			 return resultSetIndex; 
		 }

		 public void setResultSetIndex(int resultSetIndex)
		 {
			 this.resultSetIndex = resultSetIndex;
		 }

		 public boolean isEvenRow()
		 {
			 return resultSetIndex%2==0;
		 }

		 public boolean isOddRow()
		 {
			 return resultSetIndex%2==1;
		 }


		 // Delegate methods returning values from the instantiated entity
		 public Integer getCommunityId()
		 {
			 return sub.getCommunityId();
		 }

		 public String getItemName() throws Exception
		 {
			 return sub.getItemName();
		 }

		 public String getItemType() throws Exception
		 {
			 return sub.getItemType();
		 }

		 public String getItemUrl() throws Exception
		 {
			 return sub.getItemUrl();
		 }

		 public java.util.Date getItemLastUpdateDate() throws Exception
		 {
			 return sub.getItemLastUpdateDate();
		 }

		 public Integer getUserId()
		 {
			 return sub.getUserId();
		 }
		 public int getId()
		 {
			 return sub.getId();
		 }
		 public int getFrequency()
		 {
			 return sub.getFrequency();
		 }
		 public final void setSub(Subscription sub)
		 {
			 this.sub = sub;
		 }
	 }

	 public void setContextHolder(CommunityEraContextManager contextManager)
	 {
		 this.contextManager = contextManager;
	 }


	 public final void setUserFinder(UserFinder userFinder)
	 {
		 this.userFinder = userFinder;
	 }

	 public final void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	 {
		 this.subscriptionFinder = subscriptionFinder;
	 }


}
