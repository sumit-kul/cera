package support.community.framework.taglib;

import java.util.Vector;

import javax.servlet.jsp.JspException;

import support.community.framework.Option;

/**
 * 
 * 
 */
public class CheckboxListTag extends AbstractHtmlInputElementTag implements OptionContainer 
{
    /*
     * Tag used to enclose each input tag emitted.
     */
    private String m_wrapperTag = "label";
    private int m_colCount = 0;
    private String m_colTag = "";
    private String m_colCssClass = "";
    private Vector<Option> m_options = new Vector<Option>();
    private int m_optionCount = 0;
    
	/**
	 * Renders the HTML '<code>select</code>' tag to supplied {@link TagWriter}.
	 * Renders nested '<code>option</code>' tags if the {@link #setItems items}
	 * properties are set, otherwise exposes the bound value for the
	 * nested {@link OptionTag OptionTags}.
	 */
	protected int writeStartTagEditMode(TagWriter tagWriter) throws JspException 
    {      
        /*
         * We dont want an ID because we are emitting multiple elements rather
         * than a single element so ID would be duplicated. 
         */
        if (getId()!=null) throw new JspException("Do not specify ID for checkbox list");
        
        /*
         *  write out the marker field
         *  It handles a quirk in html forms. If no check boxes in a set are ticked then nothing gets sent for the set 
         *  but this makes it impossible to deselect a previous selection, 
         *  when you untick all the ticked entries the browser sends nothing so the receiving action doesn't know whether 
         *  nothing was ticked or the set simply didn't appear on the form. 
         *  The hidden field resolves the confusion, its presence indicates that the set did appear on the form 
         *  so if nothing is seen for the set then the data should be cleared.
         *  
      
        tagWriter.startTag("input");
        tagWriter.writeAttribute("type", "hidden");
        tagWriter.writeAttribute("class", "hidden");
        tagWriter.writeAttribute("value", "1");
        tagWriter.writeAttribute("name", "_" + getPath());
        tagWriter.endTag();
           */
		return EVAL_BODY_INCLUDE;
	}


    /**
     * 
     */
    protected int writeEndTagEditMode(TagWriter tagWriter) throws JspException
    {
        for (int i=0; i<m_options.size(); i++) {
        	Option o = m_options.get(i);
            addOptionEditMode(o.getLabel(), o.getValue());
        }
        return EVAL_PAGE;
    }

    
    /*
     * We dont want an ID because we are emitting multiple elements rather
     * than a single element so ID would be duplicated. 
     */
    protected void setDefaultId() throws Exception 
    {
    }
    
    
    /**
     * 
     */
    protected int writeStartTagReadMode(TagWriter tagWriter) throws JspException
    {
        return EVAL_PAGE;
    }

    /**
     * 
     */
    protected int writeEndTagReadMode(TagWriter tagWriter) throws JspException
    {
        boolean first = true;
        for (int i=0; i<m_options.size(); i++) {
            Option o = m_options.get(i);
            if (!isSelected(o.getValue())) continue; 
            tagWriter.startTag("span");
            if (!first) tagWriter.appendValue(",  "); /////////
            tagWriter.appendValue(o.getLabel());
            tagWriter.endTag();
            first = false;
        }
        return EVAL_PAGE;
    }

    public void addOption(String label, Object value, String cssClass) throws JspException
    {
    	Option o = new Option();
    	o.setLabel(label);
    	o.setValue(value);
    	m_options.add(o);    	
    }

    public void addOptionEditMode(String label, Object value) throws JspException
    {
        TagWriter tagWriter = getTagWriter();
        
        m_optionCount++;
        
        if (openNewColumn()) {
        	tagWriter.getWriter().print("<" + getColTag() + " class=\"" + getColCssClass() + "\">" );
        }
        
        tagWriter.startTag(m_wrapperTag); 
        
        tagWriter.startTag("input");
        writeDefaultAttributes(tagWriter);
        tagWriter.writeAttribute("type", "checkbox");
        tagWriter.writeAttribute("value", value.toString()); ////////////////

        if (isSelected(value)) {
            tagWriter.writeAttribute("checked", "checked");
        }

        tagWriter.endTag(); //close checkbox tag
        
        tagWriter.appendValue(label);

        tagWriter.endTag();  //close wrapper tag
        
        if (closeColumn()) {
        	tagWriter.getWriter().print("</" + getColTag() + ">");
        }
        
    }
        
    private boolean openNewColumn() 
    {
    	if (m_colCount > 0 &&  !m_colTag.equals("")) {    		
    		int colPopulation = new Double(Math.ceil(((double) m_options.size() / (double) m_colCount))).intValue();    
    		if (m_optionCount == 1 || (((m_optionCount-1) % colPopulation) == 0) ) {
    			return true;
    		}    		
    	}    	
    	return false;
    }

    private boolean closeColumn() {
    	if (m_colCount > 0 && !m_colTag.equals("")) {
    		int colPopulation = new Double(Math.ceil((double) m_options.size() / (double) m_colCount)).intValue();
    		if (m_optionCount == m_options.size() || ((m_optionCount % colPopulation) == 0)) {
    			return true;
    		}    		
    	}
    	return false;
    }
    
    /*
     * Compare the passed object against the list of values bound
     * to this tag. Return true if the object matches one of the field values.
     */
    private boolean isSelected(Object o) throws JspException 
    {
        return SelectedValueComparator.isSelected(getBindStatus(), o);
    }
    
    public void setWrapperTag(String wrapperTag)
    {
        m_wrapperTag = wrapperTag;
    }


	public String getColCssClass() {
		return m_colCssClass;
	}


	public void setColCssClass(String cssClass) {
		m_colCssClass = cssClass;
	}


	public String getColTag() {
		return m_colTag;
	}


	public void setColTag(String tag) {
		m_colTag = tag;
	}

	public int getColCount() {
		return m_colCount;
	}

	public void setColCount(int count) {
		m_colCount = count;
	}
    
}
