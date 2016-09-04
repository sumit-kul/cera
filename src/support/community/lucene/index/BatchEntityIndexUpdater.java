
package support.community.lucene.index;

import java.util.*;

import org.apache.lucene.document.*;


/**
 * 
 */
public class BatchEntityIndexUpdater 
{
    private EntityIndex index;
    
    private int commitCount = 50;
    
    private List<EntityIndexEntry> deletions;
    
    private List<EntityIndexEntry> additions;
    
    public BatchEntityIndexUpdater(EntityIndex index)
    {
        this.index = index;        
        this.deletions = new ArrayList<EntityIndexEntry>(commitCount);
        this.additions = new ArrayList<EntityIndexEntry>(commitCount);
    }
    
    public void addUpdate(EntityIndexEntry update) throws Exception
    {
        additions.add(update);
        if ((additions.size()+deletions.size())>=commitCount) flush(false);
    }

    public void addDeletion(EntityIndexEntry entry) throws Exception
    {
        deletions.add(entry);
        if ((additions.size()+deletions.size())>=commitCount) flush(false);
    }

    public void addDeletion(String entityType, int entityId) throws Exception
    {
        EntityIndexEntry entry = new EntityIndexEntry();
        entry.setEntityType(entityType);
        entry.setId(entityId);
        addDeletion(entry);
    }

    public void addDeletion(int entryNumber) throws Exception
    {
        addDeletion(index.getEntry(entryNumber));
    }

    public void addDeletion(Document doc) throws Exception
    {
        EntityIndexEntry entry = new EntityIndexEntry();
        entry.setEntityType(doc.getField(EntityIndex.ENTITY_TYPE_FIELD).stringValue());
        entry.setId(Integer.parseInt(doc.getField(EntityIndex.ENTITY_ID_FIELD).stringValue()));
        addDeletion(entry);
    }

    public void flush(boolean optimize) throws Exception
    {
        try {
            for (int n=0; n<deletions.size(); n++) {
                EntityIndexEntry u = deletions.get(n);
                index.removeEntry(u.getEntityType(), u.getId());
            }
            for (int m=0; m<additions.size(); m++) {
                EntityIndexEntry u = additions.get(m);
                String fName = u.getUserFirstName();
                if (fName != null ) fName = fName.toLowerCase();
                String lName = u.getUserLastName();
                if (lName != null ) lName = lName.toLowerCase();
                String tText = u.getTagText();
                if (tText != null ) tText = tText.toLowerCase();
                index.addEntry(u.getEntityType(), u.getId(), u.getContent(), u.getTitle(), u.getDescription(), u.getCreatedDate(), u.getModifiedDate(), u.getDataFields(), fName, lName, tText);
            }
        }
        finally {
            deletions.clear();
            additions.clear();
        }        
    }
    
    public final EntityIndex getIndex()
    {
        return index;
    }
    public final void setIndex(EntityIndex index)
    {
        this.index = index;
    }
}
