package com.saintdan.framework.common.tools;

import com.saintdan.framework.common.constant.CommonsConstant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019-03-31
 * @since JDK1.8
 */
public class SpringContextUtils {

  public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

  public static void set(String key, Object value) {
    Map<String, Object> map = threadLocal.get();
    if (map == null) {
      map = new HashMap<>();
      threadLocal.set(map);
    }
    map.put(key, value);
  }

  public static Object get(String key) {
    Map<String, Object> map = threadLocal.get();
    if (map == null) {
      map = new HashMap<>();
      threadLocal.set(map);
    }
    return map.get(key);
  }

  public static void setUserID(String userID) {
    set(CommonsConstant.CURRENT_USER_ID, userID);
  }

  public static void setUsr(String usr) {
    set(CommonsConstant.CURRENT_USR, usr);
  }

  public static void setName(String name) {
    set(CommonsConstant.CURRENT_USER_NAME, name);
  }

  public static void setToken(String token) {
    set(CommonsConstant.CURRENT_USER_TOKEN, token);
  }

  public static String getUserID() {
    Object value = get(CommonsConstant.CURRENT_USER_ID);
    return returnValue(value);
  }

  public static String getUsr() {
    Object value = get(CommonsConstant.CURRENT_USR);
    return returnValue(value);
  }


  public static String getName() {
    Object value = get(CommonsConstant.CURRENT_USER_NAME);
    return returnValue(value);
  }

  public static String getToken() {
    Object value = get(CommonsConstant.CURRENT_USER_TOKEN);
    return returnValue(value);
  }

  private static String returnValue(Object value) {
    return value == null ? null : value.toString();
  }

  public static void remove() {
    threadLocal.remove();
  }
}
