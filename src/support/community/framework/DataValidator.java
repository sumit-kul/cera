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

public abstract class DataValidator implements Validator
{
	Log logger = LogFactory.getLog(getClass());

	protected EmailValidator emailValidator = EmailValidator.getInstance();
	//protected IntegerValidator integerValidator = IntegerValidator.getInstance();
	//protected DoubleValidator doubleValidator = DoubleValidator.getInstance();
	//protected DateValidator dateValidator = DateValidator.getInstance();

	public boolean isNullOrWhitespace(Object value)
	{
		return (value == null ||!StringUtils.hasText(value.toString())); 
	}

	public final void validate(Object obj, Errors errors) 
	{
		DataBean dta = (DataBean)obj;

		Iterator i = dta.getRequestParameterNames().iterator();

		while (i.hasNext()) {
			String p = (String)i.next();
			logger.debug("Validating field "+p);
			validateField(errors, p, dta);
		}

		validateData(dta, errors);
	}

	protected void validateData(DataBean cmd, Errors errors)
	{
	}

	public boolean supports(Class clazz)
	{
		return DataBean.class.isAssignableFrom(clazz);
	}

	private void validateField(Errors errors, String field, DataBean cmd) 
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
				//errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
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
				return m;
			}
		}
		return null;
	}
}