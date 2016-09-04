package support.community.framework;

import java.util.List;

public interface DataBean
{
    public List getRequestParameterNames();
    public void setRequestParameterNames(List requestPropertyList);
    public String getFormUrl();
}