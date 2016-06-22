package com.saintdan.framework.tools;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Query helper.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 5/8/16
 * @since JDK1.8
 */
public class QueryHelper {

  /**
   * Get default sort.
   *
   * @return {@link Sort}
   */
  public static Sort getDefaultSort() {
    Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
    return new Sort(order);
  }

  /**
   * Get sort
   *
   * @param param     sort param
   * @param direction {@link Sort.Direction}
   * @return {@link Sort}
   */
  public static Sort getSort(String param, Sort.Direction direction) {
    Sort.Order order = new Sort.Order(direction, param);
    return new Sort(order);
  }

  /**
   * Get sort
   *
   * @param map sort map
   * @return {@link Sort}
   */
  public static Sort getSort(TreeMap<String, Sort.Direction> map) {
    List<Sort.Order> orderList = new ArrayList<>();
    for (Map.Entry<String, Sort.Direction> entry : map.entrySet()) {
      Sort.Order order = new Sort.Order(entry.getValue(), entry.getKey());
      orderList.add(order);
    }
    return new Sort(orderList);
  }
}
