package support.community.lucene.index;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.util.Assert;

import support.community.application.ElementNotFoundException;
import support.community.database.AbstractJdbcDaoSupport;
import support.community.database.JdbcDaoManager;
import support.community.framework.ThreadLifecycleListener;
import support.community.lucene.search.EntityIndexSearcher;

import com.era.community.pers.dao.UserDaoImpl;

public class SimpleEntityIndex implements EntityIndex, ThreadLifecycleListener, InitializingBean, ApplicationListener
{
	protected Log logger = LogFactory.getLog(getClass());

	private String path;
	private Analyzer analyzer;

	private Directory directory;

	private JdbcDaoManager daoManager;

	private List entityHandlers = new ArrayList();

	protected BinaryDataHandler binaryDataHandler;

	private IndexAccessorHolder holder;

	public int getEntryId(String entityType, int entityId) throws Exception
	{
		return getEntryId(ENTITY_UNID_FIELD, entityType+entityId);
	}

	public int getEntryId(String field, String value) throws Exception
	{
		Term term = new Term(field, value);
		TermDocs docs = holder.getReader().termDocs(term); 
		if (!docs.next()) return -1;
		int i = docs.doc();
		if (docs.next()) throw new Exception("Duplicate index entries for field ["+field+"] and value ["+value+"]");
		return i;
	}

	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(path, "The path property has not been set");
		Assert.notNull(analyzer, "The analyzer property has not been set");

		File dir = new File(path);
		if (!dir.exists()) {
			logger.error("********************************************");
			logger.error("* Creating index at "+path+"  *");
			logger.error("********************************************");
			(new IndexWriter(FSDirectory.getDirectory(path), analyzer, true)).close();
		}

		directory = FSDirectory.getDirectory(path);
		if (!IndexReader.indexExists(directory)) {
			logger.error("********************************************");
			logger.error("* No index reported at "+directory+"  *");
			logger.error("********************************************");
		}

		holder = new IndexAccessorHolder(directory, analyzer);
	}  

	public int getMaxDocNumber() throws Exception
	{
		return holder.getReader().maxDoc();
	}

	public Document getEntry(int entryId) throws Exception
	{
		IndexReader reader = holder.getReader();
		if (reader.isDeleted(entryId)) return null;
		return reader.document(entryId);
	}

	public Document getEntry(String entityType, int entityId) throws Exception
	{
		int entryNum = getEntryId(entityType, entityId);
		if (entryNum<0) return null;
		return getEntry(entryNum);
	}

	public String getEntityTypeForClass(Class type) throws Exception
	{
		return getDaoManager().getTypeNamePathForClass(type);
	}

	public Object getEntity(Document doc) throws Exception
	{
		String typeNamePath = doc.getField(EntityIndex.ENTITY_TYPE_FIELD).stringValue();
		String typeName = typeNamePath.substring(typeNamePath.lastIndexOf('/')+1);

		AbstractJdbcDaoSupport dao = getDaoManager().getDaoForTypeName(typeName);
		if (dao==null) throw new Exception("Cannot find entity class for entity type ["+typeNamePath+"]");
		Class type = getDaoManager().getEntityClassForTypeName(typeName);

		int id = Integer.parseInt(doc.getField(EntityIndex.ENTITY_ID_FIELD).stringValue());

		try {
			if ("User".equalsIgnoreCase(typeName)) {
				return ((UserDaoImpl)dao).getUserForId(id);
			} else {
				return dao.getEntity(type, new Object[] {new Integer(id)});
			}
		}
		catch (ElementNotFoundException x) {
			return null;
		}
	}    

	public synchronized void beginUpdate() throws Exception
	{
		holder.bindCurrentThreadForUpdate();
	}

	public void abortUpdate()
	{
		//TODO Take some account of the fact that update has failed
		try {
			endUpdate(false);
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}

	public synchronized void endUpdate(boolean optimize) throws Exception
	{
		try {
			if (optimize) holder.getWriter().optimize();
		}
		finally {
			holder.releaseCurrentThreadForUpdate();
			holder.closeIndexSearcher();
		}
	}

	public void addEntry(String entityType, int entityId, String content, String title, String description, Date created, 
			Date modified, Map dataFields, String userFirstName, String userLastName, String tagText) throws Exception
			{
		Document doc = new Document();

		doc.add(new Field(ENTITY_TYPE_FIELD, entityType, Field.Store.YES, Field.Index.UN_TOKENIZED));
		doc.add(new Field(ENTITY_ID_FIELD, ""+entityId, Field.Store.YES, Field.Index.UN_TOKENIZED));
		doc.add(new Field(ENTITY_UNID_FIELD, entityType+entityId, Field.Store.YES, Field.Index.UN_TOKENIZED));

		if ("User".equals(entityType)) {
			doc.add(new Field(USER_FIRST_NAME_FIELD, userFirstName, Field.Store.YES, Field.Index.UN_TOKENIZED));
			doc.add(new Field(USER_LAST_NAME_FIELD, userLastName, Field.Store.YES, Field.Index.UN_TOKENIZED));
		}

		if ("Tag".equals(entityType)) {
			doc.add(new Field(TAG_TEXT_FIELD, tagText, Field.Store.YES, Field.Index.UN_TOKENIZED));
		}

		doc.add(new Field(CONTENT_FIELD, content+" "+(title==null?"":title)+" "+(description==null?"":description), Field.Store.NO, Field.Index.TOKENIZED));

		//TODO store these fields or not depending on a property of this index 
		if (title!=null) doc.add(new Field(TITLE_FIELD, title, Field.Store.NO, Field.Index.TOKENIZED));
		if (description!=null) doc.add(new Field(DESCRIPTION_FIELD, description, Field.Store.NO, Field.Index.TOKENIZED));

		DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		if (created!=null) doc.add(new Field(DATE_FIELD, fmt.format(created), Field.Store.YES, Field.Index.UN_TOKENIZED));
		if (modified!=null) doc.add(new Field(DATE_MODIFIED_FIELD, fmt.format(modified), Field.Store.YES, Field.Index.UN_TOKENIZED));

		if (dataFields!=null) {
			Iterator i = dataFields.entrySet().iterator();
			while (i.hasNext()) {
				Map.Entry e = (Map.Entry)i.next();
				String name = (String)e.getKey();
				//TODO check the standard field names are not present in the map
				doc.add(new Field(name, e.getValue().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED));
			}
		}

		holder.getWriter().addDocument(doc);
			}

	public void removeEntry(String entityType, int entityId) throws Exception
	{
		int entryNum = getEntryId(entityType, entityId);
		holder.getReader().deleteDocument(entryNum);
	}


	public final synchronized EntityIndexSearcher getIndexSearcher() throws Exception
	{        
		return createIndexSearcher(holder.getIndexSearcher());
	}

	public void onThreadBegin() throws Exception
	{
	}

	public void onThreadEnd() throws Exception
	{
		if (holder.isThreadBoundForRead())
			holder.releaseCurrentThreadForRead();
		if (holder.isThreadBoundForUpdate())
			holder.releaseCurrentThreadForUpdate();
	}

	public void shutdown()
	{
		holder.shutdown();
	}

	protected EntityIndexSearcher createIndexSearcher(IndexSearcher searcher) throws Exception
	{
		return new EntityIndexSearcher(this, searcher);
	}

	public EntityHandler getEntityHandlerForClass(Class type) throws Exception
	{
		for (int n=0; n<entityHandlers.size(); n++) {
			EntityHandler handler = (EntityHandler)entityHandlers.get(n);
			if (handler.supports(type)) return handler;
		}
		return null;
	}

	public boolean isOpenForUpdate() 
	{
		return holder.isOpenForUpdate();
	}

	private static class IndexAccessorHolder
	{
		private Directory m_directory;
		private Analyzer m_analyzer;

		private boolean m_searcherClosePending = false;
		private int m_indexSearcherReferenceCount = 0;
		private IndexSearcher m_indexSearcher;

		private boolean m_boundForUpdate = false;
		private ThreadLocal<String> m_threadBoundFlag = new ThreadLocal<String>();

		private IndexSearcher m_updateSearcher;
		private IndexReader m_updateReader;
		private IndexWriter m_updateWriter;

		private boolean shuttingDown = false;

		protected Log logger = LogFactory.getLog(getClass());

		public IndexAccessorHolder(Directory directory, Analyzer analyzer) throws Exception
		{
			m_directory = directory;
			m_analyzer = analyzer;
		}

		public synchronized void bindCurrentThreadForRead() 
		{
			if (shuttingDown) throw new ShutdownException();
			if (isThreadBoundForUpdate()) throw new IllegalStateException("Thread has index open for update");
			if (isThreadBoundForRead()) return;
			m_indexSearcherReferenceCount++;
			m_threadBoundFlag.set("R");
		}

		public synchronized void releaseCurrentThreadForRead() 
		{
			if (!isThreadBoundForRead()) throw new IllegalStateException("Thread has not opened the index for read");
			m_indexSearcherReferenceCount--;
			m_threadBoundFlag.set(null);
			if (m_searcherClosePending) closeIndexSearcher();
			Assert.isTrue(m_indexSearcherReferenceCount>=0);
		}

		public synchronized void bindCurrentThreadForUpdate() 
		{
			if (shuttingDown) throw new ShutdownException();
			if (m_boundForUpdate) throw new IllegalStateException("Another thread already has the index open for update");
			if (isThreadBoundForRead()) throw new IllegalStateException("Thread has index open for read");
			m_boundForUpdate = true;
			m_threadBoundFlag.set("U");
			logger.info("Thread bound for update");
		}

		public synchronized void releaseCurrentThreadForUpdate() 
		{
			if (!isThreadBoundForUpdate()) throw new IllegalStateException("Thread has not opened the index for update");
			try {
				closeReader();
				closeWriter();
			}
			finally {
				m_boundForUpdate = false;
				m_threadBoundFlag.set(null);
				logger.info("Thread released for update");
			}
		}

		public boolean isThreadBound()
		{
			String s = m_threadBoundFlag.get();
			return  s!=null;
		}

		public boolean isThreadBoundForRead()
		{
			String s = m_threadBoundFlag.get();
			return  "R".equals(s);
		}

		public boolean isThreadBoundForUpdate()
		{
			String s = m_threadBoundFlag.get();
			return  "U".equals(s);
		}

		public synchronized final IndexSearcher getIndexSearcher() throws Exception
		{
			if (shuttingDown) throw new ShutdownException();
			if (m_indexSearcher==null) {
				m_indexSearcher = new IndexSearcher(m_directory);
				logger.debug("Opened index searcher");
			}
			bindCurrentThreadForRead();
			return m_indexSearcher;
		}

		public synchronized final IndexReader getReader() throws Exception
		{
			if (shuttingDown) throw new ShutdownException();

			if (!isThreadBoundForUpdate()) {
				logger.debug("Thread is not bound for update so acquiring search reader");
				return getIndexSearcher().getIndexReader();
			}

			logger.debug("Thread is bound for update so acquiring update reader");

			closeWriter();
			if (m_updateReader==null) {
				m_updateReader = IndexReader.open(m_directory);
				logger.debug("Opened update reader");
			}

			return m_updateReader;
		}

		public synchronized final IndexWriter getWriter() throws Exception
		{
			if (shuttingDown) throw new ShutdownException();

			if (!isThreadBoundForUpdate())
				throw new IllegalStateException("Index has not been opened for update");

			closeReader();
			if (m_updateWriter==null) {
				m_updateWriter = new IndexWriter(m_directory, m_analyzer, false);
				logger.debug("Opened update writer");
			}
			return m_updateWriter;
		}


		public synchronized void closeIndexSearcher() 
		{
			if (m_indexSearcher==null) return;

			if (m_indexSearcherReferenceCount>0) {
				logger.warn("*******************************************");
				logger.warn("Index searcher is not current but is pinned by reference count "+m_indexSearcherReferenceCount);
				logger.warn("*******************************************");
				m_searcherClosePending = true;
				return;
			}

			try {
				m_indexSearcher.close();
				logger.debug("Closed index searcher");
			}
			catch (Exception x) {
				logger.error("", x);
			}
			finally {
				m_indexSearcher = null;
				m_searcherClosePending = false;
			}
		}

		public synchronized void closeReader()
		{
			if (!isThreadBoundForUpdate())
				throw new IllegalStateException("Index has not been opened for update");

			if (m_updateReader==null) return;
			try {
				m_updateReader.close();
				logger.debug("Closed update reader");
			}
			catch (Exception x) {
				logger.error("******Error closing index reader", x);
			}
			finally {
				m_updateReader = null;
				logger.debug("Update reader cleared");
			}
		}

		public synchronized void closeWriter() 
		{
			if (!isThreadBoundForUpdate())
				throw new IllegalStateException("Index has not been opened for update");

			if (m_updateWriter==null) return;
			try {
				m_updateWriter.close();
				logger.debug("Closed update writer");
			}
			catch (Exception x) {
				logger.error("******Error closing index writer", x);
			}
			finally {
				m_updateWriter = null;
				logger.debug("Update writer cleared");
			}
		}

		public boolean isOpenForUpdate()
		{
			return m_updateWriter !=null || m_updateReader != null;
		}

		public void shutdown() 
		{   
			shuttingDown = true;
		}

		public final boolean isShuttingDown()
		{
			return shuttingDown;
		}
	}

	public final void setDirectory(Directory directory)
	{
		this.directory = directory;
	}
	public final void setAnalyser(Analyzer a)
	{
		this.analyzer = a;
	}


	public final String getPath()
	{
		return path;
	}
	public final void setPath(String indexPath)
	{
		this.path = indexPath;
	}
	public final void setAnalyzer(Analyzer analyzer)
	{
		this.analyzer = analyzer;
	}

	public void onApplicationEvent(ApplicationEvent event)
	{
		if (event instanceof ContextClosedEvent) {
			try {
				logger.info("Closing index on context close");
				holder.shutdown();
			}
			catch (Exception x) {
				logger.error("", x);
			}
		}
	}

	public final void setDaoManager(JdbcDaoManager daoManager)
	{
		this.daoManager = daoManager;
	}

	public final void setEntityHandlers(List entityHandlers)
	{
		this.entityHandlers = entityHandlers;
	}

	public final BinaryDataHandler getBinaryDataHandler()
	{
		return binaryDataHandler;
	}

	public final void setBinaryDataHandler(BinaryDataHandler binaryDataHandler)
	{
		this.binaryDataHandler = binaryDataHandler;
	}

	public final Analyzer getAnalyzer()
	{
		return analyzer;
	}

	public final JdbcDaoManager getDaoManager()
	{
		return daoManager;
	}
}