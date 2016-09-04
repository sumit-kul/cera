package support.community.framework;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.web.util.*;
import org.springframework.beans.propertyeditors.*;
import org.springframework.validation.*;
import org.springframework.web.bind.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.*;


/**
 * 
 * 
 */
public abstract class AbstractWizardAction extends AbstractWizardFormController 
{
    /*
     * Injected reference; 
     */
    private RunAsServerTemplate runServerAsTemplate;
  
	public AbstractWizardAction() 
    {
		setCommandName("command");
        setValidator(createValidator());
		setPages(getPageList());
        setBindOnNewForm(true);
	}

    protected abstract String[] getPageList();
    protected abstract CommandValidator createValidator();
    
    protected void beginSession(Object data) throws Exception
    {
        
    }

    protected final void validatePage(Object command, Errors errors, int page, boolean finish)
    {
        if (getValidator()!=null) getValidator().validate(command, errors);
        validateCommand(command, errors, page, finish);
    }   
    
    protected Map validateCommand(Object data, Errors errors, int page, boolean finish) 
    {
        return null;
    }

    
    protected final void onBindOnNewForm(HttpServletRequest request, Object command, BindException errors) throws Exception
    {
        beginSession(command);
    }

    protected final Map referenceData(HttpServletRequest request, Object command, Errors errors, int page) throws Exception
    {
        return referenceData(command,page);
    }

    protected Map referenceData(Object data, int page) 
    {
        return null;
    }

	protected abstract int getTargetPage(HttpServletRequest request, Object data, Errors errors, int currentPage); 
    
	protected abstract ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception; 

    protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
    {
        return new ModelAndView("redirect:/");
    }

    
    protected final ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return super.handleRequestInternal(request, response);
    }

    /*
     * Find the command object by instrospecting the handler class.
     */
    protected final Object formBackingObject(HttpServletRequest request) throws Exception
    {
        Class c = getCommandBeanClass();
        return c.newInstance();
    }
    
    /*
     * Find the command object by instrospecting the handler class.
     */
    private Class getCommandBeanClass() throws Exception
    {
        List found = getClassMembersOfType(CommandBean.class);
        if (found.isEmpty()) throw new Exception("Controller "+getClass()+" does not define a command bean");
        if (found.size()>1) throw new Exception("Controller "+getClass()+" defines mutiple command beans");
        return (Class)found.get(0);
    }


    /*
     * Find class members of a given type.
     */
    protected final List getClassMembersOfType(Class<?> type) throws Exception
    {
        List<Class> found = new ArrayList<Class>(2);
        Class[] a = getClass().getDeclaredClasses();
        for (int i=0; i<a.length; i++) {
            Class c = a[i];
            if (type.isAssignableFrom(c)) {
                found.add(c);
                continue;
            }
        }
        return found;
    }
    
    /*
     * 
     */
    protected final void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception
    {
        Object command = binder.getTarget();

        DateFormat fmt = new SimpleDateFormat("d MMM yyyy");
        fmt.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(fmt, true));
       
        if (!(command instanceof CommandBean)) return ;
       
       /*
        * Will work even for multipart since request will have been wrapped.
        */
       Map params = WebUtils.getParametersStartingWith(request, null);
       Iterator i = params.keySet().iterator();
       
       List<String> props = new ArrayList<String>(params.size());
       
       String fieldMarkerPrefix = binder.getFieldMarkerPrefix();
       while (i.hasNext()) {
           String pname = (String)i.next();
           if (fieldMarkerPrefix!=null && pname.startsWith(fieldMarkerPrefix)) 
               props.add(pname.substring(fieldMarkerPrefix.length()));
           else
               props.add(pname);
       }

       
       if (request instanceof MultipartHttpServletRequest) {
           MultipartHttpServletRequest mpr = (MultipartHttpServletRequest)request;
           i = mpr.getFileNames();
           while (i.hasNext()) {
               String pname = (String)i.next();
               if (mpr.getFile(pname).isEmpty()) continue;
               if (fieldMarkerPrefix!=null && pname.startsWith(fieldMarkerPrefix)) 
                   props.add(pname.substring(fieldMarkerPrefix.length()));
               else
                   props.add(pname);
           }
          // ((CommandBean)command).setFiles(mpr.getf);
       }
       ((CommandBean)command).setRequestParameterNames(props);
    }

    protected final RunAsServerTemplate getRunServerAsTemplate()
    {
        return runServerAsTemplate;
    }
    public final void setRunServerAsTemplate(RunAsServerTemplate runServerAsTemplate)
    {
        this.runServerAsTemplate = runServerAsTemplate;
    }
    
}
