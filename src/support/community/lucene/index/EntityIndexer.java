package support.community.lucene.index;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.util.Assert;

import support.community.database.QueryScroller;
import support.community.database.QueryScrollerCallback;

import com.era.community.jobs.dao.ScheduledJob;
import com.era.community.jobs.dao.ScheduledJobFinder;
import com.era.community.pers.dao.User;
import com.era.community.tagging.dao.Tag;

public class EntityIndexer implements InitializingBean, Runnable, ApplicationListener
{
	protected Log logger = LogFactory.getLog(getClass()); 
	protected ScheduledJobFinder scheduledJobFinder;

	protected EntityIndex index;

	private boolean shuttingDown = false;

	private List indexSets = new ArrayList();

	public void afterPropertiesSet() throws Exception 
	{
		Assert.notNull(index, "The index property has not been set");
	}

	public void run() 
	{
		logger.info("Starting indexer.");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = new Date();
			String startDt = sdf.format(start);
			Timestamp startTs = Timestamp.valueOf(startDt);
			ScheduledJob job = scheduledJobFinder.newScheduledJob();
			job.setTaskName("IndexerTask");
			job.setStarted(startTs);
			job.update();

			index.beginUpdate();
			updateIndex();
			index.endUpdate(!shuttingDown);

			Date complete = new Date();
			String completeDt = sdf.format(complete);
			Timestamp completeTs = Timestamp.valueOf(completeDt);
			job.setCompleted(completeTs);
			job.update();
		}
		catch (Exception e) {
			logger.error("", e);
			index.abortUpdate();
		}
		logger.info("Indexer ended.");
	}


	public void updateIndex() 
	{
		BatchEntityIndexUpdater indexUpdater = new BatchEntityIndexUpdater(index);

		for (int n=0; n<indexSets.size()&&!shuttingDown; n++) {
			IndexSet is = (IndexSet)indexSets.get(n);
			try {
				processIndexSet(is, indexUpdater);
			}
			catch (ShutdownException x) {
				logger.info("Shutdown detected");
			}
			catch (Exception e) {
				logger.error("", e);
				e.printStackTrace();
			}
		}

		if (shuttingDown) return;

		try {
			indexUpdater.flush(true);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private void processIndexSet(final IndexSet is, final BatchEntityIndexUpdater indexUpdater) throws Exception
	{
		logger.info("Processing index set "+is.getClass().getName());
		// This will give only non-private active communities and its contents.
		QueryScroller scroller = is.getScroller();
		scroller.doForAllRows(new QueryScrollerCallback() {

			public void handleRow(Object entity, int rowNum) throws Exception
			{
				try {
					if (shuttingDown) throw new ShutdownException();
					EntityIndexEntry entry = new EntityIndexEntry();

					Class entityClass = entity.getClass();
					EntityHandler handler = index.getEntityHandlerForClass(entityClass);
					logger.debug("Processing "+entityClass.getName()+" with id "+handler.getEntityId(entity));
					entry.setEntityType(index.getEntityTypeForClass(entityClass));
					entry.setId(handler.getEntityId(entity));

					int entryId = handler.getCurrentIndexEntry(entity, index);
					if (entryId!=-1 && !is.requiresUpdate(entity, index.getEntry(entryId))) return;

					entry.setContent(handler.getContent(entity, index.getBinaryDataHandler(), false));
					entry.setTitle(handler.getTitle(entity));
					entry.setDescription(handler.getDescription(entity));
					entry.setCreatedDate(handler.getDate(entity));
					entry.setModifiedDate(handler.getModified(entity));
					entry.setDataFields(handler.getDataFields(entity));

					if (entity instanceof User) {
						entry.setUserFirstName(handler.getUserFirstName(entity));
						entry.setUserLastName(handler.getUserLastName(entity));
					}

					if (entity instanceof Tag) {
						entry.setTagText(handler.getTagText(entity));
					}

					if (entryId>-1) indexUpdater.addDeletion(entryId);
					indexUpdater.addUpdate(entry);
				}
				catch (ShutdownException x) {
					throw x;
				}
				catch (Exception x) {
					logger.error("", x);
				}
			}

		});    
	}

	public void onApplicationEvent(ApplicationEvent event)
	{
		if (event instanceof ContextClosedEvent) {
			try {
				logger.info("Entity indexer notified of application shutdown");
				shuttingDown = true;
				index.shutdown();
				for (int n=0; n<100; n++) {
					if (!index.isOpenForUpdate()) return;
					Thread.sleep(50);
				}
				logger.error("***********************************************************");
				logger.error("*  Application is closing while index is open for update  *");
				logger.error("***********************************************************");
			}
			catch (Exception x) {
				logger.error("", x);
			}
		}
	}

	public final void setIndexSets(List indexSets)
	{
		this.indexSets = indexSets;
	}
	public final void setIndex(EntityIndex index)
	{
		this.index = index;
	}

	public void setScheduledJobFinder(ScheduledJobFinder scheduledJobFinder) {
		this.scheduledJobFinder = scheduledJobFinder;
	}
}
