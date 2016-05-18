package com.saintdan.framework.tools;

import org.springframework.data.domain.Sort;

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
     * @return      {@link Sort}
     */
    public static Sort getDefaultSort() {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        return new Sort(order);
    }

    /**
     * Get sort
     *
     * @param param         sort param
     * @param direction     {@link Sort.Direction}
     * @return              {@link Sort}
     */
    public static Sort getSort(String param, Sort.Direction direction) {
        Sort.Order order = new Sort.Order(direction, param);
        return new Sort(order);
    }
}
