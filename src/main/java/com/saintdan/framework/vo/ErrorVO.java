package com.saintdan.framework.vo;

import java.io.Serializable;
import lombok.Data;

/**
 * Result VO.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
@Data public class ErrorVO implements Serializable {

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
}
