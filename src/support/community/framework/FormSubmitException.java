package support.community.framework;

public class FormSubmitException extends Exception
{
    private String errorCode;
    private String fieldName;
    
    public FormSubmitException (String message)
    {
        super(message);
    }
    
    public FormSubmitException (String code, String message)
    {
        super(message);
        setErrorCode(code);
    }
    
    public FormSubmitException (String field, String code, String message)
    {
        this(code, message);
        setFieldName(field);
    }
    
    public final String getErrorCode()
    {
        return errorCode;
    }
    public final void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
