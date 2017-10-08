package com.saintdan.framework.vo;

import java.io.Serializable;
import lombok.Data;

/**
 * VO for {@link com.saintdan.framework.po.User}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/30/15
 * @since JDK1.8
 */
@Data public class UserVO implements Serializable {

  private static final long serialVersionUID = 6597728015488383528L;

  private Long id;

  private String name;

  private String usr;

  private String description;
}
