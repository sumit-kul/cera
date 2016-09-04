package support.community.lucene.index;

import java.io.StringReader;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import support.community.database.BlobData;
import support.community.lucene.parse.html.HtmlExtractor;

import com.era.community.communities.dao.CommunityFinder;

public abstract class EntityHandler 
{
	protected Log logger = LogFactory.getLog(getClass());
	private HtmlExtractor htmlExtractor = new HtmlExtractor();
	protected CommunityFinder communityFinder;

	public abstract boolean supports(Class type) throws Exception;
	public abstract int getEntityId(Object entity) throws Exception;
	public abstract String getContent(Object entity, BinaryDataHandler handler, boolean fast) throws Exception;
	public abstract String getTitle(Object entity) throws Exception;
	public abstract String getDescription(Object entity) throws Exception;
	public abstract Date getDate(Object entity) throws Exception;
	public abstract Date getModified(Object entity) throws Exception;
	public abstract Map<String, Object> getDataFields(Object entity) throws Exception;
	public abstract String getLink(Object entity, String query, EntityIndex index) throws Exception;

	public int getCurrentIndexEntry(Object entity, EntityIndex index) throws Exception
	{
		return index.getEntryId(index.getEntityTypeForClass(entity.getClass()), getEntityId(entity));
	}

	public String getTitle2(Object entity, String query, EntityIndex index) throws Exception 
	{ 
		return null; 
	}

	public String getTitle3(Object entity, String query, EntityIndex index) throws Exception 
	{ 
		return null; 
	}

	public String getUserFirstName(Object entity) throws Exception 
	{ 
		return null; 
	}

	public String getUserLastName(Object entity) throws Exception 
	{ 
		return null; 
	}

	public String getTagText(Object entity) throws Exception 
	{ 
		return null; 
	}

	protected String htmlToText(String html) throws Exception
	{
		if(html == null)return "";
		logger.debug("html start");
		logger.debug(html);
		logger.debug("html end");
		if (!html.toLowerCase().contains("<body>")) html = "<body>"+html+"</body>";
		if (!html.toLowerCase().contains("<html>")) html = "<html>"+html+"</html>";
		html = htmlExtractor.extractText(new StringReader(html));
		logger.debug("plain start");
		logger.debug(html);
		logger.debug("plain end");
		return html;
	}

	public void getContentForFile(String fileName, BlobData file, String contentType, BinaryDataHandler handler, StringBuffer buf, boolean fast) throws Exception
	{
		if (file==null||file.getLength()==0) return ;
		if (fast&&file.getLength()>100*1024) return ;
		logger.debug("Getting content for file "+fileName+" of type "+contentType);
		if (!handler.supports(contentType)) {
			logger.info("No handler for type ["+contentType+"]");
			return ;
		}
		buf.append(handler.extractText(file.getStream(), contentType));
		buf.append(" ");
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}
}