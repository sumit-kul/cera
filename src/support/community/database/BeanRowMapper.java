package support.community.database;

import java.beans.*;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.commons.logging.*;
import org.springframework.jdbc.core.*;

public class BeanRowMapper extends ColumnMapRowMapper
{
    protected Log logger = LogFactory.getLog(getClass()); 
    
    private Class m_type;
    private Object m_enclosingInstance;
    private JdbcDaoManager m_daoManager;
    
    public BeanRowMapper(Class type) 
    {
        m_type = type;
        m_enclosingInstance = null;
        m_daoManager = null;
    }
    
    public BeanRowMapper(Class type, Object enclosingInstance) 
    {
        m_type = type;
        m_enclosingInstance = enclosingInstance;
        m_daoManager = null;
    }
    
    BeanRowMapper(Class type, JdbcDaoManager daoManager) 
    {
        m_type = type;
        m_enclosingInstance = null;
        m_daoManager = daoManager;
    }
    
    BeanRowMapper(Class type, Object enclosingInstance ,JdbcDaoManager daoManager) 
    {
        m_type = type;
        m_daoManager = daoManager;
        m_enclosingInstance = enclosingInstance;
    }
    
    public Object mapRow(ResultSet res, int i) throws SQLException
    {
        logger.debug("begin BeanRowMapper.mapRow");
        
        try {
            
            Object o;
            
            Class enclosingClass = m_type.getEnclosingClass();
            
            if (enclosingClass==null || Modifier.isStatic(m_type.getModifiers())) {
                o = m_type.newInstance();
            }
            else {
                if (m_enclosingInstance==null)
                    throw new Exception("No enclosing instance is available for non-static inner class "+m_type.getName());
                Constructor ctor = m_type.getDeclaredConstructor(new Class[] { enclosingClass });
                o = ctor.newInstance(new Object[] { m_enclosingInstance });
            }
            
            if (o instanceof EntityWrapper) 
                populateEntityWrapperBean(o, res, i);
            else
                populateBean(o, res, i);
            
            return o; 
        }
        catch (SQLException x) {
            logger.error("Error instantiating "+m_type, x);
            throw x;
        }
        catch (Exception x) {
            logger.error("Error instantiating "+m_type, x);
            throw new SQLException(x.toString());
        }

    }

    protected String getColumnKey(String s)
    {
        logger.debug("Column name: "+s);
        s = s.toUpperCase();
        logger.debug("Column key: "+s);
        return s;
    }
    
    protected Object getColumnValue(ResultSet res, int i)  throws SQLException
    {
        return super.getColumnValue(res, i);
    }

    @SuppressWarnings("unchecked")
    void populateBean(Object bean, ResultSet res, int rownum) throws Exception
    {
        Map data = (Map) super.mapRow(res, rownum);
        data.put("resultSetIndex", new Integer(rownum)); //useful in listing JSPs
        
        Iterator i = data.keySet().iterator();
        int index = 0;
        while (i.hasNext()) {
            String col = (String)i.next();
            Object value = data.get(col);
            /*if ("".equals(col.trim())) {
            	ResultSetMetaData rsmd = res.getMetaData();
            	col = rsmd.getColumnLabel(index).toUpperCase();
			}*/
            Method m = findSetterMethod(bean.getClass(), col, value==null?null:value.getClass());
            if (m==null) continue;
            //m.setAccessible(true);
            invokeSetterMethod(m, bean, value);
            index++;
        }
        /*if (bean.getClass().getName().toUpperCase().endsWith("DTO")) {
        	ResultSetMetaData rsmd = res.getMetaData();
        	String col = rsmd.getColumnLabel(index).toUpperCase();
        	Object value = data.get(col);
        	Method m = findSetterMethod(bean.getClass(), col, value==null?null:value.getClass());
            if (m != null) invokeSetterMethod(m, bean, value);
		}*/
    }

    void populateEntityWrapperBean(Object bean, ResultSet res, int rownum) throws Exception
    {
        if (m_daoManager==null) throw new Exception("Entity wrapper bean encountered but no dao manager reference available");
        logger.debug("Populating entity wrapper bean "+bean.getClass().getName());
        
        populateBean(bean, res, rownum);

        PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(bean);
        for (int n=0; n<props.length; n++) {
            Class type = props[n].getPropertyType();
            if (!m_daoManager.isEntityType(type)) continue;
            logger.debug("Found property "+props[n].getName()+" of entity type");
            RowMapper mapper = m_daoManager.getRowMapperForType(type);
            Object entity = mapper.mapRow(res, rownum);
            Method m = props[n].getWriteMethod();
            m.invoke(bean, new Object[] { entity });
        }
    }
    
    private void invokeSetterMethod(Method method, Object o, Object value) throws Exception
    {
        logger.error("Invoking "+method+" with value "+(value==null?"[null]":value.getClass().getName()+" ["+value+"]"));
        Class argType = (method .getParameterTypes())[0];
        if (value==null) {
            if (argType.isPrimitive()) throw new Exception("Cannot assign null to property of primitive type. ("+method+")");
            method.invoke(o, new Object[] {value});
            return;
        }
        
        if (argType==boolean.class && value instanceof String) {
            if (value.equals(AbstractJdbcDaoSupport.PSEUDO_BOOLEAN_TRUE_VALUE))
                method.invoke(o, new Object[] { Boolean.TRUE });
            else if (value.equals(AbstractJdbcDaoSupport.PSEUDO_BOOLEAN_FALSE_VALUE))
                method.invoke(o, new Object[] { Boolean.FALSE });
            else
                throw new Exception("Invalid column value "+value+" for boolean property. ("+method+")");
            return;
        }
        method.invoke(o, new Object[] {value});
    }

    private Map<String, Method> _methodMap = new HashMap<String, Method>(89);

    private Method findSetterMethod(Class type, String propName, Class vc) throws Exception
    {
        Method method = (Method)_methodMap.get(type.getName()+"."+propName);  
        if (method!=null) return method;
        
        logger.debug("Looking for property "+propName+" in "+type);
        
        List<Method> found = new ArrayList<Method>(2);
        
        String methodName = "set"+propName.substring(0,1).toUpperCase()+propName.substring(1);
        for (Class c = type; c!=null; c=c.getSuperclass()) {
            Method[] methods = c.getDeclaredMethods();
            for (int n=0; n<methods.length; n++) {
                Method m = methods[n];
                if (!m.getName().equalsIgnoreCase(methodName)) continue;
                Class[] params = m.getParameterTypes();
                if (params.length!=1) continue;
                found.add(m);
            }
        }
                       
        if (found.size()>1)
            throw new NoSuchMethodException("Duplicate method "+methodName+" for "+type);
        
        if (found.isEmpty()) return null;
        
        method = found.get(0);
        _methodMap.put(type.getName()+"."+propName, method);
        return method;
    }
}