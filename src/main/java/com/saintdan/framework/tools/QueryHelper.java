package com.saintdan.framework.tools;

import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.param.BaseParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * Query helper.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 5/8/16
 * @since JDK1.8
 */
public class QueryHelper {

  public static PageRequest getPageRequest(BaseParam param) {
    return PageRequest.of(param.getPageNo() == null ? 0 : param.getPageNo() - 1,
        param.getPageSize(), QueryHelper.getSort(param.getSortBy()));
  }

  /**
   * Get default {@link Sort}.
   *
   * @return {@link Sort}
   */
  public static Sort getDefaultSort() {
    return Sort.by(Direction.ASC, "id");
  }

  /**
   * Get {@link Sort}
   *
   * @param param     sort param
   * @param direction {@link Sort.Direction}
   * @return {@link Sort}
   */
  public static Sort getSort(String param, Sort.Direction direction) {
    return Sort.by(direction, param);
  }

  /**
   * Get {@link Sort}
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
    return Sort.by(orderList);
  }

  /**
   * Get {@link Sort}
   *
   * @param sortBy sortedBy
   * @return {@link Sort}
   */
  public static Sort getSort(String sortBy) {
    return StringUtils.isBlank(sortBy) ? getDefaultSort()
        : Sort.by(Arrays.stream(sortBy.split(CommonsConstant.COMMA)).map(
            (orders) -> getOrder(orders.split(CommonsConstant.COLON)))
            .collect(Collectors.toList()));
  }

  /**
   * Get {@link Sort.Order}
   *
   * @param orders orders
   * @return {@link Sort.Order}
   */
  private static Sort.Order getOrder(String[] orders) {
    return new Sort.Order(Sort.Direction.fromString(orders[1]), orders[0]);
  }
}
