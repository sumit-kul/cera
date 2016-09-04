package support.community.application;

import java.net.*;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import org.apache.commons.beanutils.PropertyUtils;

import support.community.util.*;

/**
 * Utilities for handling the request object in an Action or JSP tag. 
 * 
 * The main purpose of this class is to address this problem: 
 * There are occasions when
 * the request URL and query string reported by the standard java HttpServletRequest 
 * API are not useful. 
 * 
 * This happens when a JSP is invoked with forward, the URL reported 
 * is then the forward URL and not the URL originally sent by the browser. To handle this
 * problem this class provides a method to store the original URL data in request attributes
 * and this data will, if present, always be used in place of the reported URL.
 * 
 * This also happens when a JSP is invoked in a POST request; the reported URL is simply
 * the address to which the POST was directed but we are more interested in the URL that
 * was used to display the form that has been submitted. We therefore provide push and pop
 * methods that allow the effective request URL used by the methods provided here to be set
 * temporarily (these are used in the &lt;csq:Form&gt; tag).    
 * 
 */
@SuppressWarnings("unchecked")
public class AppRequestUtils 
{
    /**
     * Merge a set of parameters into the query string of a URL. The parameters are specified
     * as a Map and any that already appear in the specified URL are replaced while any that 
     * are not present are added.
     * 
     * @param url  The URL into which query string parameters are to be merged.
     * @param params A set of parameters as a Map.
     * @return  A new URL string with the specified parameters merged in.
     * @throws Exception 
     */
    static public String computeUrl(String url, Map params) throws JspException
    {
        StringTokenizer tok = new StringTokenizer(url, "?");
        String href = tok.nextToken();
        String query = tok.hasMoreTokens() ? tok.nextToken() : null; 
        return computeUrl(href, query, params); 
    }
    
    /**
     *  Same as the public method but with the query string separated out of the URL.
     */
    static private String computeUrl(String href, String sQuery, Map params) throws JspException
    {
        Map map = parseQueryString(sQuery);
        
        if (params!=null) {
            Iterator i = params.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry e = (Map.Entry)i.next();
                map.put(e.getKey(), e.getValue());
            }
        }
        
        StringBuffer sb = new StringBuffer(128);
        sb.append(href);
        
        String sep = "?";
        
        Iterator i = map.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry e = (Map.Entry)i.next();
            Object value = e.getValue();
            if (value instanceof List) {
                for (Object v : ((List)value)) {
                    sb.append(sep);
                    sep = "&";
                    sb.append(e.getKey());
                    sb.append("=");
                    sb.append(StringFormat.escape(v.toString()));
                }
            }
            else {
                if (value==null||"".equals(value)) continue;
                sb.append(sep);
                sep = "&";
                sb.append(e.getKey());
                sb.append("=");
                sb.append(StringFormat.escape(value.toString()));
            }
        }
        
        return sb.toString();        
    }
    
    /**
     * Parse a query string and return the parameters as a Map.
     * 
     * @param sQuery  The query string to parse.
     * @return  A Map of the parameters found.
     * @throws Exception
     */
    static public Map parseQueryString(String sQuery) throws JspException
    {
        Map map = new HashMap();
        
        if (sQuery==null || sQuery.length()==0) return map;
      
        StringTokenizer tok = new StringTokenizer(sQuery, "&=", true);
        while (tok.hasMoreTokens()) {
            String sName = tok.nextToken();
            if (!tok.hasMoreTokens()) throw new JspException("Invalid query string(1)");
            if (!tok.nextToken().equals("=")) throw new JspException("Invalid query string(2)");
            String sValue = tok.hasMoreTokens() ? tok.nextToken() : "";
            if (sValue.equals("&")) 
                sValue = "";
            else { 
                if (tok.hasMoreTokens() && !tok.nextToken().equals("&")) throw new JspException("Invalid query string(3)");
           }
            String v = StringFormat.unescape(sValue);
            if (map.containsKey(sName)) {
                Object o = map.get(sName);
                if (o instanceof List) {
                    ((List)o).add(v);
                }
                else {
                    List values = new ArrayList(8);
                    values.add(o);
                    values.add(v);
                    map.put(sName, values);
                }
            }
            else {
                map.put(sName, v);
            }
        }
        
        return map; 
    }
    
    /**
     * Parse the query string of a URL and return the parameters as a Map.
     * 
     * @param url  The URL to parse.
     * @return  A Map of the parameters found.
     * @throws Exception
     */
    static public Map parseUrlParameters(String url) throws Exception
    {
        int n = url.indexOf('#');
        if (n>0) url = url.substring(0, n);
        StringTokenizer tok = new StringTokenizer(url, "?");
        String href = tok.nextToken();
        String query = tok.hasMoreTokens() ? tok.nextToken() : null; 
        return parseQueryString(query); 
    }
    
    
      /**
     * Parse the query string of the current request and return the parameters as a Map.
     * 
     * @param ctx  JSP page context.
     * @return  A Map of the paramters found.
     * @throws Exception
     */
    static public Map getQueryStringParameters(PageContext ctx) throws Exception
    {
        return getQueryStringParameters((HttpServletRequest)ctx.getRequest());
    }

    /**
     * Parse the query string of the current request and return the parameters as a Map.
     * 
     * @param req  Request object.
     * @return  A Map of the paramters found.
     * @throws Exception
     */
    static public Map getQueryStringParameters(HttpServletRequest req) throws Exception
    {
        return parseQueryString(req.getQueryString());
    }


    /**
     * Get the request URL without query string.
     * 
     * @param ctx  JSP page context.
     * @return The URL string.
     * @throws Exception
     */
    static public String getRequestUrl(PageContext ctx) throws Exception
    {
        return getRequestUrl(ctx, null);
    }

    /**
     * Get the request URL with its query string replaced by one built from the 
     * supplied Map of parameters.
     * 
     * @param ctx  JSP page context.
     * @param params  The parameters for the query string.
     * @return  The request URL with query string built from the parameters. 
     * @throws Exception
     */
    static public String getRequestUrl(PageContext ctx, Map params) throws Exception
    {
        return getRequestUrl((HttpServletRequest)ctx.getRequest(), params);
    }

    /**
     * Get the request URL with its query string replaced by one built from the 
     * supplied Map of parameters.
     * 
     * @param req  Servlet request object.
     * @param params  The parameters for the query string.
     * @return  The request URL with query string built from the parameters. 
     * @throws Exception
     */
    static public String getRequestUrl(HttpServletRequest req, Map params) throws Exception
    {
        String sUrl = req.getRequestURL().toString();
        return params==null ? sUrl : computeUrl(sUrl, null, params);
    }

    /**
     * Get the request URL including its query string.
     * 
     * @param ctx  JSP page context.
     * @return The URL string.
     * @throws Exception
     */
    static public String getFullRequestUrl(PageContext ctx) throws Exception
    {
        return getFullRequestUrl(ctx, null);
    }

    /**
     * Get the request URL with specified parameters merged into its query string.
     * 
     * @param ctx  JSP page context.
     * @param params  Parameters to merge into the query string.
     * @return The URL string.
     * @throws Exception
     */
    static public String getFullRequestUrl(PageContext ctx, Map params) throws Exception
    {
        return getFullRequestUrl((HttpServletRequest)ctx.getRequest(), params);
    }

    /**
     * Get the current request URL with specified parameters merged into its query string.
     * 
     * @param req  Request object.
     * @param params  Parameters to merge into the query string.
     * @return The URL string.
     * @throws Exception
     */
    static public String getFullRequestUrl(HttpServletRequest req, Map params) throws Exception
    {
        String sUrl = req.getRequestURL().toString();
        String sQuery = req.getQueryString();
        return computeUrl(sUrl, sQuery, params);
    }

   
     /**
     * Convert a context-relative URL to an absolute URL. 
     * 
     * @param ctx  JSP page context.
     * @param url  The URL to expand (may or may not be relative).
     * @return  The absolute URL.
     * @throws Exception
     */
    static public String absoluteUrl(PageContext ctx, String url) throws Exception
    {
        return absoluteUrl((HttpServletRequest)ctx.getRequest(), url);
    }

    /**
     * Convert a context-relative URL to an absolute URL. 
     * 
     * @param req  Request object.
     * @param url  The URL to expand (may or may not be relative).
     * @return  The absolute URL.
     * @throws Exception
     */
    static public String absoluteUrl(HttpServletRequest req, String url) throws Exception
    {
        if (url.startsWith("http")) return url;
        if (url.startsWith("/")) return serverURL(req).toString()+url;
        return serverURL(req)+req.getContextPath()+"/"+url;
    }


    /**
     * Return the context URL (with a trailing '/'). 
     * 
     */
    static public String getContextUrl(HttpServletRequest req) throws Exception
    {
        return serverURL(req) + req.getContextPath() + "/";
    }

    /**
     * Return the URL representing the scheme, server, and port number of
     * the current request.  Server-relative URLs can be created by simply
     * appending the server-relative path (starting with '/') to this.
     *
     * @param request The servlet request we are processing
     * @return URL representing the scheme, server, and port number of
     *     the current request
     * @exception MalformedURLException if a URL cannot be created
     */
    public static URL serverURL(HttpServletRequest request) throws MalformedURLException {

        StringBuffer url = new StringBuffer();
        String scheme = request.getScheme();
        int port = request.getServerPort();
        if (port < 0) {
            port = 80; // Work around java.net.URL bug
        }
        url.append(scheme);
        url.append("://");
        url.append(request.getServerName());
        if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }
        return (new URL(url.toString()));

    }

    /**
     * 
     */
    public static Object lookup(PageContext pageContext, String name)  throws JspException 
    {
        return pageContext.findAttribute(name);
    }

    
    /**
     * Locate and return the specified property of the specified bean in the specified page context.
     *   
     */
    public static Object lookup(
        PageContext pageContext,
        String name,
        String property)
        throws JspException 
   {
        // Look up the requested bean, and return if requested
        Object bean = lookup(pageContext, name);
        if (bean == null) 
            throw new JspException("Lookup failed for bean named "+name);
        
        if (property == null) {
            return bean;
        }

        // Locate and return the specified property
        try {
            return PropertyUtils.getProperty(bean, property);
        } 
        catch (Exception e) {
            throw new JspException(e.getMessage(), e);
        }

    }
    
    /**
      * Get a cookie.
      */
     public static String getCookie(String sName, HttpServletRequest req) throws Exception 
     {
         Cookie[] cookies = req.getCookies();
         if (cookies == null)  return null;
         for (int i = 0; i < cookies.length; i++) {
             if (cookies[i].getName().equals(sName))
                 return StringFormat.unescape(cookies[i].getValue());
         }
         return null;
     }
 
}
