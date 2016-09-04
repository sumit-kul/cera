package support.community.framework;

import java.util.*;

import org.acegisecurity.*;
import org.acegisecurity.intercept.*;

/**
 * 
 */
public class RunAsServerDefinitionSource implements ObjectDefinitionSource
{
    private ConfigAttributeDefinition attribute;
    private List<ConfigAttributeDefinition> attributeList;

    public RunAsServerDefinitionSource() 
    {
        ConfigAttributeEditor configAttribEd = new ConfigAttributeEditor();
        configAttribEd.setAsText("RUN_AS_SERVER");
        attribute = (ConfigAttributeDefinition) configAttribEd.getValue();
        attributeList = new ArrayList<ConfigAttributeDefinition>(1);
        attributeList.add(attribute);
    }

    public ConfigAttributeDefinition getAttributes(Object object) throws IllegalArgumentException
    {
        return attribute;
    }

    public Iterator getConfigAttributeDefinitions()
    {
        return attributeList.iterator();
    }

    public boolean supports(Class clazz)
    {
        return (RunAsServerCallback.class.isAssignableFrom(clazz));
    }

}
