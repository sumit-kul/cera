package com.era.community.newsfeed.ui.scheduler;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import support.community.database.QueryScroller;
import support.community.database.QueryScrollerCallback;

import com.era.community.config.dao.MailMessageConfig;
import com.era.community.jobs.dao.ScheduledJob;
import com.era.community.jobs.dao.ScheduledJobFinder;
import com.era.community.newsfeed.dao.NewsFeed;
import com.era.community.newsfeed.dao.NewsFeedFinder;

/*
 * News feed  (If you want to test this once, use /search/runScheduledTask.do?task=FeedReaderTask )
 */
public class FeedReaderJob implements Runnable
{
    protected Log logger = LogFactory.getLog(getClass());
    protected NewsFeedFinder feedFinder;
    protected MailSender mailSender;
    protected MailMessageConfig mailMessageConfig;
    protected ScheduledJobFinder scheduledJobFinder;
    
    public  void run()
    {
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	Date start = new Date();
			String startDt = sdf.format(start);
			Timestamp startTs = Timestamp.valueOf(startDt);
			ScheduledJob job = scheduledJobFinder.newScheduledJob();
			job.setTaskName("FeedReaderTask");
			job.setStarted(startTs);
			job.update();
			
            processFeeds();
            
            Date complete = new Date();
			String completeDt = sdf.format(complete);
			Timestamp completeTs = Timestamp.valueOf(completeDt);
			job.setCompleted(completeTs);
			job.update();
        }
        catch (Exception e) {
            logger.error("", e);
        }
    }


    private void processFeeds() throws Exception
    {
        QueryScroller scroller = feedFinder.listAllFeeds();
        scroller.setPageSize(1); //Will commit after each row

        String [] errors = new String [100];

        scroller.doForAllRows(new QueryScrollerCallback() {


            public void handleRow(Object rowBean, int rowNum) throws Exception
            {
                NewsFeed feed = (NewsFeed)rowBean;
                logger.info("Updating feed "+feed.getId()+" "+feed.getName());
                try {
                    feed.readFeed();
                }
                catch (Exception e) {
                    logger.error("FeedReaderJob : Error processing feed "+feed.getId() + feed.getTitle() + " Community: " + feed.getCommunityName(), e);

                    /*
                     * Parameters to substitute into the body text of the Email.
                     */
                    Map<String, String> params = new HashMap<String, String>(11);

                    params.put("#feedName#", feed.getName());
                    params.put("#feedTitle#", feed.getTitle());
                    params.put("#feedUrl#", feed.getUrl());
                    params.put("#communityName#", feed.getCommunityName());
                    params.put("#errorMessage#", e.getMessage());

                    /*
                     * Create and send the mail message.
                     */
                    SimpleMailMessage msg = mailMessageConfig.createMailMessage("feed-error-message", params);
                    msg.setTo("support@jhapak.com");
                    try {
          //              mailSender.send(msg);
                    } catch (RuntimeException e1) {
                    	e1.printStackTrace();
                    }
                }
            }

        }, true); //commit after each row
    }
    
    public final void setFeedFinder(NewsFeedFinder feedFinder)
    {
        this.feedFinder = feedFinder;
    }

    public void setMailSender(MailSender mailSender)
    {
        this.mailSender = mailSender;
    }
    
    public final void setMailMessageConfig(MailMessageConfig mailMessageConfig)
    {
        this.mailMessageConfig = mailMessageConfig;
    }

	public void setScheduledJobFinder(ScheduledJobFinder scheduledJobFinder) {
		this.scheduledJobFinder = scheduledJobFinder;
	}
}
