package com.saintdan.framework.component;

import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.tools.LogUtils;
import com.saintdan.framework.vo.ErrorVO;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Log debug message and return result.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 9/22/15
 * @since JDK1.8
 */
@Component
public class ResultHelper {

  /**
   * Return success result.
   *
   * @param object some vo.
   * @return success response entity.
   */
  @SuppressWarnings("unchecked")
  public ResponseEntity successResp(Object object, HttpStatus httpStatus) {
    return new ResponseEntity(object, httpStatus);
  }

  /**
   * Return error information.
   *
   * @param errorType error type
   * @return response entity with information.
   */
  public ResponseEntity infoResp(ErrorType errorType, HttpStatus httpStatus) {
    return infoResp(errorType, errorType.description(), httpStatus);
  }

  /**
   * Return error information.
   *
   * @param errorType error type
   * @param msg       error message
   * @return response entity with information.
   */
  @SuppressWarnings("unchecked")
  public ResponseEntity infoResp(ErrorType errorType, String msg, HttpStatus httpStatus) {
    return new ResponseEntity(new ErrorVO(errorType.name(), msg), httpStatus);
  }

  /**
   * Return error information.
   *
   * @param logger Log
   * @param msg    error message
   * @return response entity with information.
   */
  @SuppressWarnings("unchecked")
  public ResponseEntity infoResp(Logger logger, ErrorType errorType, String msg,
      HttpStatus httpStatus) {
    LogUtils.trackInfo(logger, msg);
    return new ResponseEntity(new ErrorVO(errorType.name(), msg), httpStatus);
  }

  /**
   * Return error information, and log it to error.
   *
   * @param logger    Log
   * @param errorType error type
   * @param e         e
   * @return response entity with error message.
   */
  public ResponseEntity errorResp(Logger logger, Throwable e, ErrorType errorType,
      HttpStatus httpStatus) {
    return errorResp(logger, e, errorType, errorType.description(), httpStatus);
  }

  /**
   * Return error information, and log it to error.
   *
   * @param logger    Log
   * @param errorType error type
   * @param e         e
   * @param msg       error message
   * @return response entity with error message.
   */
  @SuppressWarnings("unchecked")
  public ResponseEntity errorResp(Logger logger, Throwable e, ErrorType errorType, String msg,
      HttpStatus httpStatus) {
    LogUtils.traceError(logger, e, errorType.description());
    return new ResponseEntity(new ErrorVO(errorType.name(), msg), httpStatus);
  }
}
