package support.community.framework;

import java.util.List;

public interface CommandBean
{
    public List getRequestParameterNames();
    public void setRequestParameterNames(List requestPropertyList);
    public String getFormUrl();
}