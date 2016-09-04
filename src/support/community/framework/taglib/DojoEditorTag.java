/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package support.community.framework.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.util.*;

/**
 * 
 * 
 */
public class DojoEditorTag extends AbstractHtmlInputElementTag 
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
    
    private String items ="bold;italic;underline;listGroup;indentGroup;linkGroup" ;

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
    
   out.print("<div id=\"EditorWrapper\" style=\"padding:6px 2px 2px 6px;width:100%;text-align: left;\">");
    
    out.println("<textarea name=\"body\" cols=\"40\" rows=\"10\" id=\"bodyeditor\">");
    emitContent(tagWriter);
    out.println("</textarea>");
    
    out.print("</div>");      

    out.println("<script>");
    
    out.println("var oEdit1 = new InnovaEditor(\"oEdit1\");");
    
    out.println("oEdit1.height=\"350px\";"); 
    out.println("oEdit1.width=\"100%\";");
  
 //   out.println("oEdit1.arrCustomButtons=[[\"CustomLink\",\"toolbarActionUpdate()\",\"Save Changes\",\"btnSave.gif\"],[\"CustomLink1\",\"toolbarActionInsertPageLink()\",\"Internal Page Link\",\"btnInternalLink.gif\"],[\"CustomLink2\",\"toolbarActionInsertFileLink()\",\"Asset Link\",\"btnCustomObject.gif\"],[\"CustomLink3\",\"toolbarActionInsertImage()\",\"Insert image\",\"btnImage.gif\"],[\"CustomLink4\",\"toolbarActionInsertExternalLink()\",\"External Link\",\"btnHyperlink.gif\"],[\"CustomLink5\",\"toolbarActionParagraphStyle()\",\"Paragraph Style\",\"btnForeColor.gif\"],[\"CustomLink6\",\"toolbarActionBlockquote()\",\"Block Quote\",\"btnRTL.gif\"]];");

    out.print("oEdit1.features=[");
//    out.print("\"CustomLink\",\"|\",\"Copy\",\"Paste\",\"|\",\"Bold\",\"|\",\"CustomLink5\",\"JustifyLeft\",\"JustifyCenter\",\"JustifyRight\",\"|\",\"CustomLink6\",\"|\",\"Bullets\",\"|\",\"Line\",\"|\",\"CustomLink3\",\"|\",\"CustomLink4\",\"CustomLink1\",\"CustomLink2\", \"|\",\"Undo\",\"Redo\",\"|\"");
    out.print("\"Copy\",\"Paste\",\"|\",\"Bold\",\"JustifyLeft\",\"JustifyCenter\",\"JustifyRight\",\"|\",\"Bullets\",\"|\",\"Line\",\"|\",\"Undo\",\"Redo\",\"|\"");
    out.println("];");

   out.println("oEdit1.css=\"/css/editor.css\";");

//    out.print("oEdit1.publishingPath='"+AppRequestUtils.getContextUrl((HttpServletRequest)pageContext.getRequest())+"';");

    out.println("oEdit1.useTagSelector=true;");
    
    out.println("oEdit1.useDIV=false;");
    out.println("oEdit1.useBR=false;");

    out.println("oEdit1.REPLACE(\"bodyeditor\");");
 
    out.println("if (window.EpOptimizeHeight) { "); 
    out.println("var func = function() { EpOptimizeHeight('oEdit1'); }");
    out.println("addLoadEvent(func);");
    out.println("addResizeEvent(func);");
    out.println("} "); 
     
    out.print("</script>");

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


out.print("<div id=\"EditorWrapper\" style=\"padding:6px 2px 2px 6px;width:100%;\">");
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
  tagWriter.getWriter().print(s==null||s.trim().length()==0?"<p></p>":s);
}

/**
    .
*/
public int writeEndTagReadMode() throws Exception
{
    JspWriter out = pageContext.getOut();   

    return EVAL_PAGE;
}



    
	protected int xxwriteStartTagEditMode(TagWriter tagWriter) throws JspException 
    {
        tagWriter.startTag("div");
        writeOptionalAttribute(tagWriter, "style", "border: 1px solid #908f8f;background-color: white;height: 10em");
        
		tagWriter.startTag("textarea");
        writeDefaultAttributes(tagWriter);
        writeOptionalAttribute(tagWriter, "dojoType", "Editor");
        writeOptionalAttribute(tagWriter, "items", getItems());
        writeOptionalAttribute(tagWriter, ROWS_ATTRIBUTE, getRows());
		writeOptionalAttribute(tagWriter, COLS_ATTRIBUTE, getCols());
        tagWriter.appendValue(ObjectUtils.getDisplayString(getBoundValue()));
        tagWriter.endTag();

        tagWriter.endTag();
        return EVAL_PAGE;
	}
    
    /**
     * 
     */
    protected int xxwriteStartTagReadMode(TagWriter tagWriter) throws JspException
    {
        tagWriter.startTag("span");
        tagWriter.appendValue(ObjectUtils.getDisplayString(getBoundValue()));
        tagWriter.endTag();
        return EVAL_PAGE;
    }

    public final String getItems()
    {
      return items;
    }

    public final void setItems(String items)
    {
      this.items = items;
    }



}
