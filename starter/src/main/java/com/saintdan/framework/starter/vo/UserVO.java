package com.saintdan.framework.starter.vo;

import com.saintdan.framework.starter.po.User;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VO for {@link User}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/30/15
 * @since JDK1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {

  private static final long serialVersionUID = 6597728015488383528L;
  private Long id;
  private String name;
  private String usr;
  private String description;
}
