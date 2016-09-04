package support.community.framework.taglib;


import java.util.*;

import javax.servlet.jsp.*;

import support.community.database.*;
import support.community.framework.*;
import support.community.util.*;


/**
 *
 */
public class PaginatorTag extends AbstractHtmlElementTag 
{
    private IndexedScrollerPage m_scrollerPage;
    private int m_maxPageLinks = 10;
    private boolean m_allowScrollToKey;
    
    protected int doStartTagInternal() throws Exception 
    {
        TagWriter tagWriter = getTagWriter();
        
        tagWriter.startTag("div");
        
     
        tagWriter.writeOptionalAttributeValue("id", getId());
        tagWriter.writeOptionalAttributeValue("class", getCssClass());
        tagWriter.forceBlock();
        
        /*
         * If there is no data then display an empty paginator bar.
         */
        if (m_scrollerPage.getRowCount()==0) {
            tagWriter.startTag("ul");
            tagWriter.startTag("li");
            tagWriter.appendValue(" ");
            tagWriter.endTag(); 
            tagWriter.endTag(); 
            tagWriter.endTag(); 
            return EVAL_PAGE;
        }
        
        if (m_allowScrollToKey) {
            renderScrollToKeyBox(tagWriter, m_scrollerPage);
        }
        
        tagWriter.startTag("ul");
        tagWriter.forceBlock();

        tagWriter.startTag("li");
        tagWriter.writeAttribute("class", "first");
        tagWriter.appendValue("page ");
        tagWriter.startTag("strong");
        tagWriter.appendValue(""+m_scrollerPage.getCurrentPageNumber());
        tagWriter.endTag();
        tagWriter.appendValue(" of ");
        tagWriter.startTag("strong");
        tagWriter.appendValue(""+m_scrollerPage.getPageCount());
        tagWriter.endTag();
        tagWriter.endTag();
        
        renderPageLink(tagWriter, getPrevPageNumber(), "&lt; Previous");

        if (m_scrollerPage.getPageCount()<2)
            ;
        else if(m_scrollerPage.getPageCount()<=m_maxPageLinks) {
            renderDivider(tagWriter);
            renderAllPageLinks(tagWriter);
        }
        else { 
            renderDivider(tagWriter);
            renderSelectedPageLinks(tagWriter);
        }
        
        renderPageLink(tagWriter, getNextPageNumber(), "Next &gt;");
            
        tagWriter.endTag();  //ul
        tagWriter.endTag(); //div

		return EVAL_PAGE;
	}
    
    private void renderScrollToKeyBox(TagWriter tagWriter, IndexedScrollerPage page) throws Exception
    {
        if (getFormTag()==null) 
            renderScrollToKeyForm(tagWriter, page);
        else
            renderScrollToKeyButton(tagWriter, page);
    }

    private void renderScrollToKeyButton(TagWriter tagWriter, IndexedScrollerPage page) throws Exception
    {
        AppRequestContext context = getAppRequestContext();
        
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("page", null);
        params.put("pageKey", null);
        String url = context.computeUrl(params);

        tagWriter.startTag("div");
        tagWriter.writeAttribute("style", "float:left;");
      

        tagWriter.startTag("input");
            tagWriter.writeAttribute("type", "text");
            tagWriter.writeAttribute("class", "text");
            tagWriter.writeAttribute("name", "pageKey");
            tagWriter.writeAttribute("id", "csqPaginatorPageKey");
            tagWriter.writeAttribute("value", "");
        
            String sep = url.contains("?")?"&amp;":"?";

            tagWriter.writeAttribute("onkeypress", "if (event.keyCode==13||event.which==13) { location='"+url+sep+"pageKey='+this.value; return false } else return true");
         tagWriter.endTag();                                 // Close input tag
        
           
        tagWriter.startTag("a");

           tagWriter.writeAttribute("href", "javascript:window.location='"+url+sep+"pageKey='+document.getElementById('csqPaginatorPageKey').value");
           
             tagWriter.startTag("img");
             tagWriter.writeAttribute("src", "img/btn-go.gif");
           tagWriter.writeAttribute("class", "inline");

             tagWriter.endTag();  // End a tag
             
  
             
        tagWriter.endTag();                                 // Close input tag

        tagWriter.endTag(); //div
    }
    
    
    
    
    
    

    @SuppressWarnings("unchecked")
    private void renderScrollToKeyForm(TagWriter tagWriter, IndexedScrollerPage page) throws Exception
    {
        AppRequestContext context = getAppRequestContext();
        
        tagWriter.startTag("form");
        tagWriter.writeAttribute("style", "float:left");
        tagWriter.writeAttribute("method", "get");
        tagWriter.writeAttribute("action", context.getRequestUri());
        
        Map<String, Object> params = context.getRequestParameters();
        params.remove("page");
        params.remove("pageKey");
        for (Map.Entry<String, Object> e : params.entrySet()) {
            List values;
            if (e.getValue() instanceof List)
                values = (List)e.getValue();
            else {
                values = new ArrayList(1);
                values.add(e.getValue());
            }
            for (Object v : values) {
                tagWriter.startTag("input");
                tagWriter.writeAttribute("type", "hidden");
                tagWriter.writeAttribute("class", "hidden");
                tagWriter.writeAttribute("name", e.getKey());
                tagWriter.writeAttribute("value", v.toString());
                tagWriter.endTag();
            }
        }
        
        tagWriter.startTag("input");
        tagWriter.writeAttribute("type", "text");
        tagWriter.writeAttribute("name", "pageKey");
        tagWriter.writeAttribute("value", "");
        tagWriter.endTag();
        
        tagWriter.startTag("input");
        tagWriter.writeAttribute("type", "submit");
        tagWriter.writeAttribute("value", "Go");
        tagWriter.endTag();
        
        tagWriter.endTag(); //form
    }
    
    private int getNextPageNumber()
    {
        if (m_scrollerPage.getCurrentPageNumber()==m_scrollerPage.getPageCount())
            return -1;
        return m_scrollerPage.getCurrentPageNumber()+1;
    }

    private int getPrevPageNumber()
    {
        if (m_scrollerPage.getCurrentPageNumber()==1)
            return -1;
        return m_scrollerPage.getCurrentPageNumber()-1;
    }

    private void renderPageLink(TagWriter tagWriter, int pageNumber, String label) throws JspException
    {
        AppRequestContext context = getAppRequestContext();
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("page", new Integer(pageNumber));
        params.put("pageSize", new Integer(m_scrollerPage.getScrollerPageSize()));
        params.put("pageKey", "");
        String href = context.computeUrl(params);
        if (pageNumber<1) return;
        tagWriter.startTag("li");
        tagWriter.startTag("a");
        tagWriter.writeAttribute("href", StringFormat.escape(href)); 
        
        
        if (pageNumber==m_scrollerPage.getCurrentPageNumber())
            tagWriter.writeAttribute("class", "selected"); 
        tagWriter.appendValue(label);
        tagWriter.endTag();
        tagWriter.endTag();
    }

    private void renderAllPageLinks(TagWriter tagWriter) throws JspException
    {
        for (int p=1; p<=m_scrollerPage.getPageCount(); p++) {
            renderPageLink(tagWriter, p, ""+p);
            renderDivider(tagWriter);
        }
        
    }

    private void renderSelectedPageLinks(TagWriter tagWriter) throws JspException
    {
        int start = Math.max(1, m_scrollerPage.getCurrentPageNumber()-(int)(m_maxPageLinks/2));        
        for (int j=0; j<m_maxPageLinks; j++) {
            int p = start+j;
            if (p>m_scrollerPage.getPageCount()) break;
            renderPageLink(tagWriter, p, ""+p); 
            renderDivider(tagWriter);
        }
    }

    private void renderDivider(TagWriter tagWriter) throws JspException
    {
        tagWriter.startTag("li");
        tagWriter.writeAttribute("class", "divider");
        tagWriter.appendValue("|");
        tagWriter.endTag();
    }

    public final void setScrollerPage(IndexedScrollerPage page)
    {
        m_scrollerPage = page;
    }
    public final void setMaxPageLinks(int maxPageLinks)
    {
        m_maxPageLinks = maxPageLinks;
    }

    public final void setAllowScrollToKey(boolean allowScrollToKey)
    {
        m_allowScrollToKey = allowScrollToKey;
    }
}
