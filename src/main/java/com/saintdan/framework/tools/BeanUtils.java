package com.saintdan.framework.tools;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Bean utils
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/22/16
 * @since JDK1.8
 */
public class BeanUtils {

  /**
   * Bean properties copy ignore null.
   *
   * @param src    source
   * @param target target
   */
  public static void copyPropertiesIgnoreNull(Object src, Object target) {
    org.springframework.beans.BeanUtils.copyProperties(src, target, getNamesOfNullProperties(src));
  }

  /**
   * Get names of null properties
   *
   * @param source source object
   */
  private static String[] getNamesOfNullProperties(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<>();
    for (java.beans.PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null) {
        emptyNames.add(pd.getName());
      }
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

}
