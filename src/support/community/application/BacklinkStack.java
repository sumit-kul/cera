package support.community.application;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

/**
 * 
 * 
 */
public class BacklinkStack implements Serializable
{
    private Stack<String> m_stack = new Stack<String>();
    
    public static final String PARAMETER_NAME = "backlink"; 
    public static final String COOKIE_NAME = "IdkBacklinkStack"; 
    private static final String SEPARATOR = "@~@";
    
    public BacklinkStack(HttpServletRequest req)
    {
        String data = getCookieData(req);
        if (data==null) return;
        
        StringTokenizer tok = new StringTokenizer(data, SEPARATOR);
        while (tok.hasMoreTokens()) {
            String s = tok.nextToken();
            if (s.trim().length()>0) m_stack.addElement(s);
        }
        
    }
    
    public String top()
    {
        if (m_stack.isEmpty()) return null;
        return (String)m_stack.peek();
    }
    
    public void clear()
    {
        m_stack.clear();
    }
    
    public void push(String url) throws Exception
    {
        url = stripBacklinkParameter(url);
        if (url.equalsIgnoreCase(top())) return;
        if (m_stack.size()>5) m_stack.remove(0);
        m_stack.push(url);
    }

    public void prune(String url) throws Exception
    {
        url = stripBacklinkParameter(url);
        
        if (m_stack.isEmpty()) return;
        
        for (int n=0; n<m_stack.size(); n++) {
            
            String s = (String)m_stack.get(n);
            if (!congruentUrls(s, url)) continue;  
            
            while (m_stack.size()>n)
                m_stack.remove(n);
            
            break;
            
        }
        
    }
    
    public void storeToCookie(HttpServletRequest req,  HttpServletResponse resp) 
    {
        /*
           Get the cookie domain.
        */
        String sDomain = req.getServerName();
        int i = sDomain.indexOf('.');
        if (i > -1) sDomain = sDomain.substring(i);

        /*                                    
           Set data into a cookie.
        */
        Cookie ck = new Cookie(COOKIE_NAME, this.toString());
        ck.setDomain(sDomain);
        ck.setPath("/");
        ck.setMaxAge(-1);
        resp.addCookie(ck);
    }
    
    public String stripBacklinkParameter(String url) throws Exception
    {
        if (url.indexOf(PARAMETER_NAME)<0) return url;
        Map<String, String> params = new HashMap<String, String>(1);
        params.put(PARAMETER_NAME, "");
        return  AppRequestUtils.computeUrl(url, params);        
    }
    
    private String getCookieData(HttpServletRequest req)
    {
        Cookie[] a = req.getCookies();
        if (a==null) return null;
        for (int n=0; n<a.length; n++)
            if (a[n].getName().equals(COOKIE_NAME)) 
                return a[n].getValue();
        return null;
    }

    private boolean congruentUrls(String url1, String url2) throws Exception
    {
        if (url1==url2) return true;
        if (url1==null||url2==null) return false;
        
        if (url1.equals(url2)) return true;
        if (url1.length()!=url2.length()) return false;
        
        int i1 = url1.indexOf('?');
        int i2 = url2.indexOf('?');
        if (i1!=i2) return false;
        if (i1<0) return false;

        if (!url1.substring(0, i1).equals(url2.substring(0, i2))) return false;
        
        Map map1 = AppRequestUtils.parseUrlParameters(url1);
        Map map2 = AppRequestUtils.parseUrlParameters(url2);
        if (map1.size()!=map2.size()) return false;
        
        Iterator iter = map1.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry e = (Map.Entry)iter.next(); 
            String v2 = (String)map2.get(e.getKey());
            if (v2==null||!v2.equals(e.getValue())) return false; 
        }

        return true;
    }
    
    public String toString()
    {
        if (m_stack.isEmpty()) return SEPARATOR;
        
        StringBuffer buf = new StringBuffer(2048);
        for (int n=0; n<m_stack.size(); n++) {
            String s = (String)m_stack.get(n);
            buf.append(s);
            buf.append(SEPARATOR);
        }
        return buf.toString();
    }
    
}
