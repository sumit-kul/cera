package support.community.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator class to conveniently handle validation of CommandBean objects using
 * introspection. 
 * 
 */
public abstract class CommandValidator implements Validator
{
	/*
	 * Logger.
	 */
	Log logger = LogFactory.getLog(getClass());

	/*
	 * Standard commons validators.
	 */
	protected EmailValidator emailValidator = EmailValidator.getInstance();
	//protected IntegerValidator integerValidator = IntegerValidator.getInstance();
	//protected DoubleValidator doubleValidator = DoubleValidator.getInstance();
	//protected DateValidator dateValidator = DateValidator.getInstance();


	/**
	 *  Return true if the passed value is null or void.  
	 */
	public boolean isNullOrWhitespace(Object value)
	{
		return (value == null ||!StringUtils.hasText(value.toString())); 
	}

	/**
	 *  Validate the passed CommadBean object. Only those fields actually present in the request
	 *  bound to the command bean are validated. For each parameter name sent in the request
	 *  a validation method is selected by introspection and then invoked to perform the validation
	 *  with errors stored into the passed Errors object.  
	 */
	public final void validate(Object obj, Errors errors) 
	{
		CommandBean cmd = (CommandBean)obj;

		Iterator i = cmd.getRequestParameterNames().iterator();

		while (i.hasNext()) {
			String p = (String)i.next();
			logger.debug("Validating field "+p);
			validateField(errors, p, cmd);
		}

		validateCommand(cmd, errors);
	}

	/**
	 * Can be overridden by subclasses to perform validation of the command as 
	 * a whole after all field validation has taken place.
	 */
	protected void validateCommand(CommandBean cmd, Errors errors)
	{

	}

	public boolean supports(Class clazz)
	{
		return CommandBean.class.isAssignableFrom(clazz);
	}

	private void validateField(Errors errors, String field, CommandBean cmd) 
	{         
		try {
			Method m = findValidateMethod(this.getClass(), field);
			if (m==null) {
				logger.debug("No validation method for field "+field+" found in "+getClass());
				return;
			}
			Object value;
			try {
				value= errors.getFieldValue(field);
			}
			catch (NotReadablePropertyException x) {
				logger.warn("No readable property for field "+field+" found in "+getClass());
				return;
			}
			String error = (String)m.invoke(this, new Object[] {value, cmd});
			if (error!=null) {
				int i = error.indexOf(' ');
				String code = i<0 ? error : "";
				errors.rejectValue(field, code, error);
			}
		}
		catch (InvocationTargetException e) {
			logger.error("", e);
			errors.rejectValue(field, "", e.getCause().toString());
		}
		catch (Exception e) {
			logger.error("", e);
			errors.rejectValue(field, "", e.toString());
		}
	}

	/*
	 * 
	 */
	private Method findValidateMethod(Class type, String field) throws Exception
	{
		String methodName = "validate"+field.substring(0,1).toUpperCase()+field.substring(1);
		for (Class c = type; c!=null; c=c.getSuperclass()) {
			Method[] methods = c.getDeclaredMethods();
			for (int n=0; n<methods.length; n++) {
				Method m = methods[n];
				if (!m.getName().equalsIgnoreCase(methodName)) continue;
				Class[] params = m.getParameterTypes();
				if (params.length!=2) continue;
				//if (m.getReturnType()!=String.class) continue;
				return m;
			}
		}
		return null;
	}

}
