package com.saintdan.framework.filter;

import com.saintdan.framework.constant.CommonsConstant;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
public class LimitFilter {

  public ResponseEntity limit(RequestLimit requestLimit) {
    String key = String
        .join(CommonsConstant.UNDERLINE, requestLimit.getIp(), requestLimit.getFunc(),
            requestLimit.getFlagField());
    if (!map.containsKey(key)) {
      map.put(key, new RequestCount(key, 1, System.currentTimeMillis()));
    } else {
      RequestCount requestCount = map.get(key);
      long frequency = (System.currentTimeMillis() - requestCount.getFirstReqAt()) / 1000;
      if (frequency > requestLimit.getRange()) {
        map.remove(key);
      } else {
        if (requestCount.getCount() >= requestLimit.getCount() && frequency <= requestLimit
            .getRange()) {
          return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS.value()).build();
        } else {
          requestCount.setCount(requestCount.getCount() + 1);
          map.remove(key);
          map.put(key, requestCount);
        }
      }
    }
    return null;
  }

  private HashMap<String, RequestCount> map = new HashMap<>();

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public class RequestLimit {

    private String ip;
    private String func;
    private String flagField;
    private long range;
    private int count;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private class RequestCount {

    private String key;
    private int count;
    private Long firstReqAt;
  }
}
