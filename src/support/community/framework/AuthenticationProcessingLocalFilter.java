package support.community.framework;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.event.authentication.InteractiveAuthenticationSuccessEvent;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.rememberme.RememberMeServices;
import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;


/**
 * 
 */
public class AuthenticationProcessingLocalFilter extends AuthenticationProcessingFilter
{
	private RememberMeServices rememberMeServices;
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success: " + authResult.toString());
		}

		SecurityContextHolder.getContext().setAuthentication(authResult);

		if (logger.isDebugEnabled()) {

			logger.debug("Updated SecurityContextHolder to contain the following Authentication: '" + authResult + "'");
		}

		// Don't attempt to obtain the url from the saved request if alwaysUsedefaultTargetUrl is set
		String targetUrl = null;

		HttpSession session = request.getSession(false);
		SavedRequest savedRequest = null; 
		if (session != null) {
			savedRequest = (SavedRequest) session.getAttribute(AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY);
		}
		if (savedRequest != null) {
			targetUrl = savedRequest.getFullRequestUrl();
		}

		if (targetUrl == null) {
			String reqUrl = (String) request.getSession().getAttribute("url_prior_login");
			if (reqUrl != null || "".equals(reqUrl)) {
				targetUrl = reqUrl;
			}
		}

		if (targetUrl == null|| "".equals(targetUrl)) {
			targetUrl = getDefaultTargetUrl();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Redirecting to target URL from HTTP Session (or default): " + targetUrl);
		}
		onSuccessfulAuthentication(request, response, authResult);
		rememberMeServices.loginSuccess(request, response, authResult);

		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
		}
		sendRedirect(request, response, targetUrl);

	}
	/**
	 * @param rememberMeServices the rememberMeServices to set
	 */
	public void setRememberMeServices(RememberMeServices rememberMeServices) {
		this.rememberMeServices = rememberMeServices;
	}
}
