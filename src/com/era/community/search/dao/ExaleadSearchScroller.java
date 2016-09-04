package com.era.community.search.dao;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.springframework.util.Assert;

import support.community.database.IndexedScrollerPage;
import support.community.database.IndexedScrollerPageImpl;
import support.community.database.QueryPaginator;
import support.community.util.StringFormat;

public class ExaleadSearchScroller implements QueryPaginator
{   
	private static String NS_OPENSEARCH = "http://a9.com/-/spec/opensearch/1.1/";

	private int m_pageSize = 16; 
	private String m_userQuery;
	private String[] m_sites;

	Log logger = LogFactory.getLog(getClass());


	public ExaleadSearchScroller(String query, String[] sites) throws Exception
	{
		m_userQuery = query;
		m_sites = sites;
	}

	public IndexedScrollerPage readPage(int pageNumber) throws Exception
	{
		Assert.isTrue(pageNumber>0, "Invalid page number ["+pageNumber+"]. Must be > 0.");

		IndexedScrollerPageImpl data = new IndexedScrollerPageImpl();

		//  Namespace nsRss = Namespace.getNamespace(NS_RSS);
		Namespace nsSrch = Namespace.getNamespace(NS_OPENSEARCH);

		Document doc = runQuery(pageNumber);
		Element root = doc.getRootElement();
		Element channel = (Element)root.getChildren().get(0);

		logger.debug("*****"+channel.getName()+"::"+channel.getNamespaceURI());

		int rowCount = Integer.parseInt(channel.getChild("totalResults", nsSrch).getText());
		int pageCount = rowCount==0 ? 0 : ((int)((rowCount-1)/m_pageSize))+1;

		if (pageNumber>pageCount&&pageCount>0) pageNumber = pageCount; 

		int index = 0;

		/*
		 */
		 for (Object o : channel.getChildren("item")) {

			 Element item = (Element)o;
			 Element title = item.getChild("title");
			 Element link = item.getChild("link");
			 Element description = item.getChild("description");

			 Hit hit = new Hit();
			 hit.setTitle(title.getText().trim().length()==0?"Untitled":title.getText());
			 hit.setLink(link.getText());
			 hit.setSummary(description.getText());
			 hit.setIndex(index++);
			 data.add(hit);

		 }

		 data.setRowCount(rowCount);
		 data.setPageCount(pageCount);
		 data.setCurrentPageNumber(pageNumber);
		 data.setScrollerPageSize(m_pageSize);

		 return data;
	}

	public int readRowCount() throws Exception
	{
		Document doc = runQuery(1);
		Element channel = (Element)doc.getRootElement().getChildren().get(0);
		Namespace ns = Namespace.getNamespace(NS_OPENSEARCH);
		Element totalResults = channel.getChild("totalResults", ns);
		return Integer.parseInt(totalResults.getText());
	}

	/*
	 * Support request 1572
	 * A default web request now looks like: 
        http://api.exalead.com/search/web?output=onesearchv0_9&q=${searchTerms}&onesearchkey=${key} 

        (Previously it was: 
        http://www.exalead.com/search?output=onesearchv0_9&q=${searchTerms}&onesearchkey=${key}) 

	 * 
	 */
	private Document runQuery(int pageNumber) throws Exception
	{
		StringBuilder buf = new StringBuilder(4096);

		buf.append("http://api.exalead.com/search/web?output=onesearchv0_9&onesearchkey=0b183f41");
		buf.append("&nhits="+m_pageSize);
		buf.append("&b="+((pageNumber-1)*m_pageSize));

		buf.append("&q="+StringFormat.escape(m_userQuery)); 

		buf.append(" (");

		String sep = "";
		for (String host : m_sites) {
			buf.append(sep+"site:"+host);
			sep = " OR ";
		}

		buf.append(")");

		logger.debug(buf.toString());

		URL url = new URL(buf.toString()); 
		SAXBuilder builder = new SAXBuilder();
		return builder.build(url);
	}

	public static class Hit
	{
		private String title;
		private String link;
		private String summary;
		private int index;

		public boolean isOdd() 
		{
			return index%2>0;
		}

		public boolean isEven() 
		{
			return index%2==0;
		}

		public String getSite() throws Exception
		{
			URL url = new URL(link);
			return url.getHost();
		}

		public final String getSummary()
		{
			return summary;
		}
		public final void setSummary(String description)
		{
			this.summary = description;
		}
		public final String getLink()
		{
			return link;
		}
		public final void setLink(String link)
		{
			this.link = link;
		}
		public final String getTitle()
		{
			return title;
		}
		public final void setTitle(String title)
		{
			this.title = title;
		}
		public final int getIndex()
		{
			return index;
		}
		public final void setIndex(int index)
		{
			this.index = index;
		}
	}


	public void addScrollKey(String sName, String sDir, String sType) throws Exception
	{
		throw new Exception("Not supported");
	}

	public int getPageSize()
	{
		return m_pageSize;
	}

	public void setPageSize(int pageSize)
	{
		m_pageSize = pageSize;
	}


}
