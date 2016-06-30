package com.saintdan.framework.tools;

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

  public static boolean checkContains(String[] array, String val) {
    for (String str : array) {
      if (str.equals(val)) {
        return true;
      }
    }
    return false;
  }
}
