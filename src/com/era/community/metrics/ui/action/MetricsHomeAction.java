package com.era.community.metrics.ui.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.AppRequestContextHolder;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogEntryCommentFinder;
import com.era.community.blog.dao.BlogEntryFinder;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.config.dao.SystemParameterFinder;
import com.era.community.connections.communities.dao.MembershipFinder;
import com.era.community.doclib.dao.DocumentCommentFinder;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.events.dao.EventFinder;
import com.era.community.forum.dao.ForumFinder;
import com.era.community.forum.dao.ForumItemFinder;
import com.era.community.pers.dao.MessageFinder;
import com.era.community.pers.dao.UserFinder;
import com.era.community.wiki.dao.WikiEntryFinder;

/**
 * @spring.bean name="/metrics/index.do"
 */
public class MetricsHomeAction extends AbstractCommandAction 
{
	private AppRequestContextHolder requestContextHolder;
	private CommunityEraContextManager contextManager;
	private CommunityFinder communityFinder;
	private SystemParameterFinder paramFinder;
	private UserFinder userFinder;
	private MembershipFinder membershipFinder;
	private ForumFinder forumFinder;
	private BlogEntryFinder blogEntryFinder;
	private BlogEntryCommentFinder blogEntryCommentFinder;
	private EventFinder eventFinder;
	private ForumItemFinder forumItemFinder;
	private WikiEntryFinder wikiEntryFinder;
	private DocumentFinder documentFinder;
	private DocumentCommentFinder documentCommentFinder;
	private MessageFinder messageFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command) data;         

		if (!context.isUserSysAdmin())
			throw new Exception("You are not authorized to view this information");

		Date today = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
		cmd.setTodayAsString(  fmt.format(today)  );

		return new ModelAndView("/metrics/metrics");
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		final String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"  };

		private int groupId;

		private String todayAsString;

		public String getTodayAsString()
		{
			return todayAsString;
		}

		public void setTodayAsString(String todayAsString)
		{
			this.todayAsString = todayAsString;
		}

		public int getBlogCommentCount() throws Exception
		{
			return blogEntryCommentFinder.getCommentCount();
		}

		public int getBlogEntryCount() throws Exception
		{
			return blogEntryFinder.getEntryCount();
		}

		/*
		 * Total communities
		 */
		 public int getCommunityCount() throws Exception
		 {
			return communityFinder.getCommunityCount(groupId);
		 }

		 /*
		  * Total registered users
		  */
		 public int getUserCount() throws Exception
		 {
			 return userFinder.getUserCount();
		 }  

		 public int getParticipantCount() throws Exception
		 {
			 return forumItemFinder.getParticipantCount(groupId);
		 }

		 public int getResponseCount() throws Exception
		 {
			 return forumItemFinder.getResponseCount(groupId);
		 }

		 public int getThreadCount() throws Exception
		 {
			 return forumItemFinder.getThreadCount(groupId);
		 }

		 public int getTopicCount() throws Exception
		 {
			 return forumItemFinder.getTopicCount(groupId);
		 }

		 public int getMembershipCount() throws Exception
		 {
			 return membershipFinder.getMembershipCount(groupId);
		 }

		 public int getMemberCount() throws Exception
		 {
			 return membershipFinder.getMemberCount(groupId);
		 }

		 public int getWikiEditCount() throws Exception
		 {
			 return wikiEntryFinder.getWikiEditCount(groupId);
		 }

		 public int getWikiEntryCount() throws Exception
		 {
			 return wikiEntryFinder.getWikiEntryCount(groupId);
		 }
		 public int getDocumentCount() throws Exception
		 {
			 return documentFinder.getDocumentCount(groupId);
		 }

		 public int getEventCount() throws Exception
		 {
			 return eventFinder.getEventCount(groupId);
		 }

		 public int getDocumentCommentCount() throws Exception
		 {
			 return documentCommentFinder.getDocumentCommentCount(groupId);
		 }

		 public int getContributorCount() throws Exception
		 {
			 return userFinder.getContributorCount(groupId);
		 }

		 public int getMessageCount() throws Exception
		 {
			 return messageFinder.getMessageCount();
		 }

		 public int getMessageReceiverCount() throws Exception
		 {
			 return messageFinder.getMessageReceiverCount();
		 }

		 public int getMessageSenderCount() throws Exception
		 {
			 return messageFinder.getMessageSenderCount();
		 }

		 /*
		  * Get the data for the User Growth graph. For the system wide metrics
		  * this is registered user growth, for group statistics it is growth in the 
		  * number of users belonging to at least one community in the group.
		  */
		  /*public String getUserCountSeries() throws Exception
		  {
			  if (getGroupId()==0) {
				  return getUserCountSeries(new TimeSeries() {
					  public int getCountAt(Date date) throws Exception
					  {
						  return userFinder.getUserCountAt(date);
					  }
				  });
			  }
			  else {
				  return getUserCountSeries(new TimeSeries() {
					  public int getCountAt(Date date) throws Exception
					  {
						  return membershipFinder.getMemberCountAt(date, getGroupId());
					  }
				  });
			  }
		  }*/

		  public String getUserGrowthChartTitle() throws Exception
		  {
			  return getGroupId()==0 ? "Registered user growth" : "Member Growth";
		  }

		  /*
		   * Get the data for the Registered User Growth graph
		   */
		  /*private String getUserCountSeries() throws Exception
		  {
			  JSONObject json;
			  JSONObject chart = new JSONObject();
			  JSONArray data = new JSONArray();
			  JSONArray xlabels = new JSONArray();
			  int xMin,xMax, yMin, yMax;

			  Calendar cal = Calendar.getInstance(); 
			  json = new JSONObject();

			  cal.set(Calendar.DAY_OF_MONTH, 1);
			  cal.add(Calendar.MONTH, -6);
			  xMin = getDayNumber(cal);
			  yMin = series.getCountAt(cal.getTime());
			  json = new JSONObject();
			  json.put("x", xMin);
			  json.put("y", yMin); 
			  data.put(json);
			  xlabels.put(MONTHS[cal.get(Calendar.MONTH)]);

			  for (int n=0 ; n<6; n++) {
				  cal.add(Calendar.MONTH, 1);
				  logger.debug(cal.getTime()+"  "+getDayNumber(cal));
				  json = new JSONObject();
				  json.put("x", getDayNumber(cal));

				  json.put("y", series.getCountAt(cal.getTime()));

				  data.put(json);
				  xlabels.put(MONTHS[cal.get(Calendar.MONTH)]);
			  }

			  cal = Calendar.getInstance();
			  yMax = series.getCountAt(new Date()); ///There is an assumption here that the count only ever goes up...
			  json = new JSONObject();
			  json.put("x",  getDayNumber(cal));
			  json.put("y", yMax);
			  data.put(json);

			  cal.add(Calendar.MONTH, 1);
			  xlabels.put(MONTHS[cal.get(Calendar.MONTH)]);
			  cal.set(Calendar.DAY_OF_MONTH, 1);
			  xMax = getDayNumber(cal);

			  chart.put("data", data);
			  chart.put("yAxis", calculateAxisData(yMin, yMax));

			  JSONObject xAxis = new JSONObject();
			  xAxis.put("min", xMin);
			  xAxis.put("max", xMax);
			  xAxis.put("labels", xlabels);
			  chart.put("xAxis", xAxis);

			  logger.info(chart.toString());

			  return chart.toString();
		  }*/

		  public int getGroupId()
		  {
			  return groupId;
		  }

		  public void setGroupId(int groupId)
		  {
			  this.groupId = groupId;
		  }

	}


	private  JSONObject calculateAxisData(int min, int max) throws Exception
	{
		JSONObject axis = new JSONObject();
		JSONArray labels = new JSONArray();

		axis.put("max", max);
		axis.put("min", min);
		axis.put("labels", labels);

		int range = max - min;
		if (range<6) {
			labels.put(min);
			labels.put(max);
			return axis;
		}

		int d =  (int)Math.pow(10, Math.max(((int)Math.log10(range))-2, 0));

		max=max+d;
		min=((int)(min/d))*d;
		axis.put("max", max);
		axis.put("min",min);
		labels.put(min);

		int tick =  Math.round(range/5.0f);
		tick = ((int)(tick/d))*d;

		for (int j=1; ; j++) {
			int label = min + j*tick;
			if (label>max) break;
			labels.put(label);
		}

		return axis;
	}



	public int getDayNumber(Calendar cal) 
	{
		return (int)(cal.getTimeInMillis()/(3600*24*1000));
	}


	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setCommunityFinder(CommunityFinder commFinder)
	{
		this.communityFinder = commFinder;
	}

	public final void setRequestContextHolder(AppRequestContextHolder requestContextHolder)
	{
		this.requestContextHolder = requestContextHolder;
	}


	public final void setParamFinder(SystemParameterFinder paramFinder)
	{
		this.paramFinder = paramFinder;
	}

	public final void setForumFinder(ForumFinder forumFinder)
	{
		this.forumFinder = forumFinder;
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setEventFinder(EventFinder eventFinder)
	{
		this.eventFinder = eventFinder;
	}

	public final void setForumItemFinder(ForumItemFinder forumItemFinder)
	{
		this.forumItemFinder = forumItemFinder;
	}

	public final void setMembershipFinder(MembershipFinder membershipFinder)
	{
		this.membershipFinder = membershipFinder;
	}

	public final void setBlogEntryCommentFinder(BlogEntryCommentFinder blogEntryCommentFinder)
	{
		this.blogEntryCommentFinder = blogEntryCommentFinder;
	}

	public final void setBlogEntryFinder(BlogEntryFinder blogEntryFinder)
	{
		this.blogEntryFinder = blogEntryFinder;
	}

	public final void setDocumentFinder(DocumentFinder documentFinder)
	{
		this.documentFinder = documentFinder;
	}

	public final void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder)
	{
		this.wikiEntryFinder = wikiEntryFinder;
	}

	public final void setDocumentCommentFinder(DocumentCommentFinder documentCommentFinder)
	{
		this.documentCommentFinder = documentCommentFinder;
	}

	public final void setMessageFinder(MessageFinder messageFinder)
	{
		this.messageFinder = messageFinder;
	}
}