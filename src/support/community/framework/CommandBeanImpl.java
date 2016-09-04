
package support.community.framework;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.DataBinder;

import support.community.util.StringHelper;

/**
 */
public class CommandBeanImpl implements Serializable
{
	private String m_mode = "";

	private String m_formUrl;

	private List m_requestParameterNames;

	protected String searchType = "All Content";
	protected String queryText;
	private String toggleList = "false";

	private int page = 1;
	private int pageSize = 10;
	private int rowCount = 0;
	private int pageCount = 0;
	private String metaDescription;

	public final List getRequestParameterNames()
	{
		return m_requestParameterNames;
	}
	public final void setRequestParameterNames(List requestPropertyList)
	{
		m_requestParameterNames = requestPropertyList;
	}

	public void copyRequestDataTo(Object target) throws Exception
	{
		DataBinder binder = new DataBinder(target);
		Map map = PropertyUtils.describe(this);
		MutablePropertyValues pv = new MutablePropertyValues();
		Iterator i = map.keySet().iterator();
		while (i.hasNext()) {
			String s = (String)i.next();
			if (m_requestParameterNames.contains(s)) 
				pv.addPropertyValue(s, map.get(s));
		}
		binder.bind(pv);      
	}


	public void setTargetProperty(String prop, Object value, Object target) throws Exception
	{
		DataBinder binder = new DataBinder(target);
		MutablePropertyValues pv = new MutablePropertyValues();
		pv.addPropertyValue(prop, value);
		binder.bind(pv);      
	}


	public void copyNonNullPropertiesTo(Object target) throws Exception
	{
		DataBinder binder = new DataBinder(target);
		Map map = PropertyUtils.describe(this);
		MutablePropertyValues pv = new MutablePropertyValues();
		Iterator i = map.keySet().iterator();
		while (i.hasNext()) {
			String s = (String)i.next();
			if (map.get(s)!=null) 
				pv.addPropertyValue(s, map.get(s));
		}
		binder.bind(pv);      
	}

	public boolean hasRequestDataFor(String field)
	{
		return m_requestParameterNames.contains(field);
	}

	public void copyPropertiesFrom(Object source) throws Exception
	{
		PropertyUtils.copyProperties(this, source);
	}    

	public void copyPropertiesTo(Object target) throws Exception
	{
		PropertyUtils.copyProperties(target, this);
	}    

	public final String getMode()
	{
		return m_mode;
	}
	public final void setMode(String mode)
	{
		m_mode = mode;
	}
	public final String getFormUrl()
	{
		return m_formUrl;
	}
	public final void setFormUrl(String formUrl)
	{
		m_formUrl = formUrl;
	}
	/**
	 * @return the searchType
	 */
	 public String getSearchType() {
		return searchType;
	}
	/**
	 * @param searchType the searchType to set
	 */
	 public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	/**
	 * @return the queryText
	 */
	 public String getQueryText() {
		return queryText;
	}
	/**
	 * @param queryText the queryText to set
	 */
	 public void setQueryText(String queryText) {
		 this.queryText = queryText;
	 }
	 public String getToggleList() {
		 return toggleList;
	 }
	 public void setToggleList(String toggleList) {
		 this.toggleList = toggleList;
	 }
	 public int getPage() {
		 return page;
	 }
	 public void setPage(int page) {
		 this.page = page;
	 }
	 public int getPageSize() {
		 return pageSize;
	 }
	 public void setPageSize(int pageSize) {
		 this.pageSize = pageSize;
	 }
	 public int getRowCount() {
		 return rowCount;
	 }
	 public void setRowCount(int rowCount) {
		 this.rowCount = rowCount;
	 }
	 public int getPageCount() {
		 return pageCount;
	 }
	 public void setPageCount(int pageCount) {
		 this.pageCount = pageCount;
	 }
	 public String getMetaDescription() {
		 return metaDescription;
	 }
	 public void setMetaDescription(String metaDescription) {
		 this.metaDescription = StringHelper.escapeHTML(metaDescription);
	 }
}