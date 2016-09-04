
package support.community.framework.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.util.*;


/**
 * 
 * 
 */
public class TinyMceEditorTag extends AbstractHtmlInputElementTag 
{
	/**
	 * The name of the '<code>rows</code>' attribute.
	 */
	public static final String ROWS_ATTRIBUTE = "rows";

	/**
	 * The name of the '<code>cols</code>' attribute.
	 */
	public static final String COLS_ATTRIBUTE = "cols";

	/**
	 * The value of the '<code>rows</code>' attribute.
	 */
	private String rows;

	/**
	 * The value of the '<code>cols</code>' attribute.
	 */
	private String cols;
    
    private String toolbarItems ="bold,italic,underline,separator,bullist,numlist,separator,image,separator,link,unlink,separator,undo,redo,separator,code" ;
    
    private String plugins ;
    
    private String userData;
    

	/**
	 * Sets the value of the '<code>rows</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setRows(String rows) {
		this.rows = rows;
	}

	/**
	 * Gets the value of the '<code>rows</code>' attribute.
	 * May be a runtime expression.
	 */
	protected String getRows() {
		return this.rows;
	}

	/**
	 * Sets the value of the '<code>cols</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setCols(String cols) {
		this.cols = cols;
	}

	/**
	 * Gets the value of the '<code>cols</code>' attribute.
	 * May be a runtime expression.
	 */
	protected String getCols() {
		return this.cols;
	}

    
    
      /**
    .
*/
public int writeStartTagEditMode(TagWriter tagWriter) throws JspException
{    
  TagWriter.SafeWriter out = tagWriter.getWriter();
    
  out.print("<script language=\"javascript\" type=\"text/javascript\" src=\"tiny_mce/tiny_mce.js\"></script>\n");
    
    out.print("<div  style=\"width:98%\">");
       
    tagWriter.startTag("textarea");
    writeDefaultAttributes(tagWriter);
    tagWriter.writeOptionalAttributeValue("cols", getCols());
    tagWriter.writeOptionalAttributeValue("rows", getRows());
    tagWriter.forceBlock();
    
    emitContent(tagWriter);
    
    tagWriter.endTag();
    
    out.print("</div>\n");        

    out.println("<script type=\"text/javascript\" >");
    
    out.println(" tinyMCE.init({");

    if (plugins!=null)
        out.println("plugins : \""+plugins+"\" ,");
        
    if (userData!=null)
        out.println("csq_user_data : "+userData+" ,");
           
    //out.println("onchange_callback : \"onEditorChange\",");
    out.println("mode : \"textareas\",");
    out.println("theme : \"advanced\",");  
    out.println("invalid_elements : \"\",");  
    out.println("document_base_url : \"/\",");
    out.println("theme_advanced_buttons1 : \""+toolbarItems+"\",");
    out.println("theme_advanced_buttons2 : \"\",");
    out.println("theme_advanced_buttons3 : \"\",");
    out.println("theme_advanced_toolbar_location : \"top\",");
    out.println("theme_advanced_toolbar_align : \"left\",");
    out.println("theme_advanced_path_location : \"bottom\",");
    out.println("extended_valid_elements : \"object[width|height],embed[src|type|wmode|width|height],em,param[name|value],a[name|href|target|title|onclick],img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name],hr[class|width|size|noshade],font[face|size|color|style],span[class|align|style]\"");

    out.println("});");
  
    out.print("</script>\n");

    return EVAL_BODY_INCLUDE;
}

/**
    .  
*/
public int writeEndTagEditMode(TagWriter tagWriter) throws JspException
{
    JspWriter out = pageContext.getOut();   

    return EVAL_PAGE;
}


/**
.
*/
public int writeStartTagReadMode(TagWriter tagWriter) throws JspException
{    
  TagWriter.SafeWriter out = tagWriter.getWriter();

out.print("<div class=\"richtext-wrapper richtext\" style=\"padding:6px 2px 2px 6px;width:100%;\">");
out.print("<div id=\"content\"");
out.print(" style=\"margin:0;padding:0;");
//  if (bSplitBody.booleanValue()==false) 
    out.print("width:99%;");
//  else 
//      out.print("width:"+AppConfig.getInstance().getSetting("ui.content.width", "410px")+";");
out.print("\">");

emitContent(tagWriter);

out.print("</div>");
out.print("</div>");      

return EVAL_BODY_INCLUDE;
}

/**
.
*/
private void emitContent(TagWriter tagWriter) throws JspException
{    
  TagWriter.SafeWriter out = tagWriter.getWriter();
  String s = ObjectUtils.getDisplayString(getBoundValue());
  tagWriter.getWriter().print(s);
  //tagWriter.getWriter().print(s==null||s.trim().length()==0?"<p></p>":s);
}

/**
    .
*/
public int writeEndTagReadMode() throws Exception
{
    JspWriter out = pageContext.getOut();   

    return EVAL_PAGE;
}

public final void setToolbarItems(String items)
{
    this.toolbarItems = items;
}

public final void setUserData(String userData)
{
    this.userData = userData;
}

public final void setPlugins(String plugins)
{
    this.plugins = plugins;
}


}
