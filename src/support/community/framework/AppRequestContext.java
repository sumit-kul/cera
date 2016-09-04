package support.community.framework;

import java.util.*;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import support.community.application.*;

import org.acegisecurity.*;
import org.acegisecurity.context.*;
import org.acegisecurity.userdetails.*;
import org.apache.commons.logging.*;

import com.era.community.admin.dao.*;

/**
 * Generic application context object for each request that will be bound to the request
 * thread by an AppRequestContextHolder so that it is available to the rest of the application
 * code. Each application will typically subclass this class to provide and application-specific
 * context with more function.
 */
public class AppRequestContext
{
    protected Log logger = LogFactory.getLog(getClass());
    private BusinessParamFinder businessParamFinder;

    
    /**
     * Save a reference to the associated request object.
     */
    private HttpServletRequest request;    
   
    /**
     * Save a reference to the associated response object.
     */
    private HttpServletResponse response;     
    
    /**
     * Backlink stack.
     */
    private BacklinkStack backlinkStack;
    
    /**
     * Save these items when the context is created because the values returned by
     * methods on the request object will change when the request is forwarded to 
     * a JSP - they will then reflect the JSP and be useless.
     */
    private String requestUri;
    private String requestUrl;
    private String rootUrl;
    private String contextPath;
    private String contextUrl;
    
    /**
     * Protected constructor creating a context for the given request.
     */
    protected AppRequestContext() throws Exception
    {
    }    
    
    /**
     * Protected constructor creating a context for the given request.
     */
    public void init(HttpServletRequest req, HttpServletResponse resp) throws Exception
    {
        request = req;
        response = resp;
        
        requestUri = req.getRequestURI();
        
        Map<String, String> map = new HashMap<String, String>(1);
        map.put(BacklinkStack.PARAMETER_NAME, "");
        requestUrl = AppRequestUtils.getFullRequestUrl(req, map);

        contextPath = req.getContextPath();
        rootUrl = AppRequestUtils.serverURL(req).toString();
        contextUrl = rootUrl+contextPath;
        backlinkStack = createBacklinkStack(req);
    }
       
    /**
     * Get the Acegi UserDetails object associated with the current user or null
     * if the current user is not authenticated.
     */
    public UserDetails getCurrentUserDetails() throws Exception
    {
        SecurityContext sc = SecurityContextHolder.getContext();
        if (sc==null) return null;
        Authentication auth = sc.getAuthentication();
        if (auth==null) return null;
        Object user = auth.getPrincipal();
        if (user==null) return null;
        if (!(user instanceof UserDetails)) return null;
        return (UserDetails)user;
    }
    
    public String getTextParameter(String name) throws Exception
    {
        try {
            BusinessParam p= businessParamFinder.getParamForCategoryAndName(BusinessParam.CATEGORY_STATIC_TEXT, name);
            return p.getValue();
        }
        catch (ElementNotFoundException x) {
            return null;
        }
    }
    
    /**
     * Return true if the current user is authenticated.
     */
    public boolean isUserAuthenticated() throws Exception
    {
        return getCurrentUserDetails()!=null;
    }
    /**
     * Return true if the current user is a system administrator
     */
    public boolean isUserSysAdmin() throws Exception
    {
        return isCurrentUserInRole(RoleConstants.ROLE_SYS_ADMIN);
    }
    
    
     /**
     * Return true if the current user is a super administrator
     * This is a special role suport and maintenance purposes
     */
    public boolean isUserSuperAdmin() throws Exception
    {
        return isCurrentUserInRole(RoleConstants.ROLE_SUPER_ADMIN);
    }
    
    /**
     * Determine whether the current user is in a given role. 
     * @param role
     * @return
     */
    public boolean isCurrentUserInRole(String role) 
    {
        SecurityContext sc = SecurityContextHolder.getContext();
        if (sc==null) return false;
        Authentication auth = sc.getAuthentication();
        if (auth==null) return false;
        GrantedAuthority[] auths = auth.getAuthorities();
        for (int i=0; i<auths.length; i++) 
            if (role.equals(auths[i].getAuthority())) return true;
        return false;
    }

    public void exposeInView(String name, Object object) 
    {
        request.setAttribute(name, object);
    }
    
    private BacklinkStack createBacklinkStack(HttpServletRequest request) throws Exception
    {
        /*
         * Instantiate a backlink stack from backlink cookie data in the request.
         */
        BacklinkStack bstack = new BacklinkStack(request); 
        
        /*
         * Get the current request URL. Do NOT call AppRequestUtils methods here since
         * they can change the ordering of parameters in the query string.
         */
        String url = getRequestUrl(request);
        
        /*
         * If this URL is in the stack then remove it (and truncate the stack to that point).
         */
        bstack.prune(url);
        
        /*
         * If a backlink parameter is present in the request data then push it onto the stack. 
         * If the parameter has the value "ref" then user the referer header from the request.
         */
        String backlink = request.getParameter(BacklinkStack.PARAMETER_NAME);
        if (backlink!=null&&backlink.equals("ref")) backlink = request.getHeader("Referer");
        if (backlink!=null&&backlink.trim().length()>0) bstack.push(backlink); 
        
        /*
         * Return the backlink stack object. 
         */
        return bstack;
    }

    public String getBacklink() throws Exception
    {  
        return getBacklinkStack().top();
    }
    
    
    /**
     * Helper method to get the current request URL.
     */
    protected final String getRequestUrl(HttpServletRequest req) throws Exception
    {
        /*
         * Get the current request URL. Do NOT call AppRequestUtils methods here since
         * they can change the ordering of parameters in the query string.
         */
        StringBuffer buf = req.getRequestURL();
        String qs = req.getQueryString();
        if (qs!=null&&qs.trim().length()>0) buf.append("?" +qs);
        
        return buf.toString();
    }    

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
    public String computeUrl(Map<String, Object> params) throws JspException
    {
        String url = getFullRequestUrl();
        return AppRequestUtils.computeUrl(url, params);
    }
    
    /**
     *   
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getRequestParameters() throws JspException
    {
        String url = requestUrl;
        StringTokenizer tok = new StringTokenizer(url, "?");
        String href = tok.nextToken();
        String query = tok.hasMoreTokens() ? tok.nextToken() : null;
        return AppRequestUtils.parseQueryString(query);
    } 
        
    /**
     *   
     */
    public final String getFullRequestUrl() throws JspException
    {
        return requestUrl;
    } 
    
    /**
     *  Return the (true) context path of the request. 
     */
    public final String getContextPath()
    {
        return contextPath;
    }
    /**
     *  Return the (true) request URL without the query string. 
     */
    public final String getRequestUri()
    {
        return requestUri;
    }

    /**
     *  Return the (true) URL for the context path. 
     */
    public final String getContextUrl()
    {
        return contextUrl;
    }
    /**
     *  Return the "root" URL of the request including just the scheme, server and port. 
     */
    public final String getRootUrl()
    {
        return rootUrl;
    }
    /**
     *  Return the current request object. 
     */
    public final HttpServletRequest getRequest()
    {
        return request;
    }
    public final HttpServletResponse getResponse()
    {
        return response;
    }

    public final BacklinkStack getBacklinkStack()
    {
        return backlinkStack;
    }
    public final void setBacklinkStack(BacklinkStack backlinks)
    {
        this.backlinkStack = backlinks;
    }

    public final void setBusinessParamFinder(BusinessParamFinder businessParamFinder)
    {
        this.businessParamFinder = businessParamFinder;
    }
}
