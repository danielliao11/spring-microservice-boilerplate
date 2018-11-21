package com.saintdan.framework.filter;

import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.servlet.RequestWrapper;
import com.saintdan.framework.tools.LogUtils;
import com.saintdan.framework.tools.RemoteAddressUtils;
import java.io.IOException;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Limit filter.
 * <pre>
 *   I use map as an cache in this case.
 *   You can also use redis.
 * </pre>
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 20/10/2017
 * @since JDK1.8
 */
@Component
@Order(1)
@WebFilter(filterName = "LimitFilter")
public class LimitFilter implements Filter {

  @Override public void init(FilterConfig filterConfig) {
    range = Long.valueOf(env.getProperty(rangeProp, rangeDefaultValue));
    count = Integer.valueOf(env.getProperty(countProp, countDefaultValue));
    LogUtils.trackInfo(logger, "Initiating LimitFilter");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      RequestWrapper req = new RequestWrapper((HttpServletRequest) request);
      if (!limit(
          new RequestLimit(
              RemoteAddressUtils.getRealIp(req),
              req.getRequestURI(),
              range,
              count))) {
        ((HttpServletResponse) response).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        return;
      }
      chain.doFilter(req, response);
    } else {
      ((HttpServletResponse) response).setStatus(HttpStatus.BAD_REQUEST.value());
    }
  }

  @Override public void destroy() {
    LogUtils.trackInfo(logger, "Destroying LimitFilter");
  }

  private boolean limit(RequestLimit requestLimit) {
    String key = String.join(CommonsConstant.UNDERLINE, requestLimit.getIp(), requestLimit.getPath());
    if (!map.containsKey(key)) {
      map.put(key, new RequestCount(key, 1, System.currentTimeMillis()));
    } else {
      RequestCount requestCount = map.get(key);
      long frequency = (System.currentTimeMillis() - requestCount.getFirstReqAt());
      if (frequency > requestLimit.getRange()) {
        map.remove(key);
      } else {
        if (requestCount.getCount() >= requestLimit.getCount() && frequency <= requestLimit
            .getRange()) {
          return false;
        } else {
          requestCount.setCount(requestCount.getCount() + 1);
          map.remove(key);
          map.put(key, requestCount);
        }
      }
    }
    return true;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private class RequestLimit {

    private String ip; // Request ip
    private String path; // Request resource's path
    private long range; // Millisecond
    private int count; // Request count
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private class RequestCount {

    private String key;
    private int count;
    private long firstReqAt;
  }

  private static final Logger logger = LoggerFactory.getLogger(LimitFilter.class);
  private HashMap<String, RequestCount> map = new HashMap<>();
  private long range = 0L;
  private int count = 0;
  private String rangeProp = "request.range";
  private String rangeDefaultValue = "10000";
  private String countProp = "request.count";
  private String countDefaultValue = "3";

  private final Environment env;

  @Resource private RedisTemplate<String, RequestCount> limitRedisTemplate;

  public LimitFilter(Environment env) {
    this.env = env;
  }
}
