package support.community.framework.taglib;

import support.community.application.*;
import support.community.framework.*;


/**
 *
 */
public class BacklinkTag extends AbstractHtmlElementTag 
{
    private String m_text = "Back";
      
	/**
     * 
     * 
	 */
    protected int doStartTagInternal() throws Exception 
    {
        TagWriter tagWriter = getTagWriter();

        AppRequestContext arc =  getAppRequestContext();
        if (arc==null) return EVAL_PAGE;
        
        BacklinkStack backlinks = arc.getBacklinkStack();
        if (backlinks==null||backlinks.top()==null) return EVAL_PAGE;
        
        tagWriter.startTag("a");
        tagWriter.writeAttribute("href", backlinks.top());
        writeDefaultAttributes(tagWriter);
   
        tagWriter.appendValue(getText());
   
        tagWriter.endTag();
        
		return EVAL_PAGE;
	}
    
   
    public String getText()
    {
        return m_text;
    }
    public void setText(String text)
    {
        m_text = text;
    }
}
