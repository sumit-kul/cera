package support.community.framework.taglib;

import javax.servlet.jsp.*;



/**
 *    <cop:formSection label="Areas of expertise" cssClass="header" lastOnPage="true"  >
 *    
 *      <h2 class="header">Personal details</h2>   
 *      
 *                
 *    
 *    
 */
public class FormSectionTag extends AbstractHtmlElementTag implements FieldGroupTag
{
    /**
     * The title of the section. 
     */
    private String label;
          
    
    /**
     * 
     * 
     */
    protected int doStartTagInternal() throws Exception
    {
        TagWriter tagWriter = getTagWriter();
        
        tagWriter.startTag("h2");
        writeDefaultAttributes(tagWriter);
        
        tagWriter.appendValue(label);
        
        tagWriter.endTag();
 
        return EVAL_PAGE;
    }


    /**
     * 
     * 
     */
    protected int doEndTagInternal() throws Exception
    {
        TagWriter tagWriter = getTagWriter();

        
//     <div class="hr"><hr/></div>
        tagWriter.startTag("div");
        tagWriter.writeAttribute("class", "hr");
        tagWriter.startTag("hr");
        tagWriter.endTag();
        tagWriter.endTag();
        
        return EVAL_PAGE;
    }

    public void beginElement(TagWriter tagWriter, String errorMessage) throws JspException
    {
        tagWriter.startTag("p");
        tagWriter.forceBlock();
    }


    public void beginLabel(TagWriter tagWriter, String errorMessage) throws JspException
    {
    }


    public void endLabel(TagWriter tagWriter, String errorMessage) throws JspException
    {
    }


    public void beginField(TagWriter tagWriter, String errorMessage) throws JspException
    {
    }


    public void endField(TagWriter tagWriter, String errorMessage) throws JspException
    {
    }


    public void endElement(TagWriter tagWriter, String errorMessage) throws JspException
    {
        tagWriter.endTag();
    }

    public final void setLabel(String label)
    {
        this.label = label;
    }

}
