package support.community.database;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.RowMapper;

public class JdbcDaoManager implements ApplicationContextAware
{
    protected Log logger = LogFactory.getLog(getClass());
    
    private ApplicationContext appContext;
    
    private Collection<AbstractJdbcDaoSupport> daoList = new ArrayList<AbstractJdbcDaoSupport>(32);

    public void registerDao(AbstractJdbcDaoSupport dao)
    {
        daoList.add(dao);
    }
    
    public RowMapper getRowMapperForType(Class type) 
    {
        return getRowMapperForType(type, null);
    }
    
    public RowMapper getRowMapperForType(Class type, Object enclosingInstance) 
    {
        Iterator i = daoList.iterator();
        while (i.hasNext()) {
        	AbstractJdbcDaoSupport dao = (AbstractJdbcDaoSupport)i.next();
            if (dao.getTypeMap().containsType(type))
                return dao.getRowMapper();
        }
        
        if (Modifier.isAbstract(type.getModifiers()))
           throw new IllegalArgumentException(type.getName() + " is an abstract class.");
        
        return new BeanRowMapper(type, enclosingInstance, this);
    }
    
    public boolean isEntityType(Class type) 
    {
        Iterator i = daoList.iterator();
        while (i.hasNext()) {
        	AbstractJdbcDaoSupport dao = (AbstractJdbcDaoSupport)i.next();
            if (dao.getTypeMap().containsType(type))
                return true;
        }
        return false;
    }

    public AbstractJdbcDaoSupport getDaoForEntityClass(Class type) throws Exception
    {
        Iterator i = daoList.iterator();
        while (i.hasNext()) {
        	AbstractJdbcDaoSupport dao = (AbstractJdbcDaoSupport)i.next();
            if (dao.getTypeMap().containsType(type))
                return dao;
        }
        return null;
    }
    
    public AbstractJdbcDaoSupport getDaoForTypeName(String name) throws Exception
    {
        Iterator i = daoList.iterator();
        while (i.hasNext()) {
        	AbstractJdbcDaoSupport dao = (AbstractJdbcDaoSupport)i.next();
            if (dao.getTypeMap().containsName(name))
                return dao;
        }
        return null;
    }
    
    public Class getEntityClassForTypeName(String name) throws Exception
    {
        Iterator i = daoList.iterator();
        while (i.hasNext()) {
        	AbstractJdbcDaoSupport dao = (AbstractJdbcDaoSupport)i.next();
            if (dao.getTypeMap().containsName(name))
                return dao.getTypeMap().get(name).getMappedClass();
        }
        return null;
    }
    
    public String getTypeNameForClass(Class type) throws Exception
    {
    	AbstractJdbcDaoSupport dao = getDaoForEntityClass(type);
        return dao==null ? null : dao.getTypeMap().get(type).getName();
    }
    
    public String getTypeNamePathForClass(Class type) throws Exception
    {
    	AbstractJdbcDaoSupport dao = getDaoForEntityClass(type);
        if (dao==null) return null;
        
        List<String> list = new ArrayList<String>(8);
        for (Class c=type; c!=Object.class; c=c.getSuperclass()) {
            TypeMapEntry e = dao.getTypeMap().get(c);
            if (e!=null) list.add(e.getName());
        }
        
        StringBuffer buf = new StringBuffer(256);
        for (int n=list.size()-1; n>=0; n--) {
            if (buf.length()>0) buf.append('/');
            buf.append(list.get(n));
        }
        return buf.toString();
    }
       
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        appContext = applicationContext;
    }
}