package com.saintdan.framework.starter.interceptor;

import com.saintdan.framework.common.constant.ResourcePath;
import com.saintdan.framework.common.tools.RemoteAddressUtils;
import com.saintdan.framework.common.tools.SpringContextUtils;
import com.saintdan.framework.common.tools.SpringSecurityUtils;
import com.saintdan.framework.starter.mapper.LogMapper;
import com.saintdan.framework.starter.po.Log;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Validate param and log operation.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 23/10/2017
 * @since JDK1.8
 */
@Component
public class CustomInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {}

  @Override public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    final String method = request.getMethod();
    if (!(HttpMethod.GET.matches(request.getMethod()) || HttpMethod.DELETE.matches(request.getMethod()))) {
      final String path = request.getRequestURI();
      HttpStatus status = HttpStatus.resolve(response.getStatus());
      if (!path.contains(ResourcePath.OPEN) && status != null && !status.isError()) {
        String userId = SpringContextUtils.getUserID();
        String usr = SpringContextUtils.getUsr();
        logMapper.insert(
            Log.builder()
                .ip(RemoteAddressUtils.getRealIp(request))
                .usr(userId == null ? "" : userId)
                .clientId(SpringSecurityUtils.getCurrentClientId())
                .path(path)
                .method(method)
                .createdBy(usr == null ? "0" : usr)
                .createdAt(System.currentTimeMillis())
                .build());
      }
    }
  }

  private final LogMapper logMapper;

  @Autowired
  public CustomInterceptor(LogMapper logMapper) {
    this.logMapper = logMapper;
  }
}
