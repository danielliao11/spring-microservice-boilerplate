package com.saintdan.framework.enums;

import com.saintdan.framework.constant.ResourcePath;
import com.saintdan.framework.constant.VersionConstant;
import com.saintdan.framework.param.LoginParam;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 20/10/2017
 * @since JDK1.8
 */
public enum ResourceUri {

  LOGIN(ResourcePath.RESOURCES + VersionConstant.V1 + ResourcePath.OPEN + ResourcePath.LOGIN,
      LoginParam.class),

  UNKNOWN("", null);

  private String uri;
  private Class clazz;

  private static final Map<String, ResourceUri> mappings = new HashMap<>(2);

  static {
    for (ResourceUri resourceUri : values()) {
      mappings.put(resourceUri.uri, resourceUri);
    }
  }

  ResourceUri(String uri, Class clazz) {
    this.uri = uri;
    this.clazz = clazz;
  }

  public String uri() {
    return uri;
  }

  public Class clazz() {
    return clazz;
  }

  public static ResourceUri resolve(String uri) {
    return (uri != null ? mappings.get(uri) : UNKNOWN);
  }

  public boolean matches(String uri) {
    return (this == resolve(uri));
  }

}
