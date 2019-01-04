package com.saintdan.framework.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error VO.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorVO implements Serializable {

  private static final long serialVersionUID = -7144407219523712074L;
  private int code;
  private String description;

  public ErrorVO(int code) {
    this.code = code;
  }
}
