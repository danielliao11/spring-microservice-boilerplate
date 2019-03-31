package com.saintdan.framework.starter.param;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2018/11/20
 * @since JDK1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestCount implements Serializable {

  private static final long serialVersionUID = -7842954563509005735L;

  private String key;

  @Builder.Default
  private int count = 0;

  private final long firstReqAt = System.currentTimeMillis();
}
