package com.era.community.admin.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="BPRM"
 * 
 */
public class BusinessParam extends CecAbstractEntity
{

    public static final String CATEGORY_STATIC_TEXT = "text";
    public static final String CATEGORY_HELP = "help";
    public static final String CATEGORY_EMAIL= "email";

    
    /** 
     * @column varchar(150) not null with default
     */
    protected String Name;
    
    /**
     * @column varchar(150) not null with default
     */
    protected String Category;
    
    /**
     * @column varchar(150) not null with default
     */
    protected String Description;
    
    /**
     * @column long varchar 
     */
    protected String Value;
    
          
    /*
     * Injected dao reference.
    */
    protected BusinessParamDao dao;
    
       public String getCategory()
    {
        return Category;
    }
    
    public void setCategory(String category)
    {
        Category = category;
    }
    
    public String getName()
    {
        return Name;
    }
    
    public void setName(String name)
    {
        Name = name;
    }
    
        
    public boolean isReadAllowed(UserEntity user)
    {
        return true;
    }
    
    public boolean isWriteAllowed(UserEntity user)
    {
        if (user==null) return false;
        return false;
    }
        
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
    
    public void setDao(BusinessParamDao dao)
    {
        this.dao = dao;
    }

    public final String getValue()
    {
        return Value;
    }

    public final void setValue(String value)
    {
        Value = value;
    }

    public final String getDescription()
    {
        return Description;
    }

    public final void setDescription(String description)
    {
        Description = description;
    }
  

}
