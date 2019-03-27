package com.saintdan.framework.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/3/27
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserParam extends BaseParam {

  private static final long serialVersionUID = 6369346639838812768L;

  private String id;
  private String name;
  private String usr;
  private String pwd;

  public UserParam(String usr) {
    this.usr = usr;
  }
}
