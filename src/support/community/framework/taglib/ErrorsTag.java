package support.community.framework.taglib;

import java.util.*;

import javax.servlet.jsp.JspException;

import org.springframework.validation.*;
import org.springframework.web.servlet.support.*;

/**
 * Version of the standard spring 2.0 tag that allows :
 *      Specification of a message to be ouput when there are any errors to display.
 *      Output of all global messages (not field related) when no path is specified. 
 */
public class ErrorsTag extends AbstractHtmlElementTag 
{

	/** The HTML '<code>span</code>' tag */
	public static final String SPAN_TAG = "span";

    private String message;

	private String delimiter = "<br/>";


	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}


	protected String getName() throws JspException 
    {
		return null;
	}

      protected int doStartTagInternal() throws Exception
      {
        TagWriter tagWriter = getTagWriter();
        
        /*
         * If no path specified then show global errors and exit
         */
        if (getPath()==null) {
            showGlobalErrors(tagWriter);
            return EVAL_PAGE;
        }
        
        /*
         * If there are no errors specified by the path then exit.
         */
        try {
            if (!getBindStatus().isError()) return EVAL_PAGE;
        }
        catch (Exception x) {
            logger.error("", x);
            throw new Exception("Error in <form:errors> tag: maybe the <form:errors> tag is not inside a <form:form> tag");
        }

        /*
         * Write out the individual messages specified by the path.
         */
        String delim = "";
        tagWriter.startTag(SPAN_TAG);
        writeDefaultAttributes(tagWriter);
        if (message!=null) { 
            tagWriter.appendValue(message);
            //delim = getDelimiter();
        }
        String[] errorMessages = getBindStatus().getErrorMessages();
        for (int i = 0; i < errorMessages.length; i++) {
            String errorMessage = errorMessages[i];
            tagWriter.appendValue(delim);
            tagWriter.appendValue(errorMessage);
            //delim = getDelimiter();
        }
        tagWriter.endTag();
        
		return EVAL_PAGE;
	}
    
    private void showGlobalErrors(TagWriter tagWriter) throws JspException
    {
        RequestContext rc = getRequestContext(); 

        Errors errors = getFormErrors();
        
        if (errors.getErrorCount()==0) return ;
        
        /*
         * Write the messages.
         */
        String delim = "";
        tagWriter.startTag(SPAN_TAG);
        writeDefaultAttributes(tagWriter);
        tagWriter.forceBlock(); /// <span/> is invalid
        if (message!=null) { 
            tagWriter.appendValue(message);
            delim = getDelimiter();
        }
        List globals = errors.getGlobalErrors();
        for (int n=0; n<globals.size(); n++) {
            ObjectError err = (ObjectError)globals.get(n);
            tagWriter.appendValue(delim);
            tagWriter.appendValue(rc.getMessage(err, true));    //////////////          
            delim = getDelimiter();
        }
        tagWriter.endTag();            
        
    }

    public final String getDelimiter() throws JspException
    {
        return this.delimiter;
    }
    
    public final void setMessage(String message)
    {
        this.message = message;
    }
}
