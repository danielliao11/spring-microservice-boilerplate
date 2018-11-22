package com.saintdan.framework.filter;

import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.enums.CacheType;
import com.saintdan.framework.param.RequestCount;
import com.saintdan.framework.servlet.RequestWrapper;
import com.saintdan.framework.tools.LogUtils;
import com.saintdan.framework.tools.RemoteAddressUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
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
    String rangeProp = "request.range";
    String defaultRange = "10000";
    range = Long.valueOf(env.getProperty(rangeProp, defaultRange));
    String countProp = "request.count";
    String defaultCount = "3";
    count = Integer.valueOf(env.getProperty(countProp, defaultCount));
    String typeProp = "request.type";
    String defaultType = "MAP";
    type = CacheType.valueOf(env.getProperty(typeProp, defaultType));
    LogUtils.trackInfo(logger, "Initiating LimitFilter with: " + type.name());
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
              count), type)) {
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

  private boolean limit(RequestLimit requestLimit, CacheType type) {
    if (type.isRedis()) {
      return limitWithRedis(requestLimit);
    } else {
      return limitWithMap(requestLimit);
    }
  }

  private boolean limitWithMap(RequestLimit requestLimit) {
    String key = String.join(CommonsConstant.UNDERLINE, requestLimit.getIp(), requestLimit.getPath());
    if (!map.containsKey(key)) {
      map.put(key, new RequestCount(key, 1));
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

  private boolean limitWithRedis(RequestLimit requestLimit) {
    String key = String.join(CommonsConstant.UNDERLINE, requestLimit.getIp(), requestLimit.getPath());
    if (!limitRedisTemplate.hasKey(key)) {
      limitRedisTemplate.opsForValue().set(key, new RequestCount(key, count), range, TimeUnit.MILLISECONDS);
    } else {
      RequestCount requestCount = limitRedisTemplate.opsForValue().get(key);
      long frequency = System.currentTimeMillis() - requestCount.getFirstReqAt();
      if (requestCount.getCount() >= requestLimit.count && frequency <= requestLimit.range) {
        return false;
      } else {
        requestCount.setCount(requestCount.getCount() + 1);
        limitRedisTemplate.opsForValue().set(key, requestCount);
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

  private static final Logger logger = LoggerFactory.getLogger(LimitFilter.class);
  private HashMap<String, RequestCount> map = new HashMap<>();
  private long range = 0L;
  private int count = 0;
  private CacheType type;

  private final Environment env;

  @Resource(name = "limitRedisTemplate") private RedisTemplate<String, RequestCount> limitRedisTemplate;

  public LimitFilter(Environment env) {
    this.env = env;
  }
}
