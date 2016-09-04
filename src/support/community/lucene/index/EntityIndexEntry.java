package support.community.lucene.index;

import java.util.*;

public class EntityIndexEntry  
{
    private String entityType;
    
    private int id;
    private String title;
    private String description;
    private String content;
    
    private Date createdDate;
    private Date modifiedDate;
    
    private String userFirstName;
    private String userLastName;
    
    private String tagText;
    
    public String getTagText() {
		return tagText;
	}
	public void setTagText(String tagText) {
		this.tagText = tagText;
	}
	private Map dataFields;
    
    public final String getContent()
    {
        return content;
    }
    public final void setContent(String content)
    {
        this.content = content;
    }
    public final Date getCreatedDate()
    {
        return createdDate;
    }
    public final void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }
    public final Map getDataFields()
    {
        return dataFields;
    }
    public final void setDataFields(Map dataFields)
    {
        this.dataFields = dataFields;
    }
    public final String getDescription()
    {
        return description;
    }
    public final void setDescription(String description)
    {
        this.description = description;
    }
    public final int getId()
    {
        return id;
    }
    public final void setId(int id)
    {
        this.id = id;
    }
    public final Date getModifiedDate()
    {
        return modifiedDate;
    }
    public final void setModifiedDate(Date modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }
    public final String getTitle()
    {
        return title;
    }
    public final void setTitle(String title)
    {
        this.title = title;
    }
    public final String getEntityType()
    {
        return entityType;
    }
    public final void setEntityType(String entityType)
    {
        this.entityType = entityType;
    }
	/**
	 * @return the userFirstName
	 */
	public String getUserFirstName() {
		return userFirstName;
	}
	/**
	 * @param userFirstName the userFirstName to set
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	/**
	 * @return the userLastName
	 */
	public String getUserLastName() {
		return userLastName;
	}
	/**
	 * @param userLastName the userLastName to set
	 */
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
}
