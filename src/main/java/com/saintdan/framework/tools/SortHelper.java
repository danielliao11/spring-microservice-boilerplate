package com.saintdan.framework.tools;

import com.saintdan.framework.constant.CommonsConstant;
import java.util.Arrays;

/**
 * Helper for sort.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/29/16
 * @since JDK1.8
 */
public class SortHelper {

  public static String sort(String array) {
    final String SPACE = " ";
    final String LEFT = "[";
    final String RIGHT = "]";
    final String BLANK = "";
    return Arrays.toString(Arrays.asList(array.split(CommonsConstant.COMMA))
        .stream().mapToInt(Integer::valueOf).sorted().toArray())
        .replace(SPACE, BLANK).replace(LEFT, BLANK).replace(RIGHT, BLANK);
  }

}
