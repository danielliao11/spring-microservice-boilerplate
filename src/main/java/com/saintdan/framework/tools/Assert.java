package com.saintdan.framework.tools;

/**
 * Custom assert tool.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 02/05/2017
 * @since JDK1.8
 */
public class Assert {

  public static void defaultNotNull(Object obj) {
    final String IS_NULL = " is null";
    org.springframework.util.Assert.notNull(obj, obj.getClass().getSimpleName() + IS_NULL);
  }
}
