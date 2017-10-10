package com.saintdan.framework.exception;

import com.saintdan.framework.enums.ErrorType;

import java.io.Serializable;

/**
 * Abstract superclass for all exceptions related to an
 * {@link SystemException} object being invalid for whatever reason.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public abstract class SystemException extends Exception implements Serializable {

  private static final long serialVersionUID = 3327217472962035232L;

  /**
   * Error Type
   */
  private ErrorType errorType;

  /**
   * Constructs an {@code SystemException} with the specified message and root cause.
   *
   * @param type the {@link ErrorType}
   * @param t    the root cause
   * @param msg  the error message
   */
  public SystemException(ErrorType type, Throwable t, String msg) {
    super(msg, t);
    this.errorType = type;
  }

  /**
   * Constructs an {@code SystemException} with the specified message and root cause.
   *
   * @param type the {@link ErrorType}
   * @param msg  the error message
   */
  public SystemException(ErrorType type, String msg) {
    super(msg);
    this.errorType = type;
  }

  /**
   * Constructs an {@code SystemException} with the specified message and no root cause.
   *
   * @param type the {@link ErrorType}
   */
  public SystemException(ErrorType type) {
    super(type.name() + ": " + type.description());
    this.errorType = type;
  }

  public SystemException(String msg) {
    super(msg);
  }

  /**
   * Constructs an {@code SystemException}
   */
  public SystemException() {
    this(ErrorType.SYS0001);
  }


  private ErrorType obtainErrorType() {
    if (errorType == null) {
      return ErrorType.SYS0001;
    }
    return errorType;

  }

  /**
   * Get error type
   *
   * @return {@link ErrorType}
   */
  public ErrorType getErrorType() {
    return obtainErrorType();
  }

  /**
   * Get error code
   *
   * @return error code
   */
  public String getErrorCode() {
    return obtainErrorType().name();
  }

  /**
   * Get error msg
   *
   * @return error msg
   */
  public String getErrorMsg() {
    return obtainErrorType().description();
  }
}
