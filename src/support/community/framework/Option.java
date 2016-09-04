package support.community.framework;

public class Option
{
    private String category;
    private String label;
    private Object value;
    private String cssClass;

    public Option()
    {
    }
    
    public Option(String value)
    {
        this.value = value;
    }
    
    public Option(String label, String value)
    {
        this.label = label;
        this.value = value;
    }
    
    public Option(String category, String label, String value)
    {
        this.category = category;
        this.label = label;
        this.value = value;
    }
    
    public Option(String category, String label, String value, String cssClass)
    {
        this.category = category;
        this.label = label;
        this.value = value;
        this.cssClass = cssClass;
    }
    
    public final String getCategory()
    {
        return category;
    }
    public final void setCategory(String category)
    {
        this.category = category;
    }
    public final String getLabel()
    {
        return label;
    }
    public final void setLabel(String label)
    {
        this.label = label;
    }
    public final Object getValue()
    {
        return value;
    }
    public final void setValue(Object value)
    {
        this.value = value;
    }

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
}