package com.saintdan.framework.vo;

import java.io.Serializable;

/**
 * Result VO.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
public class ErrorVO implements Serializable {

  private static final long serialVersionUID = -7144407219523712074L;

  private String error;

  private String error_description;

  public ErrorVO() {}

  public ErrorVO(String error) {
    this.error = error;
  }

  public ErrorVO(String error, String error_description) {
    this.error = error;
    this.error_description = error_description;
  }

  @Override public String toString() {
    final StringBuffer sb = new StringBuffer("ErrorVO{");
    sb.append("error='").append(error).append('\'');
    sb.append(", error_description='").append(error_description).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getError_description() {
    return error_description;
  }

  public void setError_description(String error_description) {
    this.error_description = error_description;
  }

}
