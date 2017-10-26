package com.saintdan.framework.interceptor;

import com.saintdan.framework.component.LogHelper;
import com.saintdan.framework.constant.ResourcePath;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interceptor for log
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 23/10/2017
 * @since JDK1.8
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {}

  @Override public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    if (!(HttpMethod.GET.matches(request.getMethod())
        || HttpMethod.DELETE.matches(request.getMethod()))) {
      String path = request.getRequestURI();
      HttpStatus status = HttpStatus.resolve(response.getStatus());
      if (!path.contains(ResourcePath.OPEN) && status != null && !status.isError()) {
        logHelper.log(HttpMethod.resolve(request.getMethod()), path);
      }
    }
  }

  private final LogHelper logHelper;

  @Autowired public LogInterceptor(LogHelper logHelper) {
    this.logHelper = logHelper;
  }
}
