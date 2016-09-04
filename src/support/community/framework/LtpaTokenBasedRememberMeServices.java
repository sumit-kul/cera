package support.community.framework;

import support.community.application.*;

import org.acegisecurity.Authentication;

import org.acegisecurity.providers.rememberme.RememberMeAuthenticationToken;

import org.acegisecurity.ui.*;
import org.acegisecurity.ui.logout.*;
import org.acegisecurity.ui.rememberme.*;

import org.acegisecurity.userdetails.*;
import org.apache.commons.logging.*;

import org.springframework.beans.factory.InitializingBean;

import org.springframework.util.*;
import org.springframework.web.bind.*;

import javax.servlet.http.*;


/**
 * Identifies previously remembered users by a Base-64 encoded cookie.<p>This implementation does not rely on an
 * external database, so is attractive for simple applications. The cookie will be valid for a specific period from
 * the date of the last {@link #loginSuccess(HttpServletRequest, HttpServletResponse, Authentication)}. As per the
 * interface contract, this method will only be called when the principal completes a successful interactive
 * authentication. As such the time period commences from the last authentication attempt where they furnished
 * credentials - not the time period they last logged in via remember-me. The implementation will only send a
 * remember-me token if the parameter defined by {@link #setParameter(String)} is present.</p>
 *  <p>An {@link org.acegisecurity.userdetails.UserDetailsService} is required by this implementation, so that it
 * can construct a valid <code>Authentication</code> from the returned {@link
 * org.acegisecurity.userdetails.UserDetails}. This is also necessary so that the user's password is available and can
 * be checked as part of the encoded cookie.</p>
 *  <p>The cookie encoded by this implementation adopts the following form:</p>
 *  <p><code> username + ":" + expiryTime + ":" + Md5Hex(username + ":" + expiryTime + ":" + password + ":" + key)
 * </code>.</p>
 *  <p>As such, if the user changes their password any remember-me token will be invalidated. Equally, the system
 * administrator may invalidate every remember-me token on issue by changing the key. This provides some reasonable
 * approaches to recovering from a remember-me token being left on a public machine (eg kiosk system, Internet cafe
 * etc). Most importantly, at no time is the user's password ever sent to the user agent, providing an important
 * security safeguard. Unfortunately the username is necessary in this implementation (as we do not want to rely on a
 * database for remember-me services) and as such high security applications should be aware of this occasionally
 * undesired disclosure of a valid username.</p>
 *  <p>This is a basic remember-me implementation which is suitable for many applications. However, we recommend a
 * database-based implementation if you require a more secure remember-me approach.</p>
 *  <p>By default the tokens will be valid for 14 days from the last successful authentication attempt. This can be
 * changed using {@link #setTokenValiditySeconds(long)}.</p>
 *
 */
public class LtpaTokenBasedRememberMeServices implements RememberMeServices, InitializingBean, LogoutHandler 
{
    //~ Static fields/initializers =====================================================================================

    public static final String DEFAULT_PARAMETER = "_acegi_security_remember_me";
    protected static final Log logger = LogFactory.getLog(LtpaTokenBasedRememberMeServices.class);

    //~ Instance fields ================================================================================================

    private AuthenticationDetailsSource authenticationDetailsSource = new AuthenticationDetailsSourceImpl();
    private String key;
    private String parameter = DEFAULT_PARAMETER;
    private UserDetailsService userDetailsService;
    private int tokenValiditySeconds = 10*60*60; //10 hours 
    private boolean alwaysRemember = false;
    private String cookieDomain = null;
    private String cookiePath = null;
 
    //~ Methods ========================================================================================================

    public void afterPropertiesSet() throws Exception 
    {
        Assert.hasLength(key);
        Assert.hasLength(parameter);
        Assert.notNull(userDetailsService);
    }

    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) 
    {
        DominoLtpaToken token = new DominoLtpaToken(key, request);
        if (!token.isValid()) return null;
        
        // By this stage we have a valid token
        if (logger.isDebugEnabled()) {
            logger.debug("Remember-me cookie found for "+token.getName());
        }
        
        UserDetails user;
        try {
            user = userDetailsService.loadUserByUsername(token.getName());
        }
        catch (UsernameNotFoundException x) {
            logger.error("User ["+token.getName()+"] found in remember-me cookie not found in directory");
            return null;
        }

        RememberMeAuthenticationToken auth = new RememberMeAuthenticationToken(this.key, user, user.getAuthorities());
        auth.setDetails(authenticationDetailsSource.buildDetails(request));

        return auth;
    }

    private void cancelCookie(HttpServletRequest request, HttpServletResponse response, String reasonForLog) 
    {
        if ((reasonForLog != null) && logger.isDebugEnabled()) {
            logger.debug("Cancelling cookie for reason: " + reasonForLog);
        }
        DominoLtpaToken.clearCookie(request, response, cookieDomain, cookiePath);
    }

    public String getKey() {
        return key;
    }

    public String getParameter() {
        return parameter;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void loginFail(HttpServletRequest request, HttpServletResponse response) 
    {
        cancelCookie(request, response, "Interactive authentication attempt was unsuccessful");
    }

    protected boolean rememberMeRequested(HttpServletRequest request, String param) 
    {
        if (alwaysRemember) return true;
        return  ServletRequestUtils.getBooleanParameter(request, param, false);
    }
    
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) 
    {
        // Exit if remembering is not enabled
        if (!rememberMeRequested(request, parameter)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Did not send remember-me cookie (principal did not set parameter '" + this.parameter+ "')");
            }
            return;
        }

        // Determine username and password, ensuring empty strings
        Assert.notNull(successfulAuthentication.getPrincipal());
        Assert.notNull(successfulAuthentication.getCredentials());

        String username;
        if (successfulAuthentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) successfulAuthentication.getPrincipal()).getUsername();
        } else {
            username = successfulAuthentication.getPrincipal().toString();
        }
        Assert.hasLength(username);

        // construct token to put in cookie
        DominoLtpaToken token = new DominoLtpaToken(key, username, tokenValiditySeconds);
        token.setSessionCookie(request, response, cookieDomain, cookiePath);

        if (logger.isDebugEnabled()) {
            logger.debug("Added remember-me cookie for user '" + username);
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
    {
        cancelCookie(request, response, "Logout of user " + (authentication==null?"[null]":authentication.getName()));
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource authenticationDetailsSource) 
    {
        Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public void setKey(String key) 
    {
        this.key = key;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public boolean isAlwaysRemember() {
        return alwaysRemember;
    }

    public void setAlwaysRemember(boolean alwaysRemember) {
        this.alwaysRemember = alwaysRemember;
    }

    public final String getCookieDomain()
    {
        return cookieDomain;
    }

    public final void setCookieDomain(String cookieDomain)
    {
        this.cookieDomain = cookieDomain;
    }

    public final String getCookiePath()
    {
        return cookiePath;
    }

    public final void setCookiePath(String cookiePath)
    {
        this.cookiePath = cookiePath;
    }

    public final int getTokenValiditySeconds()
    {
        return tokenValiditySeconds;
    }

    public final void setTokenValiditySeconds(int tokenValiditySeconds)
    {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }
}
