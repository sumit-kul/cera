package com.era.community.customtag;

import javax.servlet.jsp.JspWriter;

import support.community.framework.AppRequestContext;
import support.community.framework.taglib.AbstractHtmlElementTag;
import support.community.framework.taglib.TagWriter;

public class BusinessParamTag extends AbstractHtmlElementTag
{
    private String parameterName;
    private String defaultText = "";
    private boolean inPlaceEdit = false;

    
    private String editType = "textarea"; //Alternatively "input"
    
    protected int doStartTagInternal() throws Exception
    {

        AppRequestContext context = (AppRequestContext)getAppRequestContext();

        boolean editable =     context.isUserSysAdmin() && inPlaceEdit;

        String value =context.getTextParameter(parameterName);
        
        TagWriter tagWriter = getTagWriter();
        
        if (editable) {
            tagWriter.startTag("span");                                             // onMouseOver - this.style.border = 1px green
                                                                                               // onMouseOut - this.style.border = 0px green
            tagWriter.writeAttribute("class", "editText-"+editType);
            tagWriter.writeAttribute("id", parameterName);
            
            tagWriter.writeAttribute("onMouseOver", "this.style.color = '#708090';");
            tagWriter.writeAttribute("onMouseOut",  "this.style.color  = '#000000'; ");
      
            
            tagWriter.appendValue(value==null?defaultText:value);
             tagWriter.endTag();
        }
        else
        {
              JspWriter out = pageContext.getOut();   
              
              out.print((value==null?defaultText:value));
        }

        return SKIP_BODY;
    }    
        
    public void setParameterName(String name)
    {
        this.parameterName = name;
    }

    public final void setDefaultText(String defaultText)
    {
        this.defaultText = defaultText;
    }

    public final void setInPlaceEdit(boolean inPlaceEdit)
    {
        this.inPlaceEdit = inPlaceEdit;
    }

    public final void setEditType(String editType)
    {
        this.editType = editType;
    }

}
