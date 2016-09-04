package com.era.community.monitor.ui.scheduler;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineUtils;

import support.community.application.ElementNotFoundException;
import support.community.database.QueryScroller;
import support.community.database.QueryScrollerCallback;

import com.era.community.blog.dao.BlogEntry;
import com.era.community.blog.dao.CommunityBlog;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentLibrary;
import com.era.community.events.dao.Event;
import com.era.community.events.dao.EventCalendar;
import com.era.community.forum.dao.Forum;
import com.era.community.forum.dao.ForumItem;
import com.era.community.jobs.dao.ScheduledJob;
import com.era.community.jobs.dao.ScheduledJobFinder;
import com.era.community.monitor.dao.CommunityBlogSubscription;
import com.era.community.monitor.dao.CommunitySubscription;
import com.era.community.monitor.dao.DocLibSubscription;
import com.era.community.monitor.dao.DocumentSubscription;
import com.era.community.monitor.dao.EventCalendarSubscription;
import com.era.community.monitor.dao.ForumSubscription;
import com.era.community.monitor.dao.Subscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.monitor.dao.ThreadSubscription;
import com.era.community.monitor.dao.WikiEntrySubscription;
import com.era.community.monitor.dao.WikiSubscription;
import com.era.community.monitor.ui.dto.SubscriptionItemDto;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.wiki.dao.Wiki;
import com.era.community.wiki.dao.WikiEntry;

public class SubscriptionsMailerTask implements InitializingBean, Runnable, ApplicationListener
{

	public static final int FREQUENCY_DAILY = 1;
	public static final int FREQUENCY_WEEKLY = 2;
	protected int frequency;
	protected String linkPrefix;
	protected Log logger = LogFactory.getLog(getClass());

	protected UserFinder userFinder;
	protected CommunityFinder communityFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected ScheduledJobFinder scheduledJobFinder;

	public void run()
	{
		String uFrequency = getFrequency()==FREQUENCY_DAILY ?"Daily":"Weekly";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		logger.info("Start task--> MailSubscriptionsTaskDaily");
		GregorianCalendar newCal = new GregorianCalendar( );
		int day = newCal.get( Calendar.DAY_OF_WEEK );

		// plan to run weekly job om Sunday only...Sun = 1, Mon = 2, Tue = 3, Wed = 4, Thu = 5, Fri = 6, Sat = 7
		if ( day!= 1 && this.getFrequency()==FREQUENCY_WEEKLY)
		{
			return;
		}
		try {
			Date start = new Date();
			String startDt = sdf.format(start);
			Timestamp startTs = Timestamp.valueOf(startDt);
			ScheduledJob job = scheduledJobFinder.newScheduledJob();
			job.setTaskName("MailSubscriptionsTaskDaily : " + uFrequency);
			job.setStarted(startTs);
			job.update();

			QueryScroller scroller = userFinder.listAllActiveUsersByName(User.class);
			scroller.doForAllRows(new UserHandler());

			Date complete = new Date();
			String completeDt = sdf.format(complete);
			Timestamp completeTs = Timestamp.valueOf(completeDt);
			job.setCompleted(completeTs);
			job.update();
		} catch (Exception e) {
			logger.error("Error in job task", e);
			e.printStackTrace();
		}
	}

	public class UserHandler implements QueryScrollerCallback
	{
		@SuppressWarnings("unchecked")
		public void handleRow(Object rowBean, int rowNum) throws Exception
		{
			User user = (User) rowBean;
			int counter = 0; //counter for number of updates..if 0 need not to send a mail...
			List<Subscription> subs = subscriptionFinder.getSubscriptionsForUserAndFrequency(user.getId(), getFrequency());
			if (subs.isEmpty()) {
				return;
			}
			Collections.sort(subs);
			Iterator it = subs.iterator();

			String sFrequency = getFrequency()==FREQUENCY_DAILY ?"daily":"weekly";
			String uFrequency = getFrequency()==FREQUENCY_DAILY ?"Daily":"Weekly";
			StringBuilder sBody = new StringBuilder();
			Forum forum = null;
			ForumItem topic = null;
			EventCalendar cal = null;
			Event event = null;
			DocumentLibrary doclib = null;
			Document doc = null;
			CommunityBlog blogcons = null;
			BlogEntry blogEntry = null;
			Wiki wiki = null;
			WikiEntry wikiEntry = null;

			

			Calendar timePoint = Calendar.getInstance();
			if (getFrequency() == 1) {
				timePoint.add(Calendar.DATE, -getFrequency()); 
			} else {
				timePoint.add(Calendar.DATE, -7); 
			}

			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM");
			Date when = timePoint.getTime();
			List<SubscriptionItemDto> sbList = null;

			while (it.hasNext()) {
				Subscription sub = (Subscription) it.next();
				if (sub != null) {
					Community testCommunity = null;
					try {
						testCommunity = communityFinder.getCommunityForId(sub.getCommunityId());
					} catch (ElementNotFoundException e) {
						continue;
					}
					if (!(testCommunity.isMember(user.getId()) || sub instanceof CommunitySubscription))
						continue;
					

					Object o = sub.getItem();
					if (sub instanceof ForumSubscription) {
						forum = (Forum) o;
						sbList = forum.getItemsSince(when,0);
						if (sbList.size() > 0) {
							String s = sbList.size() > 1 ? "s" : "";
							sBody.append("<tr><td><table bgcolor='#c22d05' border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr valign='top'>");							
							sBody.append("<td style='padding:0 0 0 20px' align='left'><span style='font:14px Georgia,serif;display:block;padding:10px 0;font-weight:normal;color:#ffffff;text-decoration:none'>");
							sBody.append("<b>Forum"+s+"</b> ("+sbList.size()+" new update"+s+")");
							sBody.append("</span></td></tr></tbody></table></td></tr>");
							
							for (SubscriptionItemDto sb : sbList) {
								
								sBody.append("<tr><td align='left'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td style='padding:10px 20px 20px 20px;border-top:1px solid #dedede'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td align='left' valign='top'><div>");
								
								sBody.append("<a href='"+getLinkPrefix()+ "/cid/" + testCommunity.getId() + "/forum/forumThread.do?id=" + sb.getItemId() + "' ");
								sBody.append(" style='color:#005689;font:15px/19px Georgia,serif;text-decoration:none;display:block;padding-bottom:4px' target='_blank'>" + sb.getItemTitle() + "</a>");
								
								sBody.append("</div>");
								
								sBody.append("<p style='text-decoration:none;font-family:helvetica,arial,sans-serif;font-size:16px;color:#555555;width: 540px'>");
								sBody.append("Forum topic started by" );
								sBody.append("&nbsp;<a href='"+getLinkPrefix()+"/pers/connectionResult.do?id="+sb.getAuthorId()+"' style='color: #66799f; text-decoration: none;' ");
								sBody.append(" title='Click here to visit "+sb.getAuthorName()+"&#39;s profile' >"+sb.getAuthorName()+"</a>&nbsp;" +sb.getDateEntered());
								sBody.append("</p>");
								
								sBody.append("</td></tr></tbody></table>");
								sBody.append("</td></tr></tbody></table></td></tr>");
								
								counter++;
							}
						}
					}
					
					else if (sub instanceof ThreadSubscription) {
						topic = (ForumItem) o;
						sbList = topic.getItemsSince(when);
						if (sbList.size() > 0) {
							String s = sbList.size() > 1 ? "s" : "";
							
							sBody.append("<tr><td><table bgcolor='#c22d05' border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr valign='top'>");							
							sBody.append("<td style='padding:0 0 0 20px' align='left'><span style='font:14px Georgia,serif;display:block;padding:10px 0;font-weight:normal;color:#ffffff;text-decoration:none'>");
							sBody.append("<b>Forum Response"+s+"</b> ("+sbList.size()+" new update"+s+")");
							sBody.append("</span></td></tr></tbody></table></td></tr>");
							
							for (SubscriptionItemDto sb : sbList) {
								
								sBody.append("<tr><td align='left'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td style='padding:10px 20px 20px 20px;border-top:1px solid #dedede'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td align='left' valign='top'><div>");
								
								sBody.append("<a href='"+getLinkPrefix()+ "/cid/" + testCommunity.getId() + "/forum/forumThread.do?id=" + sb.getItemId() + "' ");
								sBody.append(" style='color:#005689;font:15px/19px Georgia,serif;text-decoration:none;display:block;padding-bottom:4px' target='_blank'>" + sb.getItemTitle() + "</a>");
								
								sBody.append("</div>");
								
								sBody.append("<p style='text-decoration:none;font-family:helvetica,arial,sans-serif;font-size:16px;color:#555555;width: 540px'>");
								sBody.append("Replied by " );
								sBody.append("&nbsp;<a href='"+getLinkPrefix()+"/pers/connectionResult.do?id="+sb.getAuthorId()+"' style='color: #66799f; text-decoration: none;' ");
								sBody.append(" title='Click here to visit "+sb.getAuthorName()+"&#39;s profile' >"+sb.getAuthorName()+"</a>&nbsp;" +sb.getDateEntered());
								sBody.append("</p>");
								
								sBody.append("</td></tr></tbody></table>");
								sBody.append("</td></tr></tbody></table></td></tr>");
								
								counter++;
							}
						}
					}

					else if (sub instanceof EventCalendarSubscription) {
						cal = (EventCalendar) o;
						sbList = cal.getItemsSince(when);
						if (sbList.size() > 0) {
							String s = sbList.size() > 1 ? "s" : "";
							
							sBody.append("<tr><td><table bgcolor='#c22d05' border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr valign='top'>");							
							sBody.append("<td style='padding:0 0 0 20px' align='left'><span style='font:14px Georgia,serif;display:block;padding:10px 0;font-weight:normal;color:#ffffff;text-decoration:none'>");
							sBody.append("<b>Event"+s+"</b> ("+sbList.size()+" new update"+s+")");
							sBody.append("</span></td></tr></tbody></table></td></tr>");
							
							for (SubscriptionItemDto sb : sbList) {
								
								sBody.append("<tr><td align='left'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td style='padding:10px 20px 20px 20px;border-top:1px solid #dedede'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td align='left' valign='top'><div>");
								
								sBody.append("<a href='"+getLinkPrefix()+ "/cid/" + testCommunity.getId() + "/event/showEventEntry.do?id=" + sb.getItemId() + "' ");
								sBody.append(" style='color:#005689;font:15px/19px Georgia,serif;text-decoration:none;display:block;padding-bottom:4px' target='_blank'>" + sb.getItemTitle() + "</a>");
								
								sBody.append("</div>");
								
								sBody.append("<p style='text-decoration:none;font-family:helvetica,arial,sans-serif;font-size:16px;color:#555555;width: 540px'>");
								sBody.append("Created by " );
								sBody.append("&nbsp;<a href='"+getLinkPrefix()+"/pers/connectionResult.do?id="+sb.getAuthorId()+"' style='color: #66799f; text-decoration: none;' ");
								sBody.append(" title='Click here to visit "+sb.getAuthorName()+"&#39;s profile' >"+sb.getAuthorName()+"</a>&nbsp;" +sb.getDateEntered());
								sBody.append("</p>");
								
								sBody.append("</td></tr></tbody></table>");
								sBody.append("</td></tr></tbody></table></td></tr>");
								
								counter++;
							}
						}
					} 
					
					else if (sub instanceof DocLibSubscription) {
						doclib = (DocumentLibrary) o;
						sbList = doclib.getItemsSince(when);
						if (sbList.size() > 0) {
							String s = sbList.size() > 1 ? "s" : "";
							
							sBody.append("<tr><td><table bgcolor='#c22d05' border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr valign='top'>");							
							sBody.append("<td style='padding:0 0 0 20px' align='left'><span style='font:14px Georgia,serif;display:block;padding:10px 0;font-weight:normal;color:#ffffff;text-decoration:none'>");
							sBody.append("<b>File"+s+"</b> ("+sbList.size()+" new update"+s+")");
							sBody.append("</span></td></tr></tbody></table></td></tr>");
							
							for (SubscriptionItemDto sb : sbList) {
								sBody.append("<tr><td align='left'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td style='padding:10px 20px 20px 20px;border-top:1px solid #dedede'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td align='left' valign='top'><div>");
								
								sBody.append("<a href='"+getLinkPrefix()+ "/cid/" + testCommunity.getId() + "/library/documentdisplay.do?id=" + sb.getItemId() + "' ");
								sBody.append(" style='color:#005689;font:15px/19px Georgia,serif;text-decoration:none;display:block;padding-bottom:4px' target='_blank'>" + sb.getItemTitle() + "</a>");
								
								sBody.append("</div>");
								
								sBody.append("<p style='text-decoration:none;font-family:helvetica,arial,sans-serif;font-size:16px;color:#555555;width: 540px'>");
								sBody.append("Added by " );
								sBody.append("&nbsp;<a href='"+getLinkPrefix()+"/pers/connectionResult.do?id="+sb.getAuthorId()+"' style='color: #66799f; text-decoration: none;' ");
								sBody.append(" title='Click here to visit "+sb.getAuthorName()+"&#39;s profile' >"+sb.getAuthorName()+"</a>&nbsp;" +sb.getDateEntered());
								sBody.append("</p>");
								
								sBody.append("</td></tr></tbody></table>");
								sBody.append("</td></tr></tbody></table></td></tr>");
								
								counter++;
							}
						}
					}
					
					else if (sub instanceof DocumentSubscription) {
						doc = (Document) o;
						sbList = doc.getItemsSince(when);
						if (sbList.size() > 0) {
							String s = sbList.size() > 1 ? "s" : "";
							sBody.append("<tr><td><table bgcolor='#c22d05' border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr valign='top'>");							
							sBody.append("<td style='padding:0 0 0 20px' align='left'><span style='font:14px Georgia,serif;display:block;padding:10px 0;font-weight:normal;color:#ffffff;text-decoration:none'>");
							sBody.append("<b>File Comment"+s+"</b> ("+sbList.size()+" new update"+s+")");
							sBody.append("</span></td></tr></tbody></table></td></tr>");
							
							for (SubscriptionItemDto sb : sbList) {
								sBody.append("<tr><td align='left'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td style='padding:10px 20px 20px 20px;border-top:1px solid #dedede'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td align='left' valign='top'><div>");
								
								sBody.append("<a href='"+getLinkPrefix()+ "/cid/" + testCommunity.getId() + "/library/documentdisplay.do?id=" + sb.getItemId() + "' ");
								sBody.append(" style='color:#005689;font:15px/19px Georgia,serif;text-decoration:none;display:block;padding-bottom:4px' target='_blank'>" + sb.getItemTitle() + "</a>");
								
								sBody.append("</div>");
								
								sBody.append("<p style='text-decoration:none;font-family:helvetica,arial,sans-serif;font-size:16px;color:#555555;width: 540px'>");
								sBody.append("Commented by " );
								sBody.append("&nbsp;<a href='"+getLinkPrefix()+"/pers/connectionResult.do?id="+sb.getAuthorId()+"' style='color: #66799f; text-decoration: none;' ");
								sBody.append(" title='Click here to visit "+sb.getAuthorName()+"&#39;s profile' >"+sb.getAuthorName()+"</a>&nbsp;" +sb.getDateEntered());
								sBody.append("</p>");
								
								sBody.append("</td></tr></tbody></table>");
								sBody.append("</td></tr></tbody></table></td></tr>");
								counter++;
							}
						}
					}
					
					else if (sub instanceof WikiSubscription) {
						wiki = (Wiki) o;
						sbList = wiki.getItemsSince(when);
						if (sbList.size() > 0) {
							String s = sbList.size() > 1 ? "ies" : "y";
							String ss = sbList.size() > 1 ? "s" : "";
							sBody.append("<tr><td><table bgcolor='#c22d05' border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr valign='top'>");							
							sBody.append("<td style='padding:0 0 0 20px' align='left'><span style='font:14px Georgia,serif;display:block;padding:10px 0;font-weight:normal;color:#ffffff;text-decoration:none'>");
							sBody.append("<b>Wiki Entr"+s+"</b> ("+sbList.size()+" new update"+ss+")");
							sBody.append("</span></td></tr></tbody></table></td></tr>");
							
							for (SubscriptionItemDto sb : sbList) {
								
								sBody.append("<tr><td align='left'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td style='padding:10px 20px 20px 20px;border-top:1px solid #dedede'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td align='left' valign='top'><div>");
								
								sBody.append("<a href='"+getLinkPrefix()+ "/cid/" + testCommunity.getId() + "/wiki/wikiDisplay.do?entryId=" + sb.getItemId() + "' ");
								sBody.append(" style='color:#005689;font:15px/19px Georgia,serif;text-decoration:none;display:block;padding-bottom:4px' target='_blank'>" + sb.getItemTitle() + "</a>");
								
								sBody.append("</div>");
								
								sBody.append("<p style='text-decoration:none;font-family:helvetica,arial,sans-serif;font-size:16px;color:#555555;width: 540px'>");
								sBody.append("Wiki page published by " );
								sBody.append("&nbsp;<a href='"+getLinkPrefix()+"/pers/connectionResult.do?id="+sb.getAuthorId()+"' style='color: #66799f; text-decoration: none;' ");
								sBody.append(" title='Click here to visit "+sb.getAuthorName()+"&#39;s profile' >"+sb.getAuthorName()+"</a>&nbsp;" +sb.getDateEntered());
								sBody.append("</p>");
								
								sBody.append("</td></tr></tbody></table>");
								sBody.append("</td></tr></tbody></table></td></tr>");
								
								counter++;
							}
						}
					}
					
					else if (sub instanceof WikiEntrySubscription) {
						wikiEntry = (WikiEntry) o;
						sbList = wikiEntry.getItemsSince(when);
						if (sbList.size() > 0) {
							String s = sbList.size() > 1 ? "ies" : "y";
							String ss = sbList.size() > 1 ? "s" : "";
							sBody.append("<tr><td><table bgcolor='#c22d05' border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr valign='top'>");							
							sBody.append("<td style='padding:0 0 0 20px' align='left'><span style='font:14px Georgia,serif;display:block;padding:10px 0;font-weight:normal;color:#ffffff;text-decoration:none'>");
							sBody.append("<b>Revised Wiki Entr"+s+"</b> ("+sbList.size()+" new update"+ss+")");
							sBody.append("</span></td></tr></tbody></table></td></tr>");
							for (SubscriptionItemDto sb : sbList) {
								
								sBody.append("<tr><td align='left'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td style='padding:10px 20px 20px 20px;border-top:1px solid #dedede'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td align='left' valign='top'><div>");
								
								sBody.append("<a href='"+getLinkPrefix()+ "/cid/" + testCommunity.getId() + "/wiki/wikiDisplay.do?entryId=" + sb.getItemId() + "' ");
								sBody.append(" style='color:#005689;font:15px/19px Georgia,serif;text-decoration:none;display:block;padding-bottom:4px' target='_blank'>" + sb.getItemTitle() + "</a>");
								
								sBody.append("</div>");
								
								sBody.append("<p style='text-decoration:none;font-family:helvetica,arial,sans-serif;font-size:16px;color:#555555;width: 540px'>");
								sBody.append("Wiki page revised by" );
								sBody.append("&nbsp;<a href='"+getLinkPrefix()+"/pers/connectionResult.do?id="+sb.getAuthorId()+"' style='color: #66799f; text-decoration: none;' ");
								sBody.append(" title='Click here to visit "+sb.getAuthorName()+"&#39;s profile' >"+sb.getAuthorName()+"</a>&nbsp;" +sb.getDateEntered());
								sBody.append("</p>");
								
								sBody.append("</td></tr></tbody></table>");
								sBody.append("</td></tr></tbody></table></td></tr>");
								
								counter++;
							}
						} 
					}
					
					else if (sub instanceof CommunityBlogSubscription) {
						blogcons = (CommunityBlog) o;
						sbList = blogcons.getItemsSince(when);
						if (sbList.size() > 0) {
							String s = sbList.size() > 1 ? "ies" : "y";
							String ss = sbList.size() > 1 ? "s" : "";
							
							sBody.append("<tr><td><table bgcolor='#c22d05' border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr valign='top'>");							
							sBody.append("<td style='padding:0 0 0 20px' align='left'><span style='font:14px Georgia,serif;display:block;padding:10px 0;font-weight:normal;color:#ffffff;text-decoration:none'>");
							sBody.append("<b>Blog Entr"+s+"</b> ("+sbList.size()+" new update"+ss+")");
							sBody.append("</span></td></tr></tbody></table></td></tr>");
							
							for (SubscriptionItemDto sb : sbList) {
								
								sBody.append("<tr><td align='left'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td style='padding:10px 20px 20px 20px;border-top:1px solid #dedede'>");
								sBody.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td align='left' valign='top'><div>");
								
								sBody.append("<a href='"+getLinkPrefix()+ "/cid/" + testCommunity.getId() + "/blog/blogEntry.do?id=" + sb.getItemId() + "' ");
								sBody.append(" style='color:#005689;font:15px/19px Georgia,serif;text-decoration:none;display:block;padding-bottom:4px' target='_blank'>" + sb.getItemTitle() + "</a>");
								
								sBody.append("</div>");
								
								sBody.append("<p style='text-decoration:none;font-family:helvetica,arial,sans-serif;font-size:16px;color:#555555;width: 540px'>");
								sBody.append("Blog entry published by" );
								sBody.append("&nbsp;<a href='"+getLinkPrefix()+"/pers/connectionResult.do?id="+sb.getAuthorId()+"' style='color: #66799f; text-decoration: none;' ");
								sBody.append(" title='Click here to visit "+sb.getAuthorName()+"&#39;s profile' >"+sb.getAuthorName()+"</a>&nbsp;" +sb.getDateEntered());
								sBody.append("</p>");
								
								sBody.append("</td></tr></tbody></table>");
								sBody.append("</td></tr></tbody></table></td></tr>");
								
								counter++;
							}
						}
					}

				}
			}

			if (counter > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date today = new Date();
				String dt = sdf.format(today);

				MimeMessage message = mailSender.createMimeMessage();
				message.setFrom(new InternetAddress("support@jhapak.com"));
				message.setRecipients(Message.RecipientType.TO, user.getEmailAddress());
				message.setSubject("[Jhapak] Your "+sFrequency+" updates");

				Map model = new HashMap();    
				if(getFrequency()==FREQUENCY_DAILY) {
					String dateStart = fmter.format(today);
					model.put("dateRange", "For <strong>"+dateStart+"</strong>");
				} else {
					String dateStart = fmter.format(when);
					String dateEnd = fmter.format(today);
					model.put("dateRange", "From <strong>"+dateStart+"</strong> to <strong>"+dateEnd+"</strong>");
				}

				model.put("sFrequency", sFrequency);
				model.put("uFrequency", uFrequency);
				model.put("sBody", sBody);
				model.put("manageUrl", getLinkPrefix()+ "/pers/manageSubscriptions.do");
				
				model.put("croot", getLinkPrefix());
				model.put("cDate", dt);
				
				model.put("cEmail", user.getEmailAddress());
				model.put("cUnsubscribe", getLinkPrefix()+"/pers/unsubscribeMe.do?mid="+user.getId()+"&key="+user.getFirstKey()+"&type=daily");
				model.put("mailPreferences", getLinkPrefix()+"/pers/manageSubscriptions.do");
				model.put("cPrivacy", getLinkPrefix()+"/privacy.do");
				model.put("cHelp", getLinkPrefix()+"/help.do");
				model.put("cFeedback", getLinkPrefix()+"/feedback.do");

				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "main/resources/velocity/MailAlertTemplate.vm", "UTF-8", model);
				message.setContent(text, "text/html");
				message.setSentDate(new Date());

				Multipart multipart = new MimeMultipart();
				BodyPart htmlPart = new MimeBodyPart();
				htmlPart.setContent(text, "text/html");
				multipart.addBodyPart(htmlPart);            
				message.setContent(multipart);

				try {
					if (!user.isSuperAdministrator()) {
						mailSender.send(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public final void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public final void setFrequency(int frequency)
	{
		this.frequency = frequency;
	}

	public final void setLinkPrefix(String linkPrefix)
	{
		this.linkPrefix = linkPrefix;
	}

	public final String getLinkPrefix()
	{
		return linkPrefix;
	}

	public final int getFrequency()
	{
		return frequency;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setScheduledJobFinder(ScheduledJobFinder scheduledJobFinder) {
		this.scheduledJobFinder = scheduledJobFinder;
	}
}