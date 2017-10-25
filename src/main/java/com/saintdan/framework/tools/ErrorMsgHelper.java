package com.saintdan.framework.tools;

import com.saintdan.framework.enums.ErrorType;

/**
 * Format the error msg.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/6/15
 * @since JDK1.8
 */
public class ErrorMsgHelper {

  /**
   * Get return error message.
   *
   * @param msg    error type
   * @param param1 args
   * @param param2 args
   * @return return error message
   */
  public static String getReturnMsg(ErrorType msg, String param1, String param2) {
    return param1 != null ? String.format(msg.description(), param1, param2) : msg.description();
  }

}
