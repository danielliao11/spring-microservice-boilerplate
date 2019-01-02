package com.saintdan.framework.enums;

/**
 * Intent state interface.
 * <pre>
 *     String code;
 *     String description;
 * </pre>
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/29/15
 * @since JDK1.8
 */
public interface EnumWithCodeAndDescription {

  int code();

  String description();
}
