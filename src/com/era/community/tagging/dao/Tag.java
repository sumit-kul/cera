package com.era.community.tagging.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
*
* @entity name="TAG" 
*
* @entity.index name="01" unique="no" columns="CommunityId"
* @entity.index name="02" unique="no" columns="ParentId"
* @entity.index name="03" unique="yes" columns="PostId,ParentId,TagText" cluster="yes"  
* 
* @entity.foreignkey name="01" columns="CommunityId" target="COMM" ondelete="cascade"  
* @entity.foreignkey name="02" columns="PosterId" target="USER" ondelete="restrict"  
*/
public class Tag extends CecAbstractEntity {
	
    /**
     * @column integer
     */
    protected Integer CommunityId;
	
    /**
     * @column integer not null
     */
    protected int PosterId;

    /**
     * @column integer not null
     */
    protected int ParentId;
    
    /**
     * @column varchar(60) not null with default
     */
    protected String ParentType;
        
    /**
     * @column varchar(150) not null with default
     */
    protected String TagText;

    /*
     * Injected references.
     */
    protected TagDao dao;             
    
    /**
     * Update or insert this entity in the database.
     */
    public void update() throws Exception
    {
       dao.store(this); 
    }
    
    /** 
     *  Delete this entity from the database.
     */
    public void delete() throws Exception
    {
        dao.delete(this);
    } 
    
	public Integer getCommunityId() {
		return CommunityId;
	}

	public void setCommunityId(Integer communityId) {
		CommunityId = communityId;
	}

	public int getParentId() {
		return ParentId;
	}

	public void setParentId(int parentId) {
		ParentId = parentId;
	}

	public String getParentType() {
		return ParentType;
	}

	public void setParentType(String parentType) {
		ParentType = parentType;
	}

	public int getPosterId() {
		return PosterId;
	}

	public void setPosterId(int posterId) {
		PosterId = posterId;
	}

	public String getTagText() {
		return TagText;
	}

	public void setTagText(String tagText) {
		TagText = tagText;
	}	
	
    public boolean isReadAllowed(UserEntity user) throws Exception
    {
        if (user==null) return false;
        return true;        
   }
    
    public boolean isWriteAllowed(UserEntity user) throws Exception
    {
        if (user==null) return false;
        if (getPosterId()==user.getId()) return true;      
        return false;
    }
    
	public void setDao(TagDao dao) {
		this.dao = dao;
	}
                        
}
