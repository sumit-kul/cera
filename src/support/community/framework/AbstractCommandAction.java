package support.community.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import support.community.application.BacklinkStack;

public abstract class AbstractCommandAction extends AbstractCommandController 
{   
    public static ModelAndView REDIRECT_TO_BACKLINK = new ModelAndView();
    
    private RunAsServerTemplate runServerAsTemplate;
    private AppRequestContextHolder appRequestContextHolder;

    
    protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
    {
       // if (errors.hasErrors()) throw errors;
        try {
		
        ModelAndView mv = handle(command);
        
        if (mv!=null&&getCommandName()!=null) mv.addObject(getCommandName(), command);

        if (mv==REDIRECT_TO_BACKLINK) {
            AppRequestContext arc = appRequestContextHolder.getRequestContext();
            if (arc==null) throw new Exception("No request context associated with thread");
            BacklinkStack backlinks = arc.getBacklinkStack();
            if (backlinks==null||backlinks.top()==null) return new ModelAndView("/");
            response.setCharacterEncoding("UTF-8");
            return new ModelAndView("redirect:"+backlinks.top());
        }
        response.setCharacterEncoding("UTF-8");
        return mv;
    	
		} catch (Exception e) {
			e.printStackTrace();
			response.setCharacterEncoding("UTF-8");
			return new ModelAndView("/");
		}
        
    }

    protected abstract ModelAndView handle(Object data) throws Exception;
       
    /*
     * Instantiate a new command object by instrospecting the handler class.
     */
    protected final Object getCommand(HttpServletRequest request) throws Exception 
    {
        Object cmd;

        Class c = getCommandBeanClass();
        
        Class enclosingClass = c.getEnclosingClass();
        if (enclosingClass==null || Modifier.isStatic(c.getModifiers())) {
            cmd = c.newInstance();
        }
        else {
            Constructor ctor = c.getDeclaredConstructor(new Class[] { enclosingClass });
            cmd = ctor.newInstance(new Object[] { this });
        }
        
        initializeCommand(request, cmd);
        return cmd;        
    }
    
    /*
     * Instantiate a new command object by instrospecting the handler class.
     */
    protected void initializeCommand(HttpServletRequest request, Object command) throws Exception 
    {
    }
    
    /*
     * Find the command object by instrospecting the handler class.
     */
    protected Class getCommandBeanClass() throws Exception
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
            Class<?> c = a[i];
            if (type.isAssignableFrom(c)) {
                found.add(c);
                continue;
            }
        }
        return found;
    }


    /*
     * Find an inner class with a given name.
     */
    Class<?> getInnerClassForNameAndType(String name, Class<?> type) throws Exception
    {
        Class<?> clazz = null;
        for (Class<?> c : getClass().getDeclaredClasses()) {
            if (c.getName().endsWith("$"+name)) { 
                clazz = c;
                break;
            }
        }
        if (clazz==null) throw new Exception("Class "+getClass().getName()+" does must define an inner class named "+name);
        if (!type.isAssignableFrom(clazz)) throw new Exception("Class "+clazz.getCanonicalName()+" must be of type "+type.getCanonicalName());
        return clazz;
    }
    
    protected final RunAsServerTemplate getRunServerAsTemplate()
    {
        return runServerAsTemplate;
    }
    public final void setRunServerAsTemplate(RunAsServerTemplate runServerAsTemplate)
    {
        this.runServerAsTemplate = runServerAsTemplate;
    }

    public final void setAppRequestContextHolder(AppRequestContextHolder appRequestContextHolder)
    {
        this.appRequestContextHolder = appRequestContextHolder;
    }
}