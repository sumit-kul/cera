
package support.community.lucene.index;

import java.util.*;

import support.community.lucene.search.*;

import org.apache.lucene.analysis.*;
import org.apache.lucene.document.*;

/**
 * Represents a lucene index suitable for indexing persistent entities stored 
 * in a relational database. The entries in such an index will always include
 * fields that identify the entity type and entity id.    
 */
public interface EntityIndex
{    
    public static final String ENTITY_TYPE_FIELD  =  "EntityType";
    public static final String ENTITY_ID_FIELD  =  "EntityId";
    public static final String ENTITY_UNID_FIELD  =  "EntityUnid";
    public static final String CONTENT_FIELD  =  "Content";
    public static final String TITLE_FIELD  =  "Title";
    public static final String DESCRIPTION_FIELD  =  "Description";
    public static final String DATE_FIELD  =  "Date";
    public static final String DATE_MODIFIED_FIELD  =  "Modified";
    
    public static final String USER_FIRST_NAME_FIELD  =  "UserFirstName";
    public static final String USER_LAST_NAME_FIELD  =  "UserLastName";
    
    public static final String TAG_TEXT_FIELD  =  "TagText";

    public int getMaxDocNumber() throws Exception;
    
    public int getEntryId(String entityType, int entityId) throws Exception;
    public int getEntryId(String field, String value) throws Exception;

    public Document getEntry(int entryId) throws Exception;
    public Document getEntry(String entityType, int entityId) throws Exception;
    
    public String getEntityTypeForClass(Class type) throws Exception;
    public Object getEntity(Document doc) throws Exception;

    public void beginUpdate() throws Exception;
    public void abortUpdate() ;
    public void endUpdate(boolean optimize) throws Exception;
    public boolean isOpenForUpdate();
    
    public void addEntry(String entityType, int entityId, String content, String title, String description, Date created, 
    		Date modified, Map dataFields, String userFirstName, String userLastName, String tagText) throws Exception;
    public void removeEntry(String entityType, int entityId) throws Exception;

    public EntityIndexSearcher getIndexSearcher() throws Exception;
 
    public Analyzer getAnalyzer();
    public EntityHandler getEntityHandlerForClass(Class type) throws Exception;
    public BinaryDataHandler getBinaryDataHandler();

    public void shutdown();
    
}
