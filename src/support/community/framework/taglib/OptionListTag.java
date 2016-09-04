package support.community.framework.taglib;

import java.util.*;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.*;

import org.springframework.util.*;

import support.community.framework.*;

public class OptionListTag extends AbstractFormTag 
{
    private Object options;
    
    private String cssClass;
       
    protected int doStartTagInternal() throws Exception 
    {
        Tag parent = getParent();
        if (!(parent instanceof OptionContainer))
            throw new JspException("Option tag cannot be nested in "+parent.getClass());

        OptionContainer oc = (OptionContainer)parent;
        
        if (options==null)
            options = new ArrayList();
        else if (options.getClass().isArray())
            options = CollectionUtils.arrayToList(options);
        else if (options instanceof String)
            options = stringToOptionList((String)options);
        else if (!(options instanceof Collection)) 
            throw new JspException("Value of the 'options' attribute must be a Collection or an array");
        
        Iterator i = ((Collection)options).iterator();
        
        while (i.hasNext()) {
            Object o = i.next();
            if (o instanceof Option) {
                Option opt = (Option)o;
                oc.addOption((opt.getLabel()==null?opt.getValue().toString():opt.getLabel()), opt.getValue(), this.getCssClass());
            }
            else {
                String s = o.toString();
                oc.addOption(s, s, null);
            }
        }

		return EVAL_PAGE;
	}

    private Collection stringToOptionList(String data)
    {
        String[] items = StringUtils.tokenizeToStringArray(data, ",");
        List<Option> options1 = new ArrayList<Option>(items.length);
        for (int n=0; n<items.length; n++) {
            options1.add(stringToOption(items[n]));
        }
        return options1;
    }
 
    private Option stringToOption(String s)
    {
        Option opt = new Option();
        int i = s.indexOf('|');
        if (i<0) { 
            opt.setValue(s);
        }
        else {
            opt.setLabel(s.substring(0, i));
            opt.setValue(s.substring(i+1));
        }
        return opt;
    }
    
    public final void setOptions(Object options)
    {
        this.options = options;
    }
    
    /**
	 * Sets the value of the '<code>class</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	/**
	 * Gets the value of the '<code>class</code>' attribute.
	 * May be a runtime expression.
	 */
	protected String getCssClass() {
		return this.cssClass;
	}
}
