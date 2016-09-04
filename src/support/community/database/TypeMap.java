package support.community.database;

import java.util.*;

/**
 * 
 * 
 */
public class TypeMap 
{
    private Map<Object, TypeMapEntry> types = new HashMap<Object, TypeMapEntry>(97); 
    private Set<String> allPropertyNames = new TreeSet<String>();
    private List<Class> allClasses = new ArrayList<Class>(8);
    private List<String> allTypeNames = new ArrayList<String>(8);

    public TypeMap(List entries) throws Exception
    {  
        for (int n=0; n<entries.size(); n++) add((TypeMapEntry)entries.get(n));
    }
    
    void add(String name, Class clazz, String[] props) throws Exception
    {
        add(new TypeMapEntry(name, clazz, props));
    }
    
    void add(TypeMapEntry e) throws Exception
    {
        if (types.containsKey(e.getName()))
            throw new Exception("Duplicate type name "+e.getName());
        if (types.containsKey(e.getMappedClass()))
            throw new Exception("Duplicate class name "+e.getClass());
        types.put(e.getName(), e);
        types.put(e.getMappedClass(), e);
        allClasses.add(e.getMappedClass());
        allTypeNames.add(e.getName());
        String[] a = e.getPersistedProperties();
        for (int n=0; n<a.length; n++)
            allPropertyNames.add(a[n]);
    }
    
    public Class getPrimaryEntityClass()
    {
        return allClasses.get(0);
    }
        
    public List<String> getAllTypeNamesForClass(Class<?> type)
    {
        List<String> list = new ArrayList<String>(8);
        for (int n=0; n<allClasses.size(); n++) {
            Class<?> c = allClasses.get(n);            
            if (c.isAssignableFrom(type)) list.add(allTypeNames.get(n));
        }
        return list;
    }
    
    int numberOfTypes()
    {
        if (types.size()==0) return 0;
        return types.size()/2;
    }
    
    Set<String> getAllPropertyNames()
    {
        return allPropertyNames;
    }
    
    TypeMapEntry get(String name)
    {
        return types.get(name);
    }
    
    TypeMapEntry get(Class type)
    {
        return types.get(type);
    }

    TypeMapEntry getSoleEntry()
    {
        if (numberOfTypes()!=1) throw new RuntimeException("");
        return types.values().iterator().next();
    }
    
    public boolean containsType(Class type) 
    {
        return types.containsKey(type);
    }

    public boolean containsName(String name) 
    {
        return types.containsKey(name);
    }

}
