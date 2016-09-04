
package support.community.framework.taglib;

import javax.servlet.jsp.*;

/**
 * 
 */
public interface OptionContainer
{
    public void addOption(String label, Object value, String cssClass) throws JspException;
}
