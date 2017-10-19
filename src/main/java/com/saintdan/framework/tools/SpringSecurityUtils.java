package com.saintdan.framework.tools;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
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
   * @return IP
   */
  public static String getCurrentUserIp() {
    Authentication authentication = getAuthentication();
    if (authentication == null) {
      return "";
    }
    Object details = authentication.getDetails();
    if (details instanceof OAuth2AuthenticationDetails) {
      OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) details;
      return oAuth2AuthenticationDetails.getRemoteAddress();
    }
    if (details instanceof WebAuthenticationDetails) {
      WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
      return webDetails.getRemoteAddress();
    }
    return "";
  }

  /**
   * Get current user.
   *
   * @param <T> user details
   * @return user details
   */
  @SuppressWarnings("unchecked")
  public static <T extends UserDetails> T getCurrentUser() {
    Authentication authentication = getAuthentication();
    if (authentication == null) {
      return null;
    }
    Object principal = authentication.getPrincipal();
    if (!(principal instanceof UserDetails)) {
      return null;
    }
    return (T) principal;

  }

  /**
   * Get current username.
   *
   * @return current username
   */
  public static String getCurrentUsername() {
    Authentication authentication = getAuthentication();
    if ((authentication == null) || (authentication.getPrincipal() == null)) {
      return "";
    }
    return authentication.getName();
  }

  /**
   * Get current client id.
   *
   * @return current client id
   */
  public static String getCurrentClientId() {
    OAuth2Authentication authentication = (OAuth2Authentication) getAuthentication();
    assert authentication != null;
    return authentication.getOAuth2Request().getClientId();
  }

  /**
   * Save user details to security context.
   *
   * @param userDetails user details
   * @param request     request
   */
  public static void saveUserDetailsToContext(UserDetails userDetails, HttpServletRequest request) {
    PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
        userDetails,
        userDetails.getPassword(), userDetails.getAuthorities());

    if (request != null) {
      authentication.setDetails(new WebAuthenticationDetails(request));
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  /**
   * Get Authentication
   *
   * @return authentication
   */
  private static Authentication getAuthentication() {
    SecurityContext context = SecurityContextHolder.getContext();
    if (context == null) {
      return null;
    }
    return context.getAuthentication();
  }
}
