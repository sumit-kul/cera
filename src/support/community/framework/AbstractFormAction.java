package support.community.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import support.community.application.BacklinkStack;
import support.community.database.TransactionContextHolder;

import com.era.community.base.UserRoleConstants;

/**
 * FormAction includes validation. 
 */
public abstract class AbstractFormAction extends SimpleFormController implements UserRoleConstants
{  
	private TransactionContextHolder transactionContextHolder; 
	private AppRequestContextHolder appRequestContextHolder;
	private RunAsServerTemplate runServerAsTemplate;

	public AbstractFormAction()
	{
		setBindOnNewForm(true);
		setFormView(getView());
		setValidator(createValidator());      
	}

	protected abstract CommandValidator createValidator() ;
	protected abstract String getView() ;

	public static ModelAndView REDIRECT_TO_FORM = new ModelAndView();
	public static ModelAndView REDIRECT_TO_BACKLINK = new ModelAndView();

	protected abstract void onDisplay(Object data) throws Exception;

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception 
	{
		try {           
			ModelAndView mv = onSubmit(command);
			if (mv==null || mv==REDIRECT_TO_FORM) {
				return new ModelAndView("redirect:"+((CommandBean)command).getFormUrl());
			}
			else if (mv==REDIRECT_TO_BACKLINK) {
				AppRequestContext arc = appRequestContextHolder.getRequestContext();
				if (arc==null) throw new Exception("No request context associated with thread");
				BacklinkStack backlinks = arc.getBacklinkStack();
				if (backlinks==null||backlinks.top()==null) return new ModelAndView("redirect:"+((CommandBean)command).getFormUrl());
				return new ModelAndView("redirect:"+backlinks.top());
			}
			else {
				return mv ;
			}
		}
		catch (FormSubmitException e) {
			if (e.getFieldName() != null && !"".equals(e.getFieldName())) {
				errors.rejectValue(e.getFieldName(), "", e.getMessage());
			} else {
				errors.reject("", e.getMessage());
			}
			transactionContextHolder.getContext().setRollbackOnly();
			return showForm(request, response, errors);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/");
		}
	}

	protected abstract ModelAndView onSubmit(Object data) throws Exception;

	protected Map referenceData(Object command) throws Exception { return new HashMap(); }

	protected final Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception
	{
		Map ref = null;
		try {
			ref = referenceData(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ref;
	}

	protected final void onBindOnNewForm(HttpServletRequest request, Object command, BindException errors) throws Exception 
	{
		try {
			onDisplay(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected final Object formBackingObject(HttpServletRequest request) throws Exception
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

	protected void initializeCommand(HttpServletRequest request, Object command) throws Exception 
	{
	}

	private Class getCommandBeanClass() throws Exception
	{
		List found = getClassMembersOfType(CommandBean.class);
		if (found.isEmpty()) throw new Exception("Controller "+getClass()+" does not define a command bean");
		if (found.size()>1) throw new Exception("Controller "+getClass()+" defines mutiple command beans");
		return (Class)found.get(0);
	}

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

	@SuppressWarnings("unchecked")
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception
	{
		Object command = binder.getTarget();

		DateFormat fmt = new SimpleDateFormat("yyyy-mm-d");
		fmt.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(fmt, true));
		//binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());       

		if (!(command instanceof CommandBean)) return ;

		/*
		 * Will work even for multipart as request has been wrapped.
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
		}
		((CommandBean)command).setRequestParameterNames(props);
	}

	public final void setTransactionContextHolder(TransactionContextHolder transactionContextHolder)
	{
		this.transactionContextHolder = transactionContextHolder;
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