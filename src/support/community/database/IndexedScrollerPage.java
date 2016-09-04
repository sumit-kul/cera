package support.community.database;

import java.util.List;

import com.ibm.json.java.JSONObject;

public interface IndexedScrollerPage extends List<Object>, ScrollerPage
{
    public int getRowCount();
    public int getPageCount();
    public int getScrollerPageSize();
    public int getCurrentPageNumber();
    public String toJsonString() throws Exception;
    public JSONObject toJsonString(int pageNumber) throws Exception;
    public JSONObject toJsonStringMyHome(int pageNumber) throws Exception;
    public JSONObject toJsonStringForCommunityHome(int pageNumber, String filterTag, String toggleList) throws Exception;
    public JSONObject toCommonJsonString(int pageNumber, String filterTag, String sortOption, String toggleList) throws Exception;
    public JSONObject toJsonStringForFiles(int folderId) throws Exception;
    public JSONObject toJsonString(int pageNumber, int userId) throws Exception;
    public JSONObject toJsonString(int pageNumber, String filterTag, String commOption, String sortOption, String toggleList) throws Exception;
    public JSONObject toJsonString(int pageNumber, String filterTag, String toggleList) throws Exception;
    public JSONObject toJsonString(int pageNumber, String filterTag, String commOption, String roleOption, String sortOption, String toggleList) throws Exception;
    public JSONObject toJsonSearchContent() throws Exception;
}