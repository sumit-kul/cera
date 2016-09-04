package support.community.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.WebApplicationContextUtils;

import support.community.application.AppLog;

public class WebResourceManager 
{
	private static LRUCache _cache = null;
	private static Transformer actionTransform = null;
	
	public static void writeResource(JspWriter out, String sName, PageContext pageContext) throws Exception
	{
		out.println(getTextResource(sName, pageContext));
	}

	public static void writeAction(JspWriter out, String sName, PageContext pageContext) throws Exception
	{
		out.println(getAction(sName, pageContext));
	}

	private static String getResourceText(Resource r) 
	{
		return readText(r, "ISO-8859-1");
	}

	private static Resource getResource(String sPath, PageContext pageContext) throws Exception
	{
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		return ac.getResource(sPath);
	}

	private static String getTextResource(String sName, PageContext pageContext) throws Exception
	{
		LRUCache cache = getCache();

		String s = (String)cache.get(sName);

		if (s==null) {

			Resource r = getResource("WEB-INF/res/"+sName, pageContext);
			if (!r.exists()) return "Unknown resource "+sName;
			s = getResourceText(r);
			cache.put(sName, s);

		}

		return s;
	}

	private static String readText(Resource r, String sEncoding) 
	{
		try {

			InputStream is = r.getInputStream();
			InputStreamReader reader = new InputStreamReader(is, sEncoding);
			StringBuffer buf = new StringBuffer(2048);
			int i=0; char[] ach = new char[2048];
			while ((i=reader.read(ach))>-1)
				buf.append(ach, 0, i);
			reader.close();
			return buf.toString();    

		}
		catch (Exception x) {
			AppLog.write(x);
			return x.getMessage();
		}
	}

	private static String getAction(String sName, PageContext pageContext) throws Exception
	{
		LRUCache cache = getCache();

		String s = (String)cache.get(sName);

		if (s==null) {

			Resource r = getResource("WEB-INF/actions/"+sName+".xml", pageContext);
			if (!r.exists()) return "Unknown action "+sName;
			URL url = r.getURL();

			Transformer tran = getActionTransform(pageContext);

			StringWriter sw = new StringWriter(512);

			tran.transform(new StreamSource(url.toString()), new StreamResult(sw));

			s = sw.toString();

			cache.put(sName, s);

		}

		return s;
	}

	private static Transformer getActionTransform(PageContext pageContext) throws Exception
	{
		if (actionTransform==null) {

			TransformerFactory factory = TransformerFactory.newInstance();
			//factory.setErrorListener();
			//factory.setURIResolver();

			Resource r = getResource("WEB-INF/actions/action.xsl", pageContext);
			if (!r.exists()) throw new Exception("Cannot find action.xsl");
			URL url = r.getURL();

			actionTransform = factory.newTransformer(new StreamSource(url.toString()));
		}

		return actionTransform;
	}

	private static LRUCache getCache() throws Exception
	{
		if (_cache==null) {
			_cache= new LRUCache(97, 30000);
		}
		return _cache;
	}
}