package com.saintdan.framework.tools;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring security utils.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/26/15
 * @since JDK1.8
 */
public class SpringSecurityUtils {

    /**
     * Get current user's IP address.
     *
     * @return      IP
     */
    public static String getCurrentUserIp() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return "";
        }
        Object details = authentication.getDetails();
        if (!(details instanceof WebAuthenticationDetails)) {
            return "";
        }
        WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
        return webDetails.getRemoteAddress();
    }

    /**
     * Save user details to security context.
     *
     * @param userDetails       user details
     * @param request           request
     */
    public static void saveUserDetailsToContext(UserDetails userDetails, HttpServletRequest request) {
        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDetails,
                userDetails.getPassword(), userDetails.getAuthorities());

        if (request != null) {
            authentication.setDetails(new WebAuthenticationDetails(request));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Get Authentication
     *
     * @return      authentication
     */
    private static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        return context.getAuthentication();
    }

}
