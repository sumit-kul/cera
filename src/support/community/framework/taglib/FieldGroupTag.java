
package support.community.framework.taglib;

import javax.servlet.jsp.*;

/**
 * 
 */
public interface FieldGroupTag
{
    public void beginElement(TagWriter tagWriter, String errorMessage) throws JspException;
    public void beginLabel(TagWriter tagWriter, String errorMessage) throws JspException;
    public void endLabel(TagWriter tagWriter, String errorMessage) throws JspException;
    public void beginField(TagWriter tagWriter, String errorMessage) throws JspException;
    public void endField(TagWriter tagWriter, String errorMessage) throws JspException;
    public void endElement(TagWriter tagWriter, String errorMessage) throws JspException;
}
