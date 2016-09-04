package support.community.database;


public class TypeMapEntry
{
    private String discriminator;
    private Class clazz;
    private String[] persistedProperties;
    
    public TypeMapEntry(String s, Class c, String[] a) 
    {
        discriminator = s;
        clazz = c;
        persistedProperties = new String[a.length];
        for (int n=0; n<a.length; n++) {
            String prop = a[n];
            persistedProperties[n] = prop.substring(0, 1).toLowerCase()+prop.substring(1);
        }
    }
    
    public final Class getMappedClass()
    {
        return clazz;
    }
    public final String getName()
    {
        return discriminator;
    }
    public final String[] getPersistedProperties()
    {
        return persistedProperties;
    }
}