package com.era.community.communities.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.monitor.dao.CommunitySubscription;
import com.era.community.monitor.dao.SubscriptionFinder;

/**
 * @spring.bean name="/cid/[cec]/community/subscribeCommunity.ajx"
 */
public class SubscribeCommunityAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Community community = context.getCurrentCommunity();
		if (context.getCurrentUser() != null && community != null) {
			int currentUserId = context.getCurrentUser().getId();
			Command cmd = (Command) data;
			CommunitySubscription subs;

			try {
				subs= subscriptionFinder.getCommunitySubscriptionForUser(community.getId(), currentUserId);
			} catch (ElementNotFoundException e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String dt = sdf.format(now);
				Timestamp ts = Timestamp.valueOf(dt);
				
				subs = subscriptionFinder.newCommunitySubscription();
				subs.setUserId(new Integer(context.getCurrentUser().getId()));
				subs.setCommunityId(new Integer(context.getCurrentCommunity().getId()));
				subs.setFrequency(1);
				subs.setWebSubscription(1);
				subs.setMailSubscription(1);
				subs.setPageSubscription(1);
				subs.setModified(ts);
				subs.update();            
			}
			cmd.setFrequency(subs.getFrequency());
			cmd.setSubsId(subs.getId());
			String returnString = "<a href='javascript:void(0);' class='normalTip' onclick='unSubscribeCommunity()' title='Unsubscribe from e-mail notifications for this community' >Unfollow</a>";
			HttpServletResponse resp = contextManager.getContext().getResponse();
			resp.setContentType("text/html");
			Writer out = resp.getWriter();
			out.write(returnString);
			out.close();
			return null;
		}
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int Id;
		private int Frequency;
		private int subsId;

		public int getSubsId()
		{
			return subsId;
		}

		public void setSubsId(int subsId)
		{
			this.subsId = subsId;
		}

		public int getFrequency()
		{
			return Frequency;
		}

		public void setFrequency(int frequency)
		{
			Frequency = frequency;
		}

		public final int getId()
		{
			return Id;
		}

		public final void setId(int id)
		{
			Id = id;
		}
	}

	/**
	 * @param subscriptionFinder the subscriptionFinder to set
	 */
	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	/**
	 * @param contextManager the contextManager to set
	 */
	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}
}