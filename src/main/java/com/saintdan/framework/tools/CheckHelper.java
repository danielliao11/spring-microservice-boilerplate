package com.saintdan.framework.tools;

import java.util.Arrays;
import java.util.List;

/**
 * Helper for checking.
 * <p>
 *   Array or list must be sorted.
 * </p>
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/28/16
 * @since JDK1.8
 */
public class CheckHelper {

  public static boolean checkContains(List list, Object val) {
    return Arrays.binarySearch(list.toArray(), val) > 0;
  }

  public static boolean checkContains(Object[] array, Object val) {
    return Arrays.binarySearch(array, val) > 0;
  }
}
